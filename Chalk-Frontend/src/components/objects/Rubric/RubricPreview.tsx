import { Rubric, StardardLevels } from "./Rubric";
import { Table } from "flowbite-react";
import { standardLevelToString } from "./RubricEdit";

export function RubricPreview(rubric: Rubric) {
  return (
    <div className="overflow-x-scroll">
      <Table>
        <Table.Head>
          <Table.HeadCell className=" text-black dark:text-white bg-[#bdcee6] dark:bg-[#2e3c50] border-r-2 border-slate-400">
            Rúbrica
          </Table.HeadCell>
          {Object.keys(StardardLevels)
            .reverse()
            .map((key) => {
              return (
                <Table.HeadCell className="items-center text-black dark:text-white bg-[#bdcee6] dark:bg-[#2e3c50] border-r-2 border-slate-400">
                  <p className="text-center">{standardLevelToString(key)}</p>
                </Table.HeadCell>
              );
            })}
        </Table.Head>
        <Table.Body className="divide-y">
          {rubric.criteria.map((criteria) => {
            return (
              <Table.Row>
                <Table.Cell className="bg-slate-300 dark:bg-slate-800  font-medium text-black dark:text-white border-r-2 border-slate-400">
                  {criteria.title} ({criteria.points} pts)
                </Table.Cell>
                {criteria.standards
                  .sort(function (a, b) {
                    // Compare the 2 title {"0","1","2","3","4"}
                    if (a.title < b.title) return -1;
                    if (a.title > b.title) return 1;
                    return 0;
                  })
                  .map((standart) => {
                    return (
                      <Table.Cell className=" whitespace-nowrap font-medium text-gray-600 dark:text-white border-r-2 border-slate-400">
                        <div className="flex flex-col space-y-1 items-center">
                          <p>{standart.description}</p>
                          <p>({standart.percentage} %)</p>
                        </div>
                      </Table.Cell>
                    );
                  })}
              </Table.Row>
            );
          })}
        </Table.Body>
      </Table>
    </div>
  );
}
