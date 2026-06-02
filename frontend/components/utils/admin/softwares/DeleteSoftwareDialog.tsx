"use client";

import * as React from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { Software } from "@/services/api";
import { toast } from "sonner";
import { deleteSoftware } from "@/services/softwaresServices";

interface DeleteSoftwareDialogProps {
  software: Software | null;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onSuccess: () => void;
}

export function DeleteSoftwareDialog({
  software,
  open,
  onOpenChange,
  onSuccess,
}: DeleteSoftwareDialogProps) {
  const [isLoading, setIsLoading] = React.useState(false);

  const handleDelete = async () => {
    if (!software?.id) return;

    setIsLoading(true);
    try {
      await deleteSoftware(software.id);

      toast.success("Software deletado com sucesso.");

      onOpenChange(false);
      onSuccess();
    } catch (error) {
      console.error(error);
      toast.error("Falha ao deletar software.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>Você tem certeza?</AlertDialogTitle>
          <AlertDialogDescription>
            Esta ação não pode ser desfeita. Isso irá deletar permanentemente o
            software <strong>{software?.name}</strong>.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel disabled={isLoading}>Cancelar</AlertDialogCancel>
          <AlertDialogAction
            onClick={handleDelete}
            disabled={isLoading}
            className="bg-destructive text-white dark:text-destructive-foreground hover:bg-destructive/90"
          >
            {isLoading ? "Deletando..." : "Deletar"}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
