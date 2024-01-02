import { Link } from "react-router-dom";
import "./NavBar.css";
import { useEffect, useRef, useState } from "react";
import { useIsVisible } from "../../pages/HomePage/HomePage";

export function NavBar() {
  const ref1 = useRef(null);
  const isVisible1 = useIsVisible(ref1);
  const [triggered1, setTriggered1] = useState(false);

  useEffect(() => {
    if (isVisible1) setTriggered1(true);
  }, [ref1, isVisible1]);

  return (
    <>
      <nav
        ref={ref1}
        className={` bg-white fixed w-screen z-50 px-2 sm:px-6 lg:px-8 flex h-16 items-center justify-between drop-shadow shadow-inner overflow-visible transform transition-opacity ease-in-out duration-[2s] ${
          isVisible1 || triggered1 ? " opacity-100" : " opacity-0"
        }`}
      >
        <div className="flex flex-1 items-center sm:items-stretch justify-start">
          <div className="flex flex-shrink-0 items-center left-0 hover:scale-125 transition-all duration-50">
            <Link to="/">
              <img
                className="h-12 w-auto"
                src="chalk-logo.svg"
                alt="Your Company"
              />
            </Link>
          </div>
          <div className="hidden sm:ml-6 sm:block">
            <div className="flex space-x-4">
              <a
                href="/webapp"
                className="bg-gray-900 text-white rounded-md px-3 py-2 text-2xl font-medium font-pacifico hover:scale-110 transition-all duration-50"
                aria-current="page"
              >
                Chalk
              </a>
              <a
                href="/#features"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-xl font-medium"
              >
                About
              </a>
              <a
                href="/#team"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-xl font-medium"
              >
                Team
              </a>
              <a
                href="/#contacts"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-xl font-medium"
              >
                Contacts
              </a>
            </div>
          </div>
        </div>
        <div className="absolute hidden sm:flex inset-y-0 right-0 items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
          <button
            type="button"
            className="relative flex text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-xl font-medium"
          >
            <Link to="/login">Login</Link>
          </button>
          <button
            type="button"
            className="relative flex text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-xl font-medium"
          >
            <Link to="/register">Register</Link>
          </button>
        </div>

        {/**<!-- Mobile menu -->*/}
        <div className="dropdown w-fit h-fit p-0 first-letter:absolute inset-y-0 right-0 flex items-center sm:hidden">
          <button
            type="button"
            className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
          >
            <span className="absolute -inset-0.5"></span>
            <span className="sr-only">Open main menu</span>
            <svg
              className="block h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="currentColor"
              aria-hidden="true"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
              />
            </svg>
            <svg
              className="hidden h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="currentColor"
              aria-hidden="true"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
          <div
            className="dropdown-content hidden absolute sm:hidden right-0 z-10 mt-44 mr-4 w-fit h-fit origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
            role="menu"
            id="mobile-menu"
            aria-orientation="vertical"
          >
            <a
              href="#"
              className=" px-4 py-2 text-xl text-gray-700"
              role="menuitem"
              id="user-menu-item-0"
            >
              Your Profile
            </a>
            <a
              href="/settings"
              className=" px-4 py-2 text-xl text-gray-700"
              role="menuitem"
              id="user-menu-item-1"
            >
              Settings
            </a>
            <a
              href="#"
              className=" px-4 py-2 text-xl text-gray-700"
              role="menuitem"
              id="user-menu-item-2"
            >
              Sign out
            </a>
          </div>
        </div>
      </nav>
    </>
  );
}
