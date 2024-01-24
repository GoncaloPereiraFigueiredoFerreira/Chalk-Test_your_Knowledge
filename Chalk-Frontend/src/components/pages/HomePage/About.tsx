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
              {/* <h2 className="mb-6 text-3xl font-bold">
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
              </p> */}
              <h2 className="mb-6 text-3xl font-bold">
                Porque é que o{" "}
                <text className="font-pacifico mb-6 text-3xl font-bold">
                  Chalk
                </text>{" "}
                é tão <u className="text-primary dark:text-primary-400">bom</u>{" "}
                no que faz?
              </h2>
              <p className="mb-16 text-neutral-500 dark:text-neutral-900">
                É porque, é importante para nós, dar aos clientes os serviços
                que mais se adequam quando necessários
              </p>
            </div>
          </div>

          <div className="grid gap-y-0 grid-cols-1 lg:justify-around  w-3/4">
            <div
              ref={ref2}
              className={` m-3 py-3 md:mb-0 bg-gray-100 px-4 flex flex-row rounded-lg hover:scale-105 hover:bg-gradient-to-br hover:from-gray-100 hover:to-gray-300
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
                  Suporte 24/7
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  {/* Chalk has people working to ensure that you receive any
                  technical support needed as fast as possible. */}{" "}
                  O Chalk tem sempre pessoas a garantir que você receba qualquer
                  suporte técnico necessário, o mais rápido possível.
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
                  {/*  Safe and solid */} Sólido e seguro
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  {/* You are always Chalk's first priority so we take care of
                  protecting your data. */}{" "}
                  Os utilizadores são primeira prioridade do Chalk, por isso
                  fazemos o melhor para proteger os seus dados e browser
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
                  fill="currentColor"
                  viewBox="0 0 36 36"
                  version="1.1"
                  stroke="currentColor"
                  preserveAspectRatio="xMidYMid meet"
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-14 w-14"
                >
                  <title>cursor-hand-click-line</title>
                  <path
                    d="M30.4,17.6c-1.8-1.9-4.2-3.2-6.7-3.7c-1.1-0.3-2.2-0.5-3.3-0.6c2.8-3.3,2.3-8.3-1-11.1s-8.3-2.3-11.1,1s-2.3,8.3,1,11.1
                    c0.6,0.5,1.2,0.9,1.8,1.1v2.2l-1.6-1.5c-1.4-1.4-3.7-1.4-5.2,0c-1.4,1.4-1.5,3.6-0.1,5l4.6,5.4c0.2,1.4,0.7,2.7,1.4,3.9
                    c0.5,0.9,1.2,1.8,1.9,2.5v1.9c0,0.6,0.4,1,1,1h13.6c0.5,0,1-0.5,1-1v-2.6c1.9-2.3,2.9-5.2,2.9-8.1v-5.8
                    C30.7,17.9,30.6,17.7,30.4,17.6z M8.4,8.2c0-3.3,2.7-5.9,6-5.8c3.3,0,5.9,2.7,5.8,6c0,1.8-0.8,3.4-2.2,4.5V7.9
                    c-0.1-1.8-1.6-3.2-3.4-3.2c-1.8-0.1-3.4,1.4-3.4,3.2v5.2C9.5,12.1,8.5,10.2,8.4,8.2L8.4,8.2z M28.7,24c0.1,2.6-0.8,5.1-2.5,7.1
                    c-0.2,0.2-0.4,0.4-0.4,0.7v2.1H14.2v-1.4c0-0.3-0.2-0.6-0.4-0.8c-0.7-0.6-1.3-1.3-1.8-2.2c-0.6-1-1-2.2-1.2-3.4
                    c0-0.2-0.1-0.4-0.2-0.6l-4.8-5.7c-0.3-0.3-0.5-0.7-0.5-1.2c0-0.4,0.2-0.9,0.5-1.2c0.7-0.6,1.7-0.6,2.4,0l2.9,2.9v3l1.9-1V7.9
                    c0.1-0.7,0.7-1.3,1.5-1.2c0.7,0,1.4,0.5,1.4,1.2v11.5l2,0.4v-4.6c0.1-0.1,0.2-0.1,0.3-0.2c0.7,0,1.4,0.1,2.1,0.2v5.1l1.6,0.3v-5.2
                    l1.2,0.3c0.5,0.1,1,0.3,1.5,0.5v5l1.6,0.3v-4.6c0.9,0.4,1.7,1,2.4,1.7L28.7,24z"
                  ></path>
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-yellow-300 text-left">
                  {/* Smooth and intuitive */} Rápido e intuitivo
                </h5>
                <p className="text-neutral-500 dark:text-neutral-900">
                  {/* Chalk was made with the customer in mind, so we seek to
                  provide the best use experience possible */}{" "}
                  O Chalk foi feito pensando no cliente, por isso procuramos
                  fornecer a melhor experiência de uso possível.
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
                  fill="currentColor"
                  stroke="currentColor"
                  viewBox="0 0 64 64"
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-14 w-14"
                >
                  <path d="M38.478 42.632c-4.644-1.466-3.322-2.633 1.11-4.298c2.123-.799.832-2.484.89-3.832c.026-.617 2.452-.494 2.276-2.874c-.124-1.676-3.816-4.064-4.822-5.083c-.581-.588 1.184-2.197-.059-3.612c-1.697-1.934-1.965-5.299-2.992-7.181c0 0 .783-1.196.183-1.876c-5.176-5.859-24.491-5.321-29.427 3.302c-5.541 9.68-5.615 23.059 5.906 30.267C16.667 50.65 10.104 62 10.104 62h20.319c0-1.938-2.266-8.89 1.7-8.578c3.446.271 7.666.122 7.292-3.77c-.113-1.174-.246-2.231.574-3.204c.82-.972 2.007-2.706-1.511-3.816"></path>
                  <path d="M43.129 40.805L62 43.277v-4.943z"></path>
                  <path d="M58.46 57.081l2.024-4.281l-17.355-9.368z"></path>
                  <path d="M60.484 28.766l-2.024-4.282l-15.331 13.651z"></path>
                </svg>
              </div>
              <div className="flex flex-col">
                <h5 className="mb-2 text-3xl font-bold text-blue-300  text-left">
                  {/* We listen to you */} Nós ouvimos
                </h5>
                <p className="text-neutral-500 dark:text-neutral-300">
                  {/* Chalk pays attention to all user suggestions for further
                  improvement of this product */}{" "}
                  Chalk está sempre atento às sugestões dos utilizadores, e
                  considera-as no futuro desenvolvimento da aplicação
                </p>
              </div>
            </div>
          </div>
        </section>
      </div>
    </>
  );
}
