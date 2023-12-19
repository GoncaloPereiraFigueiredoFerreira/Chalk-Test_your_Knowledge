import { EditActionKind } from "../../EditExercise/EditExercise";
import { CQExercise, CreateEditProps } from "../Exercise";

interface CQEditProps {
  context: CreateEditProps;
  exercise: CQExercise;
}

export function CQEdit({ context, exercise }: CQEditProps) {
  return (
    <>
      <div className="flex space-x-8">
        <label>Indique o número máximo de respostas do aluno:</label>
        <input
          type="number"
          className="basic-input-text"
          onChange={(e) =>
            context.dispatch({
              type: EditActionKind.CHANGE_ITEM_TEXT,
            })
          }
          value={exercise.props.maxAnswers}
        ></input>
      </div>
      <div className="flex space-x-8">
        <label>Indique os tópicos que a AI deverá abordar:</label>
      </div>
    </>
  );
}
