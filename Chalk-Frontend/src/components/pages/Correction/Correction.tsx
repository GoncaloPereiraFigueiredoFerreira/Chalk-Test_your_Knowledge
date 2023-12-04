import "./Correction.css";
import { useEffect, useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";
import { ImgPos } from "../../objects/Exercise/Header/ExHeader";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
} from "../../objects/Exercise/Exercise";
import { ShowExerciseSimple } from "../../objects/Exercise/ShowExerciseSimple";
import { Answer, AnswerProps } from "../../objects/Answer/Answer";
import { Exercise2, ExerciseGroup } from "../../objects/Exercise/Exercise";

export interface AnswerGroup {
  question_id: string;
  resolutions: AnswerProps[];
}

const testResolutions: AnswerGroup[] = [
  {
    question_id: "1",
    resolutions: [
      {
        id: "3432",
        name: "Joana das Roscas",
        email: "sds",
        answer: {
          type: ExerciseType.OPEN_ANSWER,
          answers: ["fnesnfnfhn ufhewiugern erife"],
          correction: [""],
        },
      },
      {
        id: "332",
        name: "Joana das Roscas",
        email: "sds",
        answer: {
          type: ExerciseType.OPEN_ANSWER,
          answers: ["fnesnfnfhn ufhewiugern erife"],
          correction: [""],
        },
      },
    ],
  },
  {
    question_id: "2",
    resolutions: [
      {
        id: "3432",
        name: "Joana das Roscas",
        email: "sds",
        answer: {
          type: ExerciseType.OPEN_ANSWER,
          answers: ["fnesnfnfhn erife"],
          correction: [""],
        },
      },
    ],
  },
];

const userExercises: Exercise[] = [
  {
    id: "1",
    name: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.OPEN_ANSWER,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        pos: ImgPos.RIGHT,
      },
    },
  },
  {
    id: "2",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.RIGHT,
      },
    },
    problem: {
      justify: ExerciseJustificationKind.JUSTIFY_ALL,
      statements: [
        "Existem 9 canetas roxas ou vermelhas",
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 8 canetas pretas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
  },
  {
    id: "6",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.RIGHT,
      },
    },
    problem: {
      justify: ExerciseJustificationKind.JUSTIFY_TRUE,
      statements: [
        "Existem 9 canetas roxas ou vermelhas",
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 8 canetas pretas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
  },
  {
    id: "3",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.BOT,
      },
    },
    problem: {
      justify: ExerciseJustificationKind.JUSTIFY_FALSE,
      statements: [
        "Existem 9 canetas roxas ou vermelhas",
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 8 canetas pretas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
  },
  {
    id: "4",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.MULTIPLE_CHOICE,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
    },
    problem: {
      statements: [
        "Existem 9 canetas roxas ou vermelhas",
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 8 canetas pretas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
  },
  {
    id: "5",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.OPEN_ANSWER,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        pos: ImgPos.LEFT,
      },
    },
  },
  {
    id: "7",
    name: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    author: "Dudu",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.TOP,
      },
    },
    problem: {
      justify: ExerciseJustificationKind.NO_JUSTIFICATION,
      statements: [
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 9 canetas roxas ou vermelhas",
      ],
    },
  },
];

type ExerciseList = { [key: string]: Exercise };

type ResolutionList = { [key: string]: AnswerGroup };

export function Correction() {
  const [exerciseList, setExerciseList] = useState<ExerciseList>({});
  const [resolutionList, setResolutionList] = useState<ResolutionList>({});

  const [selectedExercise, setSelectedExercise] = useState("");

  const addExercises = (exercises: Exercise[]) => {
    let tempList = { ...exerciseList };
    exercises.forEach((ex) => (tempList[ex.id] = ex));
    setExerciseList(tempList);
  };

  const addAnswers = (answer: AnswerGroup[]) => {
    let tempList = { ...resolutionList };
    answer.forEach((an) => (tempList[an.question_id] = an));
    setResolutionList(tempList);
  };

  useEffect(() => {
    addExercises(userExercises);
    addAnswers(testResolutions);
    console.log(Object.entries(userExercises));
    console.log(Object.entries(testResolutions));
  }, []);

  return (
    <>
      <div className="h-screen overflow-auto">
        <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 ">
          <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <label className="flex text-title-1">Teste_Turma9C </label>
          </div>
          <div className="grid grid-cols-2">
            <div className=" mr-4">
              {Object.entries(exerciseList).map(([key, exercise]) => (
                <ShowExerciseSimple
                  key={key}
                  position={key}
                  exercise={exercise}
                  selectedExercise={selectedExercise}
                  setSelectedExercise={(value) => setSelectedExercise(value)}
                ></ShowExerciseSimple>
              ))}
            </div>

            <div>
              {/*               {Object.entries(
                testResolutions[selectedExercise].resolutions
              ).map(([key, resolution]) => (
                <Answer key={key} answer={resolution}></Answer>
              ))} */}
              {Object.entries(testResolutions)
                .filter(([key]) => key === selectedExercise)
                .pop()?.[1]
                .resolutions.map((resolution) => (
                  <Answer
                    key={resolution.id}
                    id={resolution.id}
                    email={resolution.email}
                    name={resolution.name}
                    answer={resolution.answer}
                  ></Answer>
                ))}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
