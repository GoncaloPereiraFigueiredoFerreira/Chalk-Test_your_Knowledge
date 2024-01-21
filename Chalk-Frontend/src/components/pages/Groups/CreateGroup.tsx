import { useContext, useState } from "react";
import { Button, Modal, TextInput } from "flowbite-react";
import { APIContext } from "../../../APIContext";

export function CreateGroupModal({ open, close }: any) {
  const [name, setGroupName] = useState("");
  const { contactBACK } = useContext(APIContext);

  function onCloseModal() {
    if (name !== "")
      contactBACK(
        "courses",
        "POST",
        undefined,
        {
          description: "",
          name: name,
        },
        "none"
      ).then(() => {
        setGroupName("");
        close();
      });
  }

  const handleKeyDown = (event: any) => {
    if (event.key === "Enter") {
      // If Enter key is pressed, prevent the default behavior and call the addStudent function
      event.preventDefault();
      onCloseModal();
    }
  };

  return (
    <Modal
      dismissible
      show={open}
      size="md"
      color="gray-600"
      onClose={close}
      popup
    >
      <Modal.Header />
      <Modal.Body>
        <div className="space-y-6 w-full ">
          <h3 className="text-xl font-medium text-black dark:text-white">
            Criar um Novo Grupo
          </h3>
          <div>
            <div className="mb-2 block w-full dark:text-white">
              <label htmlFor="name">Nome do Grupo</label>
            </div>
            <TextInput
              type="text"
              id="name"
              className="w-full rounded-md"
              value={name}
              onChange={(event) => setGroupName(event.target.value)}
              onKeyDown={handleKeyDown}
              required
            />
            <button
              className="p-2 mt-4 w-full transition-all duration-200 ease-in-out rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black  hover:scale-105"
              onClick={() => onCloseModal()}
            >
              Submeter Novo Grupo
            </button>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => onCloseModal()}>Submeter Novo Grupo</Button>
      </Modal.Footer>
    </Modal>
  );
}
