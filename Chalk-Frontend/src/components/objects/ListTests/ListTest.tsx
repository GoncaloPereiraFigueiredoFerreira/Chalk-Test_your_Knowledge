import { useEffect, useState } from "react";
import { Badge, Dropdown, Pagination } from "flowbite-react";
import { GridIcon, ListIcon } from "../SVGImages/SVGImages";
import { Link } from "react-router-dom";

const exampleData = [
  {
    id: "1",
    author: "JCR",
    title: "Teste de RPCW 2022",
    tags: ["Desenvolvimento Web", "Informática"],
  },
  {
    id: "2",
    author: "JCR",
    title: "1º Teste de PL 2023",
    tags: ["Python", "Processamento de Linguagens"],
  },
  {
    id: "3",
    author: "JMF",
    title: "Teste de RAS 2023",
    tags: ["Arquitetura de Software", "Modelagem"],
  },
  {
    id: "4",
    author: "JBB",
    title: "Teste de PI 2020",
    tags: ["Programação Imperatica", "C"],
  },
  {
    id: "5",
    author: "JNO",
    title: "Teste de CP 2021",
    tags: ["Calcúlo de Programas", "Haskell"],
  },
];

function ShowTestList(test: Test) {
  return (
    <>
      <div className="max-h-[78px] rounded-lg w-full bg-3-2 overflow-hidden">
        <div className="p-4 flex justify-between w-full">
          <div className="flex-col">
            <h5 className="mb-1 text-xl font-bold tracking-tight text-gray-900 dark:text-white">
              {test.title}
            </h5>

            <p className="mb-1 font-normal text-gray-700 dark:text-gray-400">
              <strong>Author:</strong> {test.author}
            </p>
          </div>
          <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400">
            <strong>Tags:</strong>
            {test.tags.map((tag) => {
              return (
                <Badge color={"blue"} className=" h-10  ">
                  {tag}
                </Badge>
              );
            })}
          </div>
          <div className="flex space-x-2">
            <Link
              to="#"
              className="inline-flex items-center px-3 h-12 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              Edit
            </Link>
            <Link
              to="solve" // /solve/id
              className="inline-flex items-center px-3 h-12 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
            >
              Solve
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}

function ShowTestGrid(test: Test) {
  return (
    <div className=" max-w-lg  bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 overflow-hidden">
      <a href="#">
        <img
          className="rounded-t-lg"
          src="https://flowbite.com/docs/images/carousel/carousel-1.svg"
          alt=""
        />
      </a>
      <div className="p-5">
        <a href="#">
          <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
            {test.title}
          </h5>
        </a>
        <p className="mb-2 font-normal text-gray-700 dark:text-gray-400">
          <strong>Author:</strong> {test.author}
        </p>
        <div className="flex gap-3 items-center mb-4 text-gray-700 dark:text-gray-400">
          <strong>Tags:</strong>
          {test.tags.map((tag) => {
            return (
              <Badge color={"blue"} className=" h-12  ">
                {tag}
              </Badge>
            );
          })}
        </div>
        <div className="flex w-full justify-between">
          <Link
            to="#"
            className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Edit
          </Link>
          <Link
            to="solve"
            className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
          >
            Solve
          </Link>
        </div>
      </div>
    </div>
  );
}

interface Test {
  id: string;
  author: string;
  title: string;
  tags: string[];
  publishDate?: Date;
}

type TestList = Test[];

enum ViewType {
  LIST = "LIST",
  GRID = "GRID",
}

export function ListTests() {
  const [currentPage, setCurrentPage] = useState(1);
  const onPageChange = (page: number) => setCurrentPage(page);
  const [viewType, setViewType] = useState(ViewType.GRID);
  const [testList, setTesList] = useState<TestList>([]);

  useEffect(() => {
    // load different test
    if (currentPage != 1) setTesList([]);
    else setTesList(exampleData);
  }, [currentPage]);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Testes</label>
          <div className="flex space-x-4">
            <Dropdown
              label=""
              dismissOnClick={false}
              renderTrigger={() => (
                <button className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2">
                  {viewType === ViewType.GRID ? "Grelha" : "Lista"}
                </button>
              )}
            >
              <Dropdown.Item
                as="a"
                className="flex space-x-2"
                onClick={() => setViewType(ViewType.LIST)}
              >
                <ListIcon />
                <p>Lista</p>
              </Dropdown.Item>
              <Dropdown.Item
                as="a"
                className="flex space-x-2"
                onClick={() => setViewType(ViewType.GRID)}
              >
                <GridIcon />
                <p>Grelha</p>
              </Dropdown.Item>
            </Dropdown>
            <button
              className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
              onClick={() => {}}
            >
              Criar Teste
            </button>
          </div>
        </div>
        {viewType == ViewType.GRID ? (
          <div className="grid grid-cols-2 gap-2 lg:grid-cols-4 md:grid-cols-3 md:gaps-4">
            {testList.map((test) => {
              return ShowTestGrid(test);
            })}
          </div>
        ) : (
          <div className="grid grid-cols-1 gap-2">
            {testList.map((test) => {
              return ShowTestList(test);
            })}
          </div>
        )}
        <div className="flex w-full justify-center">
          <Pagination
            currentPage={currentPage}
            totalPages={3}
            onPageChange={onPageChange}
            showIcons
          />
        </div>
      </div>
    </>
  );
}
