import { useState } from "react";
import { Test } from "../SolveTest/SolveTest";
import { TestPreview } from "../TestPreview";
import {
  ExerciseJustificationKind,
  ExerciseType,
} from "../../../objects/Exercise/Exercise";
import { Link } from "react-router-dom";

export const exampleTest: Test = {
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
          identity: {
            id: "11111",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Portugal?",
            },
          },
          type: ExerciseType.OPEN_ANSWER,
          props: {},
        },

        {
          identity: {
            id: "22222",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          props: {
            justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
            items: {
              1: { text: "Portugal" },
              2: { text: "Polo norte" },
              3: { text: "Pigs" },
              4: { text: "Setúbal" },
            },
          },
        },
        {
          identity: {
            id: "33333",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.CHAT,
          props: {
            maxAnswers: 2,
            topics: ["Geografia dos Paises"],
          },
        },
      ],
      groupCotation: 3,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
    {
      exercises: [
        {
          identity: {
            id: "55555",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Portugal?",
            },
          },
          type: ExerciseType.OPEN_ANSWER,
          props: {},
        },

        {
          identity: {
            id: "66666",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          props: {
            justifyType: ExerciseJustificationKind.JUSTIFY_ALL,
            items: {
              1: { text: "Portugal" },
              2: { text: "Polo norte" },
              3: { text: "Pigs" },
              4: { text: "Setúbal" },
            },
          },
        },
        {
          identity: {
            id: "77777",
            specialistId: "JCR",
            visibility: "public",
            cotation: 15,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          props: {
            justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
            items: {
              1: { text: "Portugal" },
              2: { text: "Polo norte" },
              3: { text: "Pigs" },
              4: { text: "Setúbal" },
            },
          },
        },
      ],
      groupCotation: 16,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
    {
      exercises: [
        {
          identity: {
            id: "88888",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Portugal?",
            },
          },
          type: ExerciseType.OPEN_ANSWER,
          props: {},
        },

        {
          identity: {
            id: "99999",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          props: {
            justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
            items: {
              1: { text: "Portugal" },
              2: { text: "Polo norte" },
              3: { text: "Pigs" },
              4: { text: "Setúbal" },
            },
          },
        },
        {
          identity: {
            id: "10101",
            specialistId: "JCR",
            visibility: "public",
            cotation: 1,
          },
          base: {
            title: "Vizinhos",
            statement: {
              text: "Qual o país que faz fronteira com Espanha?",
            },
          },
          type: ExerciseType.TRUE_OR_FALSE,
          props: {
            justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
            items: {
              1: { text: "Portugal" },
              2: { text: "Polo norte" },
              3: { text: "Pigs" },
              4: { text: "Setúbal" },
            },
          },
        },
      ],
      groupCotation: 1,
      groupInstructions:
        "Neste grupo serão apresentadas 2 exercícios de geografia!Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus mollis ante vitae felis efficitur blandit. Etiam maximus aliquet nisi eget lacinia. Quisque iaculis ligula purus, ac euismod arcu ultrices sit amet. Pellentesque id tortor pulvinar, fringilla dolor quis, molestie nibh. Donec molestie massa ut eros sollicitudin vehicula. Duis convallis, eros sit amet cursus pellentesque, ipsum orci vestibulum leo, at sodales massa lorem vitae ipsum. Nullam pharetra ullamcorper nisi id cursus. Morbi at mauris vitae risus consectetur ultrices pulvinar eu metus. Nullam pellentesque dictum hendrerit. Nunc felis nisi, auctor vitae dapibus sit amet, hendrerit vitae mauris. Cras in tempus sem. Ut vehicula risus at arcu eleifend eleifend.",
    },
  ],
};

export function PreviewTest() {
  const [test, setTest] = useState<Test>(exampleTest);

  return (
    <>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8">
          <div className="flex  w-full justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <div className="flex flex-col">
              <label className=" text-title-1">
                Pré-visualizar: {test.title}
              </label>
            </div>
            <div className="flex space-x-4">
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
          <TestPreview
            test={test}
            setShowExID={() => {}}
            showExId={""}
          ></TestPreview>
        </div>
      </div>
    </>
  );
}
