// enum MCEditActionKind {
//   ADDSTATEMENT = "ADDSTATEMENT",
//   CHANGESTATEMENT = "CHANGESTATEMENT",
//   CHANGEHEADER = "CHANGEHEADER",
//   REMOVESTATE = "REMOVESTATE",
//   RIGHTSTATEMENT = "RIGHTSTATEMENT",
// }

// type MCEditState = {
//   header: string;
//   statements: string[];
//   correct: string;
// };

// type MCEditAction = {
//   type: MCEditActionKind;
//   payload: { id?: number; value?: string };
// };

// const MCEditStateContext = createContext<{ state: MCEditState; dispatch: any }>(
//   {
//     state: { header: "", statements: [], correct: "" },
//     dispatch: undefined,
//   }
// );

// function EditReducer(state: MCEditState, action: MCEditAction): MCEditState {
//   switch (action.type) {
//     case MCEditActionKind.ADDSTATEMENT:
//       let temp1 = [...state.statements];
//       temp1.push("");
//       let addState = { ...state, statements: temp1 };
//       return addState;

//     case MCEditActionKind.RIGHTSTATEMENT:
//       let correctstate = { ...state, correct: action.payload.value ?? "" };
//       return correctstate;

//     case MCEditActionKind.CHANGEHEADER:
//       let headerState = { ...state };
//       headerState.header = action.payload.value ?? "";
//       return headerState;

//     case MCEditActionKind.CHANGESTATEMENT:
//       let changed = [...state.statements];
//       changed[action.payload.id!] = action.payload.value ?? "";
//       let statementState = { ...state, statements: changed };
//       return statementState;

//     case MCEditActionKind.REMOVESTATE:
//       let removed = [...state.statements];
//       let correct = state.correct;
//       if (state.correct === state.statements[action.payload.id!]) {
//         correct = "";
//       }
//       removed.splice(action.payload.id!, 1);
//       let removeState = { ...state, statements: removed, correct: correct };
//       return removeState;

//     default:
//       throw new Error();
//   }
// }

// export function MCEdit(props: any) {
//   let initState: MCEditState = { header: "", statements: [], correct: "" };
//   initState.header = props.enunciado.text;
//   props.problem.statements.map((text: any) => initState.statements.push(text));
//   const [state, dispatch] = useReducer(EditReducer, initState);

//   return (
//     <>
//       <form>
//         <p className="block mb-2 text-sm text-gray-900 dark:text-white">
//           Adicione as afirmações e escolha a opção correta.
//         </p>
//         <ul>
//           <MCEditStateContext.Provider value={{ state, dispatch }}>
//             {state.statements.map((_item, counter) => {
//               return (
//                 <MCStatementEdit id={counter} key={counter}></MCStatementEdit>
//               );
//             })}
//           </MCEditStateContext.Provider>
//         </ul>
//         <input
//           type="button"
//           className="edit-btn mt-4"
//           value="Add"
//           onClick={() => {
//             dispatch({ type: MCEditActionKind.ADDSTATEMENT, payload: {} });
//           }}
//         ></input>
//       </form>
//     </>
//   );
// }

// function MCStatementEdit(props: any) {
//   let name = "mc";
//   const { state, dispatch } = useContext(MCEditStateContext);
//   return (
//     <>
//       <li className="flex items-center">
//         <input
//           className="radio-blue mr-3"
//           type="radio"
//           name={name}
//           checked={state.correct === state.statements[props.id]}
//           onChange={() =>
//             dispatch({
//               type: MCEditActionKind.RIGHTSTATEMENT,
//               payload: { id: props.id, value: state.statements[props.id] },
//             })
//           }
//         ></input>
//         <input
//           type="text"
//           className="basic-input-text mr-3"
//           onChange={(e) =>
//             dispatch({
//               type: MCEditActionKind.CHANGESTATEMENT,
//               payload: { id: props.id, value: e.target.value },
//             })
//           }
//           value={state.statements[props.id]}
//         ></input>

//         <input
//           className="edit-btn"
//           type="button"
//           onClick={() =>
//             dispatch({
//               type: MCEditActionKind.REMOVESTATE,
//               payload: { id: props.id },
//             })
//           }
//           value="Remove"
//         ></input>
//       </li>
//     </>
//   );
// }
