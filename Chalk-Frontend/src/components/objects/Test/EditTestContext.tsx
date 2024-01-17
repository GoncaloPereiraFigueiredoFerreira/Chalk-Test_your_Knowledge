import { useContext, createContext } from "react";
import { ExerciseGroup, Test } from "./Test";
import {
  Exercise,
  ExerciseType,
  InitExercise,
  TranslateTestExerciseOut,
} from "../Exercise/Exercise";
import { arrayMove } from "@dnd-kit/sortable";
import { contact } from "../../../APIContext";

//------------------------------------//
//                                    //
//      EditTestStateReducer        //
//                                    //
//------------------------------------//

export enum EditTestActionKind {
  EDIT_TEST_INFO = "EDIT_TEST_INFO",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE", // Add a new exercise to a given group
  ADD_NEW_EXERCISE = "ADD_NEW_EXERCISE", // Add an exercise from ExerciseBank
  SAVE_DD_NEW_EXERCISE = "SAVE_DD_NEW_EXERCISE", // Change the id of a newly added (dragdrop) exercise in the test
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  EDIT_EXERCISE = "EDIT_EXERCISE", // Edit an exercise
  ADD_GROUP = "ADD_GROUP",
  REMOVE_GROUP = "REMOVE_GROUP",
  EDIT_GROUP = "EDIT_GROUP",
  CHANGE_EXERCISE_COTATION = "CHANGE_EXERCISE_COTATION",
  SELECT_EXERCISE_POSITION = "SELECT_EXERCISE_POSITION",
  MOVE_EXERCISE = "MOVE_EXERCISE",
  MOVE_GROUP = "MOVE_GROUP",
}

export interface EditTestAction {
  type: EditTestActionKind;
  testInfo?: {
    type: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  };
  group?: {
    groupPosition: number;
    groupInstructions?: string;
    exerciseType?: ExerciseType;
    newPosition?: number;
  };
  exercise?: {
    newID?: string;
    specialist?: string;
    exercisePosition: number;
    groupPosition: number;
    exercise?: Exercise;
    tmp?: true;
    newPosition?: {
      exercisePosition: number;
      groupPosition: number;
    };
    newCotation?: number;
  };
}

export interface EditTestState {
  test: Test;
  exercisePosition: number;
  groupPosition: number;
  contactBACK: contact;
}

