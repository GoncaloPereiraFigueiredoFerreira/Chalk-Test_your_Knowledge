import { useState } from "react";

import { Link, useNavigate } from "react-router-dom";

export function TestesPartilhadosPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const navigate = useNavigate();
  return (
    <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-2-1">
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Testes Partilhados</label>
        </div>
      </div>
    </div>
  );
}
