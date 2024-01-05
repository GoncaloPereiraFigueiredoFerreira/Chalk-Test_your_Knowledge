import { useContext, useState } from "react";
import { Button, Modal, TextInput } from "flowbite-react";
import { APIContext } from "../../../APIContext";
import { UserContext } from "../../../UserContext";

export function CreateGroupModal({ open, close }: any) {
  const [name, setGroupName] = useState("");
  const { contactBACK } = useContext(APIContext);

  function onCloseModal() {
    if (name !== "")
      contactBACK("courses", "POST", undefined, {
        description: "",
        name: name,
      }).then(() => {
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
    <Modal dismissible show={open} size="md" onClose={close}>
      <Modal.Header> Criar um Novo Grupo</Modal.Header>
      <Modal.Body>
        <div className="space-y-6">
          <div>
            <div className="mb-2 block">
              <label htmlFor="name">Nome do Grupo</label>
            </div>
            <TextInput
              type="text"
              id="name"
              value={name}
              onChange={(event) => setGroupName(event.target.value)}
              onKeyDown={handleKeyDown}
              required
            />
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => onCloseModal()}>Submeter Novo Grupo</Button>
      </Modal.Footer>
    </Modal>
  );
}
