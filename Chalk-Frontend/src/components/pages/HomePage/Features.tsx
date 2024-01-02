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
            Test Your Knowledge
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-white text-center p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">
                Create your own tests from exercise from the question bank.
              </div>
              <div className="p-4 w-1/3">
                Scheduled their launchs and correct the resolutions after.
              </div>
              <div className="p-4 w-1/3">
                You can also create the exercises and can even give it a
                solution and a rubric to facilitate the correction!
              </div>
            </div>
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#acacff] group">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-6xl py-[95px] bg-[#acacff] group-hover:translate-x-full transform transition-all duration-700">
            Using AI
          </div>
          <div className=" absolute w-full z-[5] h-[250px] top-0 left-0 text-black text-center p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">
                Questions can be generated based on validated questions on our
                question bank.
              </div>
              <div className="p-4 w-1/3">
                It can suggest the cotation to exercise resolutions.{" "}
              </div>
              <div className="p-4 w-1/3">
                Take a test directly with the AI chatbot, Chalky.
              </div>
            </div>
          </div>
        </div>
        <div className=" relative w-full h-[250px] bg-[#ffd025] group">
          <div className="relative z-10 w-full h-[250px] items-center text-center text-6xl py-[95px] bg-[#ffd025] group-hover:translate-x-full transform transition-all duration-700">
            With an Intuitive Interface
          </div>
          <div className=" absolute w-full h-[250px] z-[5] top-0 left-0 text-black text-center  p-16 text-3xl">
            <div className="flex flex-row">
              <div className="p-4 w-1/3">Have the most confort.</div>
              <div className="p-4 w-1/3">Do your tasks eficciently.</div>
              <div className="p-4 w-1/3">
                Organize your content as you wish.
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}
