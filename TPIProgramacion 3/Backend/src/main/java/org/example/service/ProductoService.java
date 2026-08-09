package org.example.service;

import org.example.model.Categoria;
import org.example.model.Producto;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.example.utils.InputUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class ProductoService {

    private ProductoRepository productoRepository = new ProductoRepository();
    private CategoriaRepository categoriaRepository = new CategoriaRepository();
    Scanner input = new Scanner(System.in);


    public void agregar() {
        List<Categoria> categorias = categoriaRepository.listarActivos();

        System.out.println("==== Agregar Producto ====");

        Producto producto = new Producto();

        System.out.printf("\nIngrese los datos del producto: ");
        producto.setNombre(InputUtil.leerString("Nombre: "));
        producto.setPrecio(InputUtil.leerDoublePositivo("Precio: "));
        producto.setStock(InputUtil.leerEnteroPositivo("Stock: "));

        //Recorremos la lista de categoria
        System.out.println("Ingrese categoria del producto (SOLO ID):");
        categorias.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c.getNombre()));

        Long idCategoria = InputUtil.leerLong("ID de categoria: ");
        Categoria categoria = categorias.stream().filter(c -> c.getId().equals(idCategoria)).findFirst().orElse(null);

        if (categoria == null) {
            System.out.println("No se encontro el categoria con ese ID: " + idCategoria);
            return;
        }

        producto.setCategoria(categoria);

        productoRepository.guardar(producto);

        System.out.println("\nSe ha guardado el producto: " + producto);
        System.out.println("Presione ENTER para continuar ...");
        Pausa();

    }

    public void eliminar() {
        List<Producto> productos = productoRepository.listarActivos();
        System.out.println("==== Eliminar Producto ====");

        if (productos.isEmpty()) {
            System.out.println("\nNo hay productos registrados.");
            Pausa();
            return;
        }

        System.out.println("\nProductos: ");
        productos.forEach(p -> System.out.println("ID: " + p.getId() + " | " + p.getNombre()));

        Long idProducto = InputUtil.leerLong("ID de producto: ");
        boolean eliminado = productoRepository.eliminarLogico(idProducto);

        if (eliminado) {
            System.out.println("Producto eliminado correctamente, ENTER para continuar ...");
            Pausa();
        }else{
            System.out.println("No existe un producto con ese ID: " + idProducto);
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

            productoRepository.guardar(producto);
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

    public void Pausa(){
        input.nextLine();
    }
}