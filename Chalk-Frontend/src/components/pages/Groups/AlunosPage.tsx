import { useContext, useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import {
  GridIcon,
  ListIcon,
  SearchIcon,
} from "../../objects/SVGImages/SVGImages";
import { APIContext } from "../../../APIContext";

import { Button, Modal, TextInput } from "flowbite-react";
import { UserContext, UserRole } from "../../../UserContext";

interface Student {
  id: string;
  name: string;
  photoPath: string;
  email: string;
}
export function AlunosPage() {
  const [studentList, setStudentList] = useState<Student[]>([]);
  const [viewMode, setViewMode] = useState<"grid" | "row">("grid");
  const { id } = useParams();
  const [searchKey, setSearch] = useState("");
  const { contactBACK } = useContext(APIContext);
  const [email, setEmail] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const { user } = useContext(UserContext);

  useEffect(() => {
    contactBACK("courses/" + id + "/students", "GET", {
      page: "0",
      itemsPerPage: "50",
    }).then((page) => {
      let students = page.items;
      setStudentList(students);
    });
  }, [id]);

  const addStudent = () => {
    if (email !== "") {
      contactBACK(
        "courses/" + id + "/students/add",
        "POST",
        undefined,
        [email],
        "none"
      ).then(() => {
        setEmail("");
        setOpenModal(false);
      });
    }
  };

  const handleKeyDown = (event: any) => {
    if (event.key === "Enter") {
      // If Enter key is pressed, prevent the default behavior and call the addStudent function
      event.preventDefault();
      addStudent();
    }
  };

  const removeStudent = (emailToRemove: string) => {
    contactBACK(
      "courses/" + id + "/students/remove",
      "DELETE",
      undefined,
      [emailToRemove],
      "none"
    ).then(() => {
      setStudentList((prevlist) =>
        prevlist.filter((student) => student.email !== emailToRemove)
      );
    });
  };

  const filteredItems: Student[] = studentList.filter((item) =>
    item.name.toLowerCase().includes(searchKey.toLowerCase())
  );

  return (
    <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-white dark:bg-black">
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <div className="">
            <div className="relative w-full justify-center">
              <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
                <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
              </div>
              <input
                type="text"
                className="py-2.5 pl-10 pr-24 rounded-lg w-full z-20 border text-black bg-white border-[#dddddd] dark:text-black dark:bg-gray-600 dark:border-gray-600 focus:ring-0 focus:border-[#dddddd] focus:dark:border-gray-600"
                placeholder="Search..."
                value={searchKey}
                onChange={(text) => setSearch(text.target.value)}
                required
              />
            </div>
          </div>
          <div className="flex  ">
            {user.user?.role === UserRole.SPECIALIST && (
              <Button
                className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                onClick={() => setOpenModal(true)}
              >
                Adicionar Aluno
              </Button>
            )}
            <Modal
              dismissible
              show={openModal}
              onClose={() => setOpenModal(false)}
            >
              <Modal.Header> Criar um Novo Grupo</Modal.Header>
              <Modal.Body>
                <div className="space-y-6">
                  <div>
                    <div className="mb-2 block">
                      <label htmlFor="name">Email do aluno:</label>
                    </div>
                    <TextInput
                      type="text"
                      id="email"
                      value={email}
                      onChange={(event) => setEmail(event.target.value)}
                      onKeyDown={handleKeyDown}
                      required
                    />
                  </div>
                </div>
              </Modal.Body>
              <Modal.Footer>
                <Button onClick={() => addStudent()}>
                  Criar novo Estudante
                </Button>
              </Modal.Footer>
            </Modal>
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
          {filteredItems.map((student, index) => (
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

              <p className="text-sm text-gray-600">Email: {student.email}</p>
              <button onClick={() => removeStudent(student.email)}>
                Remover
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
