"use client";

import { useState, useEffect } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import Mapa from "./Mapa";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import roomsData from "./rooms.json";
import { ClipboardClock } from "lucide-react";

export interface Room {
  id: string;
  label: string;
  x: number;
  y: number;
  width: number;
  height: number;
}

interface Building {
  [floor: string]: Room[];
}

export interface RoomsData {
  [building: string]: Building;
}

const rooms: RoomsData = roomsData;

function findRoomLocation(roomId: string | null): {
  block: number;
  floor: string;
} | null {
  if (!roomId) return null;

  for (const [blockKey, floors] of Object.entries(rooms)) {
    for (const [floorKey, roomList] of Object.entries(floors)) {
      if (roomList.some((room) => room.id === roomId)) {
        return { block: Number(blockKey), floor: floorKey };
      }
    }
  }

  return null;
}

const floorLabels: Record<string, string> = {
  terreo: "Térreo",
  primeiro: "Primeiro Andar",
  segundo: "Segundo Andar",
  terceiro: "Terceiro Andar",
};

export default function SectionMapa() {
  const searchParams = useSearchParams();
  const router = useRouter();

  const [room, setRoom] = useState<string | null>(null);

  useEffect(() => {
    const param = searchParams.get("room");
    if (!param) {
      setRoom("lab_01");
      const newUrl = new URL(window.location.href);
      newUrl.searchParams.set("room", "lab_01");
      router.replace(newUrl.toString());
    } else {
      setRoom(param);
    }
  }, [searchParams, router]);

  const roomLocation = findRoomLocation(room ?? "lab_01");
  const [selectedBlock, setSelectedBlock] = useState<number>(
    roomLocation?.block || 1
  );
  const [selectedFloor, setSelectedFloor] = useState<string>(
    roomLocation?.floor || "terreo"
  );

  useEffect(() => {
    if (room) {
      const location = findRoomLocation(room);
      if (location) {
        setSelectedBlock(location.block);
        setSelectedFloor(location.floor);
      }
    }
  }, [room]);

  const availableFloors = Object.keys(
    rooms[selectedBlock as keyof typeof rooms] || {}
  );
  const hasMultipleFloors = availableFloors.length > 1;

  const handleBlockChange = (value: string) => {
    const newBlock = Number(value);
    setSelectedBlock(newBlock);

    const newBlockFloors = Object.keys(
      rooms[newBlock as keyof typeof rooms] || {}
    );
    if (!newBlockFloors.includes(selectedFloor)) {
      setSelectedFloor("terreo");
    }
  };

  const currentRooms =
    rooms[selectedBlock as keyof typeof rooms]?.[
      selectedFloor as keyof (typeof rooms)[1]
    ] || [];

  const isLoading = room === null;

  return (
    <div className="h-full bg-card flex flex-col gap-4 rounded-xl lg:border p-4 shadow-sm">
      {isLoading ? (
        <div className="flex items-center justify-center h-full">
          <p className="text-muted-foreground">Carregando mapa...</p>
        </div>
      ) : (
        <>
          <div className="h-fit flex flex-col gap-2">
            <div className="w-full grid grid-cols-3 max-2xl:grid-cols-2 items-center">
              <span className="font-semibold text-nowrap text-center xl:text-sm">
                Selecionar Bloco
              </span>

              <Select
                value={selectedBlock.toString()}
                onValueChange={handleBlockChange}
              >
                <SelectTrigger className="w-full col-span-2 max-2xl:col-span-1">
                  <SelectValue placeholder="Selecione o bloco" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="1">Bloco 1</SelectItem>
                  <SelectItem value="2">Bloco 2</SelectItem>
                  <SelectItem value="3">Bloco 3</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="w-full grid grid-cols-3 max-2xl:grid-cols-2 items-center">
              <span className="font-semibold text-nowrap text-center xl:text-sm">
                Selecionar Andar
              </span>
              <Select
                value={selectedFloor}
                onValueChange={setSelectedFloor}
                disabled={!hasMultipleFloors}
              >
                <SelectTrigger className="w-full col-span-2 max-2xl:col-span-1">
                  <SelectValue
                    placeholder={
                      hasMultipleFloors ? "Selecione o andar" : "---"
                    }
                  />
                </SelectTrigger>
                <SelectContent>
                  {availableFloors.map((floor) => (
                    <SelectItem key={floor} value={floor}>
                      {floorLabels[floor]}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>

          <div className="h-full px-16 lg:px-8 flex flex-col gap-4 xl:gap-2">
            <h2 className="h-fit text-2xl max-xl:text-base font-semibold tracking-tight text-center">
              Mapa {floorLabels[selectedFloor]} - Bloco {selectedBlock}
            </h2>

            <div className="h-full lg:h-[80%] flex justify-center">
              <Mapa rooms={currentRooms} bloco={selectedBlock} />
            </div>

            <div className="w-full flex justify-center">
              <div className="xl:w-full max-xl:w-3/4 xl:grid xl:grid-cols-2 gap-2">
                <div className="flex items-center gap-2">
                  <span className="h-5 w-5 bg-muted/50 rounded-sm border border-muted-foreground/25" />
                  <p className="font-medium max-2xl:text-sm">Disponível</p>
                </div>
                <div className="flex items-center gap-2">
                  <span className="h-5 w-5 bg-blue-400/50 rounded-sm" />
                  <p className="font-medium max-2xl:text-sm">Selecionado</p>
                </div>
                <div className="flex items-center gap-2">
                  <span className="h-5 w-5 bg-red-400/50 rounded-sm" />
                  <p className="font-medium max-2xl:text-sm">Indisponível</p>
                </div>
                <div className="flex items-center gap-2">
                  <ClipboardClock size={22} className="stroke-green-600" />
                  <p className="font-medium max-2xl:text-sm">Reservas</p>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}
