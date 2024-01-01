import { useEffect, useRef, useState } from "react";
import "./HomePage.css";
import { useIsVisible } from "./HomePage";

const reviews: Review[] = [
  {
    id: "feedback-1",
    content: "Very useful, saved me a ton of time",
    name: "Xico",
    title: "Teacher",
    img: "chico.jpg",
  },
  {
    id: "feedback-2",
    content: "I was able to learn a lot.",
    name: "Chico",
    title: "Student",
    img: "chico.jpg",
  },
  {
    id: "feedback-3",
    content: "Muito inovador.",
    name: "Frank",
    title: "Chihuahua",
    img: "chico.jpg",
  },
];

interface Review {
  id: string;
  content: string;
  name: string;
  title: string;
  img: string;
}

export function Reviews() {
  const ref1 = useRef(null);
  const isVisible1 = useIsVisible(ref1);
  const [triggered1, setTriggered1] = useState(false);

  useEffect(() => {
    if (isVisible1) setTriggered1(true);
  }, [ref1, isVisible1]);

  return (
    <>
      <section
        id="clients"
        className={`sm:py-16 py-6 flex justify-center items-center flex-col relative mx-20`}
      >
        <div className="w-full flex justify-center items-center md:flex-row flex-col sm:mb-16 mb-6 relative z-[1]">
          <h2
            className={`font-poppins font-semibold xs:text-5xl text-5xl text-black xs:leading-[76.8px] leading-[66.8px] w-full transform duration-[2s] transition-all${
              isVisible1 || triggered1
                ? " scale-100 translate-x-0"
                : " scale-0 translate-x-full"
            }`}
          >
            What People are saying about us
          </h2>
        </div>

        <div
          ref={ref1}
          className="flex flex-wrap justify-center w-full feedback-container relative z-1"
        >
          {reviews.map((review, index) => (
            <div
              key={index}
              className={`flex justify-between bg-gray-100 flex-col px-10 py-12 rounded-[20px]  max-w-[370px] md:mr-10 sm:mr-5 mr-0 my-5 feedback-card
               transform duration-[1s] transition-all
              last:mr-0 ${
                isVisible1 || triggered1
                  ? " translate-x-0 opacity-100  hover:scale-110 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300"
                  : " -translate-x-full opacity-0"
              }`}
            >
              <img
                src="./quotes.svg"
                alt="double_quotes"
                className="w-[42.6px] h-[27.6px] object-contain"
              />
              <p className="font-poppins  text-xl leading-[32.4px] text-black my-10">
                {review.content}
              </p>

              <div className="flex flex-row">
                <img
                  src={review.img}
                  alt={review.name}
                  className="w-[48px] h-[48px] rounded-full"
                />
                <div className="flex flex-col ml-4">
                  <h4 className="font-poppins font-semibold text-[20px] leading-[32px] text-black">
                    {review.name}
                  </h4>
                  <p className="font-poppins font-normal text-[16px] leading-[24px] text-dimWhite">
                    {review.title}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>
    </>
  );
}
