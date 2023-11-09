import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { Exercise } from "../../objects/Exercise/Exercise";
import "./FrontPage.css";
import { useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";
import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  InputIcon,
  TextIcon,
} from "../../objects/SVGImages/SVGImages";

export function FrontPage() {
  const [selectedExercise, setSelectedExercise] = useState(-1);
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const [newExercisetype, setNewExercisetype] = useState("multiple-choice");

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
    <div className="flex flex-col w-full dark:bg-gray-700 bg-white py-8 gap-8">
      <Searchbar></Searchbar>
      <div className="flex-col flex w-full h-screen dark:bg-gray-700 px-16">
        <div className="flex w-full justify-between mb-4 px-4 pb-2.5 border-b border-b-gray-300 text-gray-600 dark:border-b-gray-500 dark:text-white">
          <label className="flex text-4xl">Exercícios</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-gray-200 hover:bg-slate-300 dark:hover:bg-gray-800 dark:bg-gray-600"
            onClick={() => setNewExercisePopUp(true)}
          >
            Criar exercício
          </button>
        </div>
        <Exercise
          name={"Quantas canetas"}
          visibility={"private"}
          type={"true-or-false"}
          author={"Dudu"}
          enunciado={{
            text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
            img: {
              url: "https://static.fnac-static.com/multimedia/Images/PT/NR/8c/63/11/1139596/1540-1/tsp20200722170925/Canetas-de-Cor-Staedtler-Triplus-Fineliner-0-3mm-10-Unidades.jpg",
              pos: "BOT",
            },
          }}
          problema={{
            justify: false,
            statements: [
              "Existem 9 canetas roxas ou vermelhas",
              "Existem tantas canetas pretas ou roxas, quanto vermelhas",
              "Existem 8 canetas pretas",
              "Existem mais canetas castanhas que amarelas",
            ],
          }}
          exerciseKey={1}
          selectedExercise={selectedExercise}
          setSelectedExercise={(value) => setSelectedExercise(value)}
        ></Exercise>
      </div>
      <PopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        children={
          <>
            <label className="flex w-full justify-between mb-4 px-4 pb-2.5 border-b border-b-gray-300 text-gray-600 dark:border-b-gray-500 dark:text-white text-4xl">
              Criar novo exercício
            </label>
            <div className="grid grid-cols-2">
              <button
                onClick={() => setNewExercisetype("multiple-choice")}
                className={`${
                  "multiple-choice" != newExercisetype
                    ? "btn-normal-frontpage text-gray-500"
                    : "btn-selected-frontpage text-white"
                } btn-frontpage text-lg dark:text-white group`}
              >
                <CheckedListIcon style="inherit-icon" size="size-12" />
                Escolha múltipla
              </button>
              <button
                onClick={() => setNewExercisetype("open-answer")}
                className={`${
                  "open-answer" != newExercisetype
                    ? "btn-normal-frontpage text-gray-500"
                    : "btn-selected-frontpage text-white"
                } btn-frontpage text-lg dark:text-white group`}
              >
                <TextIcon style="inherit-icon" size="size-12" />
                Resposta aberta
              </button>
              <button
                onClick={() => setNewExercisetype("true-or-false")}
                className={`${
                  "true-or-false" != newExercisetype
                    ? "btn-normal-frontpage text-gray-500"
                    : "btn-selected-frontpage text-white"
                } btn-frontpage text-lg dark:text-white group`}
              >
                <CheckboxIcon style="inherit-icon" size="size-12" />
                Verdadeiro ou falso
              </button>
              <button
                onClick={() => setNewExercisetype("fill-in-the-blank")}
                className={`${
                  "fill-in-the-blank" != newExercisetype
                    ? "btn-normal-frontpage text-gray-500"
                    : "btn-selected-frontpage text-white"
                } btn-frontpage text-lg dark:text-white group`}
              >
                <InputIcon style="inherit-icon" size="size-12" />
                Preenchimento de espaços
              </button>
              <button
                onClick={() => setNewExercisetype("code")}
                className={`${
                  "code" != newExercisetype
                    ? "btn-normal-frontpage text-gray-500"
                    : "btn-selected-frontpage text-white"
                } btn-frontpage text-lg dark:text-white group`}
              >
                <CodeIcon style="inherit-icon" size="size-12" />
                Código
              </button>
            </div>
            <div className="flex justify-end">
              <button
                onClick={() => createNewExercise()}
                className="btn-selected-frontpage btn-frontpage group"
              >
                <label className="text-lg text-white">Seguinte</label>
              </button>
            </div>
          </>
        }
      />
    </div>
  );
}
