import { useEffect, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseContext,
  ExerciseTypeToString,
} from "../../objects/Exercise/Exercise";
import { Test, ExerciseGroup } from "../../objects/Test/Test";

function renderExercise(
  exercise: Exercise,
  index: number,
  groupIndex: number,
  exerciseSelected: number,
  setSelectedExercise: Function,
  setShowExID: Function
) {
  return (
    <div
      key={index}
      className=" rounded-lg w-full p-4 bg-3-2 z-20 cursor-pointer"
      onClick={(e) => {
        setSelectedExercise(index == exerciseSelected ? -1 : index);
        setShowExID(index == exerciseSelected ? "" : exercise.identity.id);
        e.stopPropagation();
      }}
    >
      <div className="flex justify-between text">
        <label className="text-md font-medium">
          Exercício {index + 1} - {ExerciseTypeToString(exercise.type)}
        </label>
        <p>Cotação do Exercício: {exercise.identity.points}</p>
      </div>
      {index == exerciseSelected ? (
        <ExerciseComponent
          exercise={exercise}
          context={{ context: ExerciseContext.PREVIEW }}
          position={groupIndex + 1 + "." + (index + 1)}
        ></ExerciseComponent>
      ) : (
        <></>
      )}
    </div>
  );
}

function renderGroup(
  group: ExerciseGroup,
  index: number,
  selectedEx: number,
  setSelectedEx: Function,
  selectedGroup: number,
  setSelectedGroup: Function,
  setShowExID: Function
) {
  return (
    <div
      key={index}
      className="rounded-lg w-full p-4 bg-3-1 cursor-pointer"
      onClick={() => {
        setSelectedGroup(index == selectedGroup ? -1 : index);
        setSelectedEx(-1);
        setShowExID("");
      }}
    >
      <div className="flex justify-between z-10">
        <label className="text-xl font-medium">Grupo {index + 1}</label>
        <p>Cotação do Grupo: {group.groupPoints}</p>
      </div>
      {selectedGroup == index ? (
        <div>
          <h2 className="py-3">{group.groupInstructions}</h2>
          <div className="space-y-4">
            {group.exercises.map((exercise, exId) => {
              return renderExercise(
                exercise,
                exId,
                index,
                selectedEx,
                setSelectedEx,
                setShowExID
              );
            })}
          </div>
        </div>
      ) : (
        <></>
      )}
    </div>
  );
}

export interface TestPreviewProps {
  test: Test;
  showExId: string;
  setShowExID: Function;
}

function findId(id: string, test: Test) {
  for (let i = 0; i < test.groups.length; i++) {
    for (let j = 0; j < test.groups[i].exercises.length; j++) {
      if (test.groups[i].exercises[j].identity.id === id)
        return { group: i, ex: j };
    }
  }
  return { group: -1, ex: -1 };
}

export function TestPreview({ test, showExId, setShowExID }: TestPreviewProps) {
  const [selectedGroup, setSelectedGroup] = useState(-1);
  const [selectedEx, setSelectedEx] = useState(-1);

  useEffect(() => {
    //Queria mostrar este exercicio
    if (showExId !== "") {
      let { group, ex } = findId(showExId, test);
      if (group != selectedGroup) {
        setSelectedGroup(group);
      }
      if (ex != selectedEx) setSelectedEx(ex);
    }
  }, [showExId]);

  return (
    <>
      <div className=" w-full justify-between mt-2 px-4 pb-6 mb-3">
        <div className="flex flex-col">
          <div className="ml-4 mt-4">
            <h2 className="text-xl">Informações Gerais do Teste:</h2>
            <div className="text-md ml-4">
              <h3>
                <strong>Instruções do Teste:</strong>
                {test.globalInstructions}
              </h3>
              <h3>
                <strong>Autor:</strong> {test.author}
              </h3>
              <h3>
                <strong>Data de criação do teste:</strong>
                {test.creationDate}
              </h3>
              <h3>
                <strong>Cotação máxima do teste:</strong>
                {test.globalPoints}
              </h3>
            </div>
          </div>
        </div>

        <div className="space-y-4 mt-4">
          <h2 className="text-xl ml-4">Exercícios:</h2>

          {test.groups.map((group, index) => {
            return renderGroup(
              group,
              index,
              selectedEx,
              setSelectedEx,
              selectedGroup,
              setSelectedGroup,
              setShowExID
            );
          })}
        </div>
      </div>
    </>
  );
}
