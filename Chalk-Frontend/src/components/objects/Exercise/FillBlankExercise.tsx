import { useState, useEffect } from "react";

type FillBlankExerciseProps = {
  enunciado: any;
  problem?: any;
  contexto: string;
  cotacao?: number;
};

export function FillBlankExercise(props: FillBlankExerciseProps) {
  let exerciseDisplay = <></>;

  switch (props.contexto) {
    case "solve":
      exerciseDisplay = <FillBlankSolve></FillBlankSolve>;
      break;

    case "edit":
      exerciseDisplay = <></>;
      break;

    case "preview":
      exerciseDisplay = <></>;
      break;

    case "correct":
      exerciseDisplay = <></>;
      break;

    case "psolution":
      exerciseDisplay = <></>;
      break;
  }
  return (
    <>
      <div className="m-5 text-xl">{exerciseDisplay}</div>
    </>
  );
}

function FillBlankSolve() {
  const [rawText, setRawText] = useState("");
  const [words, setWords] = useState([]);

  useEffect(() => {
    let wordsSplit = [...words];

    //(?:(\w+)|(\d+)|([^\w\d]))
  }, [rawText]);

  return (
    <>
      <textarea
        value={rawText}
        onChange={(e) => setRawText(e.target.value)}
        className="flex w-full resize-none bg-gray-600 rounded-lg"
        rows={10}
      ></textarea>
      <div></div>
    </>
  );
}
