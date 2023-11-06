import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { Exercise } from "../../objects/Exercise/Exercise";
import "./FrontPage.css";
import { useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";

export function FrontPage() {
  const [selectedExercise, setSelectedExercise] = useState(-1);
  return (
    <div className="flex flex-col w-full dark:bg-gray-700 bg-white py-8 gap-8">
      <Searchbar></Searchbar>
      <div className="flex-col flex w-full h-screen dark:bg-gray-700 px-16">
        <label className="flex text-4xl mb-4 px-4 pb-2.5 border-b border-b-gray-300 text-gray-500 dark:border-b-gray-500 dark:text-white">
          Exercícios
        </label>
        <Exercise
          name={"Quantas canetas"}
          visibility={"private"}
          type={"multiple-choice"}
          author={"Dudu"}
          enunciado={
            "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas"
          }
          problema={[
            "Existem 9 canetas roxas ou vermelhas",
            "Existem tantas canetas pretas ou roxas, quanto vermelhas",
            "Existem 8 canetas pretas",
            "Existem mais canetas castanhas que amarelas",
          ]}
          exerciseKey={1}
          selectedExercise={selectedExercise}
          setSelectedExercise={(value) => setSelectedExercise(value)}
        ></Exercise>
      </div>
      <PopUp
        children={
          <>
            <Exercise
              name={"Quantas canetas"}
              visibility={"private"}
              type={"multiple-choice"}
              author={"Dudu"}
              enunciado={
                "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas"
              }
              problema={[
                "Existem 9 canetas roxas ou vermelhas",
                "Existem tantas canetas pretas ou roxas, quanto vermelhas",
                "Existem 8 canetas pretas",
                "Existem mais canetas castanhas que amarelas",
              ]}
              exerciseKey={1}
              selectedExercise={selectedExercise}
              setSelectedExercise={(value) => setSelectedExercise(value)}
            ></Exercise>
          </>
        }
        closePopUp={() => setSelectedExercise(selectedExercise)}
      ></PopUp>
    </div>
  );
}
