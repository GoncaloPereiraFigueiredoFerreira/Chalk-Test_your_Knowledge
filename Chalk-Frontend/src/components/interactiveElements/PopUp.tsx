import "./PopUp.css";

type PopUpProps = {
  children: JSX.Element;
  closePopUp: () => void;
};

export function PopUp({ children, closePopUp }: PopUpProps) {
  return (
    <>
      <div className="fixed top-0 right-0 z-30 flex flex-col w-screen h-screen">
        <button className="popup-btn" onClick={() => closePopUp()} />
        <div className="flex w-full min-h-max">
          <button className="popup-btn" onClick={() => closePopUp()} />
          <div className="min-w-max min-h-max p-3 rounded-lg bg-white dark:bg-gray-600">
            {children}
          </div>
          <button className="popup-btn" onClick={() => closePopUp()} />
        </div>
        <button className="popup-btn" onClick={() => closePopUp()} />
      </div>
    </>
  );
}
