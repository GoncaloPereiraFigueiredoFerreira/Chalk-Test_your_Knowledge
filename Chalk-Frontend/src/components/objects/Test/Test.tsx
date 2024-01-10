import { Exercise } from "../Exercise/Exercise";

export interface Test {
  type: string;
  conclusion: string;
  author: string;
  title: string;
  creationDate: string;
  globalPoints: number;
  globalInstructions: string;
  groups: ExerciseGroup[];
}

//------------------------------------//
//                                    //
//          ExerciseGroup             //
//                                    //
//------------------------------------//

export interface ExerciseGroup {
  id: string;
  exercises: Exercise[];
  groupInstructions: string;
  groupPoints: number;
}

//------------------------------------//
//                                    //
//             InitTest               //
//                                    //
//------------------------------------//

export function InitTest() {
  return {
    type: "",
    conclusion: "",
    author: "",
    title: "",
    creationDate: new Date().toISOString(),
    globalPoints: 0,
    globalInstructions: "",
    groups: [InitGroup()],
  } as Test;
}

export function InitGroup() {
  return {
    id: "test-group-" + 0,
    exercises: [],
    groupInstructions: "",
    groupPoints: 0,
  } as ExerciseGroup;
}