export function EditTestStateReducer(
  state: EditTestState,
  action: EditTestAction
) {
  switch (action.type) {
    case EditTestActionKind.EDIT_TEST_INFO:
      if (action.testInfo) {
        state.contactBACK(
          "tests/" + state.test.id + "/basicProperties",
          "PUT",
          undefined,
          {
            id: state.test.id,
            title: action.testInfo.title,
            globalInstructions: action.testInfo.globalInstructions,
            conclusion: action.testInfo.conclusion,
            visibility: state.test.visibility,
          }
        );
        return {
          ...state,
          test: { ...state.test, ...action.testInfo },
        };
      }

      throw new Error("Invalid Action");

    case EditTestActionKind.CREATE_NEW_EXERCISE:
      if (action.group && action.group.exerciseType && action.exercise) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        const newEx = InitExercise(action.group.exerciseType);
        newEx.identity.specialistId = action.exercise!.specialist!;
        newGroups[action.group.groupPosition].exercises.push(newEx);
        return {
          ...state,
          exercisePosition:
            newGroups[action.group.groupPosition].exercises.length - 1,
          groupPosition: action.group.groupPosition,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.ADD_NEW_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );

        const exercise = action.exercise.tmp
          ? action.exercise.exercise
          : ({
              ...action.exercise.exercise,
              identity: {
                ...action.exercise.exercise.identity,
                id: action.exercise.newID!,
              },
            } as Exercise);
        newGroups[action.exercise.groupPosition].exercises.splice(
          action.exercise.exercisePosition,
          0,
          exercise
        );
        newGroups[action.exercise.groupPosition].groupPoints =
          newGroups[action.exercise.groupPosition].groupPoints +
          (action.exercise.exercise.identity.points ?? 0);
        return {
          ...state,
          test: {
            ...state.test,
            globalPoints:
              state.test.globalPoints +
              (action.exercise.exercise.identity.points ?? 0),
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.SAVE_DD_NEW_EXERCISE:
      if (action.exercise && action.exercise.newPosition) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        const origGroup = newGroups[action.exercise.groupPosition];

        origGroup.exercises[action.exercise.exercisePosition].identity.id =
          action.exercise.newID!;

        origGroup.exercises = arrayMove(
          origGroup.exercises,
          action.exercise.exercisePosition,
          action.exercise.newPosition.exercisePosition
        );
        newGroups[action.exercise.groupPosition] = origGroup;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.REMOVE_EXERCISE:
      if (action.exercise) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        if (
          newGroups[action.exercise.groupPosition] &&
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ]
        ) {
          state.contactBACK(
            "tests/" +
              state.test.id +
              "/exercises/" +
              newGroups[action.exercise.groupPosition].exercises[
                action.exercise.exercisePosition
              ].identity.id,
            "DELETE"
          );

          // remove exercise
          newGroups[action.exercise.groupPosition].exercises.splice(
            action.exercise.exercisePosition,
            1
          );
          // update groupCotation
          let auxCotation = 0;
          newGroups[action.exercise.groupPosition].exercises.forEach(
            (element) => {
              auxCotation += element.identity.points ?? 0;
            }
          );
          newGroups[action.exercise.groupPosition].groupPoints = auxCotation;
          // update globalCotation
          auxCotation = 0;
          newGroups.forEach((element) => {
            auxCotation += element.groupPoints;
          });

          return {
            ...state,
            test: {
              ...state.test,
              globalPoints: auxCotation,
              groups: newGroups,
            },
          } as EditTestState;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.EDIT_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        const newGroups: ExerciseGroup[] = [...state.test.groups];
        newGroups[action.exercise.groupPosition].exercises[
          action.exercise.exercisePosition
        ] = action.exercise.exercise;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.ADD_GROUP:
      const newGroup = {
        id: "test-group-" + state.test.groups.length,
        groupInstructions: "",
        exercises: [],
        groupPoints: 0,
      };

      let trGroups: any[] = JSON.parse(
        JSON.stringify([...state.test.groups, newGroup])
      );
      trGroups = trGroups.map((group) => {
        group.exercises = group.exercises.map((ex: any) => {
          return TranslateTestExerciseOut(ex);
        });
        return group;
      });
      state.contactBACK(
        "tests/" + state.test.id + "/groups",
        "PUT",
        undefined,
        trGroups
      );

      return {
        ...state,
        groupPosition: state.test.groups.length,
        test: {
          ...state.test,
          groups: [...state.test.groups, newGroup],
        },
      } as EditTestState;

    case EditTestActionKind.REMOVE_GROUP:
      if (action.group) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        // remove group
        newGroups.splice(action.group.groupPosition, 1);

        let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
        trGroups = trGroups.map((group) => {
          group.exercises = group.exercises.map((ex: any) => {
            return TranslateTestExerciseOut(ex);
          });
          return group;
        });
        state.contactBACK(
          "tests/" + state.test.id + "/groups",
          "PUT",
          undefined,
          trGroups
        );

        // update globalCotation
        let auxCotation = 0;
        newGroups.forEach((element) => {
          auxCotation += element.groupPoints;
        });
        return {
          ...state,
          test: {
            ...state.test,
            globalPoints: auxCotation,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.EDIT_GROUP:
      if (action.group && action.group.groupInstructions !== undefined) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        newGroups[action.group.groupPosition].groupInstructions =
          action.group.groupInstructions;

        let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
        trGroups = trGroups.map((group) => {
          group.exercises = group.exercises.map((ex: any) => {
            return TranslateTestExerciseOut(ex);
          });
          return group;
        });
        state.contactBACK(
          "tests/" + state.test.id + "/groups",
          "PUT",
          undefined,
          trGroups
        );
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.CHANGE_EXERCISE_COTATION:
      if (action.exercise && action.exercise.newCotation !== undefined) {
        if (action.exercise.newCotation >= 0) {
          const newGroups: ExerciseGroup[] = JSON.parse(
            JSON.stringify(state.test.groups)
          );
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ].identity.points = action.exercise.newCotation;
          let auxCotation = 0;
          newGroups[action.exercise.groupPosition].exercises.forEach(
            (element) => {
              auxCotation += element.identity.points ?? 0;
            }
          );
          newGroups[action.exercise.groupPosition].groupPoints = auxCotation;
          auxCotation = 0;
          newGroups.forEach((element) => {
            auxCotation += element.groupPoints;
          });

          let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
          trGroups = trGroups.map((group) => {
            group.exercises = group.exercises.map((ex: any) => {
              return TranslateTestExerciseOut(ex);
            });
            return group;
          });
          state.contactBACK(
            "tests/" + state.test.id + "/groups",
            "PUT",
            undefined,
            trGroups
          );
          return {
            ...state,
            test: {
              ...state.test,
              globalPoints: auxCotation,
              groups: newGroups,
            },
          } as EditTestState;
        } else {
          console.warn("Cotation cannot be negative");
          return state;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.SELECT_EXERCISE_POSITION:
      if (action.exercise) {
        return {
          ...state,
          exercisePosition: action.exercise.exercisePosition,
          groupPosition: action.exercise.groupPosition,
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.MOVE_EXERCISE:
      if (action.exercise && action.exercise.newPosition) {
        const newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        if (
          action.exercise.groupPosition !==
          action.exercise.newPosition.groupPosition
        ) {
          // Move to another group
          const exercise = newGroups[
            action.exercise.groupPosition
          ].exercises.splice(action.exercise.exercisePosition, 1);
          newGroups[action.exercise.newPosition.groupPosition].exercises.splice(
            action.exercise.newPosition.exercisePosition,
            0,
            exercise[0]
          );
          let auxCotation = 0;
          newGroups[action.exercise.groupPosition].exercises.forEach(
            (element) => {
              auxCotation += element.identity.points ?? 0;
            }
          );
          newGroups[action.exercise.groupPosition].groupPoints = auxCotation;
          auxCotation = 0;
          newGroups[
            action.exercise.newPosition.groupPosition
          ].exercises.forEach((element) => {
            auxCotation += element.identity.points ?? 0;
          });
          newGroups[action.exercise.newPosition.groupPosition].groupPoints =
            auxCotation;

          let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
          trGroups = trGroups.map((group) => {
            group.exercises = group.exercises.map((ex: any) => {
              return TranslateTestExerciseOut(ex);
            });
            return group;
          });
          state.contactBACK(
            "tests/" + state.test.id + "/groups",
            "PUT",
            undefined,
            trGroups
          );

          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as EditTestState;
        } else {
          // Move within the same group
          newGroups[action.exercise.groupPosition].exercises = arrayMove(
            newGroups[action.exercise.groupPosition].exercises,
            action.exercise.exercisePosition,
            action.exercise.newPosition.exercisePosition
          );

          let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
          trGroups = trGroups.map((group) => {
            group.exercises = group.exercises.map((ex: any) => {
              return TranslateTestExerciseOut(ex);
            });
            return group;
          });
          state.contactBACK(
            "tests/" + state.test.id + "/groups",
            "PUT",
            undefined,
            trGroups
          );
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as EditTestState;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.MOVE_GROUP:
      if (action.group && action.group.newPosition !== undefined) {
        // let newGroups: ExerciseGroup[] = JSON.parse(
        //   JSON.stringify(state.test.groups)
        // );
        let newGroups: ExerciseGroup[] = JSON.parse(
          JSON.stringify(state.test.groups)
        );
        newGroups = arrayMove(
          newGroups,
          action.group.groupPosition,
          action.group.newPosition
        );
        let trGroups: any[] = JSON.parse(JSON.stringify([...newGroups]));
        trGroups = trGroups.map((group) => {
          group.exercises = group.exercises.map((ex: any) => {
            return TranslateTestExerciseOut(ex);
          });
          return group;
        });
        state.contactBACK(
          "tests/" + state.test.id + "/groups",
          "PUT",
          undefined,
          trGroups
        );
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        };
      }
      throw new Error("Invalid Action");

    default:
      alert("Unknown action");
      return state;
  }
}

//------------------------------------//
//                                    //
//          EditTestProvider          //
//                                    //
//------------------------------------//

export const EditTestContext = createContext<
  | { testState: EditTestState; dispatch: React.Dispatch<EditTestAction> }
  | undefined
>(undefined);

//------------------------------------//
//                                    //
//        useEditTestContext          //
//                                    //
//------------------------------------//

// Exports function useEditTestContext that allows you to access the contents of a EditTestContext if the context has already been defined
export function useEditTestContext() {
  const createTest = useContext(EditTestContext);
  if (createTest === undefined) {
    throw new Error(
      "useEditTestContext must be used with a EditTestContext.Provider"
    );
  }
  return createTest;
}

//------------------------------------//
//                                    //
//         EditTest Handlers          //
//                                    //
//------------------------------------//

export function findGroupByID(id: string) {
  const { testState } = useEditTestContext();
  testState.test.groups.forEach((group, index) => {
    if (group.id === id) return index;
  });
  return -1;
}

export function findExerciseByID(id: string) {
  const { testState } = useEditTestContext();
  testState.test.groups.forEach((group, groupPosition) => {
    group.exercises.forEach((exercise, exercisePosition) => {
      if (exercise.identity.id === id)
        return {
          groupPosition: groupPosition,
          exercisePosition: exercisePosition,
        };
    });
  });
  return -1;
}
