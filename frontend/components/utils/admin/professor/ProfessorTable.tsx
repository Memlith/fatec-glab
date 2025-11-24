"use client";

import * as React from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import type { Professor } from "@/services/api";
import { fetchProfessorsSet } from "@/services/professorService";
import { createColumns } from "./CreateColumns";
import { DataTable } from "./DataTable";
import { EditProfessorDialog } from "./EditProfessorDialog";
import { DeleteProfessorDialog } from "./DeleteProfessorDialog";
import { CreateProfessorDialog } from "./CreateProfessorDialog";

export function ProfessorTable() {
  const [professors, setProfessors] = React.useState<Professor[]>([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const [editingProfessor, setEditingProfessor] =
    React.useState<Professor | null>(null);
  const [deletingProfessor, setDeletingProfessor] =
    React.useState<Professor | null>(null);
  const [isCreating, setIsCreating] = React.useState(false);

  const loadProfessors = React.useCallback(async () => {
    setIsLoading(true);
    await fetchProfessorsSet(setProfessors);
    setIsLoading(false);
  }, []);

  React.useEffect(() => {
    loadProfessors();
  }, [loadProfessors]);

  const columns = React.useMemo(
    () =>
      createColumns({
        onEdit: (professor) => setEditingProfessor(professor),
        onDelete: (professor) => setDeletingProfessor(professor),
      }),
    []
  );

  if (isLoading) {
    return (
      <div className="space-y-4">
        <Skeleton className="h-[400px] w-full" />
        <div className="flex items-center justify-between">
          <Skeleton className="h-10 w-[200px]" />
          <Skeleton className="h-10 w-[300px]" />
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="flex justify-between items-center">
        <Button onClick={() => setIsCreating(true)}>
          <Plus className="mr-2 h-4 w-4" />
          Novo Professor
        </Button>
      </div>

      <DataTable columns={columns} data={professors} />

      <CreateProfessorDialog
        open={isCreating}
        onOpenChange={setIsCreating}
        onSuccess={loadProfessors}
      />

      <EditProfessorDialog
        professor={editingProfessor}
        open={!!editingProfessor}
        onOpenChange={(open) => !open && setEditingProfessor(null)}
        onSuccess={loadProfessors}
      />

      <DeleteProfessorDialog
        professor={deletingProfessor}
        open={!!deletingProfessor}
        onOpenChange={(open) => !open && setDeletingProfessor(null)}
        onSuccess={loadProfessors}
      />
    </>
  );
}
