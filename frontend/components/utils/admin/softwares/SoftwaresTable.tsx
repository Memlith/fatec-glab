"use client";

import * as React from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import type { Software } from "@/services/api";
import { createColumns } from "./CreateColumns";
import { DataTable } from "./DataTable";
import { CreateSoftwareDialog } from "./CreateSoftwareDialog";
import { DeleteSoftwareDialog } from "./DeleteSoftwareDialog";
import { fetchSoftwaresSet } from "@/services/softwaresServices";
import { EditSoftwareDialog } from "./EditSoftwareDialog";

export function SoftwaresTable() {
  const [softwares, setSoftwares] = React.useState<Software[]>([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const [editingSoftware, setEditingSoftware] = React.useState<Software | null>(
    null
  );
  const [deletingSoftware, setDeletingSoftware] =
    React.useState<Software | null>(null);
  const [isCreating, setIsCreating] = React.useState(false);

  const loadSoftwares = React.useCallback(async () => {
    setIsLoading(true);
    await fetchSoftwaresSet(setSoftwares);
    setIsLoading(false);
  }, []);

  React.useEffect(() => {
    loadSoftwares();
  }, [loadSoftwares]);

  const columns = React.useMemo(
    () =>
      createColumns({
        onEdit: (software) => setEditingSoftware(software),
        onDelete: (software) => setDeletingSoftware(software),
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
          Novo Software
        </Button>
      </div>

      <DataTable columns={columns} data={softwares} />

      <CreateSoftwareDialog
        open={isCreating}
        onOpenChange={setIsCreating}
        onSuccess={loadSoftwares}
      />

      <EditSoftwareDialog
        software={editingSoftware}
        open={!!editingSoftware}
        onOpenChange={(open) => !open && setEditingSoftware(null)}
        onSuccess={loadSoftwares}
      />

      <DeleteSoftwareDialog
        software={deletingSoftware}
        open={!!deletingSoftware}
        onOpenChange={(open) => !open && setDeletingSoftware(null)}
        onSuccess={loadSoftwares}
      />
    </>
  );
}
