import { API_URL, Booking } from "./api";


export async function fetchBookings(setBookings : React.Dispatch<React.SetStateAction<Booking[]>>) {
    try {
      const response = await fetch(`${API_URL}/bookings`);
      const data: Booking[] = await response.json();
      setBookings(data);
    } catch (error) {
      console.error("Failed to fetch bookings:", error);
    }
  }

  async function fetchBookingById(id: number) {
    try {
      const response = await fetch(`${API_URL}/bookings/${id}`);
      return await response.json();
    } catch (error) {
      console.error("Failed to fetch booking:", error);
    }
  }

  async function fetchBookingByQuery(date: string, room: string, number: number) {
    try {
      const response = await fetch(`${API_URL}/bookings/?date=${date}&room=${room}&number=${number}`);
      return await response.json();
    } catch (error) {
      console.error("Failed to fetch booking by query:", error);
    }
  }

  async function createBooking(booking: Booking) {
    try {
      await fetch(`${API_URL}/bookings`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(booking),
      });
    } catch (error) {
      console.error("Failed to create booking:", error);
    }
  }

  async function updateBooking(id: number, booking: Booking) {
    try {
      await fetch(`${API_URL}/bookings/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(booking),
      });
    } catch (error) {
      console.error("Failed to update booking:", error);
    }
  }

  async function deleteBooking(id: number) {
    try {
      await fetch(`${API_URL}/bookings/${id}`, { method: "DELETE" });
    } catch (error) {
      console.error("Failed to delete booking:", error);
    }
  }