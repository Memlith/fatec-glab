import GerenciamentoEquipamentos from "@/components/utils/pages/admin/GerenciamentoEquipamentos";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Gerenciamento de Equipamentos",
};

export default function page() {
  return <GerenciamentoEquipamentos />;
}
