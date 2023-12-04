import { ShowExercise } from "../Exercise/ShowExercise";
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
    solution: {
      true_or_false: [
        {
          phrase: "Existem 9 canetas roxas ou vermelhas",
          tfvalue: "",
          justification: "",
        },
        {
          phrase: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
          tfvalue: "",
          justification: "",
        },
        { phrase: "Existem 8 canetas pretas", tfvalue: "", justification: "" },
        {
          phrase: "Existem mais canetas castanhas que amarelas",
          tfvalue: "",
          justification: "",
        },
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
    solution: {
      true_or_false: [
        {
          phrase: "Existem 9 canetas roxas ou vermelhas",
          tfvalue: "",
          justification: "",
        },
        {
          phrase: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
          tfvalue: "",
          justification: "",
        },
        { phrase: "Existem 8 canetas pretas", tfvalue: "", justification: "" },
        {
          phrase: "Existem mais canetas castanhas que amarelas",
          tfvalue: "",
          justification: "",
        },
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
    solution: {
      true_or_false: [
        {
          phrase: "Existem 9 canetas roxas ou vermelhas",
          tfvalue: "",
          justification: "",
        },
        {
          phrase: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
          tfvalue: "",
          justification: "",
        },
        { phrase: "Existem 8 canetas pretas", tfvalue: "", justification: "" },
        {
          phrase: "Existem mais canetas castanhas que amarelas",
          tfvalue: "",
          justification: "",
        },
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
    solution: {
      multiple_choice: "",
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
        "Existem 9 canetas roxas ou vermelhas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
    solution: {
      true_or_false: [
        {
          phrase: "Existem 9 canetas roxas ou vermelhas",
          tfvalue: "",
          justification: "",
        },
        {
          phrase: "Existem mais canetas castanhas que amarelas",
          tfvalue: "",
          justification: "",
        },
      ],
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
