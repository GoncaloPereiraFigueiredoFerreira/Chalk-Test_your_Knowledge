import { useEffect, useRef, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseContext,
  ExerciseTypeToString,
} from "../../objects/Exercise/Exercise";
import { Test, ExerciseGroup } from "../../objects/Test/Test";
import { TestResolution } from "./Preview/PreviewTest";
import { textToHTML } from "../../interactiveElements/TextareaBlock";

function renderExercise(
  exercise: Exercise,
  index: number,
  groupIndex: number,
  exerciseSelected: number,
  setSelectedExercise: Function,
  setShowExID: Function,
  testResolutions?: TestResolution
) {
  return (
    <div
      key={index}
      className={`${
        index == exerciseSelected ? "max-h-full" : "max-h-16"
      } transition-[max-height] duration-500 overflow-hidden flex flex-col gap-4 w-full p-4 rounded-lg cursor-pointer bg-[#bdcee6] dark:bg-slate-700`}
      onClick={(e) => {
        setSelectedExercise(index == exerciseSelected ? -1 : index);
        setShowExID(index == exerciseSelected ? "" : exercise.identity.id);
        e.stopPropagation();
      }}
    >
      <div className="flex justify-between text-black dark:text-white">
        <label className="text-md font-medium">
          Exercício {index + 1} - {ExerciseTypeToString(exercise.type)}
        </label>
        <div>
          {testResolutions &&
          testResolutions.resolutions[exercise.identity.id] ? (
            <p>
              Cotação Obtida:{" "}
              {testResolutions.resolutions[exercise.identity.id].points}/{" "}
              {exercise.identity.points}
            </p>
          ) : (
            <p>Cotação do Exercício: {exercise.identity.points}</p>
          )}
        </div>
      </div>
      <div
        className={`${
          index == exerciseSelected ? "" : "scale-y-0"
        } mx-4 mb-4 border rounded-lg text-black dark:text-white bg-white dark:bg-slate-800 border-slate-500`}
      >
        <ExerciseComponent
          exercise={exercise}
          context={{
            context: ExerciseContext.PREVIEW,
            resolution:
              testResolutions &&
              testResolutions.resolutions[exercise.identity.id]
                ? testResolutions.resolutions[exercise.identity.id].data
                : undefined,
          }}
          position={groupIndex + 1 + "." + (index + 1)}
        ></ExerciseComponent>
        {testResolutions &&
          testResolutions.resolutions[exercise.identity.id] && (
            <p className="">
              <strong>Comentário do Especialista: </strong>
              {textToHTML(
                testResolutions.resolutions[exercise.identity.id].comment
              )}
            </p>
          )}
      </div>
    </div>
  );
}

function renderGroup(
  group: ExerciseGroup,
  index: number,
  selectedEx: number,
  setSelectedEx: Function,
  selectedGroup: number,
  setSelectedGroup: Function,
  setShowExID: Function,
  testResolutions?: TestResolution
) {
  return (
    <div
      key={index}
      className={`${
        selectedGroup === index ? "max-h-full" : "max-h-16"
      } transition-[max-height] duration-500 overflow-hidden h-full flex flex-col gap-4 rounded-lg px-8 py-4 text-black dark:text-white bg-[#d8e3f1] dark:bg-[#1e2a3f] cursor-default`}
      onClick={() => {
        setSelectedGroup(index == selectedGroup ? -1 : index);
        setSelectedEx(-1);
        setShowExID("");
      }}
    >
      <div className="flex w-full px-4 justify-between cursor-pointer">
        <label className="flex w-full items-center text-xl font-medium">
          Grupo {index + 1}
        </label>
        <div className="flex w-full justify-end items-center gap-4">
          Cotação do Grupo:
          <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-white dark:bg-slate-600">
            {group.groupPoints} pts
          </div>
        </div>
      </div>
      <div
        className={`${
          selectedGroup === index ? "" : "scale-y-0"
        } flex flex-col gap-4 pt-4 px-4 border-t-2 border-slate-400 dark:border-slate-600`}
      >
        {textToHTML(group.groupInstructions)}
        <div className="space-y-4">
          {group.exercises.map((exercise, exId) => {
            return renderExercise(
              exercise,
              exId,
              index,
              selectedEx,
              setSelectedEx,
              setShowExID,
              testResolutions
            );
          })}
        </div>
      </div>
    </div>
  );
}

export interface TestPreviewProps {
  test: Test;
  showExId: string;
  setShowExID: Function;
  testResolution?: TestResolution;
}

function findId(id: string, test: Test) {
  for (let i = 0; i < test.groups.length; i++) {
    for (let j = 0; j < test.groups[i].exercises.length; j++) {
      if (test.groups[i].exercises[j].identity.id === id)
        return { group: i, ex: j };
    }
  }
  return { group: -1, ex: -1 };
}

export function TestPreview({
  test,
  showExId,
  setShowExID,
  testResolution,
}: TestPreviewProps) {
  const [selectedGroup, setSelectedGroup] = useState(-1);
  const [selectedEx, setSelectedEx] = useState(-1);

  useEffect(() => {
    if (showExId !== "") {
      const { group, ex } = findId(showExId, test);
      if (group != selectedGroup) {
        setSelectedGroup(group);
      }
      if (ex != selectedEx) setSelectedEx(ex);
    }
  }, [showExId]);

  return (
    <>
      <div className="flex flex-col px-4 pt-4 pb-8 gap-4">
        <strong className="text-xl">Informações Gerais do Teste:</strong>
        <div className="gridTestInfo text-md pl-4 gap-x-5 gap-y-3">
          <p>Instruções do Teste:</p>
          <p>{test.globalInstructions}</p>
          <p>Autor:</p>
          <p> {test.author}</p>
          <p>Data de criação do teste:</p>
          <p>{test.creationDate}</p>
          <p>Cotação máxima do teste:</p>
          <p>{test.globalPoints}</p>
        </div>
      </div>
      <div className="flex flex-col px-4 py-8 gap-4 border-t-2 border-slate-400 dark:border-slate-600">
        <strong className="text-xl">Exercícios:</strong>
        {test.groups.map((group, index) => {
          return renderGroup(
            group,
            index,
            selectedEx,
            setSelectedEx,
            selectedGroup,
            setSelectedGroup,
            setShowExID,
            testResolution
          );
        })}
      </div>
    </>
  );
}
