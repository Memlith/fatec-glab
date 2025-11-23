import Reservas from "@/components/utils/pages/Reservas";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Reservas",
};

export default function page() {
  return (
    <Suspense
      fallback={<SuspensePage text="Carregando Reservas e Calendário..." />}
    >
      <Reservas />
    </Suspense>
  );
}
