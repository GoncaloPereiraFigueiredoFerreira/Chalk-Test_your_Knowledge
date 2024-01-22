import { Exercise, Tag } from "../Exercise/Exercise";

export interface Test {
  id: string;
  type: string;
  conclusion: string;
  author: string;
  courseId: string;
  title: string;
  visibility: string;
  creationDate: string;
  publishDate: string;
  tags: Tag[];
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
    publishDate: "",
    creationDate: new Date().toISOString(),
    globalPoints: 0,
    globalInstructions: "",
    courseId: "",
    groups: [],
    tags: [],
  } as Test;
}

export function CreateTest(author: string) {
  const test: Test = InitTest();
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
