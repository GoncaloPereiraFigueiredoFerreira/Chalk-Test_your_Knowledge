import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  CommentBalloon,
  InputIcon,
  PageIcon,
  TextIcon,
} from "../SVGImages/SVGImages";
// import { FillBlankExercise } from "./FillBlank/FillBlankExercise";
// import { CodeExercise } from "./Code/CodeExercise";
import { useEffect, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseComponentProps,
  ExerciseContext,
  Resolution,
} from "../Exercise/Exercise";
import "./ShowExercise.css";
import { ExerciseRaw } from "../../pages/Correction/Correction";
import { Answer } from "../Answer/Answer";
import { Comments } from "../Comments/Comments";

interface ExerciseSimpleProps {
  position: string;
  exercise: ExerciseRaw;
  selectedExercise: string;
  setSelectedExercise: (value: string) => void;
}

export function ShowExerciseResolutionSimple({
  position,
  exercise,
  selectedExercise,
  setSelectedExercise,
}: ExerciseSimpleProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [preview, setPreview] = useState(<></>);

  const exerciseComponent: ExerciseComponentProps = {
    exercise: exercise,
    position: position,
    context: { context: ExerciseContext.PREVIEW },
  };

  const [commentsDisplaying, setCommentsDisplaying] = useState(false);
  const [commentsDisplay, setCommentsDisplay] = useState(<></>);

  useEffect(() => {
    setCommentsDisplay(<Comments comments={exercise.comments} />);
  }, [exercise.comments]);

  useEffect(() => {
    switch (exercise.type) {
      case "multiple-choice":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckedListIcon size="size-4" />
            Escolha múltipla
          </label>
        );

        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case "open-answer":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <TextIcon size="size-4" />
            Resposta aberta
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case "true-or-false":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckboxIcon size="size-4" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case "fill-in-the-blank":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <InputIcon size="size-4" />
            Preenchimento de espaços
          </label>
        );
        setPreview(
          <></>
          // <FillBlankExercise
          //   statement={exercise.statement}
          //   problem={exercise.problem}
          //   contexto="solve"
          //   name={name}
          // ></FillBlankExercise>
        );
        break;
      case "code":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CodeIcon size="size-4" />
            Código
          </label>
        );
        setPreview(
          <></>
          // <CodeExercise
          //   statement={statement}
          //   problem={problem}
          //   contexto="solve"
          //   name={name}
          // ></CodeExercise>
        );
        break;
    }
  }, [exercise]);

  return (
    <div
      className={`${
        exercise.id === selectedExercise ? "max-h-full" : "max-h-[78px]"
      } transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2 mb-2`}
    >
      <div
        className={`${
          exercise.id === selectedExercise ? "bg-white" : " "
        } flex flex-col h-full px-5 py-2.5`}
      >
        <div
          className="flex items-center text-sm font-normal transition-all mb-4 group cursor-pointer"
          onClick={() =>
            exercise.id === selectedExercise
              ? setSelectedExercise("")
              : setSelectedExercise(exercise.id)
          }
        >
          <button className="flex flex-col gap-1.5 h-14 justify-center cursor-default">
            <label
              className={`${
                exercise.id === selectedExercise ? " text-black" : " "
              } flex min-w-max font-medium text-xl`}
            >
              {exercise.title}
            </label>
            <div
              className={`${
                exercise.id === selectedExercise ? "hidden" : "flex"
              } ml-1 gap-2`}
            >
              <div className="bg-yellow-600 tag-exercise">Matemática</div>
              <div className="bg-blue-600 tag-exercise">4º ano</div>
              <div className="bg-green-600 tag-exercise">escolinha</div>
              <div className="bg-gray-500 tag-exercise">+8</div>
            </div>
          </button>
        </div>
        <div
          className={`${
            exercise.id != selectedExercise ? "hidden" : "flex"
          } flex-wrap w-full text-sm font-normal gap-2 mx-1 mb-4 pb-4 border-b-2 border-gray-1`}
        >
          <div className="bg-yellow-600 tag-exercise">Matemática</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
        </div>
        <div
          className={`${
            exercise.id != selectedExercise ? "scale-y-0" : ""
          } flex flex-col mx-4 mb-4 border rounded-lg ex-1  `}
        >
          {preview}
        </div>
        <div className="flex justify-center">
          <button className="float-right w-fit m-4 border-blue-700 bg-blue-400 text-lg text-black p-4 bold rounded-md shadow-sm">
            Correct all answers with AI
          </button>
        </div>

        <div className="flex-row">
          <button
            className="float-right text-black"
            onClick={(e) =>
              setCommentsDisplaying(commentsDisplaying ? false : true)
            }
          >
            {commentsDisplaying ? (
              <PageIcon style=" float-right" />
            ) : (
              <CommentBalloon />
            )}
          </button>
        </div>
        {commentsDisplaying ? (
          commentsDisplay
        ) : (
          <div
            className={`${
              exercise.id != selectedExercise ? "scale-y-0" : ""
            } flex flex-col mx-1 mb-4 ex-1 shadow-2xl rounded-md`}
          >
            {exercise.resolutions
              ? Object.entries(exercise.resolutions!).map(
                  ([key, res]: [String, Resolution]) => (
                    <Answer
                      key={res.id}
                      id={res.id}
                      title={exercise.title}
                      specialistId={exercise.specialistId}
                      statement={exercise.statement}
                      visibility={exercise.visibility}
                      cotation={res.cotation}
                      type={exercise.type}
                      justifyKind={exercise.justifyKind}
                      resolution={res}
                      solution={exercise.solution}
                      comments={exercise.comments}
                    ></Answer>
                  )
                )
              : null}
          </div>
        )}
      </div>
    </div>
  );
}
