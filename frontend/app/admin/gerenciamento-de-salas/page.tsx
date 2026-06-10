"use client";

import GerenciamentoSalas from "@/components/utils/pages/admin/GerenciamentoSalas";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Suspense } from "react";

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
