"use client";

import SectionMapa, {
  RoomsData,
} from "@/components/utils/reserva/mapa/SectionMapa";
import { useSearchParams } from "next/navigation";
import roomsData from "../../reserva/mapa/rooms.json";
import RoomManagementForm from "../../admin/RoomManagementForm";

const rooms: RoomsData = roomsData;

export default function GerenciamentoSalas() {
  const searchParams = useSearchParams();
  const roomParam = searchParams.get("room");

  const roomLabel = (roomId: string, data: RoomsData): string => {
    if (!roomId) return "Nenhuma sala selecionada";

    for (const buildingKey in data) {
      const building = data[buildingKey as keyof typeof data];

      for (const floorKey in building) {
        const roomsArray = building[floorKey as keyof typeof building];

        const foundRoom = roomsArray.find((room) => room.id === roomId);

        if (foundRoom) {
          return foundRoom.label;
        }
      }
    }

    return "Sala não encontrada";
  };

  const roomName = roomLabel(roomParam ?? "", rooms);

  return (
    <div className="h-screen w-full px-8 py-4 max-lg:p-4">
      <div className="h-full grid grid-cols-3 gap-4">
        <div className="h-full col-span-2 max-lg:col-span-3 flex flex-col gap-4">
          <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight text-balance">
            Gerenciamento de Salas
          </h1>

          <RoomManagementForm roomName={roomName} />
        </div>

        <div className="max-lg:hidden">
          <SectionMapa />
        </div>
      </div>
    </div>
  );
}
