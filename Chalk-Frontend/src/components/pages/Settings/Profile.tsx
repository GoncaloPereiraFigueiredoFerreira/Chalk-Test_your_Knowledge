export function Profile() {
  return (
    <>
      <div>
        <div className=" border-t border-gray-100">
          <dl className=" divide-gray-100">
            <div className="px-4 pb-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <img className=" h-48 w-48 rounded-full" src="chico.jpg" alt="" />
              <button
                type="button"
                data-te-ripple-init
                data-te-ripple-color="light"
                className="mb-6 inline-block w-fit h-fit rounded bg-blue-600 px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
              >
                Edit Avatar
              </button>
            </div>
            <div className="px-4 pb-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <dt className="text-md font-medium leading-6 text-gray-900">
                Full name
              </dt>
              <dd className="mt-1 text-md leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                Francisco Faria
              </dd>
            </div>
            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <dt className="text-md font-medium leading-6 text-gray-900">
                Application for
              </dt>
              <dd className="mt-1 text-md leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                Backend Developer
              </dd>
            </div>
            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <dt className="text-md font-medium leading-6 text-gray-900">
                Email address
              </dt>
              <dd className="mt-1 text-md leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                kikodabeira@example.com
              </dd>
            </div>

            <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <dt className="text-md font-medium leading-6 text-gray-900">
                About
              </dt>
              <dd className="mt-1 text-md leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                Student at Uminho, Engenharia Informática
              </dd>
            </div>
            <div className="px-4 pb-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0 ">
              <button
                type="button"
                data-te-ripple-init
                data-te-ripple-color="light"
                className="mb-6 inline-block w-fit h-fit rounded bg-blue-600 px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
              >
                Edit
              </button>
            </div>
          </dl>
        </div>
      </div>
    </>
  );
}
