import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { useContext, useEffect, useState } from "react";
import { FiSave } from "react-icons/fi";
import { IoClose } from "react-icons/io5";
import { DropdownBlock } from "../../interactiveElements/DropdownBlock";
import { APIContext } from "../../../APIContext";
import { Course } from "../../../UserContext";
import { Tag } from "../Exercise/Exercise";

interface EditTestInfopProps {
  testInfo: {
    type: string;
    conclusion: string;
    visibility: string;
    title: string;
    courseId: string;
    globalInstructions: string;
    tags: Tag[];
  };
  saveEdit: (state: {
    type: string;
    conclusion: string;
    visibility: string;
    title: string;
    courseId: string;
    globalInstructions: string;
  }) => void;
  cancelEdit: () => void;
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
  const { contactBACK } = useContext(APIContext);
  const [type] = useState(testInfo.type);
  const [conclusion, setConclusion] = useState(testInfo.conclusion);
  const [course, setCourse] = useState(testInfo.courseId);
  const [courses, setCourses] = useState<Course[]>([]);

  const [visibility, setVisibility] = useState(
    testInfo.visibility
      ? translateVisibilityToString(testInfo.visibility)
      : "Público"
  );
  const [title, setTitle] = useState(testInfo.title);
  const [globalInstructions, setGlobalInstructions] = useState(
    testInfo.globalInstructions
  );

  useEffect(() => {
    if (visibility === "Curso") {
      contactBACK("courses", "GET", { page: "0", itemsPerPage: "50" }).then(
        (page) => {
          let courses = page.items;
          let courseL: Course[] = [];
          courses.map((c: any) => {
            courseL.push({ id: c.id, name: c.name });
          });
          setCourses(courseL);
        }
      );
    }
  }, [visibility]);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max text-black dark:text-white">
        <div className="flex w-full justify-between mt-8 mb-3 px-4 pb-6 border-b-2 border-slate-400 dark:border-slate-600">
          <label className="flex text-4xl text-slate-600 dark:text-white">
            Editar
          </label>
          <button
            className="flex py-3 px-4 items-center gap-2 text-base rounded-lg font-medium btn-base-color group  transition-all duration-75 ease-in-out active:scale-90"
            onClick={() =>
              saveEdit({
                type: type,
                visibility: translateStringToVisibility(visibility),
                conclusion: conclusion,
                title: title,
                courseId: course,
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
              className="rounded-lg w-full border-2 border-slate-300 focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
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
            {visibility === "Curso" && (
              <DropdownBlock
                style="rounded-lg h-[42.19px]"
                options={[...courses].map((c) => {
                  return c.name;
                })}
                chosenOption={[...courses].find((c) => c.id === course)?.name}
                setChosenOption={(option) => {
                  let id: string = "";
                  courses.map((temp) => {
                    if (temp.name === option) id = temp.id;
                  });
                  setCourse(id);
                }}
                text="curso"
                placement="bottom"
              ></DropdownBlock>
            )}
          </div>
          <strong>Instruções globais:</strong>
          <TextareaBlock
            toolbar={true}
            rows={6}
            placeholder="Coloque aqui as instruções globais..."
            value={globalInstructions}
            onChange={(value) => setGlobalInstructions(value)}
          />
          <strong>Conclusão:</strong>
          <TextareaBlock
            toolbar={true}
            rows={6}
            placeholder="Coloque aqui uma conclusão..."
            value={conclusion}
            onChange={(value) => setConclusion(value)}
          />
        </div>
        <div className="flex gap-2 p-4 border-t-2 border-slate-400 dark:border-slate-600">
          <button
            className="flex py-3 px-4 items-center gap-2 text-base rounded-lg font-medium btn-base-color group  transition-all duration-75 ease-in-out active:scale-90"
            onClick={() =>
              saveEdit({
                type: type,
                visibility: translateStringToVisibility(visibility),
                conclusion: conclusion,
                title: title,
                courseId: course,
                globalInstructions: globalInstructions,
              })
            }
          >
            <FiSave className="size-5" />
            Guardar e fechar
          </button>
          <button
            className="flex py-3 px-4 items-center gap-2 text-base rounded-lg font-medium btn-base-color group  transition-all duration-75 ease-in-out active:scale-90"
            onClick={() => cancelEdit()}
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
