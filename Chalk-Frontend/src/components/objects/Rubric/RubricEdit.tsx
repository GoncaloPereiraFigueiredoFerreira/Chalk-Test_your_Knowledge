import { useState } from "react";
import {
  Criteria,
  Rubric,
  RubricContext,
  Standard,
  createNewCriteria,
} from "./Rubric";

export function RubricEdit(rubric: Rubric) {
  const [state, setState] = useState<Rubric>(rubric);

  const addCriteria = () => {
    let newCriteriaL: Criteria[] = [...state.criteria];
    let newCriteria: Criteria = createNewCriteria();
    newCriteriaL.push(newCriteria);
    setState({ criteria: newCriteriaL });
  };

  const remCriteria = (index: number) => {
    let newCriteriaL: Criteria[] = [...state.criteria].filter((_, ind) => {
      return ind !== index;
    });
    setState({ criteria: newCriteriaL });
  };

  const changeCriteriaTitle = (index: number, title: string) => {
    let newCriteria: Criteria = { ...state.criteria[index] };
    newCriteria.title = title;
    let newCriteriaL: Criteria[] = [...state.criteria];
    newCriteriaL[index] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeCriteriaPoints = (index: number, points: string) => {
    let newCriteria: Criteria = { ...state.criteria[index] };
    newCriteria.points = Number.parseInt(points);
    let newCriteriaL: Criteria[] = [...state.criteria];
    newCriteriaL[index] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeStandardDescription = (
    critIndex: number,
    standIndex: number,
    description: string
  ) => {
    let newStandard: Standard = {
      ...state.criteria[critIndex].standards[standIndex],
    };
    newStandard.description = description;

    let newStandards: Standard[] = [...state.criteria[critIndex].standards];
    newStandards[standIndex] = newStandard;

    let newCriteria: Criteria = { ...state.criteria[critIndex] };
    newCriteria.standards = newStandards;

    let newCriteriaL: Criteria[] = [...state.criteria];
    newCriteriaL[critIndex] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  const changeStandardPercentage = (
    critIndex: number,
    standIndex: number,
    percentage: string
  ) => {
    let newStandard: Standard = {
      ...state.criteria[critIndex].standards[standIndex],
    };
    newStandard.percentage = Number.parseInt(percentage);

    let newStandards: Standard[] = [...state.criteria[critIndex].standards];
    newStandards[standIndex] = newStandard;

    let newCriteria: Criteria = { ...state.criteria[critIndex] };
    newCriteria.standards = newStandards;

    let newCriteriaL: Criteria[] = [...state.criteria];
    newCriteriaL[critIndex] = newCriteria;

    setState({ criteria: newCriteriaL });
  };

  function renderCriteria() {
    return (
      <div className="flex-col space-y-8">
        {state.criteria.map((crt, critIndex) => {
          return (
            <div className="flex-col space-y-4 border-t-2 pt-3 border-t-black">
              <div className="flex flex-row space-x-4">
                <label htmlFor="title-inpt" className="font-medium">
                  Title for the Criteria
                </label>
                <input
                  id="title-inpt"
                  type="text"
                  value={crt.title}
                  onChange={(e) =>
                    changeCriteriaTitle(critIndex, e.target.value)
                  }
                ></input>
                <label htmlFor="points-inpt " className="font-medium">
                  Percentage of Cotation
                </label>
                <input
                  id="points-inpt"
                  type="number"
                  value={crt.points}
                  min={0}
                  max={100}
                  onChange={(e) =>
                    changeCriteriaPoints(critIndex, e.target.value)
                  }
                ></input>

                <button type="button" onClick={() => remCriteria(critIndex)}>
                  X
                </button>
              </div>
              <div className="mr-2 grid grid-cols-3 gap-4">
                {crt.standards.map((standard, standIndex) => {
                  return (
                    <>
                      <p className="font-medium">{standard.title}</p>
                      <input
                        type="number"
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
                        value={standard.description}
                        onChange={(e) =>
                          changeStandardDescription(
                            critIndex,
                            standIndex,
                            e.target.value
                          )
                        }
                      ></input>
                    </>
                  );
                })}
              </div>
            </div>
          );
        })}
      </div>
    );
  }

  return (
    <>
      <Rubric context={RubricContext.PREVIEW} rubric={state} />
      <button type="button" onClick={() => addCriteria()}>
        Add a new Criteria
      </button>
      {renderCriteria()}
    </>
  );
}
