import "./Correction.css";
import { useEffect, useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";
import { ImgPos } from "../../objects/Exercise/Header/ExHeader";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
  Item,
  Resolution,
  ResolutionStatus,
} from "../../objects/Exercise/Exercise";
import { ShowExerciseSimple } from "../../objects/ListExercises/ShowExerciseSimple";
import { Answer } from "../../objects/Answer/Answer";
import { ShowExerciseResolutionSimple } from "../../objects/ListExercises/ShowExerciseResolutionSimple";
import axios from "axios";

import { userExercises } from "./example";

export interface ExerciseRaw {
  id: string;
  title: string;
  cotation?: number;
  specialistId: string;
  visibility: string;
  type: ExerciseType;
  statement: {
    imagePath?: string;
    imagePosition?: ImgPos;
    text: string;
  };
  justifyKind?: ExerciseJustificationKind;
  items?: { [id: string]: { text: string; type: string } };

  solution?: Resolution;
  resolutions?: Resolution[];
  comments: string[];
}

export interface Student {
  id: string;
  email: string;
  name: string;
}

interface ExerciseList {
  [key: string]: ExerciseRaw;
}

export function Correction() {
  const [exerciseList, setExerciseList] = useState<{
    [key: string]: ExerciseRaw;
  }>();
  const [selectedExercise, setSelectedExercise] = useState("");
  const [optionalMenuIsOpen, setOptionalMenuIsOpen] = useState(false);
  const [optionSeparatedInterface, setOptionSeparatedInterface] =
    useState(true);
  const [solution, setSolution] = useState(<></>);

  useEffect(() => {
    if (selectedExercise !== "") {
      const tempType: string = exerciseList![selectedExercise]["type"];

      switch (tempType) {
        case ExerciseType.MULTIPLE_CHOICE:
          setSolution(
            <>
              <div className="text-md">
                {Object.entries(
                  exerciseList![selectedExercise]["solution"]?.data.items!
                ).map(([key, item]: [string, Item]) =>
                  item.value ? (
                    <div className="mb-2">
                      <div>{key + ": " + item.text}</div>
                      {item.justification ? (
                        <div>{item.justification}</div>
                      ) : (
                        <></>
                      )}
                    </div>
                  ) : (
                    <></>
                  )
                )}
              </div>
            </>
          );
          break;
        case ExerciseType.OPEN_ANSWER:
          setSolution(
            <>
              <div className="text-md">
                {exerciseList![selectedExercise]["solution"]?.data.text}
              </div>
            </>
          );
          break;
        case ExerciseType.TRUE_OR_FALSE:
          setSolution(
            <>
              <div className="text-md">
                {Object.entries(
                  exerciseList![selectedExercise]["solution"]?.data.items!
                ).map(([key, item]: [string, Item]) => (
                  <div className="mb-2">
                    <div>{key + ": " + item.text}</div>
                    {item.justification ? (
                      <div>{item.justification}</div>
                    ) : (
                      <></>
                    )}
                  </div>
                ))}
              </div>
            </>
          );
          break;
      }
    }
  }, [selectedExercise]);

  useEffect(() => {
    /*     axios
      .get("http://localhost:5173/tests/" + testId + "/resolutions")
      .then((response) => console.log(response))
      .catch((error) => console.log(error)); */

    let tempListEx: ExerciseList = {};
    userExercises.forEach((ex: ExerciseRaw) => (tempListEx[ex.id] = ex));
    setExerciseList(tempListEx);
  }, []);

  //handles change in view type
  useEffect(() => {
    setOptionalMenuIsOpen(selectedExercise === "" ? false : true);
  }, [selectedExercise]);

  if (exerciseList != undefined)
    return (
      <>
        <div className="h-screen overflow-auto">
          <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 ">
            <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
              <label className="flex text-title-1 float-left text-black dark:text-white">
                Teste_Turma9C
              </label>
              <button
                className="flex float-right text-black dark:text-white
                "
                onClick={(e) =>
                  setOptionSeparatedInterface(
                    optionSeparatedInterface ? false : true
                  )
                }
              >
                {optionSeparatedInterface
                  ? "Change to Full Page"
                  : "Change to Separated Page"}
              </button>
            </div>
            <div className="flex flex-row divide-x-2 border-gray-2-2">
              {/* 
              Lista de exercicios esquerda 
              */}
              <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
                <div className=" mr-4">
                  {optionSeparatedInterface
                    ? Object.entries(exerciseList).map(([key, exercise]) => (
                        <ShowExerciseSimple
                          key={key}
                          position={key}
                          exercise={exercise}
                          selectedExercise={selectedExercise}
                          setSelectedExercise={(value) =>
                            setSelectedExercise(value)
                          }
                        ></ShowExerciseSimple>
                      ))
                    : Object.entries(exerciseList).map(([key, exercise]) => (
                        <ShowExerciseResolutionSimple
                          key={key}
                          position={key}
                          exercise={exercise}
                          selectedExercise={selectedExercise}
                          setSelectedExercise={(value) =>
                            setSelectedExercise(value)
                          }
                        ></ShowExerciseResolutionSimple>
                      ))}
                </div>
              </div>
              {/*
               Lista de exercicios direita 
              */}

              <div
                className={`${
                  optionalMenuIsOpen && optionSeparatedInterface
                    ? "w-full"
                    : "w-0"
                } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
              >
                <div className="pl-5">
                  {/* Solução se houver */}

                  {selectedExercise &&
                  exerciseList[selectedExercise]["solution"] ? (
                    <div className="bg-white text-black p-4 mb-4">
                      {solution}
                    </div>
                  ) : (
                    <></>
                  )}

                  {/* Resoluçoes */}

                  {selectedExercise && exerciseList[selectedExercise]
                    ? Object.entries(
                        exerciseList[selectedExercise]!.resolutions!
                      ).map(([key, res]: [String, Resolution]) => (
                        <Answer
                          key={res.id}
                          id={res.id}
                          title={exerciseList[selectedExercise].title}
                          specialistId={
                            exerciseList[selectedExercise].specialistId
                          }
                          statement={exerciseList[selectedExercise].statement}
                          visibility={exerciseList[selectedExercise].visibility}
                          cotation={res.cotation}
                          type={exerciseList[selectedExercise].type}
                          justifyKind={
                            exerciseList[selectedExercise].justifyKind
                          }
                          resolution={res}
                          solution={exerciseList[selectedExercise].solution}
                          comments={exerciseList[selectedExercise].comments}
                        ></Answer>
                      ))
                    : null}
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  else return <></>;
}
