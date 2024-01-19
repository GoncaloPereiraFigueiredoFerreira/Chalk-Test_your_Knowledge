import { useState } from "react";
import { EditActionKind } from "../../EditExercise/EditExercise";
import { CQExercise, CreateEditProps } from "../Exercise";
import "../Exercise.css";

interface CQEditProps {
  context: CreateEditProps;
  exercise: CQExercise;
}

export function CQEdit({ context, exercise }: CQEditProps) {
  const [topics, setTopics] = useState<string[]>([]);

  const addTopic = () => {
    const newTopic = [...topics];
    newTopic.push("");
    setTopics(newTopic);
  };

  const changeTopic = (index: number, topic: string) => {
    const newTopic = [...topics];
    newTopic[index] = topic;
    setTopics(newTopic);
    context.dispatch({
      type: EditActionKind.SET_TOPIC,
      dataLString: newTopic,
    });
  };

  const deleteTopic = (index: number) => {
    const newTopic = [...topics];
    const newTopic2 = newTopic.filter((_, ind) => {
      return ind !== index;
    });
    setTopics(newTopic2);
    context.dispatch({
      type: EditActionKind.SET_TOPIC,
      dataLString: newTopic2,
    });
  };

  const renderTopics = () => {
    return (
      <div className="flex-col flex space-y-4 mb-3">
        {topics.map((topic, index) => {
          return (
            <div className="flex flex-row space-x-4 items-center">
              <input
                type="text"
                value={topic}
                className="w-full rounded-md"
                onChange={(e) => {
                  changeTopic(index, e.target.value);
                }}
              />
              <button
                className="edit-btn bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black mx-2 px-1"
                onClick={() => deleteTopic(index)}
              >
                Remove
              </button>
            </div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="flex-col flex space-y-4">
      <div className="flex space-x-8 dark:text-white">
        <label>Indique o número máximo de respostas do aluno:</label>
        <input
          type="number"
          className="basic-input-text rounded-md w-20 mb-1"
          min={1}
          onChange={(e) =>
            context.dispatch({
              type: EditActionKind.CHANGE_MAX_ANSWERS,
              dataString: e.target.value,
            })
          }
          value={exercise.props.maxAnswers}
        ></input>
      </div>
      <div className="flex space-x-8 dark:text-white">
        <label className="pt-2">
          Indique os tópicos que a AI deverá abordar:
        </label>
        <button
          className="edit-btn mt-2 px-2 text-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black w-fit self-center cursor-pointer p-1 rounded-md"
          onClick={() => {
            addTopic();
          }}
        >
          Add
        </button>
      </div>
      {renderTopics()}
    </div>
  );
}
