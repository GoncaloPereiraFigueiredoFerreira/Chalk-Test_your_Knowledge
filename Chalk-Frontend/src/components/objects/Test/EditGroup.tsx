import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { useState } from "react";

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
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => saveEdit(state)}
          >
            Guardar e fechar
          </button>
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
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => saveEdit(state)}
          >
            Guardar e fechar
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => cancelEdit(state)}
          >
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
