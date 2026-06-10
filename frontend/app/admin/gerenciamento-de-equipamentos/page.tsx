"use client";

import GerenciamentoEquipamentos from "@/components/utils/pages/admin/GerenciamentoEquipamentos";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Suspense } from "react";

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Equipamentos" />
      }
    >
      <GerenciamentoEquipamentos />
    </Suspense>
  );
}
