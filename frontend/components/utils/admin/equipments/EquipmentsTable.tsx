"use client";

import * as React from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import type { Equipment } from "@/services/api";
import { createColumns } from "./CreateColumns";
import { DataTable } from "./DataTable";
import { fetchEquipmentSet } from "@/services/equipmentsService";
import { CreateEquipmentDialog } from "./CreateEquipmentDialog";
import { EditEquipmentDialog } from "./EditEquipmentDialog";
import { DeleteEquipmentDialog } from "./DeleteEquipmentDialog";

export function EquipmentsTable() {
  const [equipments, setEquipments] = React.useState<Equipment[]>([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const [editingEquipment, setEditingEquipment] =
    React.useState<Equipment | null>(null);
  const [deletingEquipment, setDeletingEquipment] =
    React.useState<Equipment | null>(null);
  const [isCreating, setIsCreating] = React.useState(false);

  const loadEquipments = React.useCallback(async () => {
    setIsLoading(true);
    await fetchEquipmentSet(setEquipments);
    setIsLoading(false);
  }, []);

  React.useEffect(() => {
    loadEquipments();
  }, [loadEquipments]);

  const columns = React.useMemo(
    () =>
      createColumns({
        onEdit: (equipment) => setEditingEquipment(equipment),
        onDelete: (equipment) => setDeletingEquipment(equipment),
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
          Novo Equipamento
        </Button>
      </div>

      <DataTable columns={columns} data={equipments} />

      <CreateEquipmentDialog
        open={isCreating}
        onOpenChange={setIsCreating}
        onSuccess={loadEquipments}
      />

      <EditEquipmentDialog
        equipment={editingEquipment}
        open={!!editingEquipment}
        onOpenChange={(open) => !open && setEditingEquipment(null)}
        onSuccess={loadEquipments}
      />

      <DeleteEquipmentDialog
        equipment={deletingEquipment}
        open={!!deletingEquipment}
        onOpenChange={(open) => !open && setDeletingEquipment(null)}
        onSuccess={loadEquipments}
      />
    </>
  );
}
