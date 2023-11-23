import { Link } from "react-router-dom";
import "./NavBar.css";

export function NavBar() {
  return (
    <>
      <nav className="bg-white fixed w-screen z-10 px-2 sm:px-6 lg:px-8 flex h-16 items-center justify-between drop-shadow overflow-visible">
        <div className="flex flex-1 items-center sm:items-stretch justify-start">
          <div className="flex flex-shrink-0 items-center left-0">
            <Link to="/">
              <img
                className="h-8 w-auto"
                src="chalk-logo.svg"
                alt="Your Company"
              />
            </Link>
          </div>
          <div className="hidden sm:ml-6 sm:block">
            <div className="flex space-x-4">
              <a
                href="#"
                className="bg-gray-900 text-white rounded-md px-3 py-2 text-md font-medium font-pacifico"
                aria-current="page"
              >
                <Link to="/user">Chalk</Link>
              </a>
              <a
                href="#about"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-md font-medium"
              >
                <Link to="/#about">About</Link>
              </a>
              <a
                href="#team"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-md font-medium"
              >
                <Link to="/#team">Team</Link>
              </a>
              <a
                href="#contacts"
                className="text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-md font-medium"
              >
                <Link to="/#contacts">Contacts</Link>
              </a>
            </div>
          </div>
        </div>
        <div className="absolute hidden sm:flex inset-y-0 right-0 items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
          <button
            type="button"
            className="relative flex text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-md font-medium"
          >
            <Link to="/login">Login</Link>
          </button>
          <button
            type="button"
            className="relative flex text-black hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-md font-medium"
          >
            <Link to="/register">Register</Link>
          </button>

          {/**<!-- Notification bell -->*/}
          <button
            type="button"
            className="relative rounded-full bg-white p-1 text-gray-400 hover:text-black focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-200"
          >
            <span className="absolute -inset-1.5"></span>
            <span className="sr-only">View notifications</span>
            <svg
              className="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth="1.5"
              stroke="currentColor"
              aria-hidden="true"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0"
              />
            </svg>
          </button>

          {/**<!-- Profile dropdown -->*/}

          <div className="dropdown w-fit ml-3 float-right p-1 right-0 mt-2 origin-top-right rounded-full bg-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
            <button
              type="button"
              className="rounded-full bg-gray-800"
              id="user-menu-button"
            >
              <span className=" -inset-1.5"></span>
              <span className="sr-only">Open user menu</span>
              <img className="h-8 w-8 rounded-full" src="chico.jpg" alt="" />
            </button>
            <div className=" absolute dropdown-content mt-40 mr-6 right-0 bg-white  rounded-sm shadow-inner grid-cols-1">
              <a
                href="#"
                className=" px-4 py-2 text-md text-gray-700"
                role="menuitem"
                id="user-menu-item-0"
              >
                Your Profile
              </a>
              <a
                href="#"
                className=" px-4 py-2 text-md text-gray-700"
                role="menuitem"
                id="user-menu-item-1"
              >
                <Link to="/settings">Settings</Link>{" "}
              </a>
              <a
                href="#"
                className=" px-4 py-2 text-md text-gray-700"
                role="menuitem"
                id="user-menu-item-2"
              >
                Sign out
              </a>
            </div>
          </div>
        </div>

        {/**<!-- Mobile menu -->*/}
        <div className="dropdown first-letter:absolute inset-y-0 right-0 flex items-center sm:hidden">
          <button
            type="button"
            className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
            aria-controls="mobile-menu"
            aria-expanded="false"
            id="mobile-menu-button"
            data-dropdown-toggle="mobile-menu"
            data-dropdown-trigger="hover"
          >
            <span className="absolute -inset-0.5"></span>
            <span className="sr-only">Open main menu</span>
            {/**<!--
                                Icon when menu is closed.

                                Menu open: "hidden", Menu closed: "block"
                            -->*/}
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
            {/**<!--
                                Icon when menu is open.

                                Menu open: "block", Menu closed: "hidden"
                            -->*/}
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
            className="dropdown hidden absolute sm:hidden right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
            role="menu"
            id="mobile-menu"
            aria-orientation="vertical"
          >
            <div className="dropdown-content">
              <a
                href="#"
                className="bg-gray-900 text-white block rounded-md px-3 py-2 text-base font-medium"
                role="menuitem"
                id="user-menu-item-0"
              >
                Your Profile
              </a>

              <a
                href="#"
                className="bg-gray-900 text-white block rounded-md px-3 py-2 text-base font-medium"
                role="menuitem"
                id="user-menu-item-1"
              >
                <Link to="/settings">Settings</Link>
              </a>

              <a
                href="#"
                className="bg-gray-900 text-white block rounded-md px-3 py-2 text-base font-medium"
                role="menuitem"
                id="user-menu-item-2"
              >
                Sign out
              </a>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
}
