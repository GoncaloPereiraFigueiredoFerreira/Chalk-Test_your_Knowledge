import { Profile } from "./Profile";
import { Subscription } from "./Subscription";
import { Account } from "./Account";
import { Dashboard } from "./Dashboard";
import { useState } from "react";

export function Settings() {
  const [openTab, changeTab] = useState("profile");

  return (
    <>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 mt-10">
            <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
              <label className=" text-title-1">Perfil</label>
            </div>
            <div className="flex">
              <div className=" relative mb-4 border-r border-gray-200 dark:border-gray-700">
                <ul
                  className="flex flex-col text-right space-y space-y-4 text-md font-medium text-gray-500 dark:text-gray-400 md:me-4 mb-4 md:mb-0"
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
                      onClick={() => changeTab("profile")}
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
                      onClick={() => changeTab("appearence")}
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
                      onClick={() => changeTab("plans")}
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
                      onClick={() => changeTab("account")}
                    >
                      Account
                    </button>
                  </li>
                </ul>
              </div>
              <div id="tabContent" className="px-8">
                <div
                  className={`${
                    openTab === "profile" ? "" : "hidden"
                  } rounded-lg bg-white p-4 dark:bg-gray-800`}
                  id="profile"
                  role="tabpanel"
                  aria-labelledby="profile-tab"
                >
                  <Profile></Profile>
                </div>

                <div
                  className={`${
                    openTab === "appearence" ? "" : "hidden"
                  } rounded-lg bg-white p-4 dark:bg-gray-800`}
                  id="dashboard"
                  role="tabpanel"
                  aria-labelledby="dashboard-tab"
                >
                  <Dashboard></Dashboard>
                </div>
                <div
                  className={`${
                    openTab === "plans" ? "" : "hidden"
                  } rounded-lg bg-white p-4 dark:bg-gray-800`}
                  id="subscription"
                  role="tabpanel"
                  aria-labelledby="subscription-tab"
                >
                  <Subscription></Subscription>
                </div>
                <div
                  className={`${
                    openTab === "account" ? "" : "hidden"
                  } rounded-lg bg-white p-4 dark:bg-gray-800`}
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
      </div>
    </>
  );
}
