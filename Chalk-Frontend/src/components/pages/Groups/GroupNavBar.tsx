import { useState } from "react";

import { Link, Outlet, useNavigate, useParams } from "react-router-dom";

export function GroupNavBar() {
  const { id } = useParams();
  const [selected, setSelected] = useState();
  return (
    <div className="">
      <div className="mb-6">
        <div className="flex justify-center bg-yellow-300 py-7 text-3xl">
          {id}
        </div>
        <nav className="bg-white fixed w-screen flex items-center justify-between drop-shadow overflow-visible">
          <div className="flex  items-center">
            <div className="flex ">
              <Link
                className="text-black hover:bg-gray-700  hover:text-white  px-3 py-7 text-lg font-medium"
                to="alunos"
              >
                <div className="px-5 ">Alunos</div>
              </Link>

              <Link
                className="text-black hover:bg-gray-700 hover:text-white  px-3 py-7 text-lg font-medium"
                to="avaliacoes"
              >
                <div className="px-5">Avaliações</div>
              </Link>

              <Link
                className="text-black hover:bg-gray-700 hover:text-white  px-3 py-7 text-lg font-medium"
                to="testes"
              >
                <div className="px-5 ">Testes</div>
              </Link>
              <Link
                className="text-black hover:bg-gray-700 hover:text-white  px-3 py-7 text-lg font-medium"
                to="testes"
              >
                <div className="px-5 ">Definições</div>
              </Link>
            </div>
          </div>
        </nav>
      </div>

      <Outlet />
    </div>
  );
}
