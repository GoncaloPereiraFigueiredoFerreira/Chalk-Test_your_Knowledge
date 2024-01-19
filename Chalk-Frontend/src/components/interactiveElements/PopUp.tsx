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
        <div className="flex w-full min-h-max">
          <button className="popup-btn" onClick={() => closePopUp()} />
          <div className="min-w-max min-h-max p-6 rounded-xl shadow-2xl shadow-gray-800 bg-[#dddddd] dark:bg-gray-600">
            {children}
          </div>
          <button className="popup-btn" onClick={() => closePopUp()} />
        </div>
        <button className="popup-btn" onClick={() => closePopUp()} />
      </div>
    </div>
  );
}
