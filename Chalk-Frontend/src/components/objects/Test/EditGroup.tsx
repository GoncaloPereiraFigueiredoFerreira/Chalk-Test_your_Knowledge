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
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 bg-white dark:bg-black">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Editar
          </label>
          <div className="flex gap-4">
            <button
              className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
              onClick={() => cancelEdit(state)}
            >
              <IoClose className="size-5" />
              Cancelar
            </button>
          </div>
        </div>
        <strong>Instruções do Grupo:</strong>
        <TextareaBlock
          toolbar={true}
          rows={6}
          placeholder="Coloque aqui as instruções do grupo..."
          value={state}
          onChange={(value) => setState(value)}
        />
        <div className="flex gap-2">
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
            onClick={() => saveEdit(state)}
          >
            <FiSave className="size-5" />
            Guardar e fechar
          </button>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
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
