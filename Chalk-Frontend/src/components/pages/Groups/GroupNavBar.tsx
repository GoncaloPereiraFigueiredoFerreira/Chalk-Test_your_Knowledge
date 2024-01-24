import { useContext, useEffect, useState } from "react";
import { Link, Outlet, useParams } from "react-router-dom";
import { APIContext } from "../../../APIContext";
import { Course } from "../../../UserContext";

export function GroupNavBar() {
  const { id } = useParams();
  const { contactBACK } = useContext(APIContext);
  const [course, setCourse] = useState<Course>({ id: "-1", name: "" });
  const [selectedOption, setSelectedOption] = useState(0);

  useEffect(() => {
    contactBACK("courses/" + id, "GET").then((json) => {
      setCourse({ id: json.id, name: json.name });
    });
  }, [id]);

  return (
    <>
      <div className="flex justify-center font-pacifico font-medium max-w-full h-32 container pt-6 px-6 first-section overflow-hidden">
        <div className="flex text-5xl items-center ">
          {course.name}
          <img
            src="/better-chalky.png"
            className="shadowed object-contain h-32"
            alt=""
          />
        </div>
      </div>
      <div className="flex flex-col w-full h-screen overflow-auto min-h-max px-8 pb-8 text-black dark:text-white bg-white dark:bg-slate-900">
        <div className="flex w-full pt-8 mb-3 border-b-2 border-slate-400 dark:border-slate-600">
          <div className="relative">
            <div className="absolute w-full h-11">
              <div className="flex h-full">
                <button className="w-4"></button>
                <Link
                  className="flex items-center"
                  onClick={() => setSelectedOption(0)}
                  to="alunos"
                >
                  <div className="flex px-5 h-7 w-32 items-center justify-center text-lg font-medium">
                    Alunos
                  </div>
                </Link>

                <Link
                  className="flex items-center"
                  onClick={() => setSelectedOption(1)}
                  to="avaliacoes"
                >
                  <div className="flex px-5 h-7 w-32 items-center justify-center border-l-2 text-lg font-medium border-slate-400 dark:border-slate-600">
                    Avaliações
                  </div>
                </Link>

                <Link
                  className="flex items-center"
                  onClick={() => setSelectedOption(2)}
                  to="testes"
                >
                  <div className="flex px-5 h-7 w-32 items-center justify-center border-l-2 text-lg font-medium border-slate-400 dark:border-slate-600">
                    Testes
                  </div>
                </Link>
                <Link
                  className="flex items-center"
                  onClick={() => setSelectedOption(3)}
                  to="testes"
                >
                  <div className="flex px-5 h-7 w-32 items-center justify-center border-l-2 text-lg font-medium border-slate-400 dark:border-slate-600">
                    Definições
                  </div>
                </Link>
                <button className="w-4"></button>
              </div>
            </div>
            {/* selection shadow */}
            <div className="flex w-full h-11">
              {/* left white area */}
              <span
                className="bg-slate-400 dark:bg-slate-600"
                style={{ width: 14 + 128 * selectedOption + "px" }}
              >
                <span className="flex w-full h-full rounded-br-lg bg-white dark:bg-slate-900 transition-[width]" />
              </span>
              {/* selected area */}
              <span
                className="bg-white dark:bg-slate-900"
                style={{ width: "132px" }}
              >
                <span className="flex w-full h-full rounded-t-lg bg-slate-400 dark:bg-slate-600" />
              </span>
              {/* right area */}
              <span
                className="bg-slate-400 dark:bg-slate-600"
                style={{ width: 14 + 128 * (3 - selectedOption) + "px" }}
              >
                <span className="flex w-full h-full rounded-bl-lg bg-white dark:bg-slate-900 transition-[width]" />
              </span>
            </div>
          </div>
        </div>

        <Outlet />
      </div>
    </>
  );
}
