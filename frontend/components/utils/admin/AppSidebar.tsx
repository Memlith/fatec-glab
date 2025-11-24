"use client";

import { GraduationCap, HomeIcon, LogOut } from "lucide-react";
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
  useSidebar,
} from "@/components/ui/sidebar";
import { Button } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import { usePathname } from "next/navigation";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
  TooltipProvider,
} from "@/components/ui/tooltip";
import Link from "next/link";
import { ModeToggleSidebar } from "../mode-toggle-sidebar";

const items = [
  {
    title: "Gerenciamento de Professores",
    url: "/admin/gerenciamento-de-professores",
    icon: GraduationCap,
  },
  //   {
  //     title: "Gerenciamento de Salas",
  //     url: "/admin/gerenciamento-de-salas",
  //     icon: Home,
  //   },
  //   {
  //     title: "Gerenciamento de Equipamentos",
  //     url: "/admin/gerenciamento-de-equipamentos",
  //     icon: Wrench,
  //   },
];

function HomeButton() {
  const { open } = useSidebar();

  return (
    <TooltipProvider>
      <Tooltip>
        <TooltipTrigger asChild>
          <Button
            variant="outline"
            size="icon"
            className="h-10 w-10 bg-transparent"
            asChild
          >
            <Link href="/reservas">
              <HomeIcon />
            </Link>
          </Button>
        </TooltipTrigger>
        <TooltipContent side={open ? "top" : "right"}>
          <p>Home</p>
        </TooltipContent>
      </Tooltip>
    </TooltipProvider>
  );
}

function LogoutButton() {
  const { open } = useSidebar();

  return (
    <TooltipProvider>
      <Tooltip>
        <TooltipTrigger asChild>
          <Button
            variant="outline"
            size="icon"
            className="h-10 w-10  bg-transparent"
          >
            <LogOut className="stroke-red-400" />
          </Button>
        </TooltipTrigger>
        <TooltipContent side={open ? "top" : "right"}>
          <p>Sair</p>
        </TooltipContent>
      </Tooltip>
    </TooltipProvider>
  );
}

export function AppSidebar() {
  const pathname = usePathname();
  const { open } = useSidebar();

  return (
    <Sidebar collapsible="icon">
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
        <div
          className={cn(
            "w-full flex gap-2",
            open ? " flex-row justify-evenly" : "flex-col items-center"
          )}
        >
          <LogoutButton />

          <ModeToggleSidebar variant="outline" />

          <HomeButton />
        </div>
      </SidebarFooter>
    </Sidebar>
  );
}
