"use client";

import GerenciamentoProfessores from "@/components/utils/pages/admin/GerenciamentoProfessores";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Suspense } from "react";

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Professores" />
      }
    >
      <GerenciamentoProfessores />
    </Suspense>
  );
}
