"use client";

import { Badge } from "@/components/ui/badge";
import { Clock, User } from "lucide-react";
import { cn } from "@/lib/utils";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Booking } from "@/services/api";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import EditarReservaForm from "./EditarReservaForm";
import { useEffect, useState } from "react";
import { fetchProfessorById } from "@/services/professorService";

interface BookingCardProps {
  booking: Booking;
  top: number;
  height: number;
}

const getBookingColor = (
  type: string
): { badgeClass: string; hexColor: string } => {
  switch (type) {
    case "DSM":
      return { badgeClass: "bg-indigo-600", hexColor: "#4f46e5" };
    case "COMEX":
      return { badgeClass: "bg-yellow-400", hexColor: "#facc15" };
    case "REDES":
      return { badgeClass: "bg-red-700", hexColor: "#b91c1c" };
    case "ADS":
      return { badgeClass: "bg-teal-500", hexColor: "#14b8a6" };
    case "GESTAO-EMP-V":
      return { badgeClass: "bg-emerald-700", hexColor: "#047857" };
    case "GESTAO-EMP-N":
      return { badgeClass: "bg-cyan-500", hexColor: "#06b6d4" };
    case "GESTAO-SERVICOS":
      return { badgeClass: "bg-blue-800", hexColor: "#1e40af" };
    case "LOG-AERO":
      return { badgeClass: "bg-amber-500", hexColor: "#f59e0b" };
    default:
      return { badgeClass: "bg-orange-500", hexColor: "#f97316" };
  }
};

export function BookingCard({ booking, top, height }: BookingCardProps) {
  const { badgeClass, hexColor } = getBookingColor(booking.type);
  const [professor, setProfessor] = useState<string | null>(null);

  useEffect(() => {
    async function fetchData() {
      const professorData = await fetchProfessorById(booking.professorId);

      if (professorData && professorData.name) {
        setProfessor(professorData.name);
      } else {
        setProfessor("Professor não identificado");
      }
    }
    fetchData();
  }, [booking.professorId]);

  return (
    <Dialog>
      <DialogTrigger>
        <div
          className={cn(
            "absolute left-2 right-0 mr-2 p-2.5 rounded-lg border bg-card dark:bg-[#262629] transition-all cursor-pointer overflow-hidden hover:scale-[101%]",
            hexColor && "border-l-4"
          )}
          style={{
            top: `${top}px`,
            height: `${height}px`,
            borderLeftColor: hexColor || undefined,
            minHeight: "40px",
          }}
        >
          <div className="flex items-start justify-between gap-2 mb-1">
            <h4 className="font-medium text-sm leading-tight line-clamp-1">
              {booking.title}
            </h4>
            <Badge
              className={cn(`text-xs flex-shrink-0 text-white`, badgeClass)}
            >
              {booking.type === "Agendamento"
                ? "Agendamento"
                : booking.type.toUpperCase()}
            </Badge>
          </div>

          <div className="flex items-center gap-2 text-xs text-muted-foreground">
            <div className="flex items-center gap-1">
              <Clock className="h-3 w-3" />
              <span>
                {booking.startTime.split("T")[1].slice(0, 5)} -{" "}
                {booking.endTime.split("T")[1].slice(0, 5)}
              </span>
            </div>
            {professor && (
              <div className="flex items-center gap-1">
                <User className="h-3 w-3" />
                <span className="truncate">{professor}</span>
              </div>
            )}
          </div>

          {booking.description && height > 60 && (
            <p className="text-start text-xs text-muted-foreground mt-1 line-clamp-1">
              {booking.description}
            </p>
          )}
        </div>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="flex gap-2 items-center">
            {booking.title}
          </DialogTitle>
          <Badge className={cn(`text-xs flex-shrink-0 text-white`, badgeClass)}>
            {booking.type === "Agendamento"
              ? "Agendamento"
              : booking.type.toUpperCase()}
          </Badge>
          <div className="flex items-center text-md gap-2 text-muted-foreground">
            <div className="flex items-center gap-1">
              <Clock className="h-4 w-4" />
              <span>
                {booking.startTime.split("T")[1].slice(0, 5)} -{" "}
                {booking.endTime.split("T")[1].slice(0, 5)}
              </span>
            </div>
            {professor && (
              <div className="flex items-center gap-1">
                <User className="h-4 w-4" />
                <span className="truncate">{professor}</span>
              </div>
            )}
          </div>
          <span>{booking.description}</span>

          <Accordion type="single" collapsible>
            <AccordionItem value="editar">
              <AccordionTrigger>Editar</AccordionTrigger>
              <AccordionContent className="bg-muted px-4 rounded-lg">
                <EditarReservaForm booking={booking} />
              </AccordionContent>
            </AccordionItem>
          </Accordion>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
