import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";

export function SortableItem(props: any) {
  const { attributes, listeners, setNodeRef, transform, transition } =
    useSortable({ id: props.id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    backgroundColor: "#3498db", // Background color
    color: "#ffffff", // Text color
    padding: "10px", // Padding
    borderRadius: "5px", // Border radius
    cursor: "grab", // Change cursor on hover
    outline: "none", // Remove outline on focus
    boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)", // Box shadow for a subtle lift
    width: "150px",
    // Add any other styles you want to apply
  };

  return (
    <div ref={setNodeRef} style={style} {...attributes} {...listeners}>
      {props.children}
    </div>
  );
}
