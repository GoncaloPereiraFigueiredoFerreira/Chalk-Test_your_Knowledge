import { ExerciseType } from "../Exercise/Exercise";
import { RubricEdit } from "./RubricEdit";
import { RubricPreview } from "./RubricPreview";

export enum StardardLevels {
  EXCELENT = "4",
  WELL_DONE = "3",
  SATISFACTORY = "2",
  INSATISFACTORY = "1",
  NULL = "0",
}

export interface Standard {
  title: StardardLevels;
  description: string;
  percentage: number;
}

export interface Criteria {
  title: string;
  points: number;
  standards: Standard[];
}

export interface Rubric {
  criteria: Criteria[];
}

export enum RubricContext {
  EDIT = "EDIT",
  PREVIEW = "PREVIEW",
}

export interface EditRubricContext {
  context: RubricContext.EDIT;
  setRubric: Function;
}

export interface PreviewRubricContext {
  context: RubricContext.PREVIEW;
}

export interface RubricProps {
  context: EditRubricContext | PreviewRubricContext;
  rubric: Rubric;
}

function createStandards(): Standard[] {
  const standards: Standard[] = [];
  Object.keys(StardardLevels).map((title) => {
    standards.push({
      title: StardardLevels[title as keyof typeof StardardLevels],
      description: "",
      percentage: 0,
    });
  });
  return standards;
}

export function createNewCriteria(): Criteria {
  return { title: "", points: 0, standards: createStandards() };
}

export function TranslateRubricOut(type: ExerciseType, rubric?: Rubric) {
  if (rubric !== undefined) {
    switch (type) {
      case ExerciseType.CHAT:
        return { ...rubric, type: "CE" };
      case ExerciseType.OPEN_ANSWER:
        return { ...rubric, type: "OA" };
    }
  }

  return {};
}

export function Rubric({ context, rubric }: RubricProps) {
  switch (context.context) {
    case RubricContext.PREVIEW:
      return RubricPreview(rubric);

    case RubricContext.EDIT:
      return RubricEdit(rubric, context.setRubric);
  }
}
