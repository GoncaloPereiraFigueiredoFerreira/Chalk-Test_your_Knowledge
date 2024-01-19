import { Dropdown } from "flowbite-react";
import { ListTests, ViewType } from "../../../objects/ListTests/ListTest.tsx";
import { Searchbar } from "../../../objects/Searchbar/Searchbar.tsx";
import { useState } from "react";
import { GridIcon, ListIcon } from "../../../objects/SVGImages/SVGImages.tsx";

export function TestPage() {
  const [view, setViewType] = useState(ViewType.GRID);
  const [searchKey, setSearch] = useState("");

  return (
    <div className="flex flex-row divide-x-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
      <div className="flex flex-col w-full h-screen overflow-auto bg-white dark:bg-black min-h-max px-8 pb-8">
        <Searchbar setSearch={setSearch}></Searchbar>
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Testes
          </label>
          <div className="flex space-x-4">
            <Dropdown
              label=""
              dismissOnClick={false}
              renderTrigger={() => (
                <button className="transition-all duration-100 py-2 px-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black">
                  {view === ViewType.GRID ? "Grelha" : "Lista"}
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
              className="transition-all duration-100 py-2 px-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black"
              onClick={() => {}}
            >
              Criar Teste
            </button>
          </div>
        </div>
        <ListTests
          view={view}
          courseId={""}
          visibilityType={"PUBLIC"}
          searchKey={searchKey}
          tagsList={[]}
        ></ListTests>
      </div>
    </div>
  );
}
