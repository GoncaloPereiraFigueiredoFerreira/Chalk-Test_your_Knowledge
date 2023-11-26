import { Button, Checkbox, Label, Modal, TextInput } from "flowbite-react";
import { useState } from "react";

export function Profile() {
  const [openModal, setOpenModal] = useState(false);
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [pp, setPP] = useState("");
  const [about, setAbout] = useState("");

  function onCloseModal() {
    setOpenModal(false);
    setEmail("");
  }

  return (
    <>
      <div>
        <div className=" border-gray-100">
          <dl className=" divide-gray-100">
            <div className="px-4 pb-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
              <img className=" h-48 w-48 rounded-full" src="chico.jpg" alt="" />
              <input type="file" />
              <button
                type="button"
                data-te-ripple-init
                data-te-ripple-color="light"
                className="mb-6 inline-block w-fit h-fit rounded bg-blue-600 px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
              >
                Change Avatar
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
              <Button
                className="mt-6 inline-block w-fit h-fit rounded bg-blue-600 px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
                onClick={() => setOpenModal(true)}
              >
                Edit Profile
              </Button>
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
                    <h3 className="text-xl font-medium text-gray-900 dark:text-white">
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
                      <Button>Edit</Button>
                    </div>
                  </div>
                </Modal.Body>
              </Modal>
            </div>
          </dl>
        </div>
      </div>
    </>
  );
}
