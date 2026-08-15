package com.tp.jpa;

import com.tp.jpa.service.CategoriaService;
import com.tp.jpa.service.PedidoService;
import com.tp.jpa.service.ProductoService;
import com.tp.jpa.service.UsuarioService;
import com.tp.jpa.util.JPAUtil;
import org.h2.util.json.JsonConstructorUtils;

import java.util.Scanner;

/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    private final static Scanner input = new Scanner(System.in);
    private final static ProductoService productoService = new ProductoService();
    private final static CategoriaService categoriaService = new CategoriaService();
    private final static PedidoService pedidoService = new PedidoService();
    private final static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = input.nextLine().trim();
            switch (op) {
                case "1":
                    menuCategorias();
                    break;
                case "2":
                    menuProductos();
                    break;
                case "3":
                    menuUsuarios();
                    break;
                case "4":
                    menuPedidos();
                    break;
                case "5":
                    menuReportes();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────

    private static void menuCategorias() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== GESTION DE CATEGORIAS =====");
            System.out.println("1. Crear Categoria");
            System.out.println("2. Actualizar Categoria");
            System.out.println("3. Eliminar Categoria");
            System.out.println("4. Lista de Categorias");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = input.nextLine().trim();
            switch (op) {
                case "1":
                    categoriaService.crearCategoria();
                    break;
                case "2":
                    categoriaService.editarCategoria();
                    break;
                case "3":
                    categoriaService.eliminarCategoria();
                    break;
                case "4":
                    categoriaService.listarCategorias();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }


    }

    private static void menuProductos() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Crear Producto");
            System.out.println("2. Actualizar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Lista de Productos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = input.nextLine().trim();
            switch (op) {
                case "1":
                    productoService.crearProducto();
                    break;
                case "2":
                    productoService.editarProducto();
                    break;
                case "3":
                    productoService.eliminarProducto();
                    break;
                case "4":
                    productoService.listaActivos();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void menuUsuarios() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== GESTION DE USUARIOS =====");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Actualizar Usuario");
            System.out.println("3. Eliminar Usuario");
            System.out.println("5. Buscar Usuario por mail");
            System.out.println("4. Lista de Usuarios");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = input.nextLine().trim();
            switch (op) {
                case "1":
                    usuarioService.crearUsuario();
                    break;
                case "2":
                    usuarioService.editarUsuario();
                    break;
                case "3":
                    usuarioService.eliminaUsuario();
                    break;
                case "4":
                    usuarioService.buscarPorMail();
                    break;
                case "5":
                    usuarioService.listarUsuarios();
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

    }

    private static void menuPedidos() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Crear Producto");
            System.out.println("2. Cambiar estado de pedido");
            System.out.println("3. Eliminar pedido");
            System.out.println("4. Lista de Productos");
            System.out.println("5. Listar pedidos por usuario");
            System.out.println("6. Listar pedidos por estado");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = input.nextLine().trim();
            switch (op) {
                case "1":
                    pedidoService.crearPedido();
                    break;
                case "2":
                    pedidoService.cambiarEstado();
                    break;
                case "3":
                    pedidoService.eliminarPedido();
                    break;
                case "4":
                    pedidoService.listarActivos();
                    break;
                case "5":
                    pedidoService.listarPedidosPorUsuario();
                    break;
                case "6":
                    pedidoService.listarPedidosPorEstado();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void menuReportes() {
        // TODO: Implementar submenú de Reportes.
        // Opciones: 1-Productos por categoría  2-Pedidos por usuario
        //           3-Pedidos por estado  4-Total facturado  0-Volver
        System.out.println("[Reportes] → TODO: implementar");
    }

    }
