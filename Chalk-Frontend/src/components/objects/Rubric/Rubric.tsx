import { RubricPreview } from "./RubricPreview";

export enum StardardLevels {
  EXCELENT = "5",
  WELL_DONE = "4",
  SATISFACTORY = "3",
  INSATISFACTORY = "2",
}

export interface Standard {
  title: StardardLevels;
  description: string;
  percentage: string;
}

export interface Criteria {
  title: string;
  points: number;
  standarts: Standard[];
}

export interface Rubric {
  criteria: Criteria[];
}

export enum RubricContext {
  CREATE = "CREATE",
  PREVIEW = "PREVIEW",
}

export function Rubric(context: string, rubric: Rubric) {
  switch (context) {
    case RubricContext.PREVIEW:
      return RubricPreview(rubric);

    case RubricContext.CREATE:
      return RubricPreview(rubric);
  }
}
