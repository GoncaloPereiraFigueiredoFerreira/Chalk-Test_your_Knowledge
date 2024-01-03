import { useState } from "react";
import { EditActionKind } from "../../EditExercise/EditExercise";
import { CQExercise, CreateEditProps } from "../Exercise";

interface CQEditProps {
  context: CreateEditProps;
  exercise: CQExercise;
}

export function CQEdit({ context, exercise }: CQEditProps) {
  const [topics, setTopics] = useState<string[]>([]);

  const addTopic = () => {
    let newTopic = [...topics];
    newTopic.push("");
    setTopics(newTopic);
  };

  const changeTopic = (index: number, topic: string) => {
    let newTopic = [...topics];
    newTopic[index] = topic;
    setTopics(newTopic);
    context.dispatch({
      type: EditActionKind.SET_TOPIC,
      dataLString: newTopic,
    });
  };

  const deleteTopic = (index: number) => {
    let newTopic = [...topics];
    let newTopic2 = newTopic.filter((_, ind) => {
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
      <div className="flex-col flex space-y-4">
        {topics.map((topic, index) => {
          return (
            <div className="flex flex-row space-x-4 items-center">
              <input
                type="text"
                value={topic}
                onChange={(e) => {
                  changeTopic(index, e.target.value);
                }}
              />
              <button type="button" onClick={() => deleteTopic(index)}>
                X
              </button>
            </div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="flex-col flex space-y-4">
      <div className="flex space-x-8">
        <label>Indique o número máximo de respostas do aluno:</label>
        <input
          type="number"
          className="basic-input-text"
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
      <div className="flex space-x-8">
        <label>Indique os tópicos que a AI deverá abordar:</label>
      </div>
      {renderTopics()}
      <button
        type="button"
        onClick={() => {
          addTopic();
        }}
      >
        Add
      </button>
    </div>
  );
}
