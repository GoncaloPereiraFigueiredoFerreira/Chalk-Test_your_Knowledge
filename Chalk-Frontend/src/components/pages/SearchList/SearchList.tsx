import { ListTests } from "../../objects/ListTests/ListTest.tsx";
import { Searchbar } from "../../objects/Searchbar/Searchbar.tsx";

export function SearchList() {
  return (
    <div className="flex flex-row divide-x-2 border-gray-2-2">
      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
        <Searchbar></Searchbar>
      </div>
    </div>
  );
}
