import { useState } from "react";
import { EditActionKind } from "../../EditExercise/EditExercise";
import { CQExercise, CreateEditProps } from "../Exercise";
import { HiOutlineTrash } from "react-icons/hi";

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
      <div className="flex-col flex gap-2">
        {topics.map((topic, index) => (
          <div className="flex flex-row items-center gap-2">
            <input
              type="text"
              value={topic}
              className="w-full rounded-lg border-2 border-[#dddddd] focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
              onChange={(e) => {
                changeTopic(index, e.target.value);
              }}
            />
            <button
              className="flex p-2.5 text-base rounded-lg font-medium btn-base-color group"
              onClick={() => deleteTopic(index)}
            >
              <HiOutlineTrash className="size-5" />
            </button>
          </div>
        ))}
        <button
          className="flex justify-center w-full p-2 items-center gap-2 text-base rounded-lg font-medium btn-base-color group"
          onClick={() => {
            addTopic();
          }}
        >
          Adicionar
        </button>
      </div>
    );
  };

  const handleChange = (value: string) => {
    let cleanValue = value.replace(/[^\d]/g, "").replace(/$0*/g, "");
    if (cleanValue === "") {
      cleanValue = "0";
    }

    context.dispatch({
      type: EditActionKind.CHANGE_MAX_ANSWERS,
      dataString: cleanValue,
    });
  };

  return (
    <div className="flex flex-col gap-4">
      <div className="flex gap-4 dark:text-white items-center">
        <label>Indique o número máximo de respostas do aluno:</label>
        <input
          id="input-answers"
          type="text"
          className="w-20 rounded-lg border-2 border-[#dddddd] focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
          onChange={(e) => handleChange(e.target.value)}
          value={exercise.props.maxAnswers ?? 5}
        ></input>
        <label htmlFor="input-answers">respostas</label>
      </div>
      <div className="flex dark:text-white">
        <label>Indique os tópicos que a AI deverá abordar:</label>
      </div>
      <div className="flex flex-col">{renderTopics()}</div>
    </div>
  );
}
