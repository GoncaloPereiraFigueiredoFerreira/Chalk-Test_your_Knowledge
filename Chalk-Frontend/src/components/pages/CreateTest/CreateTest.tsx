import { UnderMaintenance } from "../../UnderMaintenence";

export function CreateTest() {
  return (
    <>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 mt-10">
            <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
              <label className=" text-title-1">Criar Teste</label>
            </div>
            <UnderMaintenance />
          </div>
        </div>
      </div>
    </>
  );
}
