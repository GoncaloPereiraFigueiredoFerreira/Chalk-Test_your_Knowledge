import "../../interactiveElements/PopUpListExercise.css";
import { PopUp } from "../../interactiveElements/PopUp";
import { TextareaBlock } from "../../interactiveElements/TextareaBlock";

interface ExerciseSuggestionPopUpProps {
  show: boolean;
  closePopUp: () => void;
  onClose: () => void;
  input: string;
  setInput: (value: string) => void;
  text: string;
  setText: (value: string) => void;
}

export function ExerciseSuggestionPopUp({
  show,
  closePopUp,
  onClose,
  input,
  setInput,
  text,
  setText,
}: ExerciseSuggestionPopUpProps) {
  return (
    <PopUp show={show} closePopUp={closePopUp}>
      <>
        <label className="flex w-full max-w-4xl justify-between mb-4 px-4 pb-2.5 text-4xl text-slate-600 dark:text-white border-b-2 border-[#bbbbbb]">
          Sugestão de Exercício
        </label>
        <div className="flex flex-col text-xl p-4">
          <label className="pb-4">
            Defina o texto de base para a criação da pergunta e as diretrizes a
            seguir
          </label>
          <label className="pb-4">Texto de base:</label>
          <TextareaBlock
            toolbar={false}
            value={text}
            rows={3}
            onChange={(e) => {
              setText(e);
            }}
          ></TextareaBlock>
          <label className="pb-4">Diretrizes:</label>
          <TextareaBlock
            toolbar={false}
            value={input}
            rows={3}
            onChange={(e) => {
              setInput(e);
            }}
          ></TextareaBlock>
          <div className="flex justify-end mt-4 gap-4">
            <button
              onClick={() => closePopUp()}
              className="py-4 px-8 text-base rounded-lg font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
            >
              Cancelar
            </button>
            <button
              onClick={() => onClose()}
              className="py-4 px-8 text-base rounded-lg font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
            >
              Submeter
            </button>
          </div>
        </div>
      </>
    </PopUp>
  );
}
