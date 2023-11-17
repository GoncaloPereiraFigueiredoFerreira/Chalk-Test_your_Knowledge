import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { Exercise } from "../../objects/Exercise/Exercise";
import "./FrontPage.css";
import { useEffect, useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";
import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  InputIcon,
  TextIcon,
} from "../../objects/SVGImages/SVGImages";
import { useUserContext } from "../../../context";
import { UserActionKind } from "../../../UserInterface";
import { ImgPos } from "../../objects/Exercise/ExHeader";

const userExercises = [
  {
    id: "1",
    name: "Quantas canetas",
    visibility: "private",
    type: "true-or-false",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
        pos: "BOT",
      },
    },
    problem: {
      justify: false,
      statements: [
        "Existem 9 canetas roxas ou vermelhas",
        "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        "Existem 8 canetas pretas",
        "Existem mais canetas castanhas que amarelas",
      ],
    },
  },
  {
    id: "2",
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
    id: "3",
    name: "Quantas canetas",
    visibility: "private",
    type: "open-answer",
    author: "Dudu",
    enunciado: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      img: {
        url: "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
        pos: ImgPos.RIGHT,
      },
    },
    problem: {},
  },
];

export function FrontPage() {
  const [selectedExercise, setSelectedExercise] = useState("");
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const [newExercisetype, setNewExercisetype] = useState("multiple-choice");
  const { userState, dispatch } = useUserContext();

  useEffect(() => {
    dispatch({
      type: UserActionKind.ADD_EXERCISES,
      payload: {
        exercises: userExercises,
      },
    });
  }, []);

  function createNewExercise() {
    // colocar aqui as chamadas para criação dos exercicios
    switch (newExercisetype) {
      case "multiple-choice":
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
    <div className="flex flex-col w-full min-h-max h-screen bg-2-1 py-8 gap-8">
      <Searchbar></Searchbar>
      <div className="flex-col flex w-full gap-4 px-16 pb-8 bg-2-1">
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
          <Exercise
            key={key}
            position={key}
            name={exercise.name}
            visibility={exercise.visibility}
            type={exercise.type} //multiple-choice open-answer true-or-false fill-in-the-blank code
            author={exercise.author}
            enunciado={exercise.enunciado}
            problem={exercise.problem}
            exerciseKey={key}
            selectedExercise={selectedExercise}
            setSelectedExercise={(value) => setSelectedExercise(value)}
          ></Exercise>
        ))}
      </div>
      <PopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        children={
          <>
            <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-title-1 border-b-2 border-gray-1">
              Criar novo exercício
            </label>
            <div className="grid grid-cols-2">
              <button
                onClick={() => setNewExercisetype("multiple-choice")}
                className={`${
                  "multiple-choice" != newExercisetype
                    ? "bg-btn-4-1"
                    : "btn-selected"
                } btn-frontpage text-lg group`}
              >
                <CheckedListIcon style="inherit-icon" size="size-12" />
                Escolha múltipla
              </button>
              <button
                onClick={() => setNewExercisetype("open-answer")}
                className={`${
                  "open-answer" != newExercisetype
                    ? "bg-btn-4-1"
                    : "btn-selected"
                } btn-frontpage text-lg group`}
              >
                <TextIcon style="inherit-icon" size="size-12" />
                Resposta aberta
              </button>
              <button
                onClick={() => setNewExercisetype("true-or-false")}
                className={`${
                  "true-or-false" != newExercisetype
                    ? "bg-btn-4-1"
                    : "btn-selected"
                } btn-frontpage text-lg group`}
              >
                <CheckboxIcon style="inherit-icon" size="size-12" />
                Verdadeiro ou falso
              </button>
              <button
                onClick={() => setNewExercisetype("fill-in-the-blank")}
                className={`${
                  "fill-in-the-blank" != newExercisetype
                    ? "bg-btn-4-1"
                    : "btn-selected"
                } btn-frontpage text-lg group`}
              >
                <InputIcon style="inherit-icon" size="size-12" />
                Preenchimento de espaços
              </button>
              <button
                onClick={() => setNewExercisetype("code")}
                className={`${
                  "code" != newExercisetype ? "bg-btn-4-1" : "btn-selected"
                } btn-frontpage text-lg group`}
              >
                <CodeIcon style="inherit-icon" size="size-12" />
                Código
              </button>
            </div>
            <div className="flex justify-end">
              <button
                onClick={() => createNewExercise()}
                className="btn-selected btn-frontpage group"
              >
                <label className="text-lg">Seguinte</label>
              </button>
            </div>
          </>
        }
      />
    </div>
  );
}
