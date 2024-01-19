import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { useState } from "react";
import { FiSave } from "react-icons/fi";
import { IoClose } from "react-icons/io5";
import { DropdownBlock } from "../../interactiveElements/DropdownBlock";

interface EditTestInfopProps {
  testInfo: {
    type: string;
    conclusion: string;
    visibility: string;
    title: string;
    globalInstructions: string;
  };
  saveEdit: (state: {
    type: string;
    conclusion: string;
    visibility: string;
    title: string;
    globalInstructions: string;
  }) => void;
  cancelEdit: (state: {
    type: string;
    visibility: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  }) => void;
}

export function translateVisibilityToString(visibility: string) {
  switch (visibility) {
    case "institutional":
      return "Institucional";
    case "course":
      return "Curso";
    case "not-listed":
      return "Não listado";
    case "private":
      return "Privado";
    default:
      return "Público";
  }
}

export function translateStringToVisibility(visibility: string) {
  switch (visibility) {
    case "Institucional":
      return "institutional";
    case "Curso":
      return "course";
    case "Não listado":
      return "not-listed";
    case "Privado":
      return "private";
    default:
      return "public";
  }
}

export function EditTestInfo({
  testInfo,
  saveEdit,
  cancelEdit,
}: EditTestInfopProps) {
  const [type] = useState(testInfo.type);
  const [conclusion, setConclusion] = useState(testInfo.conclusion);
  const [visibility, setVisibility] = useState(
    testInfo.visibility
      ? translateVisibilityToString(testInfo.visibility)
      : "Público"
  );
  const [title, setTitle] = useState(testInfo.title);
  const [globalInstructions, setGlobalInstructions] = useState(
    testInfo.globalInstructions
  );

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() =>
              saveEdit({
                type: type,
                visibility: translateStringToVisibility(visibility),
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
        <div className="flex flex-col px-4 gap-4">
          <div className="flex gap-4 items-center">
            <strong>Título:</strong>
            <input
              className="rounded-lg bg-input-2"
              placeholder="Novo Teste"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div className="flex gap-4 items-center">
            <strong>Visibilidade:</strong>
            <DropdownBlock
              style="rounded-lg h-[42.19px]"
              options={[
                "Público",
                "Institucional",
                "Curso",
                "Não listado",
                "Privado",
              ]}
              chosenOption={visibility}
              setChosenOption={setVisibility}
              text="Visibilidade"
              placement="bottom"
            ></DropdownBlock>
          </div>
          <strong>Instruções globais:</strong>
          <div className="px-4">
            <TextareaBlock
              toolbar={true}
              rows={6}
              placeholder="Coloque aqui as instrucoes do grupo..."
              value={globalInstructions}
              onChange={(value) => setGlobalInstructions(value)}
            />
          </div>
          <strong>Conclusão:</strong>
          <div className="px-4">
            <TextareaBlock
              toolbar={true}
              rows={6}
              placeholder="Coloque aqui as instrucoes do grupo..."
              value={conclusion}
              onChange={(value) => setConclusion(value)}
            />
          </div>
        </div>
        <div className="flex gap-2">
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() =>
              saveEdit({
                type: type,
                visibility: translateStringToVisibility(visibility),
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            <FiSave className="size-5" />
            Guardar e fechar
          </button>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() =>
              cancelEdit({
                type: type,
                visibility: translateStringToVisibility(visibility),
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
