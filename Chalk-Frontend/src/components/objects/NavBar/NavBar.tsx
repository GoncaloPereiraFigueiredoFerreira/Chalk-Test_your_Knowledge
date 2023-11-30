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
              className=" px-4 py-2 text-md text-gray-700"
              role="menuitem"
              id="user-menu-item-0"
            >
              Your Profile
            </a>
            <a
              href="/settings"
              className=" px-4 py-2 text-md text-gray-700"
              role="menuitem"
              id="user-menu-item-1"
            >
              Settings
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
      </nav>
    </>
  );
}
