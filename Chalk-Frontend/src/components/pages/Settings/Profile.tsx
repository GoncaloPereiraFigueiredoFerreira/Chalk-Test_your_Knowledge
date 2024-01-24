import { Button, Label, Modal, TextInput } from "flowbite-react";
import { useState, useContext } from "react";
import { Course, UserContext, UserRole, UserState, User } from "../../../UserContext.tsx";
import ConfirmButton from "../../interactiveElements/ConfirmButton.tsx";
import { APIContext } from "../../../APIContext.tsx";
import { Login } from "../Login&Register/Login.tsx";

export function Profile() {
  const { contactBACK } = useContext(APIContext);
  const [openModal, setOpenModal] = useState(false);
  const { user , login} = useContext(UserContext);
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
              <dt className="text-md font-medium leading-6 ">Change Avatar</dt>
              <div><button  className="mb-4 px-4 py-2 rounded-lg btn-base-color" onClick={()=>alert("Not implemented")}>Browse...</button><a> Browse for files</a></div>
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
              className="mb-4 px-4 py-2 rounded-lg btn-base-color"
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

                  <div className="flex justify-between text-sm font-medium text-gray-500 dark:text-slate-300">
                  <ConfirmButton
                      onConfirm={() => {
                        contactBACK(
                          "users",
                          "PUT",
                          undefined,
                          {
                            name: name,
                            email: user.user?.email,
                            description: null,
                            photoPath: user.user?.photoPath
                          },
                          "none"
                        );
                        const newUser: User = {
                            id: user.user?.id!,
                            email: user.user?.email!,
                            name: name!,
                            photoPath: user.user?.photoPath!,
                            role: user.user?.role!,
                            courses: user.user?.courses
                        };
                        login(newUser);
                      }}
                      confirmationMessage="Tem acerteza confirmar a edição de perfil?"
                      button={
                        <button className="mb-4 px-4 py-2 rounded-lg btn-base-color">
                          Edit
                        </button>
                      }
                    />
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
