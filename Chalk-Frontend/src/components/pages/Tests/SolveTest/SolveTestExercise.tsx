import { useState, useContext } from "react";
import {
  SolveProps,
  ExerciseContext,
  Resolution,
  ExerciseComponent,
  TranslateResolutionOUT,
} from "../../../objects/Exercise/Exercise";
import { SolveTestContext } from "./SolveTest";
import { Test } from "../../../objects/Test/Test";
import { APIContext } from "../../../../APIContext";
import { UserContext } from "../../../../UserContext";
import ConfirmButton from "../../../interactiveElements/ConfirmButton";
export function Progress(
  test: Test,
  currentGroup: number,
  currentEx: number,
  nExercises: number
) {
  test.groups.map((value, index) => {
    if (index < currentGroup - 1) currentEx += value.exercises.length;
  });

  return (currentEx / nExercises) * 100;
}

function PreviousExerciseButton(
  currentEx: number,
  currentGroup: number,
  nlstGroupExercises: number,
  setCurrentEx: (ex: number) => void,
  setCurrentGroup: (gr: number) => void,
  sendResolution: () => Promise<any>
) {
  if (currentEx > 1 || currentGroup > 1) {
    return (
      <button
        type="button"
        className={
          "p-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black font-bold text-lg "
        }
        onClick={() => {
          sendResolution().then(() => {
            if (currentGroup > 1 && currentEx == 1) {
              setCurrentEx(nlstGroupExercises);
              setCurrentGroup(currentGroup - 1);
            } else setCurrentEx(currentEx - 1);
          });
        }}
      >
        Exercício Anterior
      </button>
    );
  } else return <></>;
}

function NextExerciseButton(
  currentEx: number,
  currentGroup: number,
  nGroups: number,
  nGroupExercises: number,
  setCurrentEx: (ex: number) => void,
  setCurrentGroup: (gr: number) => void,
  sendResolution: () => Promise<any>
) {
  if (currentEx < nGroupExercises || currentGroup < nGroups) {
    return (
      <button
        type="button"
        className={
          "p-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black font-bold text-lg "
        }
        onClick={() => {
          sendResolution().then(() => {
            if (currentGroup < nGroups && currentEx == nGroupExercises) {
              setCurrentEx(1);
              setCurrentGroup(currentGroup + 1);
            } else setCurrentEx(currentEx + 1);
          });
        }}
      >
        Próximo Exercício
      </button>
    );
  } else return <></>;
}

export function SolveTestExercise({ endTest, resolutionID }: any) {
  const [currentGroup, setCurrentGroup] = useState(1);
  const { contactBACK } = useContext(APIContext);
  const { user } = useContext(UserContext);
  const [currentEx, setCurrentEx] = useState(1);
  const { test, resolutions, setExerciseSolution, nExercises } =
    useContext(SolveTestContext);

  const groupData = test.groups[currentGroup - 1];
  const exerciseData = groupData.exercises[currentEx - 1];

  const exerciseContext: SolveProps = {
    context: ExerciseContext.SOLVE,
    resolutionData: resolutions[currentGroup - 1][currentEx - 1],
    setExerciseSolution: (resolution: Resolution) => {
      setExerciseSolution(currentGroup, currentEx, resolution);
    },
  };

  const sendResolution = () => {
    return contactBACK(
      "tests/resolutions/" + resolutionID + "/exercise",
      "PUT",
      undefined,
      {
        data: TranslateResolutionOUT(
          resolutions[currentGroup - 1][currentEx - 1]
        ),
        exerciseId: exerciseData.identity.id,
        studentId: user.user?.id,
      },
      "none"
    );
  };

  const endTestResolution = () => {
    return contactBACK(
      "tests/resolutions/" + resolutionID + "/submit",
      "PUT",
      undefined,
      undefined,
      "none"
    );
  };

  return (
    <>
      <div className="mb-6 ">
        <div className="flex justify-between mb-1"></div>
        <div className=" w-full rounded-full h-2.5 bg-[#dddddd] dark:bg-gray-700">
          <div
            className="bg-[#ffd025] h-2.5 rounded-full"
            style={{
              width: Progress(test, currentGroup, currentEx, nExercises) + "%",
            }}
          ></div>
        </div>
      </div>

      <div className="w-full relative border-b-2 pb-4 dark:text-white">
        <div className="space-y-4">
          <h1 className="text-2xl font-bold">
            Grupo {currentGroup}{" "}
            <div className="float-right space-x-5 dark:text-white">
              {PreviousExerciseButton(
                currentEx,
                currentGroup,
                currentGroup - 2 >= 0
                  ? test.groups[currentGroup - 2].exercises.length
                  : 0,
                setCurrentEx,
                setCurrentGroup,
                sendResolution
              )}
              {NextExerciseButton(
                currentEx,
                currentGroup,
                test.groups.length,
                groupData.exercises.length,
                setCurrentEx,
                setCurrentGroup,
                sendResolution
              )}
            </div>
          </h1>

          <h2 className="ml-5 text-justify">{groupData.groupInstructions}</h2>
        </div>
        <p className="absolute right-4 top-0 text-xl ">
          Cotação do Grupo: {groupData.groupPoints} valores
        </p>
      </div>

      <div className=" relative ml-8 dark:text-white">
        <p className="absolute right-4 top-3 text-xl ">
          Cotação do Exercício: {exerciseData.identity.points} valores
        </p>
      </div>
      <div className="mb-10">
        <ExerciseComponent
          context={exerciseContext}
          exercise={exerciseData}
          position={currentGroup + "." + currentEx}
        />
      </div>
      <div className="absolute right-4 bottom-4 space-x-5 dark:text-white ">
        {PreviousExerciseButton(
          currentEx,
          currentGroup,
          currentGroup - 2 >= 0
            ? test.groups[currentGroup - 2].exercises.length
            : 0,
          setCurrentEx,
          setCurrentGroup,
          sendResolution
        )}

        {NextExerciseButton(
          currentEx,
          currentGroup,
          test.groups.length,
          groupData.exercises.length,
          setCurrentEx,
          setCurrentGroup,
          sendResolution
        )}

        <ConfirmButton
        confirmationMessage="Tem acerteza que deseja finalizar o teste?"
        onConfirm={() => {
          sendResolution().then(() => {
            endTestResolution().then(() => {
              endTest(true);
            });
          });
        }}
        button={<button
          type="button"
          className="p-4 rounded-lg bg-red-800 hover:scale-110 transition-all duration-100 ease-in-out text-white"
        >
          Finalizar Teste
        </button>}>
        </ConfirmButton>
      </div>
    </>
  );
}
