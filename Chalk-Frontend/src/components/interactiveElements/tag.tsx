type TagProps = {
  children?: string;
  onClick?: () => void;
};

export function Tag({ children, onClick }: TagProps) {
  return (
    <div
      className="flex p-2 text-sm flex-shrink-0 w-fit rounded-lg text-black dark:text-white bg-[#d8e3f1] dark:bg-slate-600"
      onClick={onClick}
    >
      {children}
    </div>
  );
}
