"use client";

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import Image from "next/image";

interface SalaAlertProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  roomId: string | null;
}

const dados = {
  sala: "Lab 13",
  capacidade: 30,
  softwares: ["VSCode", "Eclipse", "Java", "Node", "AutoCAD", "Arduino"],
  equipamentos: ["Laptops", "Projetor", "TV", "Makita"],
};

export function SalaAlert({ open, onOpenChange, roomId }: SalaAlertProps) {
  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{roomId}</DialogTitle>
          <DialogDescription>
            Capacidade: {dados.capacidade} pessoas
          </DialogDescription>
        </DialogHeader>
        <div className="space-y-4 py-4">
          <h1 className="text-lg font-semibold">Softwares</h1>

          <ul className="pl-4 grid grid-cols-3 gap-4 list-disc">
            {dados.softwares.map((software) => {
              switch (software) {
                case "VSCode":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <Image
                        alt="icon"
                        src="/vscode.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                        width={16}
                        height={16}
                      />
                    </div>
                  );
                case "Java":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <Image
                        alt="icon"
                        src="/java.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                        width={16}
                        height={16}
                      />
                    </div>
                  );
                case "Node":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <Image
                        alt="icon"
                        src="/node.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                        width={16}
                        height={16}
                      />
                    </div>
                  );
                case "Arduino":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <Image
                        alt="icon"
                        src="/arduino.svg"
                        className="w-4 h-4 object-cover pointer-events-none"
                        width={16}
                        height={16}
                      />
                    </div>
                  );
              }
              return <li key={software}>{software}</li>;
            })}
          </ul>

          <h1 className="text-lg font-semibold">Equipamentos</h1>

          <ul className="pl-4 grid grid-cols-3 gap-4 list-disc">
            {dados.equipamentos.map((equipamento) => {
              if (equipamento === "Makita") {
                return (
                  <div key={equipamento} className="flex gap-2 items-center">
                    <li key={equipamento}>{equipamento}</li>
                    <Image
                      alt="icon"
                      src="/makita.png"
                      className="w-4 h-4 object-cover pointer-events-none"
                      width={16}
                      height={16}
                    />
                  </div>
                );
              }
              return <li key={equipamento}>{equipamento}</li>;
            })}
          </ul>
        </div>
      </DialogContent>
    </Dialog>
  );
}
