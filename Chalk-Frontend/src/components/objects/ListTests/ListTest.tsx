import { useEffect, useState, useContext } from "react";
import { Badge, Pagination } from "flowbite-react";
import { useNavigate } from "react-router-dom";
import { UserRole, UserContext } from "../../../UserContext.tsx";
import {
  CircularProgressbar,
  CircularProgressbarWithChildren,
  buildStyles,
} from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { AiTwotoneFileUnknown } from "react-icons/ai";
import { FaTasks } from "react-icons/fa";
import { APIContext } from "../../../APIContext.tsx";
import ConfirmButton from "../../interactiveElements/ConfirmButton.tsx";

function ShowTestList(
  test: TestPreview,
  index: number,
  role: UserRole,
  deleteTest: () => void,
  navigate: (url: string) => void
) {
  return (
    <>
      {role == UserRole.STUDENT ? (
        <div
          key={index}
          onClick={(e) => {
            navigate("/webapp/tests/" + test.id + "/preview");
            e.stopPropagation();
          }}
          className="max-h-[78px] rounded-lg w-full bg-white dark:bg-black overflow-hidden z-10"
        >
          <div className="p-4 flex justify-between w-full">
            <div className="flex-col w-60">
              <h5 className="mb-1 text-xl font-bold tracking-tight text-black dark:text-white">
                {test.title}
              </h5>

              <p className="mb-1 font-normal text-gray-700 dark:text-gray-400">
                <strong>Author:</strong> {test.specialistId}
              </p>
            </div>
            <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400 w-80">
              <strong>Tags:</strong>
              {test.tags.map((tag, index) => {
                return (
                  <Badge key={index} color={"blue"} className=" h-10  ">
                    {tag}
                  </Badge>
                );
              })}
            </div>
            <div className="flex justify-end space-x-2 w-60">
              <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400 w-36">
                <strong>Last Grade:</strong>
                {test.globalPoints === undefined
                  ? "TBD"
                  : ` ${test.globalPoints}%`}
              </div>

              <button
                type="button"
                onClick={(e) => {
                  navigate("/webapp/tests/" + test.id + "/solve");
                  e.stopPropagation();
                }}
                className="z-30 inline-flex items-center px-3 h-12 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
              >
                Solve
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div
          key={index}
          onClick={(e) => {
            navigate("/webapp/tests/" + test.id + "/preview");
            e.stopPropagation();
          }}
          className="max-h-[78px] rounded-lg w-full bg-white dark:bg-black overflow-hidden z-10"
        >
          <div className="p-4 flex justify-between w-full">
            <div className="flex-col w-60">
              <h5 className="mb-1 text-xl font-bold tracking-tight text-black dark:text-white">
                {test.title}
              </h5>

              <p className="mb-1 font-normal text-gray-700 dark:text-gray-400">
                <strong>Author:</strong> {test.specialistId}
              </p>
            </div>
            <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400 w-80">
              <strong>Tags:</strong>
              {test.tags.map((tag, index) => {
                return (
                  <Badge key={index} color={"blue"} className=" h-10  ">
                    {tag}
                  </Badge>
                );
              })}
            </div>
            <div className="flex space-x-2">
              {test.publishDate === null || test.publishDate === "" ? (
                <>
                  <button
                    type="button"
                    onClick={(e) => {
                      navigate("/webapp/tests/" + test.id + "/edit");
                      e.stopPropagation();
                    }}
                    className="z-30 inline-flex items-center px-3 h-12 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  >
                    Edit
                  </button>
                  <ConfirmButton
                  onConfirm={() => {
                      deleteTest();
                  }}
                  confirmationMessage="Tem acerteza que deseja apagar este Teste?"
                  button={<button
                    type="button"
                    className="z-30 inline-flex items-center px-6 py-2 h-12  text-sm font-medium text-center text-white bg-red-700  hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800"
                  >
                    Delete
                  </button>}>

                  </ConfirmButton>
                </>
              ) : (
                <button
                  type="button"
                  onClick={(e) => {
                    navigate("/webapp/tests/" + test.id + "/correction");
                    e.stopPropagation();
                  }}
                  className="z-30  inline-flex items-center px-3 h-12 text-sm font-medium text-center text-white bg-red-700 rounded-lg hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800"
                >
                  Evaluate
                </button>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  );
}

function ShowTestGrid(
  test: TestPreview,
  index: number,
  role: UserRole,
  deleteTest: () => void,
  navigate: (url: string) => void
) {
  return (
    <>
      {role == UserRole.STUDENT ? (
        <div
          key={index}
          onClick={(e) => {
            navigate("/webapp/tests/" + test.id + "/preview");
            e.stopPropagation();
          }}
          className=" z-10 max-w-lg  bg-white border-2 border-slate-300 rounded-lg shadow-lg shadow-slate-400 dark:bg-gray-800 dark:border-gray-700 overflow-hidden"
        >
          <div className="py-10 px-20 ">
            {test.globalPoints === undefined ? (
              <CircularProgressbarWithChildren value={0}>
                <AiTwotoneFileUnknown size="100" />
                <div style={{ fontSize: 12, marginTop: -5 }}>
                  <strong>Not Evaluated Yet...</strong>
                </div>
              </CircularProgressbarWithChildren>
            ) : (
              <CircularProgressbar
                value={test.globalPoints}
                text={`${test.globalPoints}%`}
                styles={buildStyles({
                  textColor: `rgba(${480 - test.globalPoints * 4.8}, ${
                    4.8 * test.globalPoints
                  }, ${0})`,
                  pathColor: `rgba(${480 - test.globalPoints * 4.8}, ${
                    4.8 * test.globalPoints
                  }, ${0})`,
                })}
              />
            )}
          </div>

          <div className="p-5 bg-slate-300">
            <h5 className="mb-2  px-2 text-2xl font-bold tracking-tight border-b border-slate-500 pb-2 text-black dark:text-white">
              {test.title}
            </h5>

            <p className="mb-2  px-2 font-normal text-gray-700 dark:text-gray-400">
              <strong>Author:</strong> {test.specialistId}
            </p>
            <div className="flex gap-3 px-2 items-center mb-4 text-gray-700 dark:text-gray-400">
              <strong>Tags:</strong>
              {test.tags.map((tag, index) => {
                return (
                  <Badge key={index} color={"blue"} className=" h-12  ">
                    {tag}
                  </Badge>
                );
              })}
            </div>
            <div className="flex w-full px-2 justify-between">
              <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400">
                <strong>Last Grade:</strong>
                {test.globalPoints === undefined
                  ? "TBD"
                  : ` ${test.globalPoints}%`}
              </div>
              <button
                type="button"
                onClick={(e) => {
                  navigate("/webapp/tests/" + test.id + "/solve");
                  e.stopPropagation();
                }}
                className="z-30  inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
              >
                Solve
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div
          key={index}
          onClick={(e) => {
            navigate("/webapp/tests/" + test.id + "/preview");
            e.stopPropagation();
          }}
          className=" z-10 max-w-lg  bg-white border-2 border-slate-300 rounded-lg shadow-lg shadow-slate-400 dark:bg-gray-800 dark:border-gray-700 overflow-hidden"
        >
          <div className="flex justify-center py-16  bg-yellow-300">
            <FaTasks size="120" />
          </div>

          <div className="p-5">
            <h5 className="mb-2 text-2xl px-2 font-bold border-b border-black pb-2 tracking-tight text-black dark:text-white">
              {test.title}
            </h5>

            <p className="mb-2 px-2 font-normal text-gray-700 dark:text-gray-400">
              <strong>Author:</strong> {test.specialistId}
            </p>
            <div className="flex gap-3 px-2 items-center mb-4 text-gray-700 dark:text-gray-400">
              <strong>Tags:</strong>
              {test.tags.map((tag, index) => {
                return (
                  <Badge key={index} color={"blue"} className=" h-12  ">
                    {tag}
                  </Badge>
                );
              })}
            </div>
            <div className="flex w-full px-2 justify-end">
              {test.publishDate === null || test.publishDate === "" ? (
                <>
                  <button
                    type="button"
                    onClick={(e) => {
                      navigate("/webapp/tests/" + test.id + "/edit");
                      e.stopPropagation();
                    }}
                    className="z-30  inline-flex items-center px-6 py-2 h-12 text-sm font-medium text-center text-white bg-blue-700  hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  >
                    Edit
                  </button>
                  <ConfirmButton
                  onConfirm={() => {
                      deleteTest();
                  }}
                  confirmationMessage="Tem acerteza que deseja apagar este Teste?"
                  button={<button
                    onClick={(e) => {e.stopPropagation();}}
                    type="button"
                    className="z-30 inline-flex items-center px-6 py-2 h-12  text-sm font-medium text-center text-white bg-red-700  hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800"
                  >
                    Delete
                  </button>}>

                  </ConfirmButton>
                </>
              ) : (
                <button
                  type="button"
                  onClick={(e) => {
                    navigate("/webapp/tests/" + test.id + "/correction");
                    e.stopPropagation();
                  }}
                  className="z-30 inline-flex items-center px-6 h-12 text-sm font-medium text-center text-white bg-green-700  hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
                >
                  Evaluate
                </button>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  );
}

interface TestPreview {
  id: string;
  specialistId: string;
  title: string;
  status: string;
  tags: string[];
  publishDate?: string;
  globalPoints?: number;
}

type TestList = TestPreview[];

export enum ViewType {
  LIST = "LIST",
  GRID = "GRID",
}

export function ListTests({
  view,
  courseId,
  visibilityType,
  searchKey,
  tagsList,
}: any) {
  const [currentPage, setCurrentPage] = useState(1);
  const onPageChange = (page: number) => setCurrentPage(page);
  const [testList, setTestList] = useState<TestList>([]);
  const { user } = useContext(UserContext);
  const [totalPages, setTotalPages] = useState(1);
  const { contactBACK } = useContext(APIContext);
  const navigate = useNavigate();

  useEffect(() => {
    let requestTags: any = tagsList.map((tag: any) => tag.id);
    if (user.user?.role == UserRole.SPECIALIST) {
      contactBACK("tests", "GET", {
        page: (currentPage - 1).toString(),
        itemsPerPage: "20",
        specialistId: user.user.id,
        courseId: courseId,
        visibilityType: visibilityType,
        tags: requestTags,
      }).then((page) => {
        const tests = page.items;
        setTotalPages(page.totalPages);
        tests.map((test: any) => {
          return (test["tags"] = test.tags.map((tag: any) => {
            return tag.name;
          }));
        });
        setTestList(tests);
      });
    } else {
      contactBACK("tests", "GET", {
        page: (currentPage - 1).toString(),
        itemsPerPage: "20",
        courseId: courseId,
        visibilityType: visibilityType,
        tags: requestTags,
      }).then((page) => {
        let tests = page.items;
        setTotalPages(page.totalPages);
        let promises: Promise<any>[] = [];
        tests.map((test: any) => {
          promises.push(
            contactBACK(
              "tests/" + test.id + "/resolutions/" + user.user!.id + "/last",
              "GET"
            ).then((exam) => {
              return exam && exam.status === "REVISED"
                ? exam.totalPoints
                : undefined;
            })
          );
        });
        Promise.all(promises).then((results) => {
          tests.map((teste: any, index: number) => {
            if (results[index])
              teste.globalPoints = (results[index] / teste.globalPoints) * 100;
            else teste.globalPoints = undefined;
            let tags = teste.tags.map((tag: any) => {
              return tag.name;
            });
            teste.tags = tags;
          });
          setTestList(tests);
        });
      });
    }
  }, [currentPage, tagsList]);

  const filteredItems: TestList = testList.filter((item) =>
    item.title.toLowerCase().includes(searchKey.toLowerCase())
  );

  const deleteTest = (id: string) => {
    contactBACK("tests/" + id, "DELETE", undefined, undefined, "none").then(
      () => {
        setTestList([...testList].filter((test) => id !== test.id));
      }
    );
  };

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max pb-8">
        {view == ViewType.GRID ? (
          <div className="grid grid-cols-2 gap-4 lg:grid-cols-4 md:grid-cols-3 md:gaps-4">
            {filteredItems.map((test, index) => {
              return ShowTestGrid(
                test,
                index,
                user.user!.role,
                () => deleteTest(test.id),
                navigate
              );
            })}
          </div>
        ) : (
          <div className="grid grid-cols-1 gap-2">
            {filteredItems.map((test, index) => {
              return ShowTestList(
                test,
                index,
                user.user!.role,
                () => deleteTest(test.id),
                navigate
              );
            })}
          </div>
        )}
        <div className="flex w-full justify-center">
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={onPageChange}
            showIcons
          />
        </div>
      </div>
    </>
  );
}
