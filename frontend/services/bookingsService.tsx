import { API_URL, Booking } from "./api";

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
    const response = await fetch(`${API_URL}/bookings`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ...booking,
        startTime: `${booking.startTime}`,
        endTime: `${booking.endTime}`,
      }),
    });
    return response.json();
  } catch (error) {
    console.error("Failed to create booking:", error);
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
    const response = await fetch(`${API_URL}/bookings/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(booking),
    });

    return response.json();
  } catch (error) {
    console.error("Failed to update booking:", error);
  }
}

export async function deleteBooking(id: string) {
  try {
    await fetch(`${API_URL}/bookings/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete booking:", error);
  }
}
