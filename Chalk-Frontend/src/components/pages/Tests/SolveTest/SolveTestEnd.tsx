import { useContext } from "react";
import { SolveTestContext } from "./SolveTest";

export function SolveTestEnd() {
  const context = useContext(SolveTestContext);
  return (
    <div className="dark:text-white">
      <p className="text-2xl m-20">
        <strong>Completaste o teu teste!</strong>
      </p>
      <p className="ml-20 ">
        Verifica na página das avaliações a tua performance!
      </p>

      <p className="ml-20 ">{context.test.conclusion}</p>
    </div>
  );
}
