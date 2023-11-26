import { Link } from "react-router-dom";

export function Front() {
  return (
    <>
      {" "}
      {/**<!-- FRONT section -->*/}
      <div className="max-w-full h-screen container pt-6 md:px-6 first-section overflow-hidden">
        <div className="px-6 md:px-12 text-center lg:text-left">
          <div className="grid items-center lg:grid-cols-2 lg:gap-x-12">
            <div className="mb-12 lg:mb-0">
              <h2 className="my-12 text-5xl font-bold leading-tight tracking-tight">
                Are you ready <br />
                <span className="text-success dark:text-success-400">
                  to begin testing your knowledge?
                </span>
              </h2>
              <a
                className="mb-2 inline-block rounded bg-black px-12 pt-4 pb-3.5 text-sm font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#14a44d] transition duration-150 ease-in-out hover:bg-success-600 hover:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.3),0_4px_18px_0_rgba(20,164,77,0.2)] focus:bg-success-600 focus:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.3),0_4px_18px_0_rgba(20,164,77,0.2)] focus:outline-none focus:ring-0 active:bg-success-700 active:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.3),0_4px_18px_0_rgba(20,164,77,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(20,164,77,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.2),0_4px_18px_0_rgba(20,164,77,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.2),0_4px_18px_0_rgba(20,164,77,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(20,164,77,0.2),0_4px_18px_0_rgba(20,164,77,0.1)] md:mr-2 md:mb-0"
                data-te-ripple-init
                data-te-ripple-color="light"
                href="#!"
                role="button"
              >
                <Link to="/pricing">Get started</Link>
              </a>
              <a
                className="inline-block rounded px-12 pt-4 pb-3.5 text-sm font-medium uppercase leading-normal text-success transition duration-150 ease-in-out hover:bg-neutral-500 focus:outline-none focus:ring-0 active:text-gray-50 dark:hover:bg-white dark:hover:bg-opacity-40"
                data-te-ripple-init
                data-te-ripple-color="light"
                href="#!"
                role="button"
              >
                Learn more
              </a>
            </div>
            <div className="flex justify-center">
              <img
                src="teacher.png"
                className=" h-full object-contain"
                alt=""
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
