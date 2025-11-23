"use client";

import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import AlertMobile from "@/components/utils/AlertMobile";
import { ModeToggle } from "@/components/utils/mode-toggle";
import NovaReservaForm from "@/components/utils/reserva/NovaReservaForm";
import SectionMapa from "@/components/utils/reserva/mapa/SectionMapa";
import UserButton from "@/components/utils/UserButton";
import { ChevronLeft } from "lucide-react";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { useCallback, useEffect, useState } from "react";

const softwares = ["VSCode", "Eclipse", "Java", "Node", "AutoCAD", "Arduino"];
const equipamentos = ["Laptops", "Projetor", "TV", "Makita"];

function formatDate(d: Date) {
  return d.toISOString().split("T")[0];
}

export default function NovaReserva() {
  const router = useRouter();
  const [date, setDate] = useState<Date | undefined>(new Date());
  const searchParams = useSearchParams();
  const dayParam = searchParams.get("date");

  function parseDateString(dateStr: string): Date | null {
    const [year, month, day] = dateStr.split("-").map(Number);
    if (!year || !month || !day) return null;
    return new Date(year, month - 1, day);
  }

  const handleDaySelection = useCallback(
    (newDate: Date) => {
      setDate(newDate);

      const params = new URLSearchParams();
      params.set("date", formatDate(newDate));

      router.push(`${window.location.pathname}?${params.toString()}`);
    },
    [router]
  );

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
  }, [dayParam, handleDaySelection]);

  return (
    <>
      <AlertMobile />

      <div className="max-lg:flex max-lg:justify-center max-lg:items-center h-screen w-full px-8 py-4">
        <Button asChild className="lg:hidden">
          <Link href="/reservas">Voltar para Reservas</Link>
        </Button>
        <div className="max-lg:hidden h-full grid grid-cols-3 gap-4">
          <div className="h-full col-span-2 flex flex-col gap-4">
            <div className="flex justify-between">
              <div className="flex items-center gap-2">
                <Button
                  size="icon"
                  variant="ghost"
                  className="h-10 w-10"
                  asChild
                >
                  <Link href="/reservas">
                    <ChevronLeft className="scale-175" />
                  </Link>
                </Button>

                <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight text-balance">
                  Nova Reserva
                </h1>
              </div>

              <div className="flex items-center gap-2">
                <ModeToggle variant="secondary" />

                <UserButton />
              </div>
            </div>

            <div className="h-full col-span-2 bg-card rounded-xl border p-4 shadow-sm flex gap-4">
              <div className="w-fit flex flex-col gap-4">
                <Calendar
                  mode="single"
                  selected={date}
                  onSelect={(d) => d && handleDaySelection(d)}
                  className="rounded-lg border"
                />

                <div className="flex flex-col gap-4">
                  <h1 className="text-xl font-semibold">Filtros</h1>

                  <div className="flex flex-col gap-2">
                    <Label className="font-medium">Horário de Início</Label>
                    <Input type="time" />
                  </div>

                  <div className="flex flex-col gap-2">
                    <Label className="font-medium">Horário de Término</Label>
                    <Input type="time" />
                  </div>

                  <h1 className="text-lg font-semibold">Softwares</h1>

                  <div className="grid grid-cols-2 gap-4">
                    {softwares.map((software) => {
                      return (
                        <div
                          key={software}
                          className="flex items-center space-x-2"
                        >
                          <Checkbox id={software} />
                          <Label htmlFor={software}>{software}</Label>
                        </div>
                      );
                    })}
                  </div>

                  <h1 className="text-lg font-semibold">Equipamentos</h1>

                  <div className="grid grid-cols-2 gap-4">
                    {equipamentos.map((equipamento) => {
                      return (
                        <div
                          key={equipamento}
                          className="flex items-center space-x-2"
                        >
                          <Checkbox id={equipamento} />
                          <Label htmlFor={equipamento}>{equipamento}</Label>
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>

              <div className="w-full h-[87svh] rounded-md">
                <NovaReservaForm />
              </div>
            </div>
          </div>

          <SectionMapa />
        </div>
      </div>
    </>
  );
}
