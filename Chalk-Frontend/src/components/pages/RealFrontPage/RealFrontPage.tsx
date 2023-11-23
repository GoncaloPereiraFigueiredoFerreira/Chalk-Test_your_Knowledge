import { useState } from "react";

import { MainLogo } from "../../MainLogo";
import { MessageBoxIcon } from "../../objects/SVGImages/SVGImages";
import { Link, useNavigate } from "react-router-dom";

export function RealFrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const navigate = useNavigate();
  return (
    <div className="flex flex-row divide-x-2 border-gray-2-2">
      <div className="flex flex-col w-full h-screen justify-center items-center bg-2-1">
        <MainLogo size="big"></MainLogo>
        <div className="flex flex-row space-x-4 mt-4 ">
          <button
            type="button"
            className="group flex flex-col items-center justify-center w-64 h-16 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
            onClick={() => navigate("exercise-bank")}
          >
            Banco de exercícios
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            type="button"
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
            onClick={() => navigate("test")}
          >
            Os meus Testes
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
        </div>
        <div className="flex flex-row space-x-4 mt-4 mb-28">
          <button
            type="button"
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
          >
            Criar Novo Teste
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            type="button"
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
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
