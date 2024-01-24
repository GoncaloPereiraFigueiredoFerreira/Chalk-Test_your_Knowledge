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
      <section id="features" className=" pt-40">
        <div className=" text-6xl text-center mb-10">
          <text className="font-pacifico ">Chalk</text> gives you the ability
          to...
        </div>
        <div className=" relative w-full h-[250px] bg-[#5555ce] text-white group mb-4">
          <div className="relative z-10 w-full h h-[250px] items-center text-center text-5xl py-[95px] bg-[#5555ce] group-hover:translate-x-full transform transition-all duration-700">
            Create exercises and tests quickly.
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-center p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">
                Create uniques exercises and test with advanced options and with
                help from AI.
              </div>
              <div className="p-4 w-1/3">Keep the content organized.</div>
              <div className="p-4 w-1/3">Reuze them as you wish.</div>
            </div>
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#acacff] group mb-4">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-5xl py-[95px] bg-[#acacff] group-hover:translate-x-full transform transition-all duration-700">
            Optimize the evaluation process
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-black text-center p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">
                Our exercises have solution and a rubric to facilitate the
                correction!
              </div>
              <div className="p-4 w-1/3">
                AI can suggest you cotation to your students resolutions.
              </div>
              <div className="p-4 w-1/3">
                Take a test directly with the AI chatbot, Chalky.
              </div>
            </div>
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#ffd025] group mb-4">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-5xl py-[95px] bg-[#ffd025] group-hover:translate-x-full transform transition-all duration-700">
            Become Part of the Our Community
          </div>
          <div className=" absolute w-full h-[250px] z-[5] top-0 left-0 text-black text-center  p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">Explore the available content.</div>
              <div className="p-4 w-1/3">
                Use the shared content of other teachers.
              </div>
              <div className="p-4 w-1/3">
                Create groups of students to better evaluate them or colaborate
                with your colleagues
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}
