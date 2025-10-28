"use client";

import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";

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
                      <img
                        src="/vscode.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                      />
                    </div>
                  );
                case "Java":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <img
                        src="/java.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                      />
                    </div>
                  );
                case "Node":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <img
                        src="/node.png"
                        className="w-4 h-4 object-cover pointer-events-none"
                      />
                    </div>
                  );
                case "Arduino":
                  return (
                    <div key={software} className="flex gap-2 items-center">
                      <li key={software}>{software}</li>
                      <img
                        src="/arduino.svg"
                        className="w-4 h-4 object-cover pointer-events-none"
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
                    <img
                      src="/makita.png"
                      className="w-4 h-4 object-cover pointer-events-none"
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
