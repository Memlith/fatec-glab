"use client";

import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { ModeToggle } from "@/components/utils/mode-toggle";
import { DailyScheduleCard } from "@/components/utils/reserva/DailyScheduleCard";
import SectionMapa from "@/components/utils/reserva/SectionMapa";
import { Plus } from "lucide-react";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";

const sampleBookings = [
  {
    id: "1",
    title: "GEST AGIL PROJ SOFT",
    startTime: "07:40",
    endTime: "09:20",
    user: "LILIAN",
    type: "Aula" as const,
    resource: "VSCode, Node",
    color: "#3b82f6",
  },
  {
    id: "2",
    title: "GEST AGIL PROJ SOFT",
    startTime: "09:30",
    endTime: "11:10",
    user: "LILIAN",
    type: "Aula" as const,
    resource: "Makita, Projetor",
    color: "#3b82f6",
  },
  {
    id: "3",
    title: "IHC",
    startTime: "11:20",
    endTime: "13:00",
    user: "LILIAN",
    type: "Aula" as const,
    resource: "AutoCAD",
    color: "#10b981",
  },
  {
    id: "4",
    title: "SIST. INT. GESTÃO",
    startTime: "14:30",
    endTime: "16:10",
    user: "HAMILTON",
    type: "Aula" as const,
    resource: "Projetor, TV",
    color: "#f59e0b",
  },
  {
    id: "5",
    title: "Reunião Equipe X",
    startTime: "16:30",
    endTime: "17:30",
    user: "Rogérin",
    type: "Agendamento" as const,
    resource: "VSCode, Eclipse",
    color: "#ef4444",
  },
];

export default function page() {
  const router = useRouter();
  const [date, setDate] = useState<Date | undefined>(new Date());

  const searchParams = useSearchParams();
  const dayParam = searchParams.get("date");

  function formatDate(d: Date) {
    return d.toISOString().split("T")[0];
  }

  function parseDateString(dateStr: string): Date | null {
    const [year, month, day] = dateStr.split("-").map(Number);
    if (!year || !month || !day) return null;
    return new Date(year, month - 1, day);
  }

  useEffect(() => {
    if (dayParam) {
      const parsedDate = parseDateString(dayParam);
      if (parsedDate) {
        setDate(parsedDate);
      }
    } else {
      const today = new Date();
      handleDaySelection(today);
    }
  }, [dayParam]);

  function handleDaySelection(newDate: Date) {
    if (!newDate) return;

    setDate(newDate);

    const currentParams = new URLSearchParams(
      Array.from(searchParams.entries())
    );
    currentParams.set("date", formatDate(newDate));

    if (!currentParams.get("room")) currentParams.set("room", "lab_01");

    router.push(`?${currentParams.toString()}`, { scroll: false });
  }

  return (
    <div className="h-screen w-full px-8 py-4 max-lg:p-4">
      <div className="h-full grid grid-cols-3 gap-4">
        <div className="h-full col-span-2 max-lg:col-span-3 flex flex-col gap-4">
          <div className="h-fit flex justify-between">
            <div className="flex items-center gap-2">
              <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight text-balance">
                Reservas
              </h1>
            </div>

            <div className="flex items-center gap-2">
              <ModeToggle variant="secondary" />

              <Button size="lg" asChild>
                <Link href={`/nova-reserva`}>
                  <Plus /> Nova Reserva
                </Link>
              </Button>
            </div>
          </div>
          <div className="w-full flex lg:hidden justify-between items-center">
            <Sheet>
              <SheetTrigger asChild>
                <Button variant="secondary">Calendário</Button>
              </SheetTrigger>

              <SheetContent side="left">
                <SheetHeader>
                  <SheetTitle>Selecione um dia</SheetTitle>

                  <Calendar
                    mode="single"
                    selected={date}
                    onSelect={(d) => d && handleDaySelection(d)}
                    className="rounded-lg border mt-4"
                  />
                </SheetHeader>
              </SheetContent>
            </Sheet>

            <Drawer>
              <DrawerTrigger asChild>
                <Button variant="secondary">Mapa</Button>
              </DrawerTrigger>
              <DrawerContent className="h-[90vh]">
                <SectionMapa />
                <DrawerTitle></DrawerTitle>
              </DrawerContent>
            </Drawer>
          </div>

          <div className="h-full col-span-2 bg-card rounded-xl border p-4 max-lg:p-2 shadow-sm flex gap-4">
            <div className="w-fit flex flex-col gap-4 max-lg:hidden">
              <Calendar
                mode="single"
                selected={date}
                onSelect={(d) => d && handleDaySelection(d)}
                className="rounded-lg border"
              />
            </div>

            <div className="w-full h-full rounded-md ">
              <DailyScheduleCard date={new Date()} bookings={sampleBookings} />
            </div>
          </div>
        </div>

        <div className="max-lg:hidden">
          <SectionMapa />
        </div>
      </div>
    </div>
  );
}
