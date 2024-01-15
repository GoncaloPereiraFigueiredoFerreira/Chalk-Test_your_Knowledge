import { useContext, useEffect, useState } from "react";
import {
  CQExercise,
  CQResolutionData,
  ExerciseType,
  SolveProps,
} from "../Exercise";
import "./CQ.css";
import { UserContext } from "../../../../UserContext";

export interface CQSolveProps {
  exercise: CQExercise;
  position: string;
  context: SolveProps;
}

export function CQSolve(props: CQSolveProps) {
  let initState: CQResolutionData = props.context
    .resolutionData as CQResolutionData;

  const { user } = useContext(UserContext);
  const [state, setState] = useState<CQResolutionData>({
    type: ExerciseType.CHAT,
    msgs: [],
  });

  const [currentMsg, setMsg] = useState("");

  const addMsg = (text: string) => {
    let tmpMsgs = [...state.msgs];

    tmpMsgs.unshift(text);
    setState({ ...state, msgs: tmpMsgs });
  };
  const fetchAnswer = () => {};

  useEffect(() => {
    props.context.setExerciseSolution(state);
  }, [state]);

  useEffect(() => {
    let tmpMsgs: string[] = [];
    initState.msgs.map((msg: string) => {
      tmpMsgs.push(msg);
    });

    setState({ ...state, msgs: tmpMsgs });
  }, [props.exercise]);

  // Enviar

  return (
    <div className="flex flex-col items-center justify-center min-h-full space-y-4 ">
      <div className="text">
        <p>
          Neste exercício terás de conversar com o nosso assistente e responder
          às suas perguntas! Lembra-te, todas as mensagens que enviares serão
          revistas pelo teu professor mais tarde.
        </p>
      </div>
      <div className="flex  flex-col flex-grow  w-full max-w-xl bg-white shadow-xl rounded-lg overflow-hidden">
        <div className="flex flex-col-reverse p-4 overflow-y-scroll h-80 scroll-smooth">
          {state.msgs.length % 2 == 0 ? (
            <div className="flex w-full mt-2 space-x-3 max-w-xs">
              <div className="flex-shrink-0 h-10 w-10 rounded-full">
                <img src="/chalk-simple.svg"></img>
              </div>
              <div>
                <div className="bg-gray-300 p-3 rounded-r-lg rounded-bl-lg">
                  <div className="flex justify-items-center items-center px-6">
                    <div className="dot-typing"></div>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <></>
          )}
          {state.msgs.map((msg, index) => {
            if (state.msgs.length % 2 == 0) {
              return index % 2 == 0 ? (
                <div
                  key={index}
                  className="flex w-full mt-2 space-x-3 max-w-xs ml-auto justify-end"
                >
                  <div>
                    <div className="bg-blue-600 text-white p-3 rounded-l-lg rounded-br-lg">
                      <p className="text-sm">{msg}</p>
                    </div>
                  </div>
                  <div className="flex-shrink-0 h-10 w-10 rounded-full">
                    <img src={user.user?.profilePic}></img>
                  </div>
                </div>
              ) : (
                <div
                  key={index}
                  className="flex w-full mt-2 space-x-3 max-w-xs"
                >
                  <div className="flex-shrink-0 h-10 w-10 rounded-full ">
                    <div className="flex-shrink-0 h-10 w-10 rounded-full">
                      <img src="/chalk-simple.svg"></img>
                    </div>
                  </div>
                  <div>
                    <div className="bg-gray-300 p-3 rounded-r-lg rounded-bl-lg">
                      <p className="text-sm">{msg}</p>
                    </div>
                  </div>
                </div>
              );
            } else {
              return index % 2 == 1 ? (
                <div
                  key={index}
                  className="flex w-full mt-2 space-x-3 max-w-xs ml-auto justify-end"
                >
                  <div>
                    <div className="bg-blue-600 text-white p-3 rounded-l-lg rounded-br-lg">
                      <p className="text-sm">{msg}</p>
                    </div>
                  </div>
                  <div className="flex-shrink-0 h-10 w-10 rounded-full bg-gray-300"></div>
                </div>
              ) : (
                <div
                  key={index}
                  className="flex w-full mt-2 space-x-3 max-w-xs"
                >
                  <div className="flex-shrink-0 h-10 w-10 rounded-full ">
                    <img src="/chalk-simple.svg"></img>
                  </div>
                  <div>
                    <div className="bg-gray-300 p-3 rounded-r-lg rounded-bl-lg">
                      <p className="text-sm">{msg}</p>
                    </div>
                  </div>
                </div>
              );
            }
          })}
        </div>

        <div className="bg-gray-300 p-4">
          <input
            className="flex items-center h-10 w-full rounded px-3 text-sm"
            type="text"
            value={currentMsg}
            onChange={(e) => {
              setMsg(e.target.value);
            }}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                addMsg(currentMsg);
                setMsg("");
              }
            }}
            placeholder="Type your answer"
            disabled={state.msgs.length > 6 || state.msgs.length % 2 == 0}
          />
        </div>
      </div>
    </div>
  );
}
