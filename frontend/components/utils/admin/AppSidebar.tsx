"use client";

import { Home, HomeIcon, LogOut, User, Wrench } from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { ModeToggle } from "../mode-toggle";
import { cn } from "@/lib/utils";
import { usePathname } from "next/navigation";
import { Badge } from "@/components/ui/badge";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import Link from "next/link";

const items = [
  {
    title: "Gerenciamento de Salas",
    url: "/admin/gerenciamento-de-salas",
    icon: Home,
  },
  {
    title: "Gerenciamento de Usuários",
    url: "/admin/gerenciamento-de-usuarios",
    icon: User,
  },
  {
    title: "Gerenciamento de Equipamentos",
    url: "/admin/gerenciamento-de-equipamentos",
    icon: Wrench,
  },
];

function HomeButton() {
  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <Button variant="outline" size="icon" className="h-10 w-10" asChild>
          <Link href="/reservas">
            <HomeIcon />
          </Link>
        </Button>
      </TooltipTrigger>
      <TooltipContent>
        <p>Home</p>
      </TooltipContent>
    </Tooltip>
  );
}

function LogoutButton() {
  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <Button variant="outline" size="icon" className="h-10 w-10 ">
          <LogOut className="stroke-red-400" />
        </Button>
      </TooltipTrigger>
      <TooltipContent>
        <p>Sair</p>
      </TooltipContent>
    </Tooltip>
  );
}

export function AppSidebar() {
  const pathname = usePathname();

  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>Painel Administrador</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => {
                const isActive = pathname === item.url;

                return (
                  <SidebarMenuItem key={item.title}>
                    <SidebarMenuButton asChild>
                      <a
                        href={item.url}
                        aria-current={isActive ? "page" : undefined}
                        className={cn(
                          "flex items-center gap-3 rounded-lg px-3 py-2 text-primary transition-all hover:text-primary",
                          isActive && "bg-muted font-semibold"
                        )}
                      >
                        <item.icon className="h-5 w-5" />
                        <span>{item.title}</span>
                      </a>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                );
              })}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      <SidebarFooter>
        <div className="flex flex-col gap-4 justify-center items-center bg-muted rounded-lg p-4">
          <div className="w-full flex gap-4 items-center">
            <Avatar>
              <AvatarImage src="https://avatars.githubusercontent.com/u/132837450?v=4" />
            </Avatar>

            <div className="flex flex-col gap-1 col-span-2">
              <h1 className="font-medium">Professor</h1>
              <Badge variant="default">Professor</Badge>
            </div>
          </div>

          <div className="w-full flex justify-evenly">
            <LogoutButton />

            <ModeToggle variant="outline" />

            <HomeButton />
          </div>
        </div>
      </SidebarFooter>
    </Sidebar>
  );
}
