import GerenciamentoSalas from "@/components/utils/pages/admin/GerenciamentoSalas";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Gerenciamento de Salas",
};

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Salas" />
      }
    >
      <GerenciamentoSalas />
    </Suspense>
  );
}
