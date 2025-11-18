"use client";

import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
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
import { zodResolver } from "@hookform/resolvers/zod";
import { useSearchParams } from "next/navigation";
import { useForm } from "react-hook-form";
import { z } from "zod";

const formSchema = z.object({
  title: z.string().min(2, "O nome da reserva deve ter no mínimo 2 caracteres"),
  type: z.string(),
  description: z.string().optional(),
  repeat: z.boolean().optional(),
  startTime: z.string().min(5, "A hora de início deve ser preenchida"),
  endTime: z.string().min(5, "A hora de fim deve ser preenchida"),
});

export default function NovaReservaForm() {
  const searchParams = useSearchParams();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      title: "",
      type: "",
      description: "",
      repeat: false,
      startTime: "",
      endTime: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    const data = searchParams.get("date") ?? "";
    const room = searchParams.get("room") ?? "";

    const booking = {
      title: values.title,
      type: values.type,
      description: values.description,
      repeat: values.repeat || false,
      startTime: `${data}T${values.startTime}:00`,
      endTime: `${data}T${values.endTime}:00`,
      user: "Professor",
      data,
      room,
    };

    console.log(booking);

    await createBooking(booking);
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
                </FormItem>
              )}
            />

            <div className="flex justify-center items-center">
              <FormField
                control={form.control}
                name="type"
                render={({ field }) => (
                  <FormItem className="flex w-full justify-center gap-2">
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
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <FormLabel>A reserva se repete semanalmente?</FormLabel>
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
                      <Input type="time" {...field} />
                    </FormControl>
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
                      <Input type="time" {...field} />
                    </FormControl>
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
