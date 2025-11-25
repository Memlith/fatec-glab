"use client";

import { useEffect, useState } from "react";
import { Button } from "../ui/button";
import { Settings, User, User2 } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";
import { redirect } from "next/navigation";

export default function UserButton() {
  const [logged, setLogged] = useState<boolean>(false);

  useEffect(() => {
    setLogged(true);
  }, []);

  if (logged) {
    return (
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button size="lg" variant="secondary">
            <User />
            Administrador
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent>
          <DropdownMenuItem
            onClick={() => {
              redirect("/admin/gerenciamento-de-professores");
            }}
          >
            <Settings />
            Painel do Administrador
          </DropdownMenuItem>
          {/* <DropdownMenuSeparator />
          <DropdownMenuItem
            className="text-red-400 hover:!text-red-700"
            onClick={() => {}}
          >
            <LogOut className="stroke-red-400" />
            Sair
          </DropdownMenuItem> */}
        </DropdownMenuContent>
      </DropdownMenu>
    );
  }
  return (
    <Button size="lg" variant="outline">
      <User2 />
      Fazer Login
    </Button>
  );
}
