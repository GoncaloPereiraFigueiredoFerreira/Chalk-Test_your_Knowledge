import { ListTests, ViewType } from "../../../objects/ListTests/ListTest.tsx";
import { Searchbar } from "../../../objects/Searchbar/Searchbar.tsx";
import { useContext, useState } from "react";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";
import { UserContext, UserRole } from "../../../../UserContext.tsx";

export function TestPage() {
  const [view, setViewType] = useState(ViewType.GRID);
  const [kindOfTest, setKindOfTest] = useState<"Public" | "Private">("Public");
  const [searchKey, setSearch] = useState("");
  const { user } = useContext(UserContext);

  return (
    <div className="flex flex-row divide-x-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
      <div className="flex flex-col w-full h-screen overflow-auto bg-white dark:bg-black min-h-max px-8 pb-8">
        <Searchbar setSearch={setSearch}></Searchbar>
        <div className="flex flex-col w-full gap-4 min-h-max">
          <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-[#bbbbbb] dark:border-slate-600">
            <label className="flex text-4xl text-slate-600 dark:text-white">
              {kindOfTest === "Public" ? "Testes públicos" : "Os meus testes"}
            </label>
            <button
              className="py-2 px-4 text-base rounded-lg font-medium btn-base-color"
              onClick={() => {}}
            >
              Criar Teste
            </button>
          </div>
          <div className="flex justify-between">
            <div className="flex gap-4">
              {kindOfTest === "Private" ? (
                <button
                  onClick={() => setKindOfTest("Public")}
                  className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                >
                  Testes públicos
                </button>
              ) : (
                <button
                  onClick={() => setKindOfTest("Private")}
                  className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                >
                  Os meus testes
                </button>
              )}
            </div>
            <div className="flex gap-4">
              {view === ViewType.GRID ? (
                <button
                  className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                  onClick={() => setViewType(ViewType.LIST)}
                >
                  <FaListUl className="size-5 scale-90" />
                  <p>Lista</p>
                </button>
              ) : (
                <button
                  className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                  onClick={() => setViewType(ViewType.GRID)}
                >
                  <HiViewGrid className="size-5 scale-110" />
                  <p>Grelha</p>
                </button>
              )}
            </div>
          </div>
          <ListTests
            view={view}
            courseId={""}
            visibilityType={
              user.user?.role === UserRole.SPECIALIST ? "" : "public"
            }
            searchKey={searchKey}
            tagsList={[]}
          ></ListTests>
        </div>
      </div>
    </div>
  );
}
