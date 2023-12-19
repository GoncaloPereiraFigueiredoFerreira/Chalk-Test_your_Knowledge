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
} from "./ListExerciseContext";

const userExercises: Exercise[] = [
  {
    base: {
      title: "Contar canetas",
      statement: {
        text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
        imagePath:
          "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        imagePosition: ImgPos.RIGHT,
      },
    },
    type: ExerciseType.CHAT,
    props: { maxAnswers: 3, topics: ["Matemática"] },
    identity: {
      id: "1",
      specialistId: "JCR",
      visibility: "public",
    },
  },
  {
    base: {
      title: "Contar canetas",
      statement: {
        text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
        imagePath:
          "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        imagePosition: ImgPos.RIGHT,
      },
    },
    type: ExerciseType.TRUE_OR_FALSE,
    props: {
      justifyType: ExerciseJustificationKind.JUSTIFY_ALL,
      items: {
        "1": { text: "Existem 9 canetas roxas ou vermelhas" },
        "2": {
          text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        },
        "3": { text: "Existem 8 canetas pretas" },
        "4": {
          text: "Existem mais canetas castanhas que amarelas",
        },
      },
    },
    identity: {
      id: "2",
      specialistId: "JCR",
      visibility: "public",
    },
  },

  {
    base: {
      title: "Contar canetas",
      statement: {
        text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
        imagePath:
          "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        imagePosition: ImgPos.BOT,
      },
    },
    type: ExerciseType.OPEN_ANSWER,
    props: {},
    identity: {
      id: "3",
      specialistId: "JCR",
      visibility: "public",
    },
  },

  {
    base: {
      title: "Contar canetas",
      statement: {
        text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
        imagePath:
          "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        imagePosition: ImgPos.RIGHT,
      },
    },
    type: ExerciseType.MULTIPLE_CHOICE,
    props: {
      justifyType: ExerciseJustificationKind.JUSTIFY_ALL,
      items: {
        "1": { text: "Existem 9 canetas roxas ou vermelhas" },
        "2": {
          text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        },
        "3": { text: "Existem 8 canetas pretas" },
        "4": {
          text: "Existem mais canetas castanhas que amarelas",
        },
      },
    },
    identity: {
      id: "4",
      specialistId: "JCR",
      visibility: "public",
    },
  },
];

interface ListExercisesProps {
  editMenuIsOpen: string;
  setEditMenuIsOpen: (value: string) => void;
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
            editMenuIsOpen={editMenuIsOpen}
            selectedExercise={listExerciseState.selectedExercise}
            setSelectedExercise={(value) =>
              dispatch({
                type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                payload: {
                  selectedExercise: value,
                },
              })
            }
          ></ShowExercise>
        ) : null}
        {Object.entries(listExerciseState.listExercises).map(
          ([key, exercise]) =>
            exercise.identity.id === "-1" ? null : (
              <ShowExercise
                key={key}
                position={key}
                exercise={exercise}
                setEditMenuIsOpen={setEditMenuIsOpen}
                editMenuIsOpen={editMenuIsOpen}
                selectedExercise={listExerciseState.selectedExercise}
                setSelectedExercise={(value) =>
                  dispatch({
                    type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                    payload: {
                      selectedExercise: value,
                    },
                  })
                }
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
                type: ListExerciseActionKind.ADD_NEW_EXERCISE,
                payload: {
                  newExerciseType: newExerciseType,
                },
              });
              if (editMenuIsOpen === "") setEditMenuIsOpen("-1");
              setNewExercisePopUp(false);
            }}
          />
        }
      />
    </>
  );
}
