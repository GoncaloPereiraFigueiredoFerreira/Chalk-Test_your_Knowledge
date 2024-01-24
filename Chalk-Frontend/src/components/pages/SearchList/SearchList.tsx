import { Searchbar } from "../../objects/Searchbar/Searchbar.tsx";

export function SearchList() {
  return (
    <div className="flex flex-row divide-x-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
      <div className="flex flex-col w-full h-screen overflow-auto bg-white dark:bg-slate-900">
        <Searchbar></Searchbar>
      </div>
    </div>
  );
}
