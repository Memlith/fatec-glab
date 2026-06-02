export const API_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export type User = {
  id?: number;
  name: string;
  email: string;
};

export type Classroom = {
  id?: number;
  name: string;
  capacity: string;
  equipmentsId: string[];
  softwaresId: string[];
};

export type Equipment = {
  id?: string;
  name: string;
};

export type Booking = {
  id: string;
  createdAt: string | null;
  startTime: string;
  endTime: string;
  repeat: boolean;
  type: string;
  title: string;
  description: string;
  professorId: string;
  roomId: string;
};

export type Software = {
  id?: string;
  name: string;
};

export type Professor = {
  id: string;
  name: string;
  email: string;
};
