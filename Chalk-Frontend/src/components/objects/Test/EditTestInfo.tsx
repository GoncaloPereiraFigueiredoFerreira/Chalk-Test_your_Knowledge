import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { useState } from "react";

interface EditTestInfopProps {
  testInfo: {
    type: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  };
  saveEdit: (state: {
    type: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  }) => void;
  cancelEdit: (state: {
    type: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  }) => void;
}

export function EditTestInfo({
  testInfo,
  saveEdit,
  cancelEdit,
}: EditTestInfopProps) {
  const [type, setType] = useState(testInfo.type);
  const [conclusion, setConclusion] = useState(testInfo.conclusion);
  const [title, setTitle] = useState(testInfo.title);
  const [globalInstructions, setGlobalInstructions] = useState(
    testInfo.globalInstructions
  );

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 px-16 pb-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() =>
              saveEdit({
                type: type,
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            Guardar e fechar
          </button>
        </div>
        <div className="flex flex-col px-4 gap-4">
          <strong>Título:</strong>
          <div className="px-4">
            <input
              className="rounded-lg bg-input-2"
              placeholder="Novo Teste"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
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
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() =>
              saveEdit({
                type: type,
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            Guardar e fechar
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() =>
              cancelEdit({
                type: type,
                conclusion: conclusion,
                title: title,
                globalInstructions: globalInstructions,
              })
            }
          >
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
