"use client";

import { useEffect, useState } from "react";
import { Button } from "../ui/button";
import { Avatar, AvatarImage } from "../ui/avatar";
import { Calendar, LogOut, Settings, User2 } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
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
            <Avatar>
              <AvatarImage src="https://avatars.githubusercontent.com/u/132837450?v=4" />
            </Avatar>
            Gabriel Nito
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent>
          <DropdownMenuItem
            onClick={() => {
              redirect("/minhas-reservas");
            }}
          >
            <Calendar />
            Minhas Reservas
          </DropdownMenuItem>
          <DropdownMenuItem
            onClick={() => {
              redirect("/admin/gerenciamento-de-salas");
            }}
          >
            <Settings />
            Painel do Administrador
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem
            className="text-red-400 hover:!text-red-700"
            onClick={() => {}}
          >
            <LogOut className="stroke-red-400" />
            Sair
          </DropdownMenuItem>
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
