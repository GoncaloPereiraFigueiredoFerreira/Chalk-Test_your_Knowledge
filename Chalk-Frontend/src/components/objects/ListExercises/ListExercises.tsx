import { ShowExercise } from "./ShowExercise";
import "./ListExercises.css";
import { useEffect, useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";
import { ImgPos } from "../Exercise/Header/ExHeader";
import { CreateNewExercisePopUp } from "./CreateNewExercisePopUp";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
} from "../Exercise/Exercise";
import {
  ListExerciseActionKind,
  useListExerciseContext,
} from "../../pages/ExerciseBankPage/ListExerciseContext";

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

interface ListExercisesProps {
  editMenuIsOpen: boolean;
  setEditMenuIsOpen: (value: boolean) => void;
}

export function ListExercises({
  editMenuIsOpen,
  setEditMenuIsOpen,
}: ListExercisesProps) {
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const { listExerciseState, dispatch } = useListExerciseContext();

  useEffect(() => {
    dispatch({
      type: ListExerciseActionKind.ADD_EXERCISES,
      payload: {
        exercises: userExercises,
      },
    });
  }, []);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 ">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Exercícios</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setNewExercisePopUp(true)}
          >
            Criar exercício
          </button>
        </div>
        {listExerciseState.listExercises.hasOwnProperty("-1") ? (
          <ShowExercise
            position={"1"}
            exercise={listExerciseState.listExercises["-1"]}
            setEditMenuIsOpen={setEditMenuIsOpen}
            selectedExercise={listExerciseState.selectedExercise}
            setSelectedExercise={(value) => {
              editMenuIsOpen
                ? {}
                : dispatch({
                    type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                    payload: {
                      selectedExercise: value,
                    },
                  });
            }}
          ></ShowExercise>
        ) : null}
        {Object.entries(listExerciseState.listExercises).map(
          ([key, exercise]) =>
            exercise.id === "-1" ? null : (
              <ShowExercise
                key={key}
                position={key}
                exercise={exercise}
                setEditMenuIsOpen={setEditMenuIsOpen}
                selectedExercise={listExerciseState.selectedExercise}
                setSelectedExercise={(value) => {
                  editMenuIsOpen
                    ? {}
                    : dispatch({
                        type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                        payload: {
                          selectedExercise: value,
                        },
                      });
                }}
              ></ShowExercise>
            )
        )}
      </div>
      <PopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        children={
          <CreateNewExercisePopUp
            createNewExercise={(newExerciseType: ExerciseType) => {
              dispatch({
                type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                payload: {
                  newExercise: newExerciseType,
                },
              });
              setEditMenuIsOpen(true);
              setNewExercisePopUp(false);
            }}
          />
        }
      />
    </>
  );
}
