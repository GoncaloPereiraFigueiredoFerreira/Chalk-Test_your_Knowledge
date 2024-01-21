import { createContext, useContext, useState } from "react";
import { useCookies } from "react-cookie";
import { UserContext } from "./UserContext";
import { Alert } from "flowbite-react";
import { HiInformationCircle } from "react-icons/hi";

function ContactAPI(
  address: string,
  method: "POST" | "PUT" | "GET" | "DELETE",
  token: string,
  params?: { [key: string]: string },
  body?: object | string
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

    body: typeof body !== "string" ? JSON.stringify(body) : body,
  });
}

export type contact = (
  adress: string,
  method: "POST" | "PUT" | "GET" | "DELETE",
  params?: { [key: string]: string },
  body?: object | string,
  responseType?: "json" | "string" | "none"
) => Promise<any>;

export const APIContext = createContext<{
  contactBACK: contact;
  contactAUTH: contact;
}>({
  contactBACK: (
    adress: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object | string
  ) => ContactAPI(adress, method, "", params, body),
  contactAUTH: (
    adress: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object | string
  ) => ContactAPI(adress, method, "", params, body),
});

export function APIProvider({ children }: any) {
  const AUTHSERVER = import.meta.env.VITE_AUTH;
  const BACKSERVER = import.meta.env.VITE_BACKEND;
  const [cookies] = useCookies(["chalkauthtoken"]);
  const [alertMsg, setAlertMsg] = useState("");

  const { logout } = useContext(UserContext);

  const ContactAUTH: contact = (
    endpoint: string,
    method: "POST" | "PUT" | "GET" | "DELETE",
    params?: { [key: string]: string },
    body?: object | string
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
    body?: object | string,
    responseType?: "string" | "json" | "none"
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
      } else {
        if (responseType === undefined || responseType === "json")
          return response.json();
        else if (responseType === "string") return response.text();
        else return;
      }
    });
  };

  return (
    <APIContext.Provider
      value={{
        contactBACK: ContactBACK,
        contactAUTH: ContactAUTH,
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
