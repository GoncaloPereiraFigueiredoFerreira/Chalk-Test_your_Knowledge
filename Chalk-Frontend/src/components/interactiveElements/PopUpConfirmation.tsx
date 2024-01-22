import "./PopUp.tsx";

type PopUpProps = {
  show: boolean;
  children: JSX.Element;
  closePopUp: () => void;
};

export function PopUp({ show, children, closePopUp }: PopUpProps) {
  return (
    PopUp({ show, children, closePopUp })
  );
}
