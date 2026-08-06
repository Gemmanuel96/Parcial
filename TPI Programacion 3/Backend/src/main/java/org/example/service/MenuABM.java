package org.example.service;

import org.example.utils.JPAUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuABM {
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService();
    private final Scanner input = new Scanner(System.in);


    public void MenuABM() {
        int opcion = -1;

        do {
            System.out.println("""
                    \n_________________________
                    ===== MENU ABM ======
                    1) Gestion de Productos
                    2) Gestion de Categorias
                    3) Reportes
                    0) Salir
                    """);

            try {
                System.out.print("Opcion: ");
                opcion = Integer.parseInt(input.nextLine());
                switch (opcion) {
                    case 1 -> productoMenuABM();
                    case 2 -> categoriaMenuABM();
                    case 3 -> reportes();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opcion invalida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar una opcion");
            }

        } while (opcion != 0);

    }


    public void categoriaMenuABM() {
        int opcion = -1;
        do {
            System.out.println("""
                    \n____________________________
                    ===== MENU CATEGORIA ABM =====
                    1) Agregar Categoria
                    2) Eliminar Categoria
                    3) Modificar Categoria
                    4) Listar Categorias
                    0) Volver al Menu Principal
                    """);

            try {
                System.out.print("Opcion: ");
                opcion = Integer.parseInt(input.nextLine());

                switch (opcion) {
                    case 1 -> categoriaService.alta();
                    case 2 -> categoriaService.eliminar();
                    case 3 -> categoriaService.modificar();
                    case 4 -> categoriaService.listarActivos();
                    case 0 -> salir();
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero");
            }

        } while (opcion != 0

        );

    }

    public void productoMenuABM() {
        int opcion = -1;

        do {
            System.out.println("""
                    \n______________________________
                    ===== MENU PRODUCTOS ABM =====
                    1) Agregar Producto
                    2) Eliminar Producto
                    3) Modificar Producto
                    4) Listar Producto
                    0) Volver al Menu Principal
                    """);

            try {
                System.out.print("Opcion: ");
                opcion = Integer.parseInt(input.nextLine());
                switch (opcion) {
                    case 1 -> productoService.agregar();
                    case 2 -> productoService.eliminar();
                    case 3 -> productoService.modificar();
                    case 4 -> productoService.listarActivos();
                    case 0 -> System.out.println("\nSaliendo...");
                    default -> System.out.println("\nOpcion invalida.");
                }


            } catch (NumberFormatException e) {
                System.out.println("\nError: Debe ingresar un numero entero");
            }

        } while (opcion != 0);
    }

    public void reportes() {
        System.out.println("\n===== Reportes =====");
        productoService.buscarPorCategoria();
    }

    public void salir(){
        JPAUtil.close();
    }
}
