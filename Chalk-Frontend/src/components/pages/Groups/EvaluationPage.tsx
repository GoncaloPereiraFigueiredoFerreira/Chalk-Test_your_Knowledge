import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
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

const exampleData = [
  {
    id: "1",
    student: "JCR",
    grade: 10,
    exercises_grade: [2, 2, 2, 2, 2],
  },
  {
    id: "2",
    student: "JCR",
    grade: 25,
    exercises_grade: [5, 5, 5, 5, 5],
  },
  {
    id: "3",
    student: "JMF",
    grade: 50,
    exercises_grade: [10, 10, 10, 10, 10],
  },
  {
    id: "4",
    student: "JBB",
    grade: 75,
    exercises_grade: [15, 15, 15, 15, 15],
  },
  {
    id: "5",
    student: "JNO",
    grade: 100,
    exercises_grade: [20, 20, 20, 20, 20],
  },
  {
    id: "6",
    student: "JNO",
    grade: 50,
    exercises_grade: [10, 10, 10, 10, 10],
  },
  {
    id: "7",
    student: "JNO",
    grade: 44,
    exercises_grade: [9, 10, 10, 10, 15],
  },
  {
    id: "8",
    student: "JNO",
    grade: 65,
    exercises_grade: [15, 15, 15, 10, 10],
  },
  {
    id: "9",
    student: "JNO",
    grade: 0,
    exercises_grade: [0, 0, 0, 0, 0],
  },
];
interface Test {
  id: string;
  student: string;
  grade: number;
  exercises_grade: number[];
}

type TestList = Test[];

function ShowTestGrid(test: Test, index: number) {
  return (
    <div className=" max-w-lg  bg-white border-2 border-slate-300 rounded-lg shadow-lg shadow-slate-400 dark:bg-gray-800 dark:border-gray-700 overflow-hidden">
      <div className="py-4 px-12 ">
        {test.grade === undefined ? (
          <CircularProgressbarWithChildren value={0}>
            <AiTwotoneFileUnknown size="100" />
            <div style={{ fontSize: 12, marginTop: -5 }}>
              <strong>Not Evaluated Yet...</strong>
            </div>
          </CircularProgressbarWithChildren>
        ) : (
          <CircularProgressbar
            value={test.grade}
            text={`${test.grade}%`}
            styles={buildStyles({
              textColor: `rgba(${480 - test.grade * 4.8}, ${
                4.8 * test.grade
              }, ${0})`,
              pathColor: `rgba(${480 - test.grade * 4.8}, ${
                4.8 * test.grade
              }, ${0})`,
            })}
          />
        )}
      </div>

      <div className="p-5 bg-slate-300">
        <p className="mb-2  px-2 font-normal text-gray-700 dark:text-gray-400">
          <strong>Student:</strong> {test.student}
        </p>

        <div className="flex w-full px-2 justify-between">
          <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400">
            <strong>Grade:</strong>
            {test.grade === undefined ? "TBD" : ` ${test.grade}%`}
          </div>
        </div>
      </div>
    </div>
  );
}

function ShowTestList(test: Test, index: number) {
  return (
    <div className="max-h-[78px] rounded-lg w-full bg-3-2 overflow-hidden">
      <div className="p-4 flex justify-between w-full">
        <div className="flex-col w-60">
          <p className="mb-1 font-normal text-gray-700 dark:text-gray-400">
            <strong>Student:</strong> {test.student}
          </p>
        </div>

        <div className="flex justify-end space-x-2 w-60">
          <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400 w-36">
            <strong>Grade:</strong>
            {test.grade === undefined ? "TBD" : ` ${test.grade}%`}
          </div>
        </div>
      </div>
    </div>
  );
}

function ShowExcelLike(resultsList: TestList) {
  return (
    <div className="container mx-auto my-8">
      <table className="table-auto border-collapse w-full">
        <thead>
          <tr>
            <th className="border bg-slate-400 px-4 py-2">Student</th>
            {[1.1, 1.2, 1.3, 2.1, 2.2].map((exercise) => {
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
                <td className="border bg-white px-4 py-2">{result.student}</td>
                {result.exercises_grade!.map((exercise_result) => {
                  return (
                    <td className="border bg-white px-4 py-2">
                      {exercise_result}
                    </td>
                  );
                })}
                <td className="border bg-white px-4 py-2">
                  {result.exercises_grade.reduce(
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

function histogramData(resultsList: TestList) {
  const grade_distribution = [0, 0, 0, 0, 0];
  resultsList.map((result) => {
    if (result.grade < 40) {
      grade_distribution[0] += 1;
    } else if (result.grade >= 40 && result.grade < 45) {
      grade_distribution[1] += 1;
    } else if (result.grade >= 45 && result.grade < 55) {
      grade_distribution[2] += 1;
    } else if (result.grade >= 55 && result.grade < 70) {
      grade_distribution[3] += 1;
    } else if (result.grade >= 70 && result.grade <= 100) {
      grade_distribution[4] += 1;
    }
  });
  console.log(grade_distribution);
  return grade_distribution;
}

export function EvaluationPage() {
  const [viewMode, setViewMode] = useState<"grid" | "row" | "Excel">("grid");
  const [searchKey, setSearch] = useState("");
  const [resultsList, setResultsList] = useState<TestList>([]);
  const { results_id } = useParams();

  useEffect(() => {
    setResultsList(exampleData);
  }, [results_id]);

  const addEvaluation = () => {
    const newTest: Test = {
      id: "1",
      student: "Luis",
      grade: 10,
      exercises_grade: [2, 2, 2, 2, 2],
    };
    setResultsList([...resultsList, newTest]);
  };

  return (
    <>
      <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-2-1">
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
                className=" items-center justify-center w-24 h-10 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10   dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
                onClick={addEvaluation}
              >
                Adicionar Avaliacao
              </button>
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
                {resultsList.map((test, index) => {
                  return ShowTestGrid(test, index);
                })}
              </div>
            ) : viewMode == "row" ? (
              <div className="grid grid-cols-1 gap-2">
                {resultsList.map((test, index) => {
                  return ShowTestList(test, index);
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
