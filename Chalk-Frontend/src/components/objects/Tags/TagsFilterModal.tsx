import { Button, Modal } from "flowbite-react";
import { useContext, useEffect, useState } from "react";
import { APIContext } from "../../../APIContext";

interface Tag {
  id: string;
  name: string;
  path: string;
  expanded: boolean;
}

export type TagsList = Tag[];

export interface TagsModalProps {
  openModal: boolean;
  header: string;
  setOpenModal: (v: boolean) => void;
  setTagsList: (l: TagsList) => void;
}

export function TagsFilterModal({
  header,
  setTagsList,
  openModal,
  setOpenModal,
}: TagsModalProps) {
  const [selectedTags, setSelectedTags] = useState<TagsList>([]);
  const [allTags, setAllTags] = useState<TagsList>([]);
  const [exposedTags, setExposedTags] = useState<TagsList>([]);
  const { contactBACK } = useContext(APIContext);

  useEffect(() => {
    if (openModal)
        contactBACK("tags/path", "GET", { path: "/", level: "-1" }).then(
        (response) => {
            response.json().then((tags) => {
            setAllTags(tags);
            });
        }
        );
  }, [openModal]);

  useEffect(() => {
    setExposedTags(allTags.filter((item) => item.path === "/"));
  }, [allTags]);

  const changeSelectedTags = (tag: Tag) => {
    selectedTags.includes(tag)
      ? setSelectedTags(selectedTags.filter((item) => item != tag))
      : setSelectedTags([...selectedTags, tag]);
  };

  const changeTags = (tag: Tag) => {
    if (tag.expanded === true) {
      tag.expanded = false;
      const tags_to_be_removed = allTags.filter((item) =>
        item.path.startsWith(tag.path + tag.name)
      );
      for (let tag of tags_to_be_removed) {
        tag.expanded = false;
      }
      setExposedTags(
        exposedTags.filter(
          (item) => !item.path.startsWith(tag.path + tag.name + "/")
        )
      );
    } else {
      const beforeTag = exposedTags.slice(0, exposedTags.indexOf(tag) + 1);
      const afterTag = exposedTags.slice(
        exposedTags.indexOf(tag) + 1,
        exposedTags.length
      );

      const moreTags = allTags.filter(
        (item) => item.path == tag.path + tag.name + "/"
      );
      tag.expanded = true;
      setExposedTags([...beforeTag, ...moreTags, ...afterTag]);
    }
  };

  const resetTags = () => {
    //TODO - nao ser preso por fazer isto
    for (let tag of allTags) {
      tag.expanded = false;
    }
  };

  const addTagsList = () => {
    setTagsList([...selectedTags]);
    resetTags();
    setOpenModal(false);
  };

  const closeModal = () => {
    setOpenModal(false);
    resetTags();
  };

  const getTabs = (tag: Tag) => {
    const number_of_tabs = tag.path.split("/").length - 2;
    return number_of_tabs;
  };

  //mostra as tags existentes no modal

  //seleciona todas as que quer e vao para setTagsList
  return (
    <Modal dismissible size="xl" show={openModal} onClose={() => closeModal()}>
      <Modal.Header> {header}</Modal.Header>
      <Modal.Body>
        <div className=" ">
          {exposedTags.map((tag, index) => (
            <div
              style={{ marginLeft: getTabs(tag) * 50 }}
              key={index}
              className={`px-2 py-2 `}
            >
              <button
                className={
                  selectedTags.includes(tag)
                    ? `bg-blue-500 text-white px-4 py-2 `
                    : `bg-gray-200 text-black px-4 py-2 `
                }
                onClick={() => changeSelectedTags(tag)}
              >
                {tag.name}
              </button>
              <button
                className="bg-gray-400 text-white px-4 py-2"
                onClick={() => changeTags(tag)}
              >
                {tag.expanded === undefined ? ">" : "<"}
              </button>
            </div>
          ))}
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={() => addTagsList()}>Apply</Button>
        <Button onClick={() => setSelectedTags([])}>Clear tags</Button>
      </Modal.Footer>
    </Modal>
  );
}
