import { MainLogo } from "../../MainLogo";
import { useNavigate } from "react-router-dom";

export function FrontPage() {
  const navigate = useNavigate();
  return (
    <div className="flex flex-row divide-x-2 border-[#bbbbbb] dark:border-[#bbbbbb] divide-[#dddddd] dark:divide-[#dddddd]">
      <div className="flex flex-col w-full h-screen justify-center items-center bg-white dark:bg-slate-900">
        <MainLogo size="big"></MainLogo>
        <div className="flex flex-row space-x-4 mt-4 ">
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium btn-base-color active:scale-95"
            onClick={() => navigate("exercise-bank")}
          >
            Banco de exercícios
            <div className="text-xs truncate opacity-50">
              Aceder ao banco de exercícios
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium btn-base-color active:scale-95"
            onClick={() => navigate("tests")}
          >
            Os meus Testes
            <div className="text-xs truncate opacity-50">
              Aceder ao próprio conjunto de testes
            </div>
          </button>
        </div>
        <div className="flex flex-row space-x-4 mt-4 mb-28">
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium btn-base-color active:scale-95"
            onClick={() => navigate("create-test")}
          >
            Criar Novo Teste
            <div className="text-xs truncate opacity-50">
              Criar um novo teste
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium btn-base-color active:scale-95"
            onClick={() => navigate("groups")}
          >
            Administrar Groups
            <div className="text-xs truncate opacity-50">
              Visite os seus grupos
            </div>
          </button>
        </div>
      </div>
    </div>
  );
}
