import { Rubric, StardardLevels } from "./Rubric";
import { Table } from "flowbite-react";

export function RubricPreview(rubric: Rubric) {
  return (
    <div className="overflow-x-auto">
      <Table>
        <Table.Head>
          <Table.HeadCell className="bg-3-2">Rúbrica</Table.HeadCell>
          {Object.keys(StardardLevels).map((key) => {
            return (
              <Table.HeadCell className="bg-3-2 items-center">
                <p className="text-center">{key}</p>
              </Table.HeadCell>
            );
          })}
        </Table.Head>
        <Table.Body className="divide-y">
          {rubric.criteria.map((criteria) => {
            return (
              <Table.Row>
                <Table.Cell className="bg-3-2 font-medium text-black dark:text-white">
                  {criteria.title} ({criteria.points} pts)
                </Table.Cell>
                {criteria.standards.map((standart) => {
                  return (
                    <Table.Cell className="bg-white dark:bg-slate-600 whitespace-nowrap font-medium text-gray-900 dark:text-white">
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
