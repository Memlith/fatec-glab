import { SidebarTrigger } from "@/components/ui/sidebar";
import { SoftwaresTable } from "../../admin/softwares/SoftwaresTable";

export default function GerenciamentoSoftwares() {
  return (
    <div className="h-screen w-full px-8 py-4 max-lg:p-4">
      <div className="h-full  max-lg:col-span-3 flex flex-col gap-4">
        <div className="flex items-center gap-2 ml-[-1rem]">
          <SidebarTrigger className="size-9" />

          <h1 className="scroll-m-20 text-3xl font-extrabold tracking-tight text-balance">
            Gerenciamento de Softwares
          </h1>
        </div>

        <SoftwaresTable />
      </div>
    </div>
  );
}
