import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { APIContext } from "../../../APIContext";
import { Modal, TextInput } from "flowbite-react";
import { IoSearch } from "react-icons/io5";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";

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
    <div className="flex flex-col w-full overflow-auto">
      <div className="flex w-full justify-around px-4 gap-16">
        <div className="relative w-1/2 justify-center">
          <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1.5 pointer-events-none">
            <IoSearch className="size-5 text-slate-600 dark:text-slate-400" />
          </div>
          <input
            type="text"
            className="py-2.5 pl-10 pr-24 rounded-lg w-full border-2 bg-inherit text-black border-slate-400 dark:text-white placeholder:dark:text-gray-400 dark:border-slate-700 focus:ring-0 focus:border-slate-400 focus:dark:border-slate-700"
            placeholder="Search..."
            value={searchKey}
            onChange={(text) => setSearch(text.target.value)}
            required
          />
        </div>
        <div className="flex gap-4">
          <button
            className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
            onClick={() => setOpenModal(true)}
          >
            Adicionar Aluno
          </button>
          {viewMode === "grid" ? (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewMode("row")}
            >
              <FaListUl className="size-5 scale-90" />
              <p>Lista</p>
            </button>
          ) : (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewMode("grid")}
            >
              <HiViewGrid className="size-5 scale-110" />
              <p>Grelha</p>
            </button>
          )}
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
                viewMode === "grid" ? "w-20 h-20 rounded-full mx-auto mb-4" : ""
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
            <button onClick={() => removeStudent(student.email)}>Remove</button>
          </div>
        ))}
      </div>
      <Modal dismissible show={openModal} onClose={() => setOpenModal(false)}>
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
          <button
            className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
            onClick={() => addStudent()}
          >
            Criar novo Estudante
          </button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
