import { Link, Outlet, useParams } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import {
  CircularProgressbar,
  CircularProgressbarWithChildren,
  buildStyles,
} from "react-circular-progressbar";

import {
  GridIcon,
  ListIcon,
  SearchIcon,
} from "../../objects/SVGImages/SVGImages";
import "react-circular-progressbar/dist/styles.css";
import { SiMicrosoftexcel } from "react-icons/si";
import { AiTwotoneFileUnknown } from "react-icons/ai";
import Histogram from "../../objects/Charts/Histogram";
import { APIContext } from "../../../APIContext";

const exampleData = [
  {
    resolution: {
      id: "string",
      startDate: "2024-01-06T20:30:54.146Z",
      submissionDate: "2024-01-06T20:30:54.146Z",
      submissionNr: 0,
      totalPoints: 80,
      studentId: "string",
      status: "ongoing",
      groups: [
        {
          groupPoints: 30,
          resolutions: {
            additionalProp1: {
              resolutionId: "string",
              points: 10,
            },
            additionalProp2: {
              resolutionId: "string",
              points: 10,
            },
            additionalProp3: {
              resolutionId: "string",
              points: 10,
            },
          },
        },
        {
          groupPoints: 50,
          resolutions: {
            additionalProp1: {
              resolutionId: "string",
              points: 27,
            },
            additionalProp2: {
              resolutionId: "string",
              points: 23,
            },
          },
        },
      ],
      testId: "string",
    },
    student: {
      email: "hugoferreiranogueira@gmail.com",
      id: "string",
      name: "Hugo Nogueira",
    },
  },
  {
    resolution: {
      id: "string",
      startDate: "2024-01-06T20:30:54.146Z",
      submissionDate: "2024-01-06T20:30:54.146Z",
      submissionNr: 0,
      totalPoints: 30,
      studentId: "string",
      status: "ongoing",
      groups: [
        {
          groupPoints: 25,
          resolutions: {
            additionalProp1: {
              resolutionId: "string",
              points: 10,
            },
            additionalProp2: {
              resolutionId: "string",
              points: 10,
            },
            additionalProp3: {
              resolutionId: "string",
              points: 5,
            },
          },
        },
        {
          groupPoints: 5,
          resolutions: {
            additionalProp1: {
              resolutionId: "string",
              points: 5,
            },
            additionalProp2: {
              resolutionId: "string",
              points: 0,
            },
          },
        },
      ],
      testId: "string",
    },
    student: {
      email: "email@gmail.com",
      id: "string",
      name: "Nome diferente",
    },
  },
];

function lista_exercicios(groups: any): number[] {
  let exercicios = [];
  let count = 0;
  for (let group of groups) {
    count += 1;
    let nr = Object.keys(group.resolutions).length;
    for (let i = 1; i < nr + 1; i++) {
      exercicios.push(count + i / 10);
    }
  }
  return exercicios;
}

function lista_nota_exercicios(result: any): number[] {
  console.log("debug aqui");
  console.log(result);
  console.log("aqui tambem");
  let groups = result.groups;
  let nota_exercicios = [];
  for (let group of groups) {
    let ex: any;
    for (ex of Object.values(group.resolutions)) {
      nota_exercicios.push(ex.points);
    }
  }
  return nota_exercicios;
}

function ShowTestGrid(test: Result) {
  return (
    <div className=" max-w-lg  bg-white border-2 border-slate-300 rounded-lg shadow-lg shadow-slate-400 dark:bg-gray-800 dark:border-gray-700 overflow-hidden">
      <div className="py-4 px-12 ">
        {!test.status.toLowerCase().includes("revised") ? (
          <CircularProgressbarWithChildren value={0}>
            <AiTwotoneFileUnknown size="100" />
            <div style={{ fontSize: 12, marginTop: -5 }}>
              <strong>Not Evaluated Yet...</strong>
            </div>
          </CircularProgressbarWithChildren>
        ) : (
          <CircularProgressbar
            value={test.totalPoints}
            text={`${test.totalPoints}%`}
            styles={buildStyles({
              textColor: `rgba(${480 - test.totalPoints * 4.8}, ${
                4.8 * test.totalPoints
              }, ${0})`,
              pathColor: `rgba(${480 - test.totalPoints * 4.8}, ${
                4.8 * test.totalPoints
              }, ${0})`,
            })}
          />
        )}
      </div>

      <div className="p-5 bg-slate-300">
        <p className="mb-2  px-2 font-normal text-gray-700 dark:text-gray-400">
          <strong>Student:</strong> {test.studentId}
        </p>

        <div className="flex w-full px-2 justify-between">
          <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400">
            <strong>Grade:</strong>
            {!test.status.toLowerCase().includes("revised")
              ? "TBD"
              : ` ${test.totalPoints}%`}
          </div>
        </div>
      </div>
    </div>
  );
}

