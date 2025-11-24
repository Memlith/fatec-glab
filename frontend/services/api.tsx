export const API_URL = process.env.NEXT_PUBLIC_BACKEND_URL;

export type User = {
  id?: number;
  name: string;
  email: string;
};

export type Laboratory = {
  id?: number;
  name: string;
  location: string;
};

export type Equipment = {
  id?: number;
  name: string;
  description: string;
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
  id?: number;
  name: string;
  version: string;
};

export type Professor = {
  id: string;
  name: string;
  email: string;
};
