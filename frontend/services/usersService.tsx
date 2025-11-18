import { API_URL, User } from "./api";

export async function fetchUsers(
  setUsers: React.Dispatch<React.SetStateAction<User[]>>
) {
  try {
    const response = await fetch(`${API_URL}/users`);
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    const data: User[] = await response.json();
    setUsers(data);
  } catch (error) {
    console.error("Failed to fetch users:", error);
  }
}

export async function fetchUserById(id: number) {
  try {
    const response = await fetch(`${API_URL}/users/${id}`);
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch user:", error);
  }
}

export async function createUser(user: User) {
  try {
    const response = await fetch(`${API_URL}/users`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });
    return await response.json();
  } catch (error) {
    console.error("Failed to create user:", error);
  }
}

export async function updateUser(id: number, user: User) {
  try {
    await fetch(`${API_URL}/users/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });
  } catch (error) {
    console.error("Failed to update user:", error);
  }
}

export async function deleteUser(id: number) {
  try {
    await fetch(`${API_URL}/users/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete user:", error);
  }
}
