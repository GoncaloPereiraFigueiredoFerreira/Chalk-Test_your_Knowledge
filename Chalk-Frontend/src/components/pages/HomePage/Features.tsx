export const features = [
  {
    id: "feature-1",
    img: "",
    title: "Rewards",
    content:
      "The best credit cards offer some tantalizing combinations of promotions and prizes",
  },
  {
    id: "feature-2",
    img: "",
    title: "100% Secured",
    content:
      "We take proactive steps make sure your information and transactions are secure.",
  },
  {
    id: "feature-3",
    img: "",
    title: "Balance Transfer",
    content:
      "A balance transfer credit card can save you a lot of money in interest charges.",
  },
];

export function Features() {
  return (
    <>
      <section id="features" className=" pt-20">
        <div className=" text-6xl text-center mb-10">
          <text className="font-pacifico ">Chalk</text> helps you ...
        </div>
        <div className=" relative w-full h-[250px] bg-[#5555ce] group">
          <div className="relative z-10 w-full h h-[250px] items-center text-center text-6xl py-[95px] bg-[#5555ce] group-hover:translate-x-full transform transition-all duration-700">
            Test Your knowledge
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-white text-center p-16 text-2xl">
            Our users can create their own test be it to solve them on their own
            or share them with their students, all while still on the
            plattaform.
            <br />
            Teacher can correct th Open-answer com rubrica - Testes autoavalição
            sem multiplas com jusitificação - Tópicos/Tags - Pesquisas
            explicitas por tag
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#acacff] group">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-6xl py-[95px] bg-[#acacff] group-hover:translate-x-full transform transition-all duration-700">
            Using AI
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-black text-center p-16 text-2xl">
            Questions can be created and correct. <br /> A test can also be
            directly taken with the AI chatbot, Chalky.
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#ffd025] group">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-6xl py-[95px] bg-[#ffd025] group-hover:translate-x-full transform transition-all duration-700">
            With an intuitive interface
          </div>
          <div className=" absolute w-full h-[250px] z-[5] top-0 left-0 text-black text-center  p-16 text-2xl">
            We designed the interface to give the best confort and efficiency
            possible for our users in all the previously mensioned features.
          </div>
        </div>
      </section>
    </>
  );
}
