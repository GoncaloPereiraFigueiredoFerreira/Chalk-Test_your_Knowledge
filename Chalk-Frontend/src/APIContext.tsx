import { createContext, useContext, useEffect } from "react";
import { useCookies } from "react-cookie";
import { UserContext } from "./UserContext";

function ContactAPI(
  address: string,
  method: "POST" | "PUT" | "GET" | "DELETE",
  token: string,
  params?: { [key: string]: string },
  body?: object
) {
  let query: URLSearchParams = new URLSearchParams();

  for (let key in params) {
    query.append(key, params[key]);
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
  const [cookies, setCookie] = useCookies(["chalkauthtoken"]);
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
    </APIContext.Provider>
  );
}
