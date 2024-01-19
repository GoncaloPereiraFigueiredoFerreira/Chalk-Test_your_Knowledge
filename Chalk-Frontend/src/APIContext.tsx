import { createContext, useContext, useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { UserContext } from "./UserContext";
import { Alert } from "flowbite-react";
import { HiInformationCircle } from "react-icons/hi";

function ContactAPI(
  address: string,
  method: "POST" | "PUT" | "GET" | "DELETE",
  token: string,
  params?: { [key: string]: string },
  body?: object
) {
  const query: URLSearchParams = new URLSearchParams();

  for (const key in params) {
    if (params[key] !== "") query.append(key, params[key]);
  }

  return fetch(address + "?" + new URLSearchParams(query), {
    method: method,
    mode: "cors",
    credentials: "include",
    headers: {
      "Content-type": "application/json",
      chalkauthtoken: token,
    },

    body: JSON.stringify(body),
  });
}

export type contact = (
  adress: string,
  method: "POST" | "PUT" | "GET" | "DELETE",
  params?: { [key: string]: string },
  body?: object
) => Promise<Response>;

export const APIContext = createContext<{
  contactBACK: contact;
  contactAUTH: contact;
  contactCHALKY: contact;
}>({
  contactBACK: (
    adress: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => ContactAPI(adress, method, "", params, body),
  contactAUTH: (
    adress: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => ContactAPI(adress, method, "", params, body),
  contactCHALKY: (
    adress: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => ContactAPI(adress, method, "", params, body),
});

export function APIProvider({ children }: any) {
  const AUTHSERVER = import.meta.env.VITE_AUTH;
  const BACKSERVER = import.meta.env.VITE_BACKEND;
  const CHALKYSERVER = import.meta.env.VITE_AI_API;
  const [cookies] = useCookies(["chalkauthtoken"]);
  const [alertMsg, setAlertMsg] = useState("");

  const { logout } = useContext(UserContext);

  const ContactAUTH: contact = (
    endpoint: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => {
    return ContactAPI(
      AUTHSERVER + endpoint,
      method,
      cookies.chalkauthtoken,
      params,
      body
    );
  };
  const ContactBACK: contact = (
    endpoint: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => {
    return ContactAPI(
      BACKSERVER + endpoint,
      method,
      cookies.chalkauthtoken,
      params,
      body
    ).then((response) => {
      if (response.status == 401) logout();
      else if (response.status === 404 || response.status === 403) {
        setAlertMsg(response.headers.get("X-Error") ?? "");
        setTimeout(() => {
          setAlertMsg("");
        }, 5000);
      }

      return response;
    });
  };
  const ContactCHALKY: contact = (
    endpoint: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object
  ) => {
    return ContactAPI(
      CHALKYSERVER + endpoint,
      method,
      cookies.chalkauthtoken,
      params,
      body
    );
  };

  return (
    <APIContext.Provider
      value={{
        contactBACK: ContactBACK,
        contactAUTH: ContactAUTH,
        contactCHALKY: ContactCHALKY,
      }}
    >
      {children}
      <div className="absolute bottom-0 left-1/4 z-40">
        <>
          {alertMsg !== "" && (
            <Alert
              color="failure"
              onDismiss={() => {
                setAlertMsg("");
              }}
              icon={HiInformationCircle}
            >
              <span className="font-medium text-xl"></span> {alertMsg}
            </Alert>
          )}
        </>
      </div>
    </APIContext.Provider>
  );
}
