"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ScrollArea } from "@/components/ui/scroll-area";
import { BookingCard } from "./BookingCard";
import { useEffect, useState } from "react";
import { Loader2 } from "lucide-react";
import { Booking } from "@/services/api";

interface DailyScheduleCardProps {
  date: Date;
  bookings?: Booking[];
}

const timeToMinutes = (time: string): number => {
  const [hours, minutes] = time.split(":").map(Number);
  return hours * 60 + minutes;
};

const extractTimeFromISO = (isoString: string): string => {
  try {
    return isoString.split("T")[1].substring(0, 5);
  } catch (error) {
    console.error("Error extracting time from ISO string:", isoString, error);
    return "00:00";
  }
};

const calculateBookingPosition = (
  startTime: string,
  endTime: string,
  startHour: number,
  hourHeight: number
) => {
  const startMinutes = timeToMinutes(startTime);
  const endMinutes = timeToMinutes(endTime);
  const startHourMinutes = startHour * 60;

  const topOffset = ((startMinutes - startHourMinutes) / 60) * hourHeight;
  const height = ((endMinutes - startMinutes) / 60) * hourHeight;

  return { top: topOffset, height };
};

export function DailyScheduleCard({
  date,
  bookings = [],
}: DailyScheduleCardProps) {
  const [isLoading, setIsLoading] = useState(true);
  const [data, setData] = useState<Booking[]>([]);
  const [currentTimePosition, setCurrentTimePosition] = useState<number | null>(
    null
  );

  const formatDate = (date: Date) => {
    return new Intl.DateTimeFormat("pt-BR", {
      weekday: "long",
      day: "numeric",
      month: "long",
    }).format(date);
  };

  const getTimeSlots = () => {
    const slots = [];
    for (let hour = 7; hour <= 24; hour++) {
      slots.push(`${hour.toString().padStart(2, "0")}:00`);
    }
    return slots;
  };

  const timeSlots = getTimeSlots();
  const currentTime = new Intl.DateTimeFormat("pt-BR", {
    hour: "2-digit",
    minute: "2-digit",
  }).format(new Date());

  const HOUR_HEIGHT = 60;
  const START_HOUR = 7;

  const getCurrentTimePosition = () => {
    const currentMinutes = timeToMinutes(currentTime);
    const startMinutes = START_HOUR * 60;
    if (currentMinutes >= startMinutes && currentMinutes <= 24 * 60) {
      const position = ((currentMinutes - startMinutes) / 60) * HOUR_HEIGHT;
      setCurrentTimePosition(position);
      return position;
    }
    return null;
  };

  useEffect(() => {
    setInterval(getCurrentTimePosition, 300000);
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setData(bookings);
      setIsLoading(false);
    };

    fetchData();
  }, [bookings]);

  return (
    <Card className="w-full h-full rounded-md flex flex-col pb-0 ">
      <CardHeader className="pb-3 flex-shrink-0">
        <CardTitle className="text-lg font-semibold capitalize">
          {formatDate(date)}
        </CardTitle>
        <p className="text-sm text-muted-foreground">
          {bookings.length} {bookings.length === 1 ? "reserva" : "reservas"}{" "}
          hoje
        </p>
      </CardHeader>

      <ScrollArea className="h-[73svh] max-2xl:h-[65svh]">
        <CardContent className="pb-4">
          <div className="flex gap-4">
            <div className="flex-shrink-0 w-12">
              {timeSlots.map((slot) => (
                <div
                  key={slot}
                  className="text-sm font-medium text-muted-foreground"
                  style={{ height: `${HOUR_HEIGHT}px` }}
                >
                  {slot}
                </div>
              ))}
            </div>

            <div className="w-px bg-border flex-shrink-0" />

            <div className="flex-1 relative">
              {timeSlots.map((slot, index) => (
                <div
                  key={`grid-${slot}`}
                  className="absolute w-full border-t border-border"
                  style={{ top: `${index * HOUR_HEIGHT}px` }}
                />
              ))}

              {currentTimePosition !== null && (
                <div
                  className="absolute w-full border-t-2 border-red-500 z-10"
                  style={{ top: `${currentTimePosition}px` }}
                >
                  <div className="absolute -left-2 -top-1.5 w-3 h-3 rounded-full bg-red-500" />
                </div>
              )}

              {!isLoading &&
                data.map((booking) => {
                  const startHourMinute = extractTimeFromISO(booking.startTime);
                  const endHourMinute = extractTimeFromISO(booking.endTime);

                  const { top, height } = calculateBookingPosition(
                    startHourMinute,
                    endHourMinute,
                    START_HOUR,
                    HOUR_HEIGHT
                  );

                  return (
                    <BookingCard
                      key={booking.id}
                      booking={booking}
                      top={top}
                      height={height}
                    />
                  );
                })}

              {/* {bookings.map((booking) => {
                const { top, height } = calculateBookingPosition(
                  booking.startTime,
                  booking.endTime,
                  START_HOUR,
                  HOUR_HEIGHT
                );

                return (
                  <BookingCard
                    key={booking.id}
                    booking={booking}
                    top={top}
                    height={height}
                  />
                );
              })} */}

              <div style={{ height: `${timeSlots.length * HOUR_HEIGHT}px` }} />
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
