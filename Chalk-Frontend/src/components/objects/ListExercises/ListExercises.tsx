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
  createNewExercise,
} from "../Exercise/Exercise";

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

export function ListExercises() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
  const [selectedExercise, setSelectedExercise] = useState("");
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const [exerciseList, setExerciseList] = useState<ExerciseList>({});

  const addExercises = (exercises: Exercise[]) => {
    let tempList = { ...exerciseList };
    exercises.forEach((ex) => (tempList[ex.id] = ex));
    setExerciseList(tempList);
  };

  useEffect(() => {
    addExercises(userExercises);
  }, []);

  const remExercise = (id: string) => {
    let tempList = { ...exerciseList };
    if (Object.keys(exerciseList).includes(id)) {
      delete tempList[id];
      setExerciseList(tempList);
    }
  };

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
        {Object.entries(exerciseList).map(([key, exercise]) => (
          <ShowExercise
            key={key}
            position={key}
            exercise={exercise}
            setEditMenuIsOpen={setEditMenuIsOpen}
            selectedExercise={selectedExercise}
            setSelectedExercise={(value) => setSelectedExercise(value)}
            remExercise={(id: string) => remExercise(id)}
          ></ShowExercise>
        ))}
      </div>
      <PopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        children={
          <CreateNewExercisePopUp
            createNewExercise={(newExerciseType: ExerciseType) => {
              createNewExercise(newExerciseType);
              setEditMenuIsOpen(true);
              setNewExercisePopUp(false);
            }}
          />
        }
      />
    </>
  );
}
