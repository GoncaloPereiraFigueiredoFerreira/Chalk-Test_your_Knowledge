import { Footer } from "../../objects/Footer/Footer";
import { NavBar } from "../../objects/NavBar/NavBar";

import { Tabs } from "flowbite";
import type { TabsOptions, TabsInterface, TabItem } from "flowbite";
import type { InstanceOptions } from "flowbite";

import { Profile } from "./Profile";
import { Subscription } from "./Subscription";
import { Account } from "./Account";
import { Dashboard } from "./Dashboard";

export function Settings() {
  //TABS

  const tabsElement = document.getElementById("tabs");

  // create an array of objects with the id, trigger element (eg. button), and the content element
  const tabElements: TabItem[] = [
    {
      id: "profile",
      triggerEl: document.querySelector<HTMLElement>("#profile-tab")
        ? document.querySelector<HTMLElement>("#profile-tab")
        : undefined,
      targetEl: document.querySelector("#profile"),
    },
    {
      id: "dashboard",
      triggerEl: document.querySelector<HTMLElement>("#dashboard-tab"),
      targetEl: document.querySelector("#dashboard"),
    },
    {
      id: "subscription",
      triggerEl: document.querySelector<HTMLElement>("#subscription-tab"),
      targetEl: document.querySelector("#subscription"),
    },
    {
      id: "account",
      triggerEl: document.querySelector<HTMLElement>("#account-tab"),
      targetEl: document.querySelector("#account"),
    },
  ];

  // options with default values
  const options = {
    defaultTabId: "profile",
    activeClasses:
      "text-blue-600 hover:text-blue-600 dark:text-blue-500 dark:hover:text-blue-400 border-blue-600 dark:border-blue-500",
    inactiveClasses:
      "text-gray-500 hover:text-gray-600 dark:text-gray-400 border-gray-100 hover:border-gray-300 dark:border-gray-700 dark:hover:text-gray-300",
    onShow: () => {
      console.log("tab is shown");
    },
  };

  // instance options with default values
  const instanceOptions = {
    id: "tabs",
    override: true,
  };

  /*
   * tabElements: array of tab objects
   * options: optional
   * instanceOptions: optional
   */ const tabs = new Tabs(tabsElement, tabElements, options, instanceOptions);

  return (
    <>
      <NavBar></NavBar>;
      <div className=" min-h-full h-screen mt-0 mb-20 sm:mx-36 mx-4 pt-20">
        <div className=" md:max-w-xl lg:max-w-3xl">
          <h2 className="mb-12 text-3xl font-bold">Settings</h2>
        </div>
        <div className="flex">
          <div className=" relative mb-4 border-r border-gray-200 dark:border-gray-700">
            <ul
              className="flex-column text-right space-y space-y-4 text-md font-medium text-gray-500 dark:text-gray-400 md:me-4 mb-4 md:mb-0"
              id="tabs"
              role="tablist"
            >
              <li className="me-2" role="presentation">
                <button
                  className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
                  id="profile-tab"
                  type="button"
                  role="tab"
                  aria-controls="profile"
                  aria-selected="false"
                >
                  Profile
                </button>
              </li>
              <li className="me-2" role="presentation">
                <button
                  className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
                  id="dashboard-tab"
                  type="button"
                  role="tab"
                  aria-controls="dashboard"
                  aria-selected="false"
                >
                  Appearance
                </button>
              </li>
              <li className="me-2" role="presentation">
                <button
                  className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
                  id="subscription-tab"
                  type="button"
                  role="tab"
                  aria-controls="subscription"
                  aria-selected="false"
                >
                  Billing and plans
                </button>
              </li>
              <li role="presentation">
                <button
                  className="inline-block rounded-t-lg border-b-2 border-transparent p-4 hover:border-gray-300 hover:text-gray-600 dark:hover:text-gray-300"
                  id="account-tab"
                  type="button"
                  role="tab"
                  aria-controls="account"
                  aria-selected="false"
                >
                  Account
                </button>
              </li>
            </ul>
          </div>
          <div id="tabContent" className="px-8">
            <div
              className="hidden rounded-lg bg-white p-4 dark:bg-gray-800"
              id="profile"
              role="tabpanel"
              aria-labelledby="profile-tab"
            >
              <Profile></Profile>
            </div>

            <div
              className="hidden rounded-lg bg-white p-4 dark:bg-gray-800"
              id="dashboard"
              role="tabpanel"
              aria-labelledby="dashboard-tab"
            >
              <Dashboard></Dashboard>
            </div>
            <div
              className="hidden rounded-lg bg-white p-4 dark:bg-gray-800"
              id="subscription"
              role="tabpanel"
              aria-labelledby="subscription-tab"
            >
              <Subscription></Subscription>
            </div>
            <div
              className="hidden rounded-lg bg-white p-4 dark:bg-gray-800"
              id="account"
              role="tabpanel"
              aria-labelledby="account-tab"
            >
              <Account></Account>
            </div>
          </div>
        </div>
      </div>
      <Footer></Footer>
    </>
  );
}
