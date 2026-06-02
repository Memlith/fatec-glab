"use client";

import type { ColumnDef } from "@tanstack/react-table";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { MoreHorizontal, Pencil, Trash2 } from "lucide-react";
import { Software } from "@/services/api";

export type SoftwareActionsProps = {
  onEdit: (software: Software) => void;
  onDelete: (software: Software) => void;
};

export const createColumns = (
  actions: SoftwareActionsProps
): ColumnDef<Software>[] => [
  {
    accessorKey: "name",
    header: "Nome",
  },
  {
    id: "actions",
    header: "Ações",
    cell: ({ row }) => {
      const software = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Abrir menu</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Ações</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem onClick={() => actions.onEdit(software)}>
              <Pencil className="mr-2 h-4 w-4" />
              Editar
            </DropdownMenuItem>
            <DropdownMenuItem
              onClick={() => actions.onDelete(software)}
              className="text-destructive focus:text-destructive"
            >
              <Trash2 className="mr-2 h-4 w-4" />
              Deletar
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  },
];
