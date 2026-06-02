"use client";

import { useState, useEffect } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Plus, Trash2 } from "lucide-react";
import { Label } from "@/components/ui/label";
import { Equipment } from "@/services/api";
import { fetchEquipments } from "@/services/equipmentsService";

type EquipmentEntry = {
  id: string;
  quantity: string;
};

type EquipmentFieldProps = {
  value: string[];
  onChange: (value: string[]) => void;
};

export function EquipmentField({ value, onChange }: EquipmentFieldProps) {
  const [equipments, setEquipments] = useState<Equipment[]>([]);
  const [entries, setEntries] = useState<EquipmentEntry[]>([]);

  useEffect(() => {
    const loadEquipments = async () => {
      const data = await fetchEquipments();
      if (data) {
        setEquipments(data);
      }
    };
    loadEquipments();
  }, []);

  useEffect(() => {
    if (value && value.length > 0) {
      const parsed = value
        .map((item) => {
          if (!item) return null;
          try {
            const cleaned = item.replace(/^['"]|['"]$/g, "");
            const obj = JSON.parse(cleaned.replace(/'/g, '"'));
            return obj as EquipmentEntry;
          } catch {
            return null;
          }
        })
        .filter(Boolean) as EquipmentEntry[];

      setEntries(parsed.length > 0 ? parsed : [{ id: "", quantity: "" }]);
    } else {
      setEntries([{ id: "", quantity: "" }]);
    }
  }, [value]);

  const updateEntries = (newEntries: EquipmentEntry[]) => {
    setEntries(newEntries);
    const stringified = newEntries
      .filter((entry) => entry.id && entry.quantity)
      .map((entry) =>
        JSON.stringify({ id: entry.id, quantity: entry.quantity })
      );
    onChange(stringified);
  };

  const handleAddEntry = () => {
    updateEntries([...entries, { id: "", quantity: "" }]);
  };

  const handleRemoveEntry = (index: number) => {
    const newEntries = entries.filter((_, i) => i !== index);
    updateEntries(
      newEntries.length > 0 ? newEntries : [{ id: "", quantity: "" }]
    );
  };

  const handleEquipmentChange = (index: number, equipmentId: string) => {
    const newEntries = [...entries];
    newEntries[index].id = equipmentId;
    updateEntries(newEntries);
  };

  const handleQuantityChange = (index: number, quantity: string) => {
    const newEntries = [...entries];
    newEntries[index].quantity = quantity;
    updateEntries(newEntries);
  };

  return (
    <div className="space-y-4 w-full">
      <Label>Equipamentos</Label>
      {entries.map((entry, index) => (
        <div
          key={index}
          className="w-full grid grid-cols-[1fr_8rem_40px] gap-2 items-center"
        >
          <Select
            value={entry.id}
            onValueChange={(value) => handleEquipmentChange(index, value)}
          >
            <SelectTrigger className="w-full text-ellipsis overflow-hidden whitespace-nowrap">
              <SelectValue placeholder="Select equipment" />
            </SelectTrigger>
            <SelectContent>
              {equipments.map((equipment) => (
                <SelectItem key={equipment.id} value={equipment.id || ""}>
                  {equipment.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Input
            type="number"
            placeholder="Quantidade"
            value={entry.quantity}
            onChange={(e) => handleQuantityChange(index, e.target.value)}
            min="1"
          />

          {index === entries.length - 1 && entry.id && entry.quantity ? (
            <Button
              type="button"
              onClick={handleAddEntry}
              size="icon"
              variant="outline"
            >
              <Plus className="h-4 w-4" />
            </Button>
          ) : (
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
      ))}
    </div>
  );
}
