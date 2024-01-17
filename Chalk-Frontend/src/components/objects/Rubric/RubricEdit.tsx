import {
  Criteria,
  Rubric,
  RubricContext,
  Standard,
  createNewCriteria,
} from "./Rubric";
import "../Exercise/Exercise.css";

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
                <label
                  htmlFor="title-inpt"
                  className="font-medium rounded-md dark:text-white"
                >
                  Title for the Criteria
                </label>
                <input
                  id="title-inpt"
                  type="text"
                  className="rounded-md w-full"
                  value={crt.title}
                  onChange={(e) =>
                    changeCriteriaTitle(critIndex, e.target.value)
                  }
                ></input>
                <label
                  htmlFor="points-inpt "
                  className="font-medium rounded-md dark:text-white"
                >
                  Percentage of Cotation
                </label>
                <input
                  id="points-inpt"
                  type="number"
                  className="rounded-md "
                  value={crt.points}
                  min={0}
                  max={100}
                  onChange={(e) =>
                    changeCriteriaPoints(critIndex, e.target.value)
                  }
                ></input>
              </div>
              <div className="mr-2 grid grid-cols-3 gap-4">
                {crt.standards.map((standard, standIndex) => {
                  return (
                    <>
                      <p className="font-medium dark:text-white">
                        {standard.title}
                      </p>
                      <input
                        type="number"
                        className="rounded-md "
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
                        className="rounded-md"
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
                    </>
                  );
                })}
              </div>
              <button
                className="edit-btn mx-2 px-1 float-right bg-btn-4-2"
                onClick={() => remCriteria(critIndex)}
              >
                Remove
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
        className=" w-fit self-center cursor-pointer p-1 rounded-md bg-btn-4-2"
        onClick={() => addCriteria()}
      >
        Add a new Criteria
      </button>
      {renderCriteria()}
    </>
  );
}
