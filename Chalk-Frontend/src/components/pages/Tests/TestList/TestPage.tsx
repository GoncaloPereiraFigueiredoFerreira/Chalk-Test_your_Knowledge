import { ListTests, ViewType } from "../../../objects/ListTests/ListTest.tsx";

import { useContext, useState } from "react";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";
import { UserContext, UserRole } from "../../../../UserContext.tsx";
import {
  TagsList,
  TagsFilterModal,
} from "../../../objects/Tags/TagsFilterModal.tsx";
import { FilterByTagsSearchBar } from "../../../objects/Searchbar/FilterByTagsSearchBar.tsx";
import { useNavigate } from "react-router-dom";

export function TestPage() {
  const [view, setViewType] = useState(ViewType.GRID);
  const [kindOfTest, setKindOfTest] = useState<"Public" | "Private">("Public");
  const [searchKey, setSearch] = useState("");
  const { user } = useContext(UserContext);
  const [tagsList, setTagsList] = useState<TagsList>([]);
  const [openModal, setOpenModal] = useState(false);
  const navigate = useNavigate();

  return (
    <div className="flex flex-col w-full h-screen overflow-auto px-8 pb-8 bg-white dark:bg-slate-900 divide-slate-400 dark:divide-slate-600">
      <FilterByTagsSearchBar
        setSearch={setSearch}
        setOpenModal={setOpenModal}
        tagsList={tagsList}
      ></FilterByTagsSearchBar>
      <TagsFilterModal
        setTagsList={setTagsList}
        openModal={openModal}
        setOpenModal={setOpenModal}
        header={"Selecione as tags que pretende ver"}
      ></TagsFilterModal>
      <div className="flex flex-col w-full gap-4 min-h-max">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-slate-400 dark:border-slate-600">
          <label className="flex text-4xl text-slate-600 dark:text-white">
            {kindOfTest === "Public" ? "Testes públicos" : "Os meus testes"}
          </label>
          {user.user?.role == UserRole.SPECIALIST ? (
            <button
              className="py-2 px-4 text-base rounded-lg font-medium btn-base-color transition-all duration-75 active:scale-90"
              onClick={() => {
                navigate("/webapp/create-test");
              }}
            >
              Criar Teste
            </button>
          ) : (
            <></>
          )}
        </div>
        <div className="flex self-start gap-4 px-8">
          {kindOfTest === "Private" ? (
            <button
              onClick={() => setKindOfTest("Public")}
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90"
            >
              Testes públicos
            </button>
          ) : (
            <button
              onClick={() => setKindOfTest("Private")}
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90"
            >
              Os meus testes
            </button>
          )}
          {view === ViewType.GRID ? (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewType(ViewType.LIST)}
            >
              <FaListUl className="size-5 scale-90" />
              <p>Lista</p>
            </button>
          ) : (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewType(ViewType.GRID)}
            >
              <HiViewGrid className="size-5 scale-110" />
              <p>Grelha</p>
            </button>
          )}
        </div>
        <div className="px-4">
          <ListTests
            view={view}
            courseId={""}
            visibilityType={kindOfTest == "Public" ? "public" : ""}
            searchKey={searchKey}
            tagsList={tagsList}
            differentRoute={
              kindOfTest == "Public"
                ? ""
                : user.user?.role === UserRole.SPECIALIST
                ? ""
                : `/autoEvaluation/student/${user.user?.id}`
            }
          ></ListTests>
        </div>
      </div>
    </div>
  );
}
