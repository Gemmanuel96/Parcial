import type { Rol } from "./Rol";

export interface IUser {
  nombreCompleto: string;
  email: string;
  contrasenia: string;
  rol: Rol;
}
