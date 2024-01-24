type TagProps = {
  children?: string;
  onClick?: () => void;
};

export function Tag({ children, onClick }: TagProps) {
  return (
    <div
      className="flex p-2 text-sm flex-shrink-0 w-fit rounded-lg bg-[#d8e3f1] dark:bg-[#1e2a3f]"
      onClick={onClick}
    >
      {children}
    </div>
  );
}
