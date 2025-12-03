"use client";

import SectionMapa from "@/components/utils/reserva/mapa/SectionMapa";
import { SidebarTrigger } from "@/components/ui/sidebar";
import {
  Drawer,
  DrawerContent,
  DrawerDescription,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";
import { Button } from "@/components/ui/button";
import ClassroomsForm from "../../admin/classrooms/ClassroomsForm";

export default function GerenciamentoSalas() {
  return (
    <div className="h-screen w-full px-8 py-4 max-lg:p-4">
      <div className="h-full grid grid-cols-3 gap-4">
        <div className="h-full col-span-2 max-lg:col-span-3 flex flex-col gap-4">
          <div className="flex items-center gap-2 ml-[-1rem]">
            <SidebarTrigger className="size-9" />

            <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight text-balance">
              Gerenciamento de Salas
            </h1>

            <div className="lg:hidden">
              <Drawer modal={false}>
                <DrawerTrigger asChild>
                  <Button variant="secondary">Mapa</Button>
                </DrawerTrigger>
                <DrawerContent className="h-[90vh] bg-card">
                  <DrawerHeader>
                    <DrawerTitle />
                    <DrawerDescription />
                  </DrawerHeader>
                  <SectionMapa />
                </DrawerContent>
              </Drawer>
            </div>
          </div>

          <ClassroomsForm />
        </div>

        <div className="max-lg:hidden">
          <SectionMapa />
        </div>
      </div>
    </div>
  );
}
