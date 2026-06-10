"use client";

import { useAuth } from "@/context/AuthContext";
import { ShowForRole } from "./ShowForRole";
import { Button } from "../ui/button";
import { Settings, User, User2, LogOut } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";
import { useRouter } from "next/navigation";
import { auth } from "@/lib/firebase";
import { signOut } from "firebase/auth";

export default function UserButton() {
  const { user, userProfile } = useAuth();
  const router = useRouter();

  const handleLogout = async () => {
    await signOut(auth!);
    router.push("/auth");
  };

  if (user) {
    return (
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button size="lg" variant="secondary">
            <User />
            {userProfile?.name || "Usuário"}
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent>
          <ShowForRole role="ADMIN">
            <DropdownMenuItem
              onClick={() => {
                router.push("/admin/gerenciamento-de-professores");
              }}
            >
              <Settings />
              Painel do Administrador
            </DropdownMenuItem>
          </ShowForRole>
          <DropdownMenuItem onClick={handleLogout} className="text-red-500">
            <LogOut className="mr-2 h-4 w-4" />
            Sair
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    );
  }
  return (
    <Button size="lg" variant="outline" onClick={() => router.push("/auth")}>
      <User2 />
      Fazer Login
    </Button>
  );
}
