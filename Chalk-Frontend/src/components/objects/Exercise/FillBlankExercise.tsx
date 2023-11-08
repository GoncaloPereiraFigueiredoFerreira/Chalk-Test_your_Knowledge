type FillBlankExerciseProps = {
  enunciado: any;
  problema?: any;
  contexto: string;
  cotacao?: number;
};

export function FillBlankExercise(props: FillBlankExerciseProps) {
  let exerciseDisplay = <></>;

  switch (props.contexto) {
    case "solve":
      exerciseDisplay = <></>;
      break;

    case "edit":
      exerciseDisplay = <></>;
      break;

    case "previz":
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
      <div className="m-5 text-xl">
        <p className="text-4xl strong mb-8">Verdadeiro ou Falso</p>
        {exerciseDisplay}
      </div>
    </>
  );
}
