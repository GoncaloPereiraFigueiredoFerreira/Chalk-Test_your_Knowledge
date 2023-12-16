import { useEffect, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseContext,
  ExerciseGroup,
  ExerciseJustificationKind,
  ExerciseType,
} from "../../objects/Exercise/Exercise";
import { Test } from "./SolveTest/SolveTest";
import { Link } from "react-router-dom";

const basictest: Test = {
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
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.OPEN_ANSWER,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "22222",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com França?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "Portugal" },
            2: { text: "Polo norte" },
            3: { text: "Pigs" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "3333",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          justifyKind: ExerciseJustificationKind.JUSTIFY_FALSE,
        },
      ],
      groupCotation: 3,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
    {
      exercises: [
        {
          id: "11111",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.OPEN_ANSWER,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "22222",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com França?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "Portugal" },
            2: { text: "Polo norte" },
            3: { text: "Pigs" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.OPEN_ANSWER,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "3333",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.MULTIPLE_CHOICE,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
      ],
      groupCotation: 16,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
    {
      exercises: [
        {
          id: "11111",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.OPEN_ANSWER,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "22222",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com França?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "Portugal" },
            2: { text: "Polo norte" },
            3: { text: "Pigs" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.OPEN_ANSWER,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        {
          id: "3333",
          specialistId: "JCR",
          cotation: 1,
          visibility: "public",
          statement: {
            text: "Qual o país que faz fronteira com Portugal?",
          },
          title: "Vizinhos",
          items: {
            1: { text: "França" },
            2: { text: "Espanha" },
            3: { text: "Argentina" },
            4: { text: "Setúbal" },
          },
          type: ExerciseType.MULTIPLE_CHOICE,
          justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
      ],
      groupCotation: 1,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
  ],
};

function renderExercise(exercise: Exercise, index: number, groupIndex: number) {
  return (
    <>
      <div key={index} className=" rounded-lg w-full p-4 bg-3-2">
        <div className="flex justify-between">
          <label className="text-md font-medium">Exercício {index}</label>
          <p>Cotação do Exercício: {exercise.cotation}</p>
        </div>
        <ExerciseComponent
          exercise={exercise}
          context={{ context: ExerciseContext.PREVIEW }}
          position={groupIndex + "." + index}
        ></ExerciseComponent>
      </div>
    </>
  );
}

function renderGroup(group: ExerciseGroup, index: number) {
  return (
    <>
      <div key={index} className=" rounded-lg w-full p-4 bg-3-1">
        <div className="flex justify-between mb-4">
          <label className="text-xl font-medium">Grupo {index}</label>
          <p>Cotação do Grupo: {group.groupCotation}</p>
        </div>
        <div>
          <h2>{group.groupInstructions}</h2>
          <div className="space-y-4">
            {group.exercises.map((exercise, exId) => {
              return renderExercise(exercise, exId + 1, index + 1);
            })}
          </div>
        </div>
      </div>
    </>
  );
}

export function TestPreview() {
  const [test, setTest] = useState<Test>();
  useEffect(() => {
    setTest(basictest);
  }, []);
  return (
    <>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8">
          <div className="flex  w-full justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <div className="flex flex-col">
              <label className=" text-title-1">
                Pré-visualizar: {test?.title}
              </label>
              <div className="ml-4 mt-4">
                <h2 className="text-xl">Informações Gerais do Teste:</h2>
                <div className="text-md ml-4">
                  <h3>
                    <strong>Instruções do Teste:</strong>
                    {test?.globalInstructions}
                  </h3>
                  <h3>
                    <strong>Autor:</strong> {test?.author}
                  </h3>
                  <h3>
                    <strong>Data de criação do teste:</strong>
                    {test?.creationDate}
                  </h3>
                  <h3>
                    <strong>Cotação máxima do teste:</strong>
                    {test?.globalCotation}
                  </h3>
                </div>
              </div>
            </div>
            <div className="flex flex-col space-y-4">
              <Link
                to="../solve"
                className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
              >
                Editar teste
              </Link>
              <Link
                to="../solve"
                className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
              >
                Resolver teste
              </Link>
            </div>
          </div>
          <div className="space-y-4">
            {test?.groups.map((group, index) => {
              return renderGroup(group, index);
            })}
          </div>
        </div>
      </div>
    </>
  );
}
