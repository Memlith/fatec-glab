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
import { createBooking } from "@/services/bookingsService";
import { fetchProfessors } from "@/services/professorService";
import { zodResolver } from "@hookform/resolvers/zod";
import { useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { z } from "zod";

const HH_MM_REGEX = /^([01]\d|2[0-3]):([0-5]\d)$/;

const formSchema = z.object({
  title: z.string().min(2, "O nome da reserva deve ter no mínimo 2 caracteres"),
  type: z.string(),
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
  professorId: z.string(),
});

type Professor = {
  id: string;
  name: string;
  email: string;
};

export default function NovaReservaForm() {
  const searchParams = useSearchParams();
  const [professors, setProfessors] = useState<Professor[]>([]);

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      startTime: "",
      endTime: "",
      type: "",
      title: "",
      description: "",
      professorId: "",
      repeat: false,
    },
  });

  useEffect(() => {
    const loadProfessors = async () => {
      try {
        const data = await fetchProfessors();
        setProfessors(data);
      } catch (error) {
        console.error("Failed to fetch professors:", error);
        toast.error("Erro ao carregar professores");
      }
    };

    loadProfessors();
  }, []);

  async function onSubmit(values: z.infer<typeof formSchema>) {
    const data = searchParams.get("date") ?? "";
    const room = searchParams.get("room") ?? "";

    const booking = {
      startTime: `${data}T${values.startTime}:00`,
      endTime: `${data}T${values.endTime}:00`,
      professorId: values.professorId,
      type: values.type,
      title: values.title,
      description: values.description,
      roomId: room,
      repeat: values.repeat || false,
    };

    const response = await createBooking(booking);

    console.log(response);

    if (response.id) {
      toast.success("Reserva criada com sucesso!");
      return;
    }
    toast.error("Erro ao criar reserva, tente novamente mais tarde.");
  }

  return (
    <div className="h-full border rounded-md shadow-sm">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="h-full flex flex-col justify-between p-4"
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

            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="type"
                render={({ field }) => (
                  <FormItem className="flex max-2xl:flex-col items-center">
                    <FormLabel>Curso/Tipo da reserva</FormLabel>
                    <FormControl>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <SelectTrigger className="w-full">
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
                name="professorId"
                render={({ field }) => (
                  <FormItem className="flex max-2xl:flex-col items-center">
                    <FormLabel>Professor</FormLabel>
                    <FormControl>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <SelectTrigger className="w-full">
                          <SelectValue placeholder="Selecione o professor" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectGroup>
                            <SelectLabel>Professores</SelectLabel>
                            {professors.map((professor) => (
                              <SelectItem
                                key={professor.id}
                                value={professor.id}
                              >
                                {professor.name}
                              </SelectItem>
                            ))}
                          </SelectGroup>
                        </SelectContent>
                      </Select>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>

            <div className="flex justify-center">
              <FormField
                control={form.control}
                name="repeat"
                render={({ field }) => (
                  <FormItem className="flex items-center gap-2 space-y-0">
                    <FormControl>
                      <Checkbox
                        id="recorrencia"
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <FormLabel className="!mt-0">
                      A reserva se repete semanalmente?
                    </FormLabel>
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

          <Button type="submit">Criar reserva</Button>
        </form>
      </Form>
    </div>
  );
}
