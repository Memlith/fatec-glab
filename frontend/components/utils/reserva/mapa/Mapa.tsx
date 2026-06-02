"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { Info } from "lucide-react";
import { useState } from "react";
import rectsData from "./rects.json";
import { SalaAlert } from "../SalaAlert";
// import { cn } from "@/lib/utils";

interface Rect {
  x: number;
  y: number;
  width: number;
  height: number;
}

const rects: Rect[] = rectsData;

interface room {
  id: string;
  label: string;
  x: number;
  y: number;
  width: number;
  height: number;
}

interface MapaProps {
  rooms: room[];
  bloco: number;
}

const baseStyle = {
  strokeMiterlimit: 10,
  strokeWidth: "10px",
};

export const mapStyles = {
  base: "relative transition-all fill-muted/50 cursor-pointer hover:fill-muted/75 stroke-muted-foreground/25 hover:stroke-muted-foreground/50",
  base_button:
    "cursor-pointer rounded-full flex items-center justify-center shadow-lg hover:bg-muted-foreground/50 hover:border-muted-foreground/75 transition-colors",
  selected:
    "!fill-blue-400/50 hover:!fill-blue-500/50 dark:!fill-blue-400/50 dark:hover:!fill-blue-500/40 !stroke-blue-500 hover:!stroke-blue-600 dark:!stroke-blue-800",
  selected_button:
    "border-blue-500 hover:!border-blue-600 dark:border-blue-800",
  highlighted:
    "!fill-orange-400/50 hover:!fill-orange-500/50 dark:!fill-orange-400/50 dark:hover:!fill-orange-500/40 !stroke-orange-500 hover:!stroke-orange-600 dark:!stroke-orange-800",
  highlighted_button:
    "border-orange-500 hover:!border-orange-600 dark:border-orange-800",
};

export default function Mapa({ rooms, bloco }: MapaProps) {
  const router = useRouter();
  const searchParams = useSearchParams();
  const selectedRoom = searchParams.get("room");

  const [dialogOpen, setDialogOpen] = useState(false);
  const [dialogRoomId, setDialogRoomId] = useState<string | null>(null);

  function handleMapSelection(room: string) {
    const current = new URLSearchParams(Array.from(searchParams.entries()));
    current.set("room", room);

    const search = current.toString();
    const query = search ? `?${search}` : "";

    router.push(`${window.location.pathname}${query}`);
  }

  function handleButtonClick(e: React.MouseEvent, room: string) {
    e.stopPropagation();
    setDialogRoomId(room);
    setDialogOpen(true);
  }

  return (
    <>
      <SalaAlert
        open={dialogOpen}
        onOpenChange={setDialogOpen}
        roomId={dialogRoomId}
      />

      <div className="h-full object-contain">
        <svg
          id="Layer_1"
          xmlns="http://www.w3.org/2000/svg"
          className="h-full"
          viewBox={
            bloco == 1
              ? "0 0 1740 3020"
              : bloco == 2
              ? "0 0 1650 2700"
              : "0 0 1000 1940"
          }
        >
          {rooms.map((room) => {
            const centerX = room.x + room.width / 2;
            const centerY = room.y + room.height / 2;

            const buttonSize = 200;
            const buttonX = room.x + room.width - buttonSize * 0.75;
            const buttonY = room.y + room.height - buttonSize * 0.75;

            // const reservasSize = 150;
            // const reservasX = room.x + room.width - reservasSize * 1.25;
            // const reservasY = room.y + reservasSize * 0.15;

            // const reservas = 0;

            return (
              <g key={room.id} className="relative">
                <rect
                  id={`room-${room.id}`}
                  data-room-id={room.id}
                  style={baseStyle}
                  className={
                    selectedRoom === room.id
                      ? `${mapStyles.base} ${mapStyles.selected}`
                      : mapStyles.base
                  }
                  x={room.x}
                  y={room.y}
                  width={room.width}
                  height={room.height}
                  rx={70}
                  ry={70}
                  onClick={() => handleMapSelection(room.id)}
                />

                <text
                  x={centerX}
                  y={centerY}
                  textAnchor="middle"
                  alignmentBaseline="middle"
                  fontSize="100"
                  fill="black"
                  className="fill-foreground font-semibold pointer-events-none"
                  pointerEvents="none"
                >
                  {room.label}
                </text>

                {/* <foreignObject
                  x={reservasX}
                  y={reservasY}
                  width={reservasSize}
                  height={reservasSize * 0.75}
                  className="pointer-events-none"
                >
                  <div className="text-[80px] flex justify-end items-center gap-4 text-muted-foreground">
                    <ClipboardClock
                      size={80}
                      className={cn(
                        reservas > 0
                          ? "stroke-green-600"
                          : "stroke-muted-foreground"
                      )}
                    />
                    <span>{reservas}</span>
                  </div>
                </foreignObject> */}

                <foreignObject
                  x={buttonX}
                  y={buttonY}
                  width={buttonSize}
                  height={buttonSize}
                >
                  <button
                    type="button"
                    onClick={(e) => handleButtonClick(e, room.id)}
                    className={
                      selectedRoom === room.id
                        ? `${mapStyles.base_button} ${mapStyles.selected_button}`
                        : mapStyles.base_button
                    }
                  >
                    <Info size={buttonSize * 0.5} />
                  </button>
                </foreignObject>

                {bloco == 1 ? (
                  <>
                    {rects.map((rect, index) => (
                      <rect
                        className="fill-muted stroke-muted-foreground/25 stroke-3"
                        key={index}
                        x={rect.x}
                        y={rect.y}
                        width={rect.width}
                        height={rect.height}
                      />
                    ))}
                    <text
                      x={1500}
                      y={1550}
                      textAnchor="middle"
                      alignmentBaseline="middle"
                      fontSize="80"
                      fill="black"
                      className="fill-foreground font-medium pointer-events-none"
                      pointerEvents="none"
                    >
                      Escadas
                    </text>
                  </>
                ) : null}
              </g>
            );
          })}
        </svg>
      </div>
    </>
  );
}
