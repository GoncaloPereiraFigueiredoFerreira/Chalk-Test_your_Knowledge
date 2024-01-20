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
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
            onClick={() => navigate("exercise-bank")}
          >
            Banco de exercícios
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
            onClick={() => navigate("tests")}
          >
            Os meus Testes
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
        </div>
        <div className="flex flex-row space-x-4 mt-4 mb-28">
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
            onClick={() => navigate("exercise-bank")}
          >
            Criar Novo Teste
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 rounded-md font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
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
