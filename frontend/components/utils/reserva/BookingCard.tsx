"use client";

import { Badge } from "@/components/ui/badge";
import { Clock, User } from "lucide-react";
import { cn } from "@/lib/utils";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

interface Booking {
  id: string;
  title: string;
  startTime: string;
  endTime: string;
  user?: string;
  type?: "Aula" | "Agendamento";
  resource?: string;
  color?: string;
}

interface BookingCardProps {
  booking: Booking;
  top: number;
  height: number;
}

export function BookingCard({ booking, top, height }: BookingCardProps) {
  return (
    <Dialog>
      <DialogTrigger>
        <div
          className={cn(
            "absolute left-2 right-0 mr-2 p-2.5 rounded-lg border bg-card dark:bg-[#262629] transition-all cursor-pointer overflow-hidden hover:scale-[101%]",
            booking.color && "border-l-4"
          )}
          style={{
            top: `${top}px`,
            height: `${height}px`,
            borderLeftColor: booking.color || undefined,
            minHeight: "40px",
          }}
        >
          <div className="flex items-start justify-between gap-2 mb-1">
            <h4 className="font-medium text-sm leading-tight line-clamp-1">
              {booking.title}
            </h4>
            <Badge
              variant={booking.type === "Aula" ? "destructive" : "default"}
              className="text-xs flex-shrink-0"
            >
              {booking.type === "Aula" ? "Aula" : "Agendamento"}
            </Badge>
          </div>

          <div className="flex items-center gap-2 text-xs text-muted-foreground">
            <div className="flex items-center gap-1">
              <Clock className="h-3 w-3" />
              <span>
                {booking.startTime} - {booking.endTime}
              </span>
            </div>
            {booking.user && (
              <div className="flex items-center gap-1">
                <User className="h-3 w-3" />
                <span className="truncate">{booking.user}</span>
              </div>
            )}
          </div>

          {booking.resource && height > 60 && (
            <p className="text-start text-xs text-muted-foreground mt-1 line-clamp-1">
              {booking.resource}
            </p>
          )}
        </div>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{booking.title}</DialogTitle>
          <div className="flex items-center text-md gap-2 text-muted-foreground">
            <div className="flex items-center gap-1">
              <Clock className="h-4 w-4" />
              <span>
                {booking.startTime} - {booking.endTime}
              </span>
            </div>
            {booking.user && (
              <div className="flex items-center gap-1">
                <User className="h-4 w-4" />
                <span className="truncate">{booking.user}</span>
              </div>
            )}
          </div>
          <span>{booking.resource}</span>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
