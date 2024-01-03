import { useEffect, useRef, useState } from "react";
import { useIsVisible } from "./HomePage";

export function About() {
  const ref1 = useRef(null);
  const isVisible1 = useIsVisible(ref1);
  const [triggered1, setTriggered1] = useState(false);

  useEffect(() => {
    if (isVisible1) setTriggered1(true);
  }, [ref1, isVisible1]);

  const ref2 = useRef(null);
  const isVisible2 = useIsVisible(ref2);
  const [triggered2, setTriggered2] = useState(false);

  useEffect(() => {
    if (isVisible2) setTriggered2(true);
  }, [ref2, isVisible2]);

  const ref3 = useRef(null);
  const isVisible3 = useIsVisible(ref3);
  const [triggered3, setTriggered3] = useState(false);

  useEffect(() => {
    if (isVisible3) setTriggered3(true);
  }, [ref3, isVisible3]);

  const ref4 = useRef(null);
  const isVisible4 = useIsVisible(ref4);
  const [triggered4, setTriggered4] = useState(false);

  useEffect(() => {
    if (isVisible4) setTriggered4(true);
  }, [ref4, isVisible4]);

  const ref5 = useRef(null);
  const isVisible5 = useIsVisible(ref5);
  const [triggered5, setTriggered5] = useState(false);

  useEffect(() => {
    if (isVisible5) setTriggered5(true);
  }, [ref5, isVisible5]);

  return (
    <>
      <div
        className="min-w-full container py-40 mx-0 px-0 flex justify-center"
        id="about"
      >
        {/**<!-- Section: Design Block -->*/}
        <section className=" text-left flex flex-row text-xl w-3/4">
          <div
            ref={ref1}
            className={`flex justify-center px-4 w-1/4 transform transition-all ease-in-out duration-1000 ${
              isVisible1 || triggered1
                ? " translate-x-0 opacity-100"
                : " translate-x-full opacity-0"
            }`}
          >
            <div className="max-w-full">
              <h2 className="mb-6 text-3xl font-bold">
                Why is{" "}
                <text className="font-pacifico mb-6 text-3xl font-bold">
                  Chalk
                </text>{" "}
                so <u className="text-primary dark:text-primary-400">great</u>
                <p>at what it does?</p>
              </h2>
              <p className="mb-16 text-neutral-500 dark:text-neutral-900">
                It's because we care about providing the most suitable services
                to our customers needs reliably and at anytime
              </p>
            </div>
          </div>

          <div className="grid gap-y-0 grid-cols-1 lg:justify-around  w-3/4">
            <div
              ref={ref2}
              className={` m-3 py-3 py-3 md:mb-0 bg-gray-100 px-4 flex flex-row rounded-lg hover:scale-105 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300
            transform duration-1000 transition-all animate-slide_in ${
              isVisible2
                ? " translate-x-0 opacity-100"
                : " translate-x-full opacity-0"
            }`}
            >
              <div className=" inline-block rounded-full bg-primary-100 p-4 text-primary text-orange-400 shadow-sm">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="2"
                  stroke="currentColor"
                  className="h-14 w-14"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M11.42 15.17L17.25 21A2.652 2.652 0 0021 17.25l-5.877-5.877M11.42 15.17l2.496-3.03c.317-.384.74-.626 1.208-.766M11.42 15.17l-4.655 5.653a2.548 2.548 0 11-3.586-3.586l6.837-5.63m5.108-.233c.55-.164 1.163-.188 1.743-.14a4.5 4.5 0 004.486-6.336l-3.276 3.277a3.004 3.004 0 01-2.25-2.25l3.276-3.276a4.5 4.5 0 00-6.336 4.486c.091 1.076-.071 2.264-.904 2.95l-.102.085m-1.745 1.437L5.909 7.5H4.5L2.25 3.75l1.5-1.5L7.5 4.5v1.409l4.26 4.26m-1.745 1.437l1.745-1.437m6.615 8.206L15.75 15.75M4.867 19.125h.008v.008h-.008v-.008z"
                  />
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-orange-400  text-left">
                  Support 24/7
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  Chalk has people working to ensure that you receive any
                  technical support needed as fast as possible.
                </p>
              </div>
            </div>

            <div
              ref={ref3}
              className={` m-3 py-3 md:mb-0 bg-gray-100 px-4 flex flex-row rounded-lg
                         transform duration-1000 transition-all animate-slide_in ${
                           isVisible3
                             ? " translate-x-0 opacity-100 hover:scale-105 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300"
                             : " translate-x-full opacity-0"
                         }`}
            >
              <div className=" inline-block rounded-full bg-primary-100 p-4 text-primary text-green-400 shadow-sm">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="2"
                  stroke="currentColor"
                  className="h-14 w-14"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M15.75 5.25a3 3 0 013 3m3 0a6 6 0 01-7.029 5.912c-.563-.097-1.159.026-1.563.43L10.5 17.25H8.25v2.25H6v2.25H2.25v-2.818c0-.597.237-1.17.659-1.591l6.499-6.499c.404-.404.527-1 .43-1.563A6 6 0 1121.75 8.25z"
                  />
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-green-400  text-left">
                  Safe and solid
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  You are always Chalk's first priority so we take care of
                  protecting your data.
                </p>
              </div>
            </div>

            <div
              ref={ref4}
              className={` m-3 py-3 md:mb-0 bg-gray-100 px-4 flex flex-row rounded-lg hover:scale-105 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300
            transform duration-1000 transition-all animate-slide_in ${
              isVisible4
                ? " translate-x-0 opacity-100"
                : " translate-x-full opacity-0"
            }`}
            >
              <div className=" inline-block rounded-full bg-primary-100 text-yellow-300  p-4 text-primary shadow-sm">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="2"
                  stroke="currentColor"
                  className="h-14 w-14"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M8.25 18.75a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h6m-9 0H3.375a1.125 1.125 0 01-1.125-1.125V14.25m17.25 4.5a1.5 1.5 0 01-3 0m3 0a1.5 1.5 0 00-3 0m3 0h1.125c.621 0 1.129-.504 1.09-1.124a17.902 17.902 0 00-3.213-9.193 2.056 2.056 0 00-1.58-.86H14.25M16.5 18.75h-2.25m0-11.177v-.958c0-.568-.422-1.048-.987-1.106a48.554 48.554 0 00-10.026 0 1.106 1.106 0 00-.987 1.106v7.635m12-6.677v6.677m0 4.5v-4.5m0 0h-12"
                  />
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-yellow-300 text-left">
                  Extremely fast
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  Chalk is very optimized wink wink.
                </p>
              </div>
            </div>

            <div
              ref={ref5}
              className={` m-3 py-3 md:mb-0 bg-gray-100 px-4 flex flex-row rounded-lg hover:scale-105 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300
            transform duration-1000 transition-all animate-slide_in ${
              isVisible5
                ? " translate-x-0 opacity-100"
                : " translate-x-full opacity-0"
            }`}
            >
              <div className=" inline-block rounded-full bg-primary-100 p-4 text-primary text-blue-300 shadow-sm">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="2"
                  stroke="currentColor"
                  className="h-14 w-14"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M10.5 6a7.5 7.5 0 107.5 7.5h-7.5V6z"
                  />
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M13.5 10.5H21A7.5 7.5 0 0013.5 3v7.5z"
                  />
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-blue-300  text-left">
                  Live analytics
                </h5>
                <p className="text-neutral-500 dark:text-neutral-300">
                  We provide you updated analytics about your personal use of
                  the plattaform.
                </p>
              </div>
            </div>
          </div>
        </section>
      </div>
    </>
  );
}
