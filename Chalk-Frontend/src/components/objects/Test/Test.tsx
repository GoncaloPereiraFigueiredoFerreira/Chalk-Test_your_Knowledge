import { Exercise } from "../Exercise/Exercise";

export interface Test {
  id: string;
  type: string;
  conclusion: string;
  author: string;
  title: string;
  visibility: string;
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
    id: "",
    type: "",
    conclusion: "",
    author: "",
    visibility: "private",
    title: "Novo Teste",
    creationDate: new Date().toISOString(),
    globalPoints: 0,
    globalInstructions: "",
    groups: [],
  } as Test;
}

export function CreateTest(author: string) {
  let test: Test = InitTest();
  test.author = author;
  return test;
}

export function InitGroup() {
  return {
    id: "test-group-" + 0,
    exercises: [],
    groupInstructions: "",
    groupPoints: 0,
  } as ExerciseGroup;
}
