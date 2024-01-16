import { CQExercise, CQResolutionData, PreviewProps } from "../Exercise";
import { ExerciseHeaderComp } from "../Header/ExHeader";

export interface CQPreviewProps {
  exercise: CQExercise;
  position: string;
  context: PreviewProps;
}

export function CQPreview({ exercise, context }: CQPreviewProps) {
  const msgs = context.resolution
    ? (context.resolution as CQResolutionData).msgs
    : [];
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement} />

      <div className="flex flex-col flex-grow  w-full bg-white shadow-xl rounded-lg overflow-hidden justify-items-center ">
        <div className="flex flex-col-reverse p-4 overflow-y-scroll h-56 scroll-smooth">
          {context.resolution ? (
            msgs.map((msg, index) => {
              if (msgs.length % 2 == 0) {
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
                    <div className="flex-shrink-0 h-10 w-10 rounded-full bg-gray-300"></div>
                    <div>
                      <div className="bg-gray-300 p-3 rounded-r-lg rounded-bl-lg">
                        <p className="text-sm">{msg}</p>
                      </div>
                    </div>
                  </div>
                );
              }
            })
          ) : (
            <></>
          )}
        </div>
      </div>
    </>
  );
}
