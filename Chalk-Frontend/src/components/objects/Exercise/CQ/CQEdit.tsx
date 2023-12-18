import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { Exercise } from "../Exercise";

interface CQEditProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
}

export function CQEdit({ dispatch, state }: CQEditProps) {
  return (
    <>
      <div className="flex space-x-8">
        <label>Indique o número máximo de respostas do aluno:</label>
        <input
          type="number"
          className="basic-input-text"
          onChange={(e) =>
            dispatch({
              type: EditActionKind.CHANGE_ITEM_TEXT,
            })
          }
          value={state.additionalProps?.maxMsgs}
        ></input>
      </div>
      <div className="flex space-x-8">
        <label>Indique os tópicos que a AI deverá abordar:</label>
      </div>
    </>
  );
}
