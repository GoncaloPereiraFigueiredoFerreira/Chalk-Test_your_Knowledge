const TagColors: { [index: number]: any } = {
  0: { color: "#34ffa0", text: "black" },
  1: { color: "#fcff5d", text: "black" },
  2: { color: "#277da7", text: "black" },
  3: { color: "#0ec434", text: "black" },
  4: { color: "#228c68", text: "white" },
  5: { color: "#5acae0", text: "black" },
  6: { color: "#dab232", text: "black" },
  7: { color: "#34d834", text: "black" },
  8: { color: "#3998f5", text: "white" },
  9: { color: "#5a28af", text: "white" },
  10: { color: "#31cfbd", text: "white" },
  11: { color: "#3750db", text: "white" },
  12: { color: "#f22020", text: "white" },
  13: { color: "#991919", text: "white" },
  14: { color: "#7dfc00", text: "black" },
  15: { color: "#f88f2d", text: "black" },
  16: { color: "#c56133", text: "white" },
  17: { color: "#96341c", text: "white" },
  18: { color: "#632819", text: "white" },
  19: { color: "#f3c12b", text: "black" },
  20: { color: "#f47a22", text: "black" },
  21: { color: "#2f2aa0", text: "white" },
  22: { color: "#a12cb3", text: "white" },
  23: { color: "#772b9d", text: "white" },
  24: { color: "#f7659f", text: "black" },
  25: { color: "#d30b94", text: "white" },
  26: { color: "#ae3eda", text: "white" },
  27: { color: "#ae3bd4", text: "white" },
  28: { color: "#5d4c86", text: "white" },
  29: { color: "#ec7f36", text: "black" },
};

type TagProps = {
  children?: string;
  onClick?: () => void;
};

export function Tag({ children, onClick }: TagProps) {
  const stringToColour = (str: string) => {
    let hash = 0;
    str.split("").forEach((char) => {
      hash = char.charCodeAt(0) + ((hash << 5) - hash);
    });
    hash = Math.abs(hash) % Object.keys(TagColors).length;
    return {
      backgroundColor: TagColors[hash].color,
      color: TagColors[hash].text === "white" ? "#ffffff" : "#000000",
    };
  };

  return (
    <div
      className="flex p-2 text-sm flex-shrink-0 w-fit rounded-lg text-black dark:text-white"
      style={stringToColour(children ?? "")}
      onClick={onClick}
    >
      {children}
    </div>
  );
}
