import { useState } from "react";

const basictest = {
  type: "basic",
  conclusion: "",
  author: "JCR",
  title: "Teste de Avaliação de Geografia",
  creationDate: "22/08/2023",
  globalCotation: 20.0,
  globalInstructions: "Teste muito dificil. Tais todos fodidos!",
  groups: [
    {
      exercises: [
        {
          id: "11111",
          cotation: 1,
          statement: {
            imagePath: "",
            imagePosition: "",
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: [
            { id: 1, text: "França", type: "string" },
            { id: 2, text: "Espanha", type: "string" },
            { id: 3, text: "Argentina", type: "string" },
            { id: 4, text: "Setúbal", type: "string" },
          ],
          type: "mc",
          mctype: "10",
        },
        {
          id: "22222",
          cotation: 1,
          statement: {
            imagePath: "",
            imagePosition: "",
            text: "Qual o país que faz fronteira com Inglaterra?",
          },
          title: "Vizinhos",
          items: [
            { id: 1, text: "França", type: "string" },
            { id: 2, text: "Espanha", type: "string" },
            { id: 3, text: "Irlanda", type: "string" },
            { id: 4, text: "Setúbal", type: "string" },
          ],
          type: "mc",
          mctype: "13",
        },
      ],
      groupCotation: 3,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
  ],
};

function CountExercises(test: any) {
  let result: number = 0;
  test.groups.map((group: any) => {
    result += group.exercises.length;
  });
  return result;
}

function SolveTestLanding(props: any) {
  let nExercises = CountExercises(props.test);
  return (
    <>
      <div className="space-y-12 w-full">
        <div className="space-y-2">
          <p className="text-xl">
            <strong>Título:</strong> {props.test.title}
          </p>
          <p className="text-xl">
            <strong>Autor:</strong> {props.test.author}
          </p>
          <p className="text-xl">
            <strong>Data:</strong> {props.test.creationDate}
          </p>
          <div className="text-xl flex space-x-4">
            <strong>Tópicos:</strong>{" "}
            <p className=" border border-gray-400 rounded-lg "> RPCW</p>
          </div>
        </div>

        <div className="w-full">
          <h2 className=" font-bold text-md">Instruções básicas do teste:</h2>

          <p className="w-full border-2 rounded-lg border-gray-200 p-4">
            {props.test.globalInstructions}
          </p>
        </div>
        <p>
          <strong>Número de exercicios:</strong> {nExercises}
        </p>

        <button
          className="absolute right-4 bottom-4 p-4 rounded-lg bg-blue-300"
          onClick={() => props.startTest(true)}
        >
          Começar o teste {"->"}
        </button>
        <p className="absolute right-4 top-0 text-2xl">
          Cotação máxima: {props.test.globalCotation} valores
        </p>
      </div>
    </>
  );
}

function SolveTestExercise({ test, endTest }: any) {
  const [currentGroup, setCurrentGroup] = useState(1);
  const [currentEx, setCurrentEx] = useState(1);
  let groupData = test.groups[currentGroup - 1];
  let exerciseData = groupData.exercises[currentEx - 1];

  return (
    <>
      <div className="w-full relative border-b-2 pb-4">
        <div className="space-y-4">
          <h1 className="text-2xl font-bold">Grupo {currentGroup}</h1>

          <h2 className="ml-5 text-justify">{groupData.groupInstructions}</h2>
        </div>
        <p className="absolute right-4 top-0 text-xl ">
          Cotação do Grupo: {groupData.groupCotation} valores
        </p>
      </div>

      <div className=" relative ml-8">
        <h1 className="text-2xl font-bold mt-4">
          Exercício {currentGroup}.{currentEx}
        </h1>
        <p className="absolute right-4 top-0 text-xl ">
          Cotação do Exercício: {exerciseData.cotation} valores
        </p>
      </div>
      <div className="absolute right-4 bottom-4 space-x-5">
        {currentGroup < test.groups.length ? (
          <button
            type="button"
            className="p-4 rounded-lg bg-blue-300"
            onClick={() => {
              setCurrentGroup(currentGroup + 1);
              setCurrentEx(1);
            }}
          >
            Próximo Grupo
          </button>
        ) : (
          <></>
        )}
        {currentEx < groupData.exercises.length ? (
          <button
            type="button"
            className="p-4 rounded-lg bg-blue-300"
            onClick={() => setCurrentEx(currentEx + 1)}
          >
            Próximo Exercício
          </button>
        ) : (
          <></>
        )}
        {currentEx == groupData.exercises.length &&
        currentGroup == test.groups.length ? (
          <button
            type="button"
            className="p-4 rounded-lg bg-blue-300"
            onClick={() => {
              endTest(true);
            }}
          >
            Finalizar Teste
          </button>
        ) : (
          <></>
        )}
      </div>
    </>
  );
}

function SolveTestEnd({ test }: any) {
  return (
    <>
      <p className="text-2xl m-20">
        <strong>Completaste o teu teste!</strong>
      </p>
      <p className="ml-20 ">
        Verifica na página das avaliações a tua performance!
      </p>
    </>
  );
}

export function SolveTest() {
  const [started, startTest] = useState(false);
  const [ended, endTest] = useState(false);
  const [currentEx, setCurrentEx] = useState(1);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 h-screen">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2 mt-10">
          <label className="flex text-title-1">Teste de Avaliação</label>
        </div>
        <div className="flex rounded-lg bg-1-1 h-[38rem]">
          <div className="m-7 relative w-full ">
            {!started ? (
              <SolveTestLanding
                test={basictest}
                startTest={startTest}
              ></SolveTestLanding>
            ) : (
              <>
                {!ended ? (
                  <SolveTestExercise
                    test={basictest}
                    endTest={endTest}
                  ></SolveTestExercise>
                ) : (
                  <SolveTestEnd test={basictest}></SolveTestEnd>
                )}
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
