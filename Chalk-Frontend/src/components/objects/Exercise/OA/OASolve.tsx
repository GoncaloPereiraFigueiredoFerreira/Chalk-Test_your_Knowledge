import { useEffect, useState } from "react";
import { ExerciseHeaderComp } from "../Header/ExHeader";
import {
  ExerciseType,
  OAExercise,
  OAResolutionData,
  SolveProps,
} from "../Exercise";
import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";

export interface OASolveProps {
  exercise: OAExercise;
  position: string;
  context: SolveProps;
}

export function OASolve(props: OASolveProps) {
  let initState: OAResolutionData = props.context
    .resolutionData as OAResolutionData;

  const [state, setState] = useState<OAResolutionData>(initState);

  const setText = (text: string) => {
    setState({
      type: ExerciseType.OPEN_ANSWER,
      text: text,
    });
  };

  useEffect(() => {
    setState(initState);
  }, [props.exercise]);

  useEffect(() => {
    props.context.setExerciseSolution(state);
  }, [state]);

  return (
    <>
      <ExerciseHeaderComp
        header={props.exercise.base.statement}
      ></ExerciseHeaderComp>

      <form>
        <div className="w-full mb-4">
          <div className="px-4 py-2 rounded-b-lg">
            <TextareaBlock
              id={props.exercise.identity.id}
              toolbar
              className="w-full focus:ring-0 bg-white"
              value={state.text}
              onChange={setText}
            ></TextareaBlock>
          </div>
        </div>
      </form>
    </>
  );
}
