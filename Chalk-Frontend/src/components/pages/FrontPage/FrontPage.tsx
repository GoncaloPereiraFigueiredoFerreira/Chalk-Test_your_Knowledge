import { MainLogo } from "../../MainLogo";
import { useNavigate } from "react-router-dom";

export function FrontPage() {
  const navigate = useNavigate();
  return (
    <div className="flex flex-row divide-x-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
      <div className="flex flex-col w-full h-screen justify-center items-center bg-white dark:bg-black">
        <MainLogo size="big"></MainLogo>
        <div className="flex flex-row space-x-4 mt-4 ">
          <button
            className="group flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black rounded-md"
            onClick={() => navigate("exercise-bank")}
          >
            Banco de exercícios
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black rounded-md"
            onClick={() => navigate("tests")}
          >
            Os meus Testes
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
        </div>
        <div className="flex flex-row space-x-4 mt-4 mb-28">
          <button className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black rounded-md">
            Criar Novo Teste
            <div className="text-xs truncate opacity-50">
              Additional Text Below
            </div>
          </button>
          <button
            className="flex flex-col items-center justify-center w-64 h-16 text-sm font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black rounded-md"
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
