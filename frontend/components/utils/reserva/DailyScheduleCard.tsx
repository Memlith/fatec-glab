"use client";

import { useEffect, useMemo, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Loader2 } from "lucide-react";
import { Booking } from "@/services/api";
import { BookingCard } from "./BookingCard";
import { useSearchParams } from "next/navigation";

const HOUR_HEIGHT = 60;
const START_HOUR = 7;

const toMinutes = (time: string): number => {
  const [h, m] = time.split(":").map(Number);
  return h * 60 + m;
};

const extractTimeFromISO = (iso: string): string => {
  try {
    return iso.split("T")[1].substring(0, 5);
  } catch {
    return "00:00";
  }
};

const calculateVerticalPosition = (
  startTime: string,
  endTime: string,
  startHour: number
) => {
  const startMinutes = toMinutes(startTime);
  const endMinutes = toMinutes(endTime);
  const baseMinutes = startHour * 60;

  return {
    top: ((startMinutes - baseMinutes) / 60) * HOUR_HEIGHT,
    height: ((endMinutes - startMinutes) / 60) * HOUR_HEIGHT,
  };
};

interface DailyScheduleCardProps {
  bookings?: Booking[];
}

export function DailyScheduleCard({ bookings = [] }: DailyScheduleCardProps) {
  const [isLoading, setIsLoading] = useState(true);
  const [currentTimePos, setCurrentTimePos] = useState<number | null>(null);
  const searchParams = useSearchParams();
  const selectedDate = searchParams.get("date");

  const timeSlots = useMemo(() => {
    return Array.from({ length: 24 - START_HOUR + 1 }, (_, i) => {
      const hour = i + START_HOUR;
      return `${hour.toString().padStart(2, "0")}:00`;
    });
  }, []);

  const formattedDate = useMemo(() => {
    const dateToFormat = selectedDate
      ? selectedDate + "T00:00:00"
      : new Date().toISOString();

    const dateObject = new Date(dateToFormat);

    if (isNaN(dateObject.getTime())) {
      return "Data Inválida";
    }

    return new Intl.DateTimeFormat("pt-BR", {
      weekday: "long",
      day: "numeric",
      month: "long",
      timeZone: "America/Sao_Paulo",
    }).format(dateObject);
  }, [selectedDate]);

  useEffect(() => {
    const updateTimePosition = () => {
      const now = new Date();
      const current = `${now.getHours().toString().padStart(2, "0")}:${now
        .getMinutes()
        .toString()
        .padStart(2, "0")}`;

      const currentMinutes = toMinutes(current);
      const startMinutes = START_HOUR * 60;

      if (currentMinutes >= startMinutes && currentMinutes <= 24 * 60) {
        return setCurrentTimePos(
          ((currentMinutes - startMinutes) / 60) * HOUR_HEIGHT
        );
      }

      setCurrentTimePos(null);
    };

    updateTimePosition();
    const timer = setInterval(updateTimePosition, 5 * 60 * 1000);

    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    setIsLoading(true);
    const timer = setTimeout(() => setIsLoading(false), 300);
    return () => clearTimeout(timer);
  }, [bookings]);

  return (
    <Card className="w-full h-full rounded-md flex flex-col pb-0">
      <CardHeader className="pb-3 flex-shrink-0">
        <CardTitle className="text-lg font-semibold capitalize">
          {formattedDate}
        </CardTitle>
        <p className="text-sm text-muted-foreground">
          {bookings.length} {bookings.length === 1 ? "reserva" : "reservas"}{" "}
          hoje
        </p>
      </CardHeader>

      <ScrollArea className="h-[73svh] max-2xl:h-[65svh]">
        <CardContent className="pb-4 relative">
          <div className="flex gap-4">
            <div className="flex-shrink-0 w-12">
              {timeSlots.map((slot) => (
                <div
                  key={slot}
                  className="text-sm font-medium text-muted-foreground"
                  style={{ height: HOUR_HEIGHT }}
                >
                  {slot}
                </div>
              ))}
            </div>

            <div className="w-px bg-border flex-shrink-0" />

            <div className="flex-1 relative">
              {timeSlots.map((_, idx) => (
                <div
                  key={idx}
                  className="absolute w-full border-t border-border"
                  style={{ top: idx * HOUR_HEIGHT }}
                />
              ))}

              {currentTimePos !== null && (
                <div
                  className="absolute w-full border-t-2 border-red-500 z-10"
                  style={{ top: currentTimePos }}
                >
                  <div className="absolute -left-2 -top-[7px] w-3 h-3 rounded-full bg-red-500" />
                </div>
              )}

              {!isLoading &&
                bookings.map((booking) => {
                  const start = extractTimeFromISO(booking.startTime);
                  const end = extractTimeFromISO(booking.endTime);

                  const pos = calculateVerticalPosition(start, end, START_HOUR);

                  return (
                    <BookingCard
                      key={booking.id}
                      booking={booking}
                      top={pos.top}
                      height={pos.height}
                    />
                  );
                })}

              <div style={{ height: timeSlots.length * HOUR_HEIGHT }} />
            </div>
          </div>

          {isLoading && (
            <div className="absolute inset-0 bg-black/40 flex items-center justify-center z-20 rounded-md">
              <Loader2 className="w-12 h-12 text-white animate-spin" />
            </div>
          )}
        </CardContent>
      </ScrollArea>
    </Card>
  );
}
