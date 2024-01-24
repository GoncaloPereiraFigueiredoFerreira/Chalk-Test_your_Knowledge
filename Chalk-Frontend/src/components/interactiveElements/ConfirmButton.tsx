import React, { useState, ReactNode } from "react";
import { Button, Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";

type OptionalEventHandler = (
  event?: React.SyntheticEvent<Element, Event>
) => void;

interface ConfirmButtonProps {
  onConfirm: OptionalEventHandler;
  onCancel?: OptionalEventHandler;
  confirmationMessage: string;
  button: ReactNode; // Use ReactNode for the button element
}

const ConfirmButton: React.FC<ConfirmButtonProps> = ({
  onConfirm,
  onCancel,
  confirmationMessage,
  button,
}) => {
  const [openModal, setOpenModal] = useState(false);

  return (
    <>
      {React.cloneElement(button as React.ReactElement, {
        onClick: (event: React.MouseEvent) => {
          event.stopPropagation(); // Prevents the event from reaching parent components
          setOpenModal(true);
        },
      })}
      <Modal
        dismissible
        show={openModal}
        size="md"
        onClose={() => setOpenModal(false)}
        popup
      >
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">
              {confirmationMessage}
            </h3>
            <div className="flex justify-center gap-4">
              <Button
                color="failure"
                onClick={(event: React.MouseEvent) => {
                  event.stopPropagation();
                  setOpenModal(false);
                  onConfirm && onConfirm();
                }}
              >
                Confirmar
              </Button>
              <Button
                color="gray"
                onClick={(event: React.MouseEvent) => {
                  event.stopPropagation();
                  setOpenModal(false);
                  onCancel && onCancel();
                }}
              >
                Cancelar
              </Button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default ConfirmButton;
