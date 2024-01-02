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
    <Modal dismissible show={open} size="md" onClose={close} popup>
      <Modal.Header />
      <Modal.Body>
        <div className="space-y-6">
          <h3 className="text-xl font-medium text-gray-900 dark:text-white">
            Criar um Novo Grupo
          </h3>
          <div>
            <div className="mb-2 block">
              <label htmlFor="name">Nome do Grupo</label>
            </div>
            <input
              type="text"
              id="name"
              value={name}
              onChange={(event) => setGroupName(event.target.value)}
              required
            />
            <button
              type="button"
              className="p-4 rounded-lg bg-blue-300 dark:bg-blue-800"
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
