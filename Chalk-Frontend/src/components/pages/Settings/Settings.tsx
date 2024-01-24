import { Profile } from "./Profile";
import { Subscription } from "./Subscription";
import { Dashboard } from "./Dashboard";
import { useState } from "react";

import "./Settings.css";

export function Settings() {
  const [tab, setTab] = useState("profile");

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

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tabID)!.style.display = "block";
  }

  return (
    <div className="h-screen overflow-auto bg-white dark:bg-black">
      <div className=" h-screen sm:mx-10 md:mx-26 lg:mx-36 px-4 pt-7 ">
        <div className="flex h-full">
          <div
            className="border-r border-gray-600 h-5/6 flex flex-col justify-normal grid-cols-1 dark:text-white dark:border-gray-700 text-right space-y-4 text-md font-medium mb-4"
            id="tabs"
          >
            <button
              className={`h-fit inline-block rounded-l-lg border-b-2 border-transparent p-4 bg-[#dddddd] dark:bg-gray-600 hover:border-gray-600 text-lg${
                tab == "profile"
                  ? " bg-black dark:bg-[#dddddd] text-white dark:text-black"
                  : ""
              }`}
              onClick={() => {
                setTab("profile");
                openTab("profile");
              }}
              id="profileButton"
            >
              Perfil
            </button>

            <button
              className={`h-fit inline-block rounded-l-lg border-b-2 border-transparent p-4 bg-[#dddddd] dark:bg-gray-600 hover:border-gray-600 text-lg  ${
                tab == "dashboard"
                  ? " bg-black dark:bg-[#dddddd] text-white dark:text-black"
                  : ""
              }`}
              onClick={() => {
                setTab("dashboard");
                openTab("dashboard");
              }}
              id="dashboardButton"
            >
              Aparência
            </button>

            <button
              className={`h-fit inline-block rounded-l-lg border-b-2 border-transparent p-4 bg-[#dddddd] dark:bg-gray-600 hover:border-gray-600 text-lg  ${
                tab == "subscription"
                  ? " bg-black dark:bg-[#dddddd] text-white dark:text-black"
                  : ""
              }`}
              onClick={() => {
                setTab("subscription");
                openTab("subscription");
              }}
              id="subscriptionButton"
            >
              Planos de subscrição
            </button>
          </div>
          <div className="h-5/6 px-0 lg:px-8 w-10/12">
            <div
              className=" rounded-lg p-4 text-black dark:text-white tabcontent"
              id="profile"
            >
              <Profile></Profile>
            </div>

            <div
              className=" rounded-lg p-4  tabcontent hidden"
              id="dashboard"
              role="tabpanel"
              aria-labelledby="dashboard-tab"
            >
              <Dashboard></Dashboard>
            </div>
            <div
              className=" rounded-lg p-4 text-black dark:text-white tabcontent hidden"
              id="subscription"
              role="tabpanel"
              aria-labelledby="subscription-tab"
            >
              <Subscription></Subscription>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
