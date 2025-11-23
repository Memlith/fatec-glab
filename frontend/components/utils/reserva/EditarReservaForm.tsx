"use client";

import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Booking } from "@/services/api";
import { deleteBooking, updateBooking } from "@/services/bookingsService";
import { zodResolver } from "@hookform/resolvers/zod";
import { Check, Trash } from "lucide-react";
import { useSearchParams } from "next/navigation";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";
import roomsData from "./mapa/rooms.json";

const HH_MM_REGEX = /^([01]\d|2[0-3]):([0-5]\d)$/;

const formSchema = z.object({
  title: z.string().min(2, "O nome da reserva deve ter no mínimo 2 caracteres"),
  type: z.string(),
  roomId: z.string().min(1, "O campo sala é obrigatório"),
  description: z.string().optional(),
  repeat: z.boolean().optional(),
  startTime: z
    .string()
    .regex(HH_MM_REGEX, "Formato de hora inválido")
    .min(5, "A hora de início deve ser preenchida"),
  endTime: z
    .string()
    .regex(HH_MM_REGEX, "Formato de hora inválido")
    .min(5, "A hora de término deve ser preenchida"),
});

interface EditarReservaFormProps {
  booking: Booking;
}

export default function EditarReservaForm({ booking }: EditarReservaFormProps) {
  const searchParams = useSearchParams();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      title: booking.title,
      type: booking.type,
      roomId: booking.roomId,
      description: booking.description,
      repeat: booking.repeat,
      startTime: booking.startTime.split("T")[1].slice(0, 5),
      endTime: booking.endTime.split("T")[1].slice(0, 5),
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    const data = searchParams.get("date") ?? "";

    try {
      const newBooking = {
        startTime: `${data}T${values.startTime}:00`,
        endTime: `${data}T${values.endTime}:00`,
        type: values.type,
        title: values.title,
        description: values.description,
        repeat: values.repeat || false,
        professorId: "Professor",
        roomId: values.roomId,
      };

      const response = await updateBooking(booking.id, newBooking);

      if (response.id) {
        toast.success("Reserva alterada com sucesso!");
        return;
      }
    } catch (error) {
      console.error(error);
      toast.error("Erro ao alterar reserva, tente novamente mais tarde.");
    } finally {
      window.location.reload();
    }
  }

  return (
    <div className="pt-4">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="h-full flex flex-col justify-between"
        >
          <div className="flex flex-col gap-4">
            <FormField
              control={form.control}
              name="title"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Título</FormLabel>
                  <FormControl>
                    <Input placeholder="Título da reserva" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="description"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    Descrição{" "}
                    <span className="text-muted-foreground text-xs">
                      (Opcional)
                    </span>
                  </FormLabel>
                  <FormControl>
                    <Input placeholder="Descrição da reserva" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="repeat"
              render={({ field }) => (
                <FormItem className="flex w-full justify-center gap-2">
                  <FormControl>
                    <Checkbox
                      id="recorrencia"
                      checked={field.value}
                      onCheckedChange={field.onChange}
                    />
                  </FormControl>
                  <FormLabel>A reserva se repete semanalmente?</FormLabel>
                  <FormMessage />
                </FormItem>
              )}
            />

            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="type"
                render={({ field }) => (
                  <FormItem className="flex flex-col w-full justify-center gap-2">
                    <FormLabel>Curso/Tipo da reserva</FormLabel>
                    <FormControl>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <SelectTrigger className="w-[180px]">
                          <SelectValue placeholder="Curso" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectGroup>
                            <SelectLabel>Agendamento</SelectLabel>
                            <SelectItem value="agendamento">
                              Agendamento
                            </SelectItem>
                          </SelectGroup>
                          <SelectGroup>
                            <SelectLabel>Cursos</SelectLabel>
                            <SelectItem value="DSM">DSM</SelectItem>
                            <SelectItem value="COMEX">
                              Comércio Exterior
                            </SelectItem>
                            <SelectItem value="REDES">Redes</SelectItem>
                            <SelectItem value="ADS">ADS</SelectItem>
                            <SelectItem value="GESTAO-EMP-V">
                              Gestão Empresarial Vespertino
                            </SelectItem>
                            <SelectItem value="GESTAO-EMP-N">
                              Gestão Empresarial Noturno
                            </SelectItem>
                            <SelectItem value="GESTAO-SERVICOS">
                              Gestão de Serviços
                            </SelectItem>
                            <SelectItem value="LOG-AERO">
                              Logística Aeroportuária
                            </SelectItem>
                          </SelectGroup>
                        </SelectContent>
                      </Select>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="roomId"
                render={({ field }) => (
                  <FormItem className="flex flex-col w-full justify-center gap-2">
                    <FormLabel>Sala/Laboratório</FormLabel>
                    <FormControl>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <SelectTrigger className="w-full">
                          <SelectValue placeholder="Selecione a sala" />
                        </SelectTrigger>
                        <SelectContent>
                          {Object.keys(roomsData).map((buildingKey) => (
                            <div key={buildingKey}>
                              {Object.entries(
                                roomsData[buildingKey as keyof typeof roomsData]
                              ).map(([floorKey, rooms]) => (
                                <SelectGroup key={`${buildingKey}-${floorKey}`}>
                                  <SelectLabel>
                                    Prédio {buildingKey} -{" "}
                                    {floorKey.charAt(0).toUpperCase() +
                                      floorKey.slice(1)}
                                  </SelectLabel>
                                  {rooms.map((room) => (
                                    <SelectItem key={room.id} value={room.id}>
                                      {room.label}
                                    </SelectItem>
                                  ))}
                                </SelectGroup>
                              ))}
                            </div>
                          ))}
                        </SelectContent>
                      </Select>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>

            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="startTime"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Horário de início</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormDescription>Digite no formato HH:MM</FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="endTime"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Horário de Término</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormDescription>Digite no formato HH:MM</FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
          </div>

          <div className="w-full grid grid-cols-2 gap-4 mt-4">
            <Button
              variant="destructive"
              onClick={() => deleteBooking(booking.id)}
            >
              <Trash className="mr-2 h-4 w-4" />
              Deletar Reserva
            </Button>

            <Button type="submit">
              <Check className="mr-2 h-4 w-4" />
              Salvar Alterações
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
}
