import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { Searchbar } from "../../objects/Searchbar/Searchbar";

export function FrontPage() {
  return (
    <div className="flex flex-row">
      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
        <Searchbar></Searchbar>
        <ListExercises></ListExercises>
      </div>
    </div>
  );
}
