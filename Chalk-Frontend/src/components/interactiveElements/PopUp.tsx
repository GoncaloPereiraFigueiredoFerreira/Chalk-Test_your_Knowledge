import "./PopUp.css";

type PopUpProps = {
  show: boolean;
  children: JSX.Element;
  closePopUp: () => void;
};

export function PopUp({ show, children, closePopUp }: PopUpProps) {
  return (
    <div
      className={`${
        show ? "" : "translate-x-full"
      } fixed top-0 right-0 z-30 duration-100`}
    >
      <div className="flex flex-col w-screen h-screen">
        <button className="popup-btn" onClick={() => closePopUp()} />
        <div className="flex w-full">
          <button className="popup-btn" onClick={() => closePopUp()} />
          <div className="min-w-max min-h-max max-h-[90vh] overflow-auto p-6 rounded-xl shadow-2xl shadow-slate-700 dark:shadow-black bg-white dark:bg-slate-800">
            {children}
          </div>
          <button className="popup-btn" onClick={() => closePopUp()} />
        </div>
        <button className="popup-btn" onClick={() => closePopUp()} />
      </div>
    </div>
  );
}
