export const API_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export type User = {
  id?: string;
  name: string;
  email: string;
  role: string;
};

export type Classroom = {
  id?: string;
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
  startTime: string;
  endTime: string;
  professorId: string;
  type: string;
  title: string;
  description: string;
  roomId: string;
  repeat: boolean;
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
