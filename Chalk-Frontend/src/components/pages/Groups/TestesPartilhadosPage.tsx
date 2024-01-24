import { useCallback, useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ListTests, ViewType } from "../../objects/ListTests/ListTest";
import { TagsList, TagsFilterModal } from "../../objects/Tags/TagsFilterModal";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";
import { FilterByTagsSearchBar } from "../../objects/Searchbar/FilterByTagsSearchBar";
import { APIContext } from "../../../APIContext";
import { UserContext } from "../../../UserContext";
import { Modal, Pagination } from "flowbite-react";
import { Test } from "../../objects/Test/Test";
import { TagBlock } from "../../interactiveElements/TagBlock";

function ListTestsModal({ modalState, closeModal }: any) {
  const [tests, setTests] = useState<Test[]>([]);
  const [page, setPage] = useState(1);
  const [maxPages, setMaxPage] = useState(1);
  const { contactBACK } = useContext(APIContext);
  const { user } = useContext(UserContext);

  useEffect(() => {
    contactBACK("tests", "GET", {
      page: (page - 1).toString(),
      itemsPerPage: "10",
      specialistId: user.user?.id!,
    }).then((json) => {
      setTests(json.items);
      setMaxPage(json.totalPages);
    });
  }, [page]);

  return (
    <Modal
      dismissible
      size="2xl"
      show={modalState}
      onClose={() => closeModal("")}
    >
      <Modal.Header>
        <p>Lista de testes disponíveis para adicionar ao grupo</p>
      </Modal.Header>
      <Modal.Body>
        <div className="flex flex-col w-full gap-4 min-h-max pb-8">
          <div className="flex flex-col">
            {tests.map((test: Test, index: number) => (
              <>
                <div
                  key={index}
                  onClick={(e) => {
                    closeModal(test.id);
                    e.stopPropagation();
                  }}
                  className=" hover:bg-slate-400 select-pointer border-t border-b p-4 w-full border-slate-300 dark:border-slate-600"
                >
                  <div className="grid-ListTest w-full items-center">
                    <div className="flex flex-col">
                      <h5 className="text-xl font-bold tracking-tight text-black dark:text-white">
                        {test.title}
                      </h5>
                    </div>
                    <div className="flex justify-start gap-2 items-center text-slate-700 dark:text-slate-200">
                      <strong>Tags:</strong>
                      {test.tags.map((tag, index) => {
                        return <TagBlock key={index}>{tag.name}</TagBlock>;
                      })}
                    </div>
                  </div>
                </div>
              </>
            ))}
          </div>

          <div className="flex w-full justify-center">
            <Pagination
              currentPage={page}
              totalPages={maxPages}
              onPageChange={() => setPage(page + 1)}
              showIcons
            />
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
}

export function TestesPartilhadosPage() {
  const [searchKey, setSearch] = useState("");
  const [viewMode, setViewMode] = useState<ViewType>(ViewType.LIST);
  const [openModal, setOpenModal] = useState(false);
  const [testsModal, setTestsModal] = useState(false);
  const [testID, setTestId] = useState("");
  const { id } = useParams();
  const [tagsList, setTagsList] = useState<TagsList>([]);
  const { contactBACK } = useContext(APIContext);

  //Codigo manhoso
  const [, updateState] = useState<any>();
  const forceUpdate = useCallback(() => updateState({}), []);

  const addTest = (id: string) => {
    setTestId(id);
    setTestsModal(false);
  };

  useEffect(() => {
    if (testID !== "")
      contactBACK(
        "tests/" + testID + "/duplicate",
        "POST",
        undefined,
        { courseId: id, visibility: "COURSE" },
        "none"
      ).then(() => {
        useCallback(() => forceUpdate(), []);
      });
  }, [testID]);

  return (
    <div className="w-full h-screen py-24 overflow-auto bg-white dark:bg-black">
      <div className=" w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-16 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <div className="">
            <div className="relative w-full justify-center ">
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
              <ListTestsModal
                modalState={testsModal}
                closeModal={addTest}
              ></ListTestsModal>
            </div>
          </div>

          <div className="flex  items-center">
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
              onClick={() => {
                setTestsModal(true);
              }}
            >
              Adicionar teste
            </button>
            {viewMode === ViewType.GRID ? (
              <button
                className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                onClick={() => setViewMode(ViewType.LIST)}
              >
                <FaListUl className="size-5 scale-90" />
                <p>Lista</p>
              </button>
            ) : (
              <button
                className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                onClick={() => setViewMode(ViewType.GRID)}
              >
                <HiViewGrid className="size-5 scale-110" />
                <p>Grelha</p>
              </button>
            )}
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
