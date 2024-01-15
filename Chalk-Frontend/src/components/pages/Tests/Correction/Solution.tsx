import { Rubric, RubricContext } from "../../../objects/Rubric/Rubric";
import { LuGhost } from "react-icons/lu";
import { Rubrics } from "./Correction";

interface SolutionProps {
  exid: string;
  rubrics: Rubrics;
}

export function Solution({ exid, rubrics }: SolutionProps) {
  if (exid !== "")
    return (
      <>
        {rubrics[exid] ? (
          <Rubric
            context={{ context: RubricContext.PREVIEW }}
            rubric={rubrics[exid]!}
          />
        ) : (
          <div className="flex h-max items-center justify-center">
            <div className="flex flex-col items-center space-y-4">
              <LuGhost size={120} />
              <p className="text-xl">Rúbrica não encontrada!</p>
            </div>
          </div>
        )}
      </>
    );
  else
    return (
      <p className="text-gray-600">
        Selecione um exercício para ver os seus críterios de correção
      </p>
    );
}
