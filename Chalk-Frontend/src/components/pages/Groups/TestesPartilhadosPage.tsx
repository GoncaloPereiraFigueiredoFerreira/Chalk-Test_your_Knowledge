import { useState } from "react";
import { useParams } from "react-router-dom";
import { ListTests, ViewType } from "../../objects/ListTests/ListTest";
import {
  GridIcon,
  ListIcon,
  SearchIcon,
} from "../../objects/SVGImages/SVGImages";
import { TagsList, TagsFilterModal } from "../../objects/Tags/TagsFilterModal";
import { Button } from "flowbite-react";

export function TestesPartilhadosPage() {
  const [searchKey, setSearch] = useState("");
  const [viewMode, setViewMode] = useState<ViewType>(ViewType.LIST);
  const [openModal, setOpenModal] = useState(false);

  const { id } = useParams();
  const [tagsList, setTagsList] = useState<TagsList>([]);

  return (
    <div className="w-full h-screen py-24 overflow-auto bg-white dark:bg-black">
      <div className=" w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-16 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <div className="">
            <div className="relative w-full justify-center">
              <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
                <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
              </div>
              <input
                type="text"
                className="py-2.5 pl-10 pr-24 rounded-lg w-full z-20 border text-black bg-white border-[#dddddd] dark:text-black dark:bg-gray-600 dark:border-gray-600 focus:ring-0 focus:border-[#dddddd] focus:dark:border-gray-600"
                placeholder="Search..."
                value={searchKey}
                onChange={(text) => setSearch(text.target.value)}
                required
              />
            </div>
          </div>
          <div>
            {tagsList.map((tag, index) => (
              <button key={index} className="bg-blue-300">
                {tag.name}
              </button>
            ))}
          </div>
          <div>
            <Button onClick={() => setOpenModal(true)}>Filter by tags</Button>
            <TagsFilterModal
              setTagsList={setTagsList}
              openModal={openModal}
              setOpenModal={setOpenModal}
              header={"Selecione as tags que pretende ver"}
            ></TagsFilterModal>
          </div>
          <div className="flex  items-center">
            <button
              className="  w-24 h-10 text-sm font-medium text-black focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10   dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
              onClick={() => {}}
            >
              Adicionar teste
            </button>
            <button
              className="px-2 w-12"
              onClick={() =>
                setViewMode(
                  viewMode === ViewType.GRID ? ViewType.LIST : ViewType.GRID
                )
              }
            >
              {viewMode === ViewType.GRID ? (
                <ListIcon size="size-8" />
              ) : (
                <GridIcon size="size-8" />
              )}
            </button>
          </div>
        </div>
        <ListTests
          view={viewMode}
          courseId={id}
          visibilityType={"COURSE"}
          searchKey={searchKey}
          tagsList={tagsList}
          differentRoute={""}
        ></ListTests>
      </div>
    </div>
  );
}
