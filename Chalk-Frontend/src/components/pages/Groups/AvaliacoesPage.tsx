import { useState, useEffect, useContext } from "react";

import { useNavigate, useParams } from "react-router-dom";
import { SearchIcon } from "../../objects/SVGImages/SVGImages";
import { APIContext } from "../../../APIContext.tsx";
import { UserContext, UserRole } from "../../../UserContext.tsx";
import { IoSearch } from "react-icons/io5";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";
import { ViewType } from "../../objects/ListTests/ListTest.tsx";

interface Test {
  id: string;
  title: string;
}

type TestList = Test[];

export function AvaliacoesPage() {
  const [searchKey, setSearch] = useState("");
  const [viewMode, setViewMode] = useState<"grid" | "row">("row");
  const { contactBACK } = useContext(APIContext);
  const [examList, setExamList] = useState<TestList>([]);
  const { id } = useParams();
  const { user } = useContext(UserContext);

  useEffect(() => {
    // load different test
    contactBACK("tests", "GET", {
      page: "0",
      itemsPerPage: "20",
      visibilityType: "COURSE",
      courseId: id!,
    }).then((page) => {
      const tests = page.items;
      setExamList(tests);
    });
  }, []);

  const filteredItems: TestList = examList.filter((item) =>
    item.title.toLowerCase().includes(searchKey.toLowerCase())
  );

  const navigate = useNavigate();

  return (
    <div className="flex flex-col w-full overflow-auto">
      <div className="flex w-full justify-around px-4 gap-16">
        <div className="relative w-1/2 justify-center">
          <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1.5 pointer-events-none">
            <IoSearch className="size-5 text-slate-600 dark:text-slate-400" />
          </div>
          <input
            type="text"
            className="py-2.5 pl-10 pr-24 rounded-lg w-full border-2 bg-inherit text-black border-slate-400 dark:text-white placeholder:dark:text-gray-400 dark:border-slate-700 focus:ring-0 focus:border-slate-400 focus:dark:border-slate-700"
            placeholder="Search..."
            value={searchKey}
            onChange={(text) => setSearch(text.target.value)}
            required
          />
        </div>
        <div className="flex gap-4">
          {viewMode === "grid" ? (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewMode("row")}
            >
              <FaListUl className="size-5 scale-90" />
              <p>Lista</p>
            </button>
          ) : (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewMode("grid")}
            >
              <HiViewGrid className="size-5 scale-110" />
              <p>Grelha</p>
            </button>
          )}
        </div>
      </div>

      {filteredItems.map((item, index) => (
        <div
          key={index}
          className="flex justify-between bg-white p-6 rounded-lg shadow-md"
        >
          <div className=" text-xl font-semibold text-gray-800 m-2 p-4">
            {item.title}
          </div>
          <div>
            <button
              className=" self-end w-32 lg:w-64 h-20 text-sm font-medium text-black focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
              onClick={() => navigate(`${item.id}`)}
            >
              Notas
            </button>
          </div>
        </div>
      ))}
    </div>
  );
}
