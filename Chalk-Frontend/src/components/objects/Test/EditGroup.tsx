import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { useState } from "react";
import { FiSave } from "react-icons/fi";
import { IoClose } from "react-icons/io5";

interface EditGroupProps {
  exerciseInstructions: string;
  saveEdit: (state: string) => void;
  cancelEdit: (state: string) => void;
}

export function EditGroup({
  exerciseInstructions,
  saveEdit,
  cancelEdit,
}: EditGroupProps) {
  const [state, setState] = useState(exerciseInstructions);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <div className="flex gap-4">
            <button
              className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
              onClick={() => cancelEdit(state)}
            >
              <IoClose className="size-5" />
              Cancelar
            </button>
          </div>
        </div>
        <TextareaBlock
          toolbar={true}
          rows={6}
          placeholder="Coloque aqui as instrucoes do grupo..."
          value={state}
          onChange={(value) => setState(value)}
        />
        <div className="flex gap-2">
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => saveEdit(state)}
          >
            <FiSave className="size-5" />
            Guardar e fechar
          </button>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => cancelEdit(state)}
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
