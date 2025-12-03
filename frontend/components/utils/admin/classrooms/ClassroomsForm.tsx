"use client";

import { useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Loader2 } from "lucide-react";
import { Classroom } from "@/services/api";
import { toast } from "sonner";
import {
  fetchClassroomById,
  updateClassroom,
} from "@/services/classroomService";
import { EquipmentField } from "./EquipmentField";
import { SoftwareField } from "./SoftwareField";

import * as roomsData from "../../reserva/mapa/rooms.json";

const formSchema = z.object({
  name: z.string().min(1, "Name is required"),
  capacity: z.string().min(1, "Capacity is required"),
  equipmentsId: z.array(z.string()),
  softwaresId: z.array(z.string()),
});

type FormData = z.infer<typeof formSchema>;

// --- Type Definitions for rooms.json to eliminate 'any' ---
type Room = {
  id: string; // The ID used in the URL parameter
  dbId?: string | null; // The database ID
  [key: string]: unknown; // Allows for other properties without explicit 'any'
};

type Floor = Room[];

type Building = {
  [floorId: string]: Floor;
};

type RoomsData = {
  [buildingId: string]: Building;
};

// Cast the imported data to the defined type for use
const typedRoomsData = roomsData as unknown as RoomsData;
// ----------------------------------------------------------

function findDbIdByRoomId(roomId: string): string | null {
  // Use the strongly typed object
  for (const building of Object.values(typedRoomsData)) {
    // building is of type Building
    for (const floor of Object.values(building)) {
      // floor is of type Floor (Room[])
      for (const room of floor) {
        // room is of type Room
        if (room.id === roomId) {
          return room.dbId ?? null;
        }
      }
    }
  }
  return null;
}

export default function ClassroomsForm() {
  const searchParams = useSearchParams();
  const room = searchParams.get("room");
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const form = useForm<FormData>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      capacity: "",
      equipmentsId: [],
      softwaresId: [],
    },
  });

  useEffect(() => {
    const loadClassroom = async () => {
      if (!room) {
        setLoading(false);
        return;
      }

      const dbId = findDbIdByRoomId(room);
      if (!dbId) {
        toast.error("Room not found in rooms.json");
        setLoading(false);
        return;
      }

      try {
        const classroom: Classroom = await fetchClassroomById(dbId);

        if (classroom) {
          form.reset({
            name: classroom.name ?? "",
            capacity: classroom.capacity ? String(classroom.capacity) : "",
            equipmentsId: Array.isArray(classroom.equipmentsId)
              ? classroom.equipmentsId
              : [],
            softwaresId: Array.isArray(classroom.softwaresId)
              ? classroom.softwaresId
              : [],
          });
        }
      } catch (error) {
        console.error("Failed to load classroom:", error);
        toast.error("Failed to load classroom data");
      } finally {
        setLoading(false);
      }
    };

    loadClassroom();
  }, [room, form]);

  const onSubmit = async (data: FormData) => {
    setSubmitting(true);
    try {
      if (!room) {
        toast.error("Invalid classroom ID");
        setSubmitting(false);
        return;
      }

      const dbId = findDbIdByRoomId(room);
      if (!dbId) {
        toast.error("Room not found in rooms.json");
        setSubmitting(false);
        return;
      }

      console.log("Submitting payload:", {
        dbId,
        name: data.name,
        capacity: data.capacity,
        equipmentsId: data.equipmentsId,
        softwaresId: data.softwaresId,
      });

      await updateClassroom(dbId, {
        name: data.name,
        // Ensure capacity is converted to the expected type for the service call, assuming the service expects a number if it came from the capacity input, but keeping it as string for safety based on the form schema
        capacity: data.capacity,
        equipmentsId: data.equipmentsId,
        softwaresId: data.softwaresId,
      });

      toast.success("Classroom updated successfully");
    } catch (error) {
      console.error("Failed to update classroom:", error);
      toast.error("Failed to update classroom");
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Editar Sala</CardTitle>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome</FormLabel>
                    <FormControl>
                      <Input placeholder="Lab 1" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="capacity"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Capacidade</FormLabel>
                    <FormControl>
                      <Input type="number" placeholder="40" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="equipmentsId"
                render={({ field }) => (
                  <FormItem>
                    <FormControl>
                      <EquipmentField
                        value={field.value}
                        onChange={field.onChange}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="softwaresId"
                render={({ field }) => (
                  <FormItem>
                    <FormControl>
                      <SoftwareField
                        value={field.value}
                        onChange={field.onChange}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <div className="flex gap-4">
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => router.push("/classrooms")}
                  disabled={submitting}
                >
                  Cancelar
                </Button>
                <Button type="submit" disabled={submitting}>
                  {submitting && (
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  )}
                  Salvar Alterações
                </Button>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
