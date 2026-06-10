import { API_URL, Booking } from "./api";
import { auth } from "@/lib/firebase";

export async function fetchBookings(
  setBookings: React.Dispatch<React.SetStateAction<Booking[]>>
) {
  try {
    const response = await fetch(`${API_URL}/bookings`);
    const data: Booking[] = await response.json();
    setBookings(data);
  } catch (error) {
    console.error("Failed to fetch bookings:", error);
  }
}

export async function fetchBookingById(id: number) {
  try {
    const response = await fetch(`${API_URL}/bookings/${id}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch booking:", error);
  }
}

export async function fetchBookingByQuery(date: string, room: string) {
  try {
    const response = await fetch(
      `${API_URL}/bookings/search?date=${date}&roomId=${room}`
    );
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch booking by query:", error);
  }
}

export async function createBooking(booking: {
  startTime: string;
  endTime: string;
  professorId: string;
  type: string;
  title: string;
  description?: string;
  roomId: string;
  repeat: boolean;
}) {
  try {
    const user = auth?.currentUser;
    const token = user ? await user.getIdToken() : "";

    const response = await fetch(`${API_URL}/bookings`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({
        ...booking,
        startTime: `${booking.startTime}`,
        endTime: `${booking.endTime}`,
      }),
    });

    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

    const text = await response.text();
    return text ? JSON.parse(text) : null;
  } catch (error) {
    console.error("Failed to create booking:", error);
    return null;
  }
}

export async function updateBooking(
  id: string,
  booking: {
    startTime: string;
    endTime: string;
    professorId: string;
    type: string;
    title: string;
    description?: string;
    roomId: string;
    repeat: boolean;
  }
) {
  try {
    const user = auth?.currentUser;
    const token = user ? await user.getIdToken() : "";

    const response = await fetch(`${API_URL}/bookings/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(booking),
    });

    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

    const text = await response.text();
    return text ? JSON.parse(text) : null;
  } catch (error) {
    console.error("Failed to update booking:", error);
    return null;
  }
}

export async function deleteBooking(id: string) {
  try {
    const user = auth?.currentUser;
    const token = user ? await user.getIdToken() : "";

    const response = await fetch(`${API_URL}/bookings/${id}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`
      }
    });

    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return true;
  } catch (error) {
    console.error("Failed to delete booking:", error);
    return false;
  }
}
