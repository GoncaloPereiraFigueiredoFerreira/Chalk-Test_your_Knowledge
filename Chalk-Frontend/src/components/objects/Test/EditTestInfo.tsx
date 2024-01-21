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
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 bg-white dark:bg-black">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Editar
          </label>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
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
              className="rounded-lg border-[#dddddd] focus:ring-0 dark:bg-gray-600 dark:border-gray-600 dark:focus:border-gray-600"
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
          <div className="px-4">
            <TextareaBlock
              toolbar={true}
              rows={6}
              placeholder="Coloque aqui as instruções globais..."
              value={globalInstructions}
              onChange={(value) => setGlobalInstructions(value)}
            />
          </div>
          <strong>Conclusão:</strong>
          <div className="px-4">
            <TextareaBlock
              toolbar={true}
              rows={6}
              placeholder="Coloque aqui uma conclusão..."
              value={conclusion}
              onChange={(value) => setConclusion(value)}
            />
          </div>
        </div>
        <div className="flex gap-2">
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
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
            className="flex p-3 items-center gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
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
