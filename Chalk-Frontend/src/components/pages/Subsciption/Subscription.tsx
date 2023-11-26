import { Link } from "react-router-dom";
import { Footer } from "../../objects/Footer/Footer";
import { NavBar } from "../../objects/NavBar/NavBar";
import "./Subscription.css";

export function Subscription() {
  return (
    <>
      <NavBar></NavBar>

      <div
        x-data="{ 
        billingType: 'month', 
        basicPrice: 'Free',
        premiumPrice: '29',
        proPrice: '39'
    }"
        className="min-h-full h-screen py-12 chalkBackground dark:bg-black"
      >
        <div className="w-full pt-16 pb-24 text-center">
          <h4 className=" text-4xl text-white font-pacifico">
            Choose the right plan for you
          </h4>
          <p className=" text-lg text-white mt-2">
            Pricing built to accomodate all individuals and institutions. Choose
            package that suits your needs.
          </p>
        </div>
        <div className="w-full 2xl:w-3/4 flex items-center justify-center px-8 md:px-32 lg:px-16 2xl:px-0 mx-auto -mt-8 ">
          <div className="w-full grid grid-cols-1 xl:grid-cols-3 gap-8">
            <div className="bg-white shadow-2xl rounded-lg py-4">
              <p className="text-xl text-center font-bold text-yellow-400">
                Basic
              </p>
              <p className="text-center py-8">
                <span className="text-4xl font-bold text-gray-700">
                  <span x-text="basicPrice">Free</span>
                </span>
              </p>
              <ul className="border-t border-gray-300 py-8 space-y-6">
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-yellow-400 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">
                    Knowledge Evalutation wiht our public tests and exercises
                  </span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Class/Group Management</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Reports</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">Unlimited Users</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">Data Export</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">Automated Workflows</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">API Access</span>
                </li>
              </ul>
              <div className="flex items-center justify-center mt-6">
                <a
                  href="#"
                  className="bg-yellow-400 hover:bg-yellow-700 px-8 py-2 text-sm text-gray-200 uppercase rounded font-bold transition duration-150"
                  title="Purchase"
                >
                  <Link to="/register">Create Account</Link>
                </a>
              </div>
            </div>
            <div className="bg-white shadow-2xl rounded-lg py-4">
              <p className="text-xl text-center font-bold text-blue-600">
                Premium
              </p>
              <p className="text-center py-8">
                <span className="text-4xl font-bold text-gray-700">
                  $<span x-text="premiumPrice">29</span>
                </span>
                <span className="text-xs uppercase text-gray-500">
                  / <span x-text="billingType">month</span>
                </span>
              </p>
              <ul className="border-t border-gray-300 py-8 space-y-6">
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">
                    Knowledge Evalutation wiht our public tests and exercises
                  </span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Class/Group Management</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Reports</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Unlimited Users</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Data Export</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">Automated Workflows</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-gray-300 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-400 ">API Access</span>
                </li>
              </ul>
              <div className="flex items-center justify-center mt-6">
                <a
                  href="#"
                  className="bg-blue-600 hover:bg-blue-700 px-8 py-2 text-sm text-gray-200 uppercase rounded font-bold transition duration-150"
                  title="Purchase"
                >
                  <Link to="/register">Purchase</Link>
                </a>
              </div>
            </div>
            <div className="bg-white shadow-2xl rounded-lg py-4">
              <p className="text-xl text-center font-bold text-blue-600">Pro</p>
              <p className="text-center py-8">
                <span className="text-4xl font-bold text-gray-700">
                  $<span x-text="proPrice">39</span>
                </span>
                <span className="text-xs uppercase text-gray-500">
                  / <span x-text="billingType">month</span>
                </span>
              </p>
              <ul className="border-t border-gray-300 py-8 space-y-6">
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">
                    Knowledge Evalutation wiht our public tests and exercises
                  </span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Class/Group Management</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Reports</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Unlimited Users</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Data Export</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">Automated Workflows</span>
                </li>
                <li className="flex items-center space-x-2 px-8">
                  <span className="bg-blue-600 rounded-full p-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-3 w-3 text-white"
                      viewBox="0 0 20 20"
                      fill="currentColor"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </span>
                  <span className="text-gray-600 ">API Access</span>
                </li>
              </ul>
              <div className="flex items-center justify-center mt-6">
                <a
                  href="#"
                  className="bg-blue-600 hover:bg-blue-700 px-8 py-2 text-sm text-gray-200 uppercase rounded font-bold transition duration-150"
                  title="Purchase"
                >
                  <Link to="/register">Purchase</Link>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer></Footer>
    </>
  );
}
