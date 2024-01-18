import { Button, Label, Modal, TextInput } from "flowbite-react";
import { useState } from "react";

export function Profile() {
  const [openModal, setOpenModal] = useState(false);
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  //const [pp, setPP] = useState("");
  const [about, setAbout] = useState("");

  function onCloseModal() {
    setOpenModal(false);
    setEmail("");
  }

  return (
    <>
      <div>
        <div className=" border-gray-100 grid grid-cols-1 sm:gap-4 ">
          <div className="px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-2">
            <img
              className=" h-48 w-48 rounded-full mb-6 "
              src="chico.jpg"
              alt=""
            />
            <div className="">
              <input type="file" className="mb-4" />
              <button
                data-te-ripple-init
                data-te-ripple-color="light"
                className="mb-6 inline-block w-fit h-fit rounded bg-btn-4-1 dark:text-black px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal"
              >
                Change Avatar
              </button>
            </div>
          </div>
          <div className="px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <dt className="text-md font-medium leading-6 ">Full name</dt>
            <dd className="mt-1 text-md leading-6  sm:col-span-2 sm:mt-0">
              Francisco Faria
            </dd>
          </div>
          <div className="px-0 sm:px-4 py-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <dt className="text-md font-medium leading-6 ">Email address</dt>
            <dd className="mt-1 text-md leading-6 sm:col-span-2 sm:mt-0">
              kikodabeira@example.com
            </dd>
          </div>

          <div className="px-0 sm:px-4 py-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <dt className="text-md font-medium leading-6 ">About</dt>
            <dd className="mt-1 text-md leading-6  sm:col-span-2 sm:mt-0">
              Student at Uminho, Engenharia Informática
            </dd>
          </div>
          <div className="px-0 sm:px-4 pb-6 grid grid-cols-1 md:grid-cols-3 sm:gap-4 ">
            <button
              className="mt-6 inline-block w-fit h-fit rounded bg-btn-4-1 px-6 pt-2.5 pb-2 text-xs font-medium uppercase p-4"
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
                      <Label htmlFor="about" value="Your about" />
                    </div>
                    <TextInput
                      id="about"
                      placeholder="I'm ..."
                      value={about}
                      onChange={(event) => setAbout(event.target.value)}
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

                  <div className="flex justify-between text-sm font-medium text-gray-500 dark:text-gray-300">
                    <button className="bg-btn-4-1 rounded-md p-3">Edit</button>
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