function ShowTestList(test: Result) {
  return (
    <div className="max-h-[78px] rounded-lg w-full bg-white dark:bg-black overflow-hidden">
      <div className="p-4 flex justify-between w-full">
        <div className="flex-col w-60">
          <p className="mb-1 font-normal text-gray-700 dark:text-gray-400">
            <strong>Student:</strong> {test.studentId}
          </p>
        </div>

        <div className="flex justify-end space-x-2 w-60">
          <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400 w-36">
            <strong>Grade:</strong>
            {!test.status.toLowerCase().includes("revised")
              ? "TBD"
              : ` ${test.totalPoints}%`}
          </div>
        </div>
      </div>
    </div>
  );
}

function ShowExcelLike(resultsList: ResultList) {
  return (
    <div className="container mx-auto my-8">
      <table className="table-auto border-collapse w-full">
        <thead>
          <tr>
            <th className="border bg-slate-400 px-4 py-2">Student</th>
            {lista_exercicios(resultsList[0].groups).map((exercise) => {
              return (
                <th className="border  bg-slate-400 px-4 py-2">{exercise}</th>
              );
            })}
            <th className="border  bg-yellow-300 px-4 py-2">Total</th>
          </tr>
        </thead>
        <tbody>
          {resultsList.map((result) => {
            return (
              <tr>
                <td className="border bg-white px-4 py-2">
                  {result.studentId}
                </td>
                {lista_nota_exercicios(result).map((exercise_result) => {
                  return (
                    <td className="border bg-white px-4 py-2">
                      {exercise_result}
                    </td>
                  );
                })}
                <td className="border bg-white px-4 py-2">
                  {lista_nota_exercicios(result).reduce(
                    (accumulator, currentValue) => accumulator + currentValue,
                    0
                  )}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

function histogramData(resultsList: ResultList) {
  let grade_distribution = [0, 0, 0, 0, 0];
  resultsList.map((result) => {
    let totalPoints = result.totalPoints;
    if (totalPoints < 40) {
      grade_distribution[0] += 1;
    } else if (totalPoints >= 40 && totalPoints < 45) {
      grade_distribution[1] += 1;
    } else if (totalPoints >= 45 && totalPoints < 55) {
      grade_distribution[2] += 1;
    } else if (totalPoints >= 55 && totalPoints < 70) {
      grade_distribution[3] += 1;
    } else if (totalPoints >= 70 && totalPoints <= 100) {
      grade_distribution[4] += 1;
    }
  });
  console.log(grade_distribution);
  return grade_distribution;
}

interface Result {
  id: string;
  totalPoints: number;
  studentId: string;
  status: string;
  groups: any;
}

type ResultList = Result[];

export function EvaluationPage() {
  const [viewMode, setViewMode] = useState<"grid" | "row" | "Excel">("grid");
  const [searchKey, setSearch] = useState("");
  const [resultsList, setResultsList] = useState<ResultList>([]);
  const { results_id } = useParams();
  const { contactBACK } = useContext(APIContext);

  useEffect(() => {
    contactBACK("tests/" + results_id + "/resolutions/last", "GET", {}).then(
      (response) => {
        response.json().then((tests) => {
          setResultsList(tests);
        });
      }
    );
  }, [results_id]);

  return (
    <>
      <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-white dark:bg-black">
        <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
          <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <div className="">
              <div className="relative w-full justify-center">
                <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
                  <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
                </div>
                <input
                  type="text"
                  className="py-2.5 pl-10 pr-24 rounded-lg w-full z-20 border bg-input-1"
                  placeholder="Search..."
                  value={searchKey}
                  onChange={(text) => setSearch(text.target.value)}
                  required
                />
              </div>
            </div>
            <div className="flex  ">
              <button
                className="px-2 w-12"
                onClick={() =>
                  setViewMode(
                    viewMode === "grid"
                      ? "row"
                      : viewMode == "row"
                      ? "Excel"
                      : "grid"
                  )
                }
              >
                {viewMode === "grid" ? (
                  <ListIcon size="size-8" />
                ) : viewMode === "row" ? (
                  <SiMicrosoftexcel size="size-8" />
                ) : (
                  <GridIcon size="size-8" />
                )}
              </button>
            </div>
          </div>
          <h1 className="text-3xl font-bold mb-4">Grades Histogram</h1>
          <Histogram data={histogramData(resultsList)} />
          <div className="flex flex-col w-full gap-4 min-h-max pb-8">
            {viewMode == "grid" ? (
              <div className="grid grid-cols-2 gap-4 lg:grid-cols-6 md:grid-cols-3 md:gaps-4">
                {resultsList.map((test) => {
                  return ShowTestGrid(test);
                })}
              </div>
            ) : viewMode == "row" ? (
              <div className="grid grid-cols-1 gap-2">
                {resultsList.map((test) => {
                  return ShowTestList(test);
                })}
              </div>
            ) : (
              <div className="container mx-auto my-8">
                <table className="table-auto border-collapse w-full">
                  {ShowExcelLike(resultsList)}
                </table>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
