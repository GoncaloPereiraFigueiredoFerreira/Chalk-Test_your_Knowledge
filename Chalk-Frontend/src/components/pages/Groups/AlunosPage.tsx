import { useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import {
  GridIcon,
  ListIcon,
  SearchIcon,
} from "../../objects/SVGImages/SVGImages";

interface Student {
  id: string;
  name: string;
  email: string;
}
export function AlunosPage() {
  const [studentList, setStudentList] = useState<Student[]>([]);
  const [viewMode, setViewMode] = useState<"grid" | "row">("grid");
  const { id } = useParams();
  const [searchKey, setSearch] = useState("");
  useEffect(
    () =>
      setStudentList([
        { id: "1", name: "John Doe", email: "john.doe@example.com" },
        { id: "2", name: "John Doe", email: "john.doe@example.com" },
        { id: "3", name: "John Doe", email: "john.doe@example.com" },
        { id: "4", name: "John Doe", email: "john.doe@example.com" },
        { id: "5", name: "John Doe", email: "john.doe@example.com" },
      ]),
    [id]
  );
  const addStudent = () => {
    const newStudent: Student = {
      id: "1",
      name: "Luis",
      email: "john.doe@example.com",
    };
    setStudentList([...studentList, newStudent]);
  };

  const removeStudent = (idToRemove: string) => {
    setStudentList((prevlist) =>
      prevlist.filter((student) => student.id !== idToRemove)
    );
  };

  return (
    <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-2-1">
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <div className="">
            <div className="relative w-full justify-center">
              <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
                <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
              </div>
              <input
                type="text"
                className="py-2.5 pl-10 pr-24 rounded-lg w-full z-20 border bg-input-1"
                placeholder="Search..."
                value={searchKey}
                onChange={(text) => setSearch(text.target.value)}
                required
              />
            </div>
          </div>
          <div className="flex  ">
            <button
              className=" items-center justify-center w-24 h-10 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10   dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
              onClick={addStudent}
            >
              Adicionar Estudante
            </button>
            <button
              className="px-2 w-12"
              onClick={() => setViewMode(viewMode === "grid" ? "row" : "grid")}
            >
              {viewMode === "grid" ? (
                <ListIcon size="size-8" />
              ) : (
                <GridIcon size="size-8" />
              )}
            </button>
          </div>
        </div>

        <div
          className={
            viewMode === "grid"
              ? "grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4 py-10 px-4"
              : "flex flex-col gap-4 px-10"
          }
        >
          {studentList.map((student, index) => (
            <div key={index} className="bg-white p-6 rounded-lg shadow-md">
              <img
                className={
                  viewMode === "grid"
                    ? "w-20 h-20 rounded-full mx-auto mb-4"
                    : ""
                }
                src={
                  viewMode === "grid"
                    ? student.name == "Luis"
                      ? "https://i.pinimg.com/736x/7a/3c/37/7a3c375db24b716ada1f81f057d9f4cd.jpg"
                      : "https://wowxwow.com/wp-content/uploads/2020/05/Redmer-Hoekstra-Hedgehog-on-Goose.jpg"
                    : ""
                }
                alt={viewMode === "grid" ? "Student Avatar" : ""}
              />
              <h2 className="text-xl font-semibold text-gray-800 mb-2">
                {student.name}
              </h2>

              <p className="text-sm text-gray-600">ID: {student.id}</p>
              <p className="text-sm text-gray-600">Email: {student.email}</p>
              <button onClick={() => removeStudent(student.id)}>Remove</button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
