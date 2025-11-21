import NovaReserva from "@/components/utils/pages/NovaReserva";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Nova Reserva",
};

export default function page() {
  return <NovaReserva />;
}
