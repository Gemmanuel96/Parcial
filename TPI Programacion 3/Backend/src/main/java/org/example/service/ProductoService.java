package org.example.service;

import org.example.model.Categoria;
import org.example.model.Producto;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class ProductoService {

    private ProductoRepository productoRepository = new ProductoRepository();
    private CategoriaRepository categoriaRepository = new CategoriaRepository();
    Scanner input = new Scanner(System.in);


    public void agregar() {
        List<Categoria> categorias = categoriaRepository.listarActivos();

        Producto producto = new Producto();

        System.out.println("Ingrese nombre del producto:");
        producto.setNombre(input.nextLine());

        System.out.println("Ingrese el precio del producto:");
        producto.setPrecio(Double.parseDouble(input.nextLine()));

        System.out.println("Ingrese stock del producto:");
        producto.setStock(Integer.parseInt(input.nextLine()));

        System.out.println("Ingrese categoria del producto (SOLO ID):");
        categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c.getNombre()));
        Long idCategoria = Long.parseLong(input.nextLine());

        Categoria categoria = categorias.stream().filter(c -> c.getId().equals(idCategoria)).findFirst().orElse(null);
        producto.setCategoria(categoria);

        productoRepository.guardar(producto);
        System.out.println("\nProducto agregado correctamente");
        System.out.println("\n==== ENTER para continuar ====");
        input.nextLine();

    }

    public void eliminar() {
        System.out.println("Ingrese el id del producto:");
        Long idProducto = Long.parseLong(input.nextLine());
        boolean t = productoRepository.eliminarLogico(idProducto);

        if (t) {
            System.out.println("\nEl producto eliminado");
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();
        } else {
            System.out.println("\nNo se pudo eliminar el producto");
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();
        }
    }

    public void modificar() {

        List<Producto> productoList = productoRepository.listarActivos();
        System.out.println("\n===== Modicar Producto ======");
        productoList.forEach(producto -> System.out.println(producto.getId() + ") " + producto.getNombre()));

        System.out.print("\nIngrese ID del producto: ");
        Long idProducto = Long.parseLong(input.nextLine());
        Optional<Producto> p = productoRepository.buscarPorId(idProducto);

        if (p.isPresent()) {
            Producto producto = p.get();

            System.out.println("Actual: " + producto.getId() + ") " + producto.getNombre());

            System.out.print("Ingrese el nombre (enter para mantener el nombre): ");
            String nombreProducto = input.nextLine();
            if (!nombreProducto.isBlank()) producto.setNombre(nombreProducto);

            System.out.print("Ingrese descripcion (enter para mantener descripcion): ");
            String descripcionProducto = input.nextLine();
            if (!descripcionProducto.isBlank()) producto.setDescripcion(descripcionProducto);

            System.out.print("Ingrese stock (enter para mantener stock): ");
            String stockProducto = input.nextLine();
            if (!stockProducto.isBlank()) producto.setStock(Integer.parseInt(stockProducto));

            System.out.println("Ingrese precio (enter para mantener precio): ");
            String precioProducto = input.nextLine();
            if (!precioProducto.isBlank()) producto.setPrecio(Double.parseDouble(precioProducto));

            boolean eliminado = false;
            producto.setEliminado(eliminado);

            productoRepository.modificar(producto);
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();

        } else {
            System.out.println("\nProducto con ese ID no existe.");
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();
        }

    }

    public void listarActivos() {
        List<Producto> productos = productoRepository.listarActivos();
        if (productos.isEmpty()) {
            System.out.println("\nNo existen productos");
            System.out.println("\n==== ENTER para volver al menu ====");
            input.nextLine();

        } else {
            System.out.println("\n====== Listado de Productos ======");
            productos.forEach(p -> System.out.println("ID: " + p.getId() + "  |  Nombre: " + p.getNombre() + "  |  Precio: " + p.getPrecio() + "  |  Categoria: " + p.getCategoria().getNombre()));
            System.out.println("\n==== ENTER para volver el menu ====");
            input.nextLine();
        }

    }

    public void buscarPorCategoria() {
        List<Categoria> categorias = categoriaRepository.listarActivos();
        List<Producto> productos = productoRepository.listarActivos();

        System.out.println("\n======= Buscar por Categoria =======");
        System.out.println("\nCategorias: ");
        categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c.getNombre()));

        System.out.println(" ");
        System.out.print("Ingrese ID: ");
        Long idCategoria = Long.parseLong(input.nextLine());

        // Filtrar productos por categoría
        List<Producto> productosFiltrados = productos.stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(idCategoria))
                .toList();

        if (productosFiltrados.isEmpty()) {
            System.out.println("\nNo existen productos para esta categoría.");
            System.out.println("\nPresione ENTER para volver el menú principal para continuar.");
            input.nextLine();

        } else {
            System.out.println("\n====== Listado de Productos por categoría ======");
            productosFiltrados.forEach(p ->
                    System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + "  |  Categoria: " + p.getCategoria().getNombre())
            );
            System.out.println("\nPresione ENTER para volver al menú principal");
            input.nextLine();
        }
    }

}
