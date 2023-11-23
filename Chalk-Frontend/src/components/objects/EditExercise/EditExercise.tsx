import { useState } from "react";
import { useUserContext } from "../../../context";
import { ImgPos } from "../Exercise/Header/ExHeader";
import "./EditExercise.css";
import { EditHeader } from "../Exercise/Header/EditHeader";
import { ExerciseType } from "../../../UserInterface";
import { TFEdit } from "../Exercise/TF/TFEdit";

interface EditExerciseProps {
  setEditMenuIsOpen: (value: boolean) => void;
}

export function EditExercise({ setEditMenuIsOpen }: EditExerciseProps) {
  const { userState, dispatch } = useUserContext();

  const [statement, setStatement] = useState("");
  const [img, setImg] = useState("");
  const [addImg, setAddImg] = useState(false);
  const [postionImg, setPostionImg] = useState(ImgPos.RIGHT);

  console.log(userState.listExercises[userState.selectedExercise]);

  function editExerciseContent() {
    let exercise = userState.listExercises[userState.selectedExercise];
    switch (exercise.type) {
      case ExerciseType.MULTIPLE_CHOICE:
        return <></>;
      case ExerciseType.OPEN_ANSWER:
        return <></>;
      case ExerciseType.TRUE_OR_FALSE:
        return <></>;
      case ExerciseType.FILL_IN_THE_BLANK:
        return <></>;
      case ExerciseType.CODE:
        return <></>;
    }
  }

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 px-16 pb-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setEditMenuIsOpen(false)}
          >
            Guardar e fechar
          </button>
        </div>
        <EditHeader
          setAddImg={setAddImg}
          addImg={addImg}
          setPostionImg={setPostionImg}
          postionImg={postionImg}
          setImg={setImg}
          img={img}
        />
        {editExerciseContent()}
        <div className="flex gap-2">
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setEditMenuIsOpen(false)}
          >
            Guardar e fechar
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setEditMenuIsOpen(false)}
          >
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
