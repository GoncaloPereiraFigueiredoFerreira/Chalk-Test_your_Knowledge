import { useContext, useEffect, useState } from "react";
import { APIContext } from "../../../../APIContext";
import { redirect, useNavigate } from "react-router-dom";
import { CreateTest, InitTest } from "../../../objects/Test/Test";
import { UserContext } from "../../../../UserContext";

export function CreateTestPage() {
  const { contactBACK } = useContext(APIContext);
  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  useEffect(() => {
    contactBACK(
      "tests",
      "POST",
      undefined,
      CreateTest(user.user?.id ?? "")
    ).then((response) => {
      response.text().then((id) => {
        {
          navigate("/webapp/tests/" + id + "/edit");
        }
      });
    });
  }, []);

  return <></>;
}
