import { useDroppable } from "@dnd-kit/core";

export function Droppable(props: any) {
  const { isOver, setNodeRef } = useDroppable({
    id: props.id,
  });
  const style = {
    width: "300px",
    height: "200px",
    backgroundColor: isOver ? "#e0f7fa" : "#fff", // Change background color when a draggable element is over
    border: "2px dashed #4caf50", // Dashed border to indicate droppable area
    borderRadius: "8px",
    padding: "20px",
    margin: "20px",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  };

  return (
    <div ref={setNodeRef} style={style}>
      {props.children}
    </div>
  );
}
