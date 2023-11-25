import { Profile } from "./Profile";
import { Subscription } from "./Subscription";
import { Account } from "./Account";
import { Dashboard } from "./Dashboard";
//import { useState } from "react";

import "./Settings.css";

export function Settings() {
  function openTab(tabID: string) {
    // Declare all variables
    //var i, tabcontent;

    // Get all elements with class="tabcontent" and hide them
    /*
    tabcontent = document.querySelectorAll<HTMLElement>("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
      console.log(tabcontent[i].style.display);
    } 
    */
    document.getElementById("profile")!.style.display = "none";
    document.getElementById("subscription")!.style.display = "none";
    document.getElementById("dashboard")!.style.display = "none";
    document.getElementById("account")!.style.display = "none";

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabID)!.style.display = "block";
  }

  return (
    <div className="h-screen overflow-auto">
      <div className=" min-h-full mt-0 mb-20 sm:mx-10 md:mx-26 lg:mx-36  mx-4 pt-20">
        <div className=" md:max-w-xl lg:max-w-3xl flex flex-row justify-between">
          <h2 className="mb-12 text-3xl font-bold">Settings</h2>
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
                className=" px-4 py-2 text-md text-gray-700"
                onClick={() => openTab("profile")}
                id="profileButton"
              >
                Profile
              </a>
              <a
                className=" px-4 py-2 text-md text-gray-700"
                onClick={() => openTab("dashboard")}
                id="dashboardButton"
              >
                Appearance
              </a>
              <a
                className=" px-4 py-2 text-md text-gray-700"
                onClick={() => openTab("subscription")}
                id="subscriptionButton"
              >
                Billing and plans
              </a>
              <a
                className=" px-4 py-2 text-md text-gray-700"
                onClick={() => openTab("account")}
                id="accountButton"
              >
                Account
              </a>
            </div>
          </div>
        </div>
        <div className="flex">
          <div
            className=" hidden sm:grid h-fit grid-cols-1 border-r border-gray-200 dark:border-gray-700 text-right space-y-4 text-md font-medium text-gray-500 dark:text-gray-400 md:me-4 mb-4 md:mb-0"
            id="tabs"
          >
            <button
              className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
              onClick={() => openTab("profile")}
              id="profileButton"
            >
              Profile
            </button>

            <button
              className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
              onClick={() => openTab("dashboard")}
              id="dashboardButton"
            >
              Appearance
            </button>

            <button
              className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
              onClick={() => openTab("subscription")}
              id="subscriptionButton"
            >
              Billing and plans
            </button>
            <button
              className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
              onClick={() => openTab("account")}
              id="accountButton"
            >
              Account
            </button>
          </div>
          <div className="px-0 lg:px-8 w-10/12">
            <div
              className=" rounded-lg bg-white p-4 dark:bg-gray-800 tabcontent"
              id="profile"
            >
              <Profile></Profile>
            </div>

            <div
              className=" rounded-lg bg-white p-4 dark:bg-gray-800 tabcontent hidden"
              id="dashboard"
              role="tabpanel"
              aria-labelledby="dashboard-tab"
            >
              <Dashboard></Dashboard>
            </div>
            <div
              className=" rounded-lg bg-white p-4 dark:bg-gray-800 tabcontent hidden"
              id="subscription"
              role="tabpanel"
              aria-labelledby="subscription-tab"
            >
              <Subscription></Subscription>
            </div>
            <div
              className=" rounded-lg bg-white p-4 dark:bg-gray-800 tabcontent hidden"
              id="account"
              role="tabpanel"
              aria-labelledby="account-tab"
            >
              <Account></Account>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
