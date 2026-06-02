"use client";

import { useState, useEffect } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Trash2 } from "lucide-react";
import { Label } from "@/components/ui/label";
import { Software } from "@/services/api";
import { fetchSoftwares } from "@/services/softwaresServices";

type SoftwareEntry = {
  id: string;
};

type SoftwareFieldProps = {
  value: string[];
  onChange: (value: string[]) => void;
};

const createInitialEntries = (value: string[]): SoftwareEntry[] => {
  const selectedEntries = value.filter((id) => id !== "").map((id) => ({ id }));
  return [...selectedEntries, { id: "" }];
};

export function SoftwareField({ value, onChange }: SoftwareFieldProps) {
  const [softwares, setSoftwares] = useState<Software[]>([]);

  const [entries, setEntries] = useState<SoftwareEntry[]>(
    createInitialEntries(value)
  );

  useEffect(() => {
    const currentSelectedIds = entries
      .slice(0, -1)
      .map((e) => e.id)
      .filter((id) => id !== "");

    if (
      JSON.stringify(value.sort()) !== JSON.stringify(currentSelectedIds.sort())
    ) {
      setEntries(createInitialEntries(value));
    }
  }, [value, entries]);

  useEffect(() => {
    const loadSoftwares = async () => {
      const data = await fetchSoftwares();
      if (data) setSoftwares(data);
    };
    loadSoftwares();
  }, []);

  const updateEntries = (newEntries: SoftwareEntry[]) => {
    setEntries(newEntries);

    const selectedIds = newEntries
      .map((entry) => entry.id)
      .filter((id) => id !== "");

    onChange(selectedIds);
  };

  const handleSoftwareChange = (index: number, softwareId: string) => {
    const newEntries = [...entries];
    newEntries[index].id = softwareId;

    const isLastEntry = index === entries.length - 1;
    if (isLastEntry && softwareId !== "") {
      newEntries.push({ id: "" });
    }

    updateEntries(newEntries);
  };

  const handleRemoveEntry = (index: number) => {
    const newEntries = entries.filter((_, i) => i !== index);

    if (
      newEntries.length === 0 ||
      newEntries[newEntries.length - 1].id !== ""
    ) {
      newEntries.push({ id: "" });
    }

    updateEntries(newEntries);
  };

  const renderSelect = (entry: SoftwareEntry, index: number) => {
    return (
      <div
        key={index}
        className="w-full grid grid-cols-[1fr_40px] gap-2 items-center"
      >
        <Select
          value={entry.id}
          onValueChange={(value) => handleSoftwareChange(index, value)}
        >
          <SelectTrigger className="w-full text-ellipsis overflow-hidden whitespace-nowrap">
            <SelectValue placeholder="Select software" />
          </SelectTrigger>
          <SelectContent>
            {softwares.map((software) => (
              <SelectItem
                key={software.id}
                value={software.id || ""}
                disabled={entries.some(
                  (e, i) => i !== index && e.id === software.id
                )}
              >
                {software.name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>

        {entry.id !== "" && (
          <Button
            type="button"
            onClick={() => handleRemoveEntry(index)}
            size="icon"
            variant="destructive"
          >
            <Trash2 className="h-4 w-4" />
          </Button>
        )}
      </div>
    );
  };

  return (
    <div className="space-y-4 w-full">
      <Label>Softwares</Label>

      {entries.map(renderSelect)}
    </div>
  );
}
