import { ShowExercise } from "../Exercise/ShowExercise";
import "./ListExercises.css";
import { useEffect, useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";
import { useUserContext } from "../../../context";
import { Exercise, UserActionKind } from "../../../UserInterface";
import { ImgPos } from "../Exercise/ExHeader";
import { ListExercisesPopUp } from "./ListExercisesPopUp";

const userExercises = [
  {
    id: "1",
    name: "Quantas canetas",
    visibility: "public",
    type: "open-answer",
    author: "Dudu",
    enunciado: {
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
    type: "true-or-false",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.RIGHT,
      },
    },
    problem: {
      justify: "false-only",
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
    type: "true-or-false",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.BOT,
      },
    },
    problem: {
      justify: "all",
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
    type: "multiple-choice",
    author: "Dudu",
    enunciado: {
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
    type: "open-answer",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        pos: ImgPos.LEFT,
      },
    },
  },
  {
    id: "6",
    name: "Quantas canetas",
    visibility: "private",
    type: "true-or-false",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: ImgPos.TOP,
      },
    },
    problem: {
      justify: "none",
      statements: [
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 9 canetas roxas ou vermelhas",
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
  const { userState, dispatch } = useUserContext();

  useEffect(() => {
    dispatch({
      type: UserActionKind.ADD_EXERCISES,
      payload: {
        exercises: userExercises,
      },
    });
  }, []);

  function createNewExercise(newExercisetype: string) {
    let newExercise: Exercise;

    // colocar aqui as chamadas para criação dos exercicios
    switch (newExercisetype) {
      case "multiple-choice":
        newExercise = {
          id: "",
          name: "Novo exercício",
          visibility: "public",
          type: "multiple-choice",
          author: "utilizador atual", //userState.username,
          enunciado: {
            text: "",
          },
          problem: {
            justify: "none",
            statements: [""],
          },
        };
        break;
      case "open-answer":
        break;
      case "true-or-false":
        break;
      case "fill-in-the-blank":
        break;
      case "code":
        break;
    }
  }

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
        {Object.entries(userState.listExercises).map(([key, exercise]) => (
          <ShowExercise
            key={key}
            position={key}
            exercise={exercise}
            setEditMenuIsOpen={setEditMenuIsOpen}
            selectedExercise={userState.selectedExercise}
            setSelectedExercise={(value) => {
              editMenuIsOpen
                ? {}
                : dispatch({
                    type: UserActionKind.SET_SELECTED_EXERCISE,
                    payload: {
                      selectedExercise: value,
                    },
                  });
            }}
          ></ShowExercise>
        ))}
      </div>
      <PopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        children={
          <ListExercisesPopUp
            createNewExercise={(newExercisetype) => {
              createNewExercise(newExercisetype);
              setNewExercisePopUp(false);
            }}
          />
        }
      />
    </>
  );
}
