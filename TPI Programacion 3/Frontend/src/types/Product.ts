import type { ICategorias } from "./Categorias";

export type Product = {
    id:number;
    eliminado: boolean;
    createdAt: string;
    nombre:string;
    precio:number;
    descripcion:string;
    stock:number;
    imagen:string;
    disponible:boolean;
    categorias: ICategorias[];
}