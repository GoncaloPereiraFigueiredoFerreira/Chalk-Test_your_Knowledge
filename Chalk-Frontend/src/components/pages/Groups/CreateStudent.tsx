import { useContext, useState } from "react";
import { Button, Modal, TextInput } from "flowbite-react";
import { APIContext } from "../../../APIContext";
import { useParams } from "react-router-dom";

interface Student {
  id: string;
  name: string;
  photoPath: string;
  email: string;
}

export function CreateStudentModal({ studentList, setStudentList }: any) {
  const { contactBACK } = useContext(APIContext);
  const [email, setEmail] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const { id } = useParams();

  const addStudent = () => {
    if (email !== "") {
      const newStudent: Student = {
        id: id!,
        name: "Luis",
        photoPath: "",
        email: email,
      };

      contactBACK("courses/" + id + "/students/add", "POST", undefined, [
        email,
      ]).then((response) => {
        setStudentList([...studentList, newStudent]);
        setEmail("");
        setOpenModal(false);
      });
    }
  };

  return (
    <>
      <Button onClick={() => setOpenModal(true)}>Adicionar Aluno</Button>
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
                id="name"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
              />
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => addStudent()}>Criar novo Estudante</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
