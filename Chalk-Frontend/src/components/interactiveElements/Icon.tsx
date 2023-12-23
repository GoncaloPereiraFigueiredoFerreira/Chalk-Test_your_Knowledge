import { IconContext } from "react-icons";

export function Icon(children: any) {
  return (
    <IconContext.Provider value={{ className: "shared-class", size: "70" }}>
      {children}
    </IconContext.Provider>
  );
}
