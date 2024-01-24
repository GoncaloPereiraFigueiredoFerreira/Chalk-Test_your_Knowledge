import { Button, Label, Modal, TextInput } from "flowbite-react";
import { useState, useContext } from "react";
import { Course, UserContext, UserRole } from "../../../UserContext.tsx";

export function Profile() {
  const [openModal, setOpenModal] = useState(false);
  const { user } = useContext(UserContext);
  const [email, setEmail] = useState(user.user?.email);
  const [name, setName] = useState(user.user?.name);
  const [imagePath, setImagePath] = useState(user.user?.photoPath);
  //const [pp, setPP] = useState("");

  function onCloseModal() {
    setOpenModal(false);
  }

  return (
    <>
      <div className="">
        <div className=" grid grid-cols-1 sm:gap-4 ">
          <div className=" px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-2">
            <img
              className=" h-48 w-48 rounded-full mb-6 "
              src={imagePath}
              alt=""
            />
            <div className="">
              <div>
                <button
                  className="mb-4 btn-base-color"
                  onClick={() => alert("Not implemented")}
                />
                <a>Browse for files</a>
              </div>
              <button
                data-te-ripple-init
                data-te-ripple-color="light"
                className="mb-6 inline-block w-fit h-fit rounded bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] dark:text-black px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal"
              >
                Change Avatar
              </button>
            </div>
          </div>
          <div className="px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <dt className="text-md font-medium leading-6 ">Full name</dt>
            <dd className="mt-1 text-md leading-6  sm:col-span-2 sm:mt-0">
              {name}
            </dd>
          </div>
          <div className="px-0 sm:px-4 py-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <dt className="text-md font-medium leading-6 ">Email address</dt>
            <dd className="mt-1 text-md leading-6 sm:col-span-2 sm:mt-0">
              {email}
            </dd>
          </div>

          <div className="px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <button
              className="mt-6 inline-block w-fit h-fit rounded bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] px-6 pt-2.5 pb-2 text-xs font-medium uppercase p-4"
              onClick={() => setOpenModal(true)}
            >
              Edit Profile
            </button>
            <Modal
              dismissible
              show={openModal}
              size="md"
              onClose={onCloseModal}
              popup
            >
              <Modal.Header />
              <Modal.Body>
                <div className="space-y-6">
                  <h3 className="text-xl font-medium  dark:text-black">
                    Edit your profile
                  </h3>
                  <div>
                    <div className="mb-2 block">
                      <Label htmlFor="name" value="Your Name" />
                    </div>
                    <TextInput
                      id="name"
                      placeholder="name"
                      value={name}
                      onChange={(event) => setName(event.target.value)}
                    />
                  </div>
                  <div>
                    <div className="mb-2 block">
                      <Label htmlFor="email" value="Your email" />
                    </div>
                    <TextInput
                      id="email"
                      placeholder="name@company.com"
                      value={email}
                      onChange={(event) => setEmail(event.target.value)}
                    />
                  </div>

                  <div className="flex justify-between text-sm font-medium text-gray-500 dark:text-slate-300">
                    <button className="bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] rounded-md p-3">
                      Edit
                    </button>
                  </div>
                </div>
              </Modal.Body>
            </Modal>
          </div>
        </div>
      </div>
    </>
  );
}
