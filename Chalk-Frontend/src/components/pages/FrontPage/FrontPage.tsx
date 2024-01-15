import { useState } from "react";

import { MainLogo } from "../../MainLogo";
import { MessageBoxIcon } from "../../objects/SVGImages/SVGImages";
import { Link, useNavigate } from "react-router-dom";

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const navigate = useNavigate();
  return (
    <div className="flex flex-row divide-x-2 border-gray-2-2">
      <div className="flex flex-col w-full h-screen justify-center items-center bg-2-1">
        <MainLogo size="big"></MainLogo>
        <div className="flex flex-row space-x-4 mt-4 ">
          <button
            className="group flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-btn-4-2 rounded-md"
            onClick={() => navigate("exercise-bank")}
          >
            Banco de exercícios
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-btn-4-2 rounded-md"
            onClick={() => navigate("tests")}
          >
            Os meus Testes
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
        </div>
        <div className="flex flex-row space-x-4 mt-4 mb-28">
          <button className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-btn-4-2 rounded-md">
            Criar Novo Teste
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-btn-4-2 rounded-md"
            onClick={() => navigate("groups")}
          >
            Manage Groups
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
        </div>
      </div>
    </div>
  );
}
