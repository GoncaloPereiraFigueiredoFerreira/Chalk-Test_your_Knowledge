import {
  CheckListIcon,
  EyeSlashIcon,
  GarbageIcon,
  LockIcon,
  PenIcon,
} from "../SVGImages/SVGImages";
import "./Exercise.css";

type ExerciseProps = {
  name: String;
  visibility: String; // privado, não listado, curso, institucional ou público
  type: String; // escolha múltipla, resposta aberta, verdadeiro e falso, preenchimento de espaços e código
  author: String;
  enunciado: String;
  problema: String | String[];
  isSelected: boolean;
};

export function Exercise({
  name,
  visibility,
  type,
  author,
  enunciado,
  problema,
  isSelected,
}: ExerciseProps) {
  return (
    <>
      <div className="flex w-full items-center justify-between px-5 py-2.5 gap-4 rounded-lg text-sm font-normal bg-gray-200 dark:text-white dark:bg-gray-600 hover:bg-gray-[550] duration-100 group">
        <div className="flex flex-col gap-1.5">
          <label className="font-medium text-xl">{name}</label>
          <div className="flex ml-1 gap-2">
            <div className="bg-yellow-600 tag-exercise">Matemática</div>
            <div className="bg-blue-600 tag-exercise">4º ano</div>
            <div className="bg-green-600 tag-exercise">escolinha</div>
          </div>
        </div>
        <div
          className={`${
            isSelected
              ? "mr-[204px] pr-4 border-r"
              : "group-hover:mr-[204px] group-hover:pr-4 group-hover:border-r"
          } flex justify-end h-full gap-4 z-10 bg-inherit duration-150 w-56 border-r-gray-300 dark:border-r-gray-500`}
        >
          <div className="flex flex-col justify-center">
            <div className="caracteristics-exercise">
              <LockIcon size="size-4" />
              {visibility}
            </div>
            <label className="caracteristics-exercise">
              <CheckListIcon size="size-4" />
              {type}
            </label>
          </div>
        </div>
        <div className={"flex absolute right-[86px] items-center gap-4"}>
          <button className="btn-options-exercise gray-icon">
            <PenIcon size="size-5" />
            Editar
          </button>
          <button className="btn-options-exercise gray-icon">
            <EyeSlashIcon size="size-5" />
            Visibilidade
          </button>
          <button className="btn-options-exercise gray-icon">
            <GarbageIcon size="size-5" />
            Eliminar
          </button>
        </div>
      </div>
    </>
  );
}
