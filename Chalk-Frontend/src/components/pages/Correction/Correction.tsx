import "./Correction.css";
import { useEffect, useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";
import { ImgPos } from "../../objects/Exercise/Header/ExHeader";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
} from "../../objects/Exercise/Exercise";
import { ShowExerciseSimple } from "../../objects/ListExercises/ShowExerciseSimple";
import { Answer, AnswerProps } from "../../objects/Answer/Answer";

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
          answers: ["fnesnfnfhn erife"],
          correction: [""],
        },
      },
    ],
  },
  {
    question_id: "2",
    resolutions: [
      {
        id: "111",
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
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.OPEN_ANSWER,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
  },
  {
    id: "2",
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_ALL,
    items: {
      "1": { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      "2": {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      "3": { text: "Existem 8 canetas pretas", type: "string" },
      "4": {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
  },
  {
    id: "6",
    title: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_TRUE,
    items: {
      "1": { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      "2": {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      "3": { text: "Existem 8 canetas pretas", type: "string" },
      "4": {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
  },
  {
    id: "3",
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.BOT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_FALSE,
    items: {
      "1": { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      "2": {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      "3": { text: "Existem 8 canetas pretas", type: "string" },
      "4": {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
  },
  {
    id: "4",
    title: "Quantas canetas",
    visibility: "not-listed",
    type: ExerciseType.MULTIPLE_CHOICE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_MARKED,
    items: {
      "1": { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      "2": {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      "3": { text: "Existem 8 canetas pretas", type: "string" },
      "4": {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
  },
  {
    id: "5",
    title: "Quantas canetas",
    visibility: "course",
    type: ExerciseType.OPEN_ANSWER,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.LEFT,
    },
  },
  {
    id: "7",
    title: "Quantas canetas",
    visibility: "institutional",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.TOP,
    },
    justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
    items: {
      "1": { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      "2": {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
  },
];

interface ExerciseList {
  [key: string]: Exercise;
}

interface ResolutionList {
  [key: string]: AnswerGroup;
}

export function Correction() {
  const [exerciseList, setExerciseList] = useState<{
    [key: string]: Exercise;
  }>();
  const [resolutionList, setResolutionList] = useState<{
    [key: string]: AnswerGroup;
  }>();

  const [selectedExercise, setSelectedExercise] = useState("");

  useEffect(() => {
    let tempListEx: ExerciseList = {};
    userExercises.forEach((ex: Exercise) => (tempListEx[ex.id] = ex));
    setExerciseList(tempListEx);

    let tempListRes: ResolutionList = {};
    testResolutions.forEach((an) => (tempListRes[an.question_id] = an));
    setResolutionList(tempListRes);
  }, [userExercises, testResolutions]);

  if (exerciseList != undefined && resolutionList != undefined)
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
                {selectedExercise
                  ? resolutionList[selectedExercise].resolutions.map(
                      (resolution) => (
                        <Answer
                          key={resolution.id}
                          id={resolution.id}
                          email={resolution.email}
                          name={resolution.name}
                          answer={resolution.answer}
                        ></Answer>
                      )
                    )
                  : null}
              </div>
            </div>
          </div>
        </div>
      </>
    );
  else return <></>;
}
