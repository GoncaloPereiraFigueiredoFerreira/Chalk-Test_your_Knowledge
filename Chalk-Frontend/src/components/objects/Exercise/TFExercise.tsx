import { useState } from "react";

type TFExerciseProps = {
  enunciado: any;
  problema?: any;
  contexto: string;
};

function TFJustify(props: any) {
  let hidden = "hidden";
  let fontsize = "";
  if (props.flag == "false") {
    hidden = "";
    fontsize = "text-xs";
  }
  return (
    <div>
      <p className={fontsize}>{props.text}</p>
      <input
        className={hidden + " text-black min-w-[40rem] max-h-8"}
        type="text"
        name={"justification" + props.counter}
        placeholder="Justifique a sua resposta"
      ></input>
    </div>
  );
}

function TFStatement(props: any) {
  const [tfvalue, settfvalue] = useState("");

  let name = "radio-button-" + props.counter;
  return (
    <tr>
      <td className="p-3">
        <input
          className="relative h-5 w-5 cursor-pointer appearance-none rounded-full border text-green-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-green-500 checked:before:bg-green-500 hover:before:opacity-10"
          type="radio"
          name={name}
          onChange={(e) => settfvalue("true")}
        ></input>
      </td>
      <td className="p-3">
        <input
          className="relative h-5 w-5 cursor-pointer appearance-none rounded-full border  text-red-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-red-700 checked:before:bg-red-500 hover:before:opacity-10"
          type="radio"
          name={name}
          onChange={(e) => settfvalue("false")}
        ></input>
      </td>
      <td>
        {props.justify ? (
          <TFJustify
            text={props.text}
            flag={tfvalue}
            counter={props.counter}
          ></TFJustify>
        ) : (
          <p>{props.text}</p>
        )}
      </td>
    </tr>
  );
}

export function TFExercise({ enunciado, problema, contexto }: TFExerciseProps) {
  //dependendo do contexto isto deveria mudar

  return (
    <div className="m-5 text-xl">
      <p className="text-4xl strong mb-8">Verdadeiro ou Falso</p>
      <p>{enunciado.text}</p>
      <table className="table-auto mt-4">
        <thead>
          <tr>
            <th className="p-3">V</th>
            <th className="p-3">F</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {problema.statements.map((message: any, counter: number) => (
            <TFStatement
              key={counter}
              text={message}
              counter={counter++}
              justify={true}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
}
