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
import { Professor } from "@/services/api";
import { updateProfessor } from "@/services/professorService";
import { toast } from "sonner";

interface EditProfessorDialogProps {
  professor: Professor | null;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onSuccess: () => void;
}

export function EditProfessorDialog({
  professor,
  open,
  onOpenChange,
  onSuccess,
}: EditProfessorDialogProps) {
  const [isLoading, setIsLoading] = React.useState(false);
  const [formData, setFormData] = React.useState({
    name: "",
    email: "",
  });

  React.useEffect(() => {
    if (professor) {
      setFormData({
        name: professor.name,
        email: professor.email,
      });
    }
  }, [professor]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!professor) return;

    setIsLoading(true);
    try {
      await updateProfessor(professor.id, {
        name: formData.name,
        email: formData.email,
      });

      toast.success("Professor atualizado com sucesso.");

      onOpenChange(false);
      onSuccess();
    } catch (error) {
      console.error(error);
      toast.error("Falha ao atualizar professor.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <form onSubmit={handleSubmit}>
          <DialogHeader>
            <DialogTitle>Editar Professor</DialogTitle>
            <DialogDescription>
              Faça alterações nos dados do professor aqui.
            </DialogDescription>
          </DialogHeader>
          <div className="grid gap-4 py-4">
            <div className="grid gap-2">
              <Label htmlFor="name">Nome</Label>
              <Input
                id="name"
                value={formData.name}
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
                required
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={formData.email}
                onChange={(e) =>
                  setFormData({ ...formData, email: e.target.value })
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
              {isLoading ? "Salvando..." : "Salvar alterações"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
