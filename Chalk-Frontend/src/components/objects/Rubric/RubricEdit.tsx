import {
  Criteria,
  Rubric,
  RubricContext,
  Standard,
  createNewCriteria,
  StardardLevels,
} from "./Rubric";
import "../Exercise/Exercise.css";

export function standardLevelToString(num: string) {
  switch (num) {
    case StardardLevels.NULL:
    case "NULL":
      return "Reduzido";
    case StardardLevels.SATISFACTORY:
    case "SATISFACTORY":
      return "Não Satisfaz";
    case StardardLevels.SATISFACTORY:
    case "SATISFACTORY":
      return "Satisfaz";
    case StardardLevels.WELL_DONE:
    case "WELL_DONE":
      return "Satisfaz Bastante";
    case StardardLevels.EXCELENT:
    case "EXCELENT":
      return "Excelente";
  }
  return "Não computado";
}

export function RubricEdit(rubric: Rubric, setState: Function) {
  const addCriteria = () => {
    const newCriteriaL: Criteria[] = [...rubric.criteria];
    const newCriteria: Criteria = createNewCriteria();
    newCriteriaL.push(newCriteria);
    setState({ criteria: newCriteriaL });
  };

  const remCriteria = (index: number) => {
    const newCriteriaL: Criteria[] = [...rubric.criteria].filter((_, ind) => {
      return ind !== index;
    });
    setState({ criteria: newCriteriaL });
  };

  const changeCriteriaTitle = (index: number, title: string) => {
    const newCriteria: Criteria = { ...rubric.criteria[index] };
    newCriteria.title = title;
    const newCriteriaL: Criteria[] = [...rubric.criteria];
    newCriteriaL[index] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeCriteriaPoints = (index: number, points: string) => {
    const newCriteria: Criteria = { ...rubric.criteria[index] };
    newCriteria.points = Number.parseInt(points);
    const newCriteriaL: Criteria[] = [...rubric.criteria];
    newCriteriaL[index] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeStandardDescription = (
    critIndex: number,
    standIndex: number,
    description: string
  ) => {
    const newStandard: Standard = {
      ...rubric.criteria[critIndex].standards[standIndex],
    };
    newStandard.description = description;

    const newStandards: Standard[] = [...rubric.criteria[critIndex].standards];
    newStandards[standIndex] = newStandard;

    const newCriteria: Criteria = { ...rubric.criteria[critIndex] };
    newCriteria.standards = newStandards;

    const newCriteriaL: Criteria[] = [...rubric.criteria];
    newCriteriaL[critIndex] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeStandardPercentage = (
    critIndex: number,
    standIndex: number,
    percentage: string
  ) => {
    const newStandard: Standard = {
      ...rubric.criteria[critIndex].standards[standIndex],
    };
    newStandard.percentage = Number.parseInt(percentage);

    const newStandards: Standard[] = [...rubric.criteria[critIndex].standards];
    newStandards[standIndex] = newStandard;

    const newCriteria: Criteria = { ...rubric.criteria[critIndex] };
    newCriteria.standards = newStandards;

    const newCriteriaL: Criteria[] = [...rubric.criteria];
    newCriteriaL[critIndex] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  function renderCriteria() {
    return (
      <div className="flex-col space-y-16 mb-3">
        {rubric.criteria.map((crt, critIndex) => {
          return (
            <div className="flex-col space-y-4 border-t-2 pt-3 border-t-black dark:border-[#dddddd]">
              <div className="flex flex-row space-x-4">
                <div className="flex flex-col w-3/5">
                  <label
                    htmlFor="title-inpt"
                    className="font-medium rounded-md dark:text-white"
                  >
                    Título do Critério
                  </label>
                  <input
                    id="title-inpt"
                    type="text"
                    className="rounded-lg w-full border-2 border-slate-300 focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
                    value={crt.title}
                    onChange={(e) =>
                      changeCriteriaTitle(critIndex, e.target.value)
                    }
                  ></input>
                </div>
                <div className="flex flex-col">
                  <label
                    htmlFor="points-inpt "
                    className="font-medium rounded-md dark:text-white"
                  >
                    Percentagem da Cotação
                  </label>
                  <input
                    id="points-inpt"
                    type="number"
                    className="rounded-lg w-full border-2 border-slate-300 focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
                    value={crt.points}
                    min={0}
                    max={100}
                    onChange={(e) =>
                      changeCriteriaPoints(critIndex, e.target.value)
                    }
                  ></input>
                </div>
              </div>
              <div className="mr-2 flex flex-col gap-2">
                {crt.standards
                  .sort(function (a, b) {
                    // Compare the 2 dates
                    if (a.title < b.title) return -1;
                    if (a.title > b.title) return 1;
                    return 0;
                  })
                  .map((standard, standIndex) => {
                    return (
                      <div className=" flex flex-row gap-4">
                        <p className="font-medium dark:text-white my-auto w-1/4">
                          {standardLevelToString(standard.title)}
                        </p>
                        <input
                          type="number"
                          className="rounded-lg w-fit border-2 border-slate-300 focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
                          value={standard.percentage}
                          min={0}
                          max={100}
                          onChange={(e) =>
                            changeStandardPercentage(
                              critIndex,
                              standIndex,
                              e.target.value
                            )
                          }
                        ></input>
                        <input
                          type="text"
                          className="rounded-lg w-full border-2 border-slate-300 focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
                          value={standard.description}
                          onChange={(e) =>
                            changeStandardDescription(
                              critIndex,
                              standIndex,
                              e.target.value
                            )
                          }
                          placeholder="Descrição"
                        ></input>
                      </div>
                    );
                  })}
              </div>
              <button
                className=" mx-2 p-1 px-2 text-md rounded-md float-right btn-base-color active:scale-95"
                onClick={() => remCriteria(critIndex)}
              >
                Eliminar
              </button>
            </div>
          );
        })}
      </div>
    );
  }

  return (
    <>
      <Rubric context={{ context: RubricContext.PREVIEW }} rubric={rubric} />
      <button
        className=" w-full text-center self-center cursor-pointer p-1 my-2 rounded-md btn-base-color active:scale-95"
        onClick={() => addCriteria()}
      >
        Adicionar critério
      </button>
      {renderCriteria()}
    </>
  );
}
