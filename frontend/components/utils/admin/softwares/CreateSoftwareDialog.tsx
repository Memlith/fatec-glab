"use client";

import * as React from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";
import { createSoftware } from "@/services/softwaresServices";

interface CreateSoftwareDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onSuccess: () => void;
}

export function CreateSoftwareDialog({
  open,
  onOpenChange,
  onSuccess,
}: CreateSoftwareDialogProps) {
  const [isLoading, setIsLoading] = React.useState(false);
  const [formData, setFormData] = React.useState({
    id: "",
    name: "",
  });

  React.useEffect(() => {
    if (!open) {
      setFormData({ id: "", name: "" });
    }
  }, [open]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setIsLoading(true);
    try {
      await createSoftware({
        name: formData.name,
      });

      toast.success("Software criado com sucesso.");

      onOpenChange(false);
      onSuccess();
    } catch (error) {
      console.error(error);
      toast.error("Falha ao criar software.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <form onSubmit={handleSubmit}>
          <DialogHeader>
            <DialogTitle>Criar Software</DialogTitle>
            <DialogDescription>
              Adicione um novo software ao sistema.
            </DialogDescription>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid gap-2">
              <Label htmlFor="create-name">Nome</Label>
              <Input
                id="create-name"
                value={formData.name}
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
                required
              />
            </div>
          </div>
          <DialogFooter>
            <Button
              type="button"
              variant="outline"
              onClick={() => onOpenChange(false)}
              disabled={isLoading}
            >
              Cancelar
            </Button>
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Criando..." : "Criar Software"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
