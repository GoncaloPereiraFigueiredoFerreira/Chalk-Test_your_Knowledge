import { useContext, useState } from "react";
import { Modal } from "flowbite-react";
import { APIContext } from "../../../APIContext";

export function CreateGroupModal({ open, close }: any) {
  const [name, setGroupName] = useState("");
  const { contactBACK } = useContext(APIContext);

  function onCloseModal() {
    // if name !== ""
    // contactBACK("course", "POST", undefined,{course: { description: "", instituitionID: "", name: name }});
    setGroupName("");
    close();
  }

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
            <input
              className="w-full rounded-md"
              type="text"
              id="name"
              value={name}
              onChange={(event) => setGroupName(event.target.value)}
              required
            />
            <button
              className="p-2 mt-4 w-full transition-all duration-200 ease-in-out rounded-lg bg-btn-4-2  hover:scale-105"
              onClick={() => onCloseModal()}
            >
              Submeter Novo Grupo
            </button>
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
}
