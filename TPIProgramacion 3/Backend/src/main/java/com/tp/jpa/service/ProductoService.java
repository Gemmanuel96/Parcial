package com.tp.jpa.service;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.util.InputUtil;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductoService {
    private ProductoRepository productoRepo = new ProductoRepository();
    private CategoriaRepository categoriaRepo = new CategoriaRepository();

    public void crearProducto(){

        List<Categoria> listaCategorias = categoriaRepo.listarActivos();
        System.out.println("=== Crear producto  ===");

        if (listaCategorias.isEmpty()){
            System.out.println("\nPrimero debe registrar categorias antes de crear un producto.");
            Pausa();
            return;
        }


        System.out.println("\nIngrese ID de categoria: ");
        listaCategorias.forEach(p-> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Descripcion: " + p.getDescripcion()));
        Producto producto = new Producto();

        producto.setCategoria(InputUtil.leerCategoria("Categoria ID: ",categoriaRepo));

        System.out.println("Ingrese datos del producto");
        producto.setNombre(InputUtil.leerString("Nombre: "));
        producto.setDescripcion(InputUtil.leerString("Descripcion: "));
        producto.setPrecio(InputUtil.leerDoublePositivo("Precio: "));
        producto.setStock(InputUtil.leerEnteroPositivo("Stock: "));

        Producto productoGuardado = productoRepo.guardar(producto);
        System.out.println("Producto guardado exitosamente: " + productoGuardado);
        Pausa();

    }

    public void eliminarProducto(){
        List<Producto> lista = productoRepo.listarActivos();

        System.out.println("=== Eliminar producto ===");

        if (lista.isEmpty()){
            System.out.println("No existen productos registrados");
            Pausa();
            return;
        }

        System.out.println("\nLista de productos: ");
        lista.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre()));
        Long id = InputUtil.leerLong("Ingrese ID del producto: ");
        boolean eliminado = productoRepo.eliminarLogico(id);

        if (eliminado){
            System.out.println("Producto eliminado exitosamente.");
        }else{
            System.out.println("Producto no se pudo eliminar.");
        }

        Pausa();
    }

    public void actualizarProducto(){
        List<Producto> lista = productoRepo.listarActivos();
        System.out.println("=== Actualizar producto ===");

        if (lista.isEmpty()) {
            System.out.println("No existen productos registrados");
            Pausa();
            return;
        }

        System.out.println("\nLista de productos: ");
        lista.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Descripcion: " + p.getDescripcion() + " | Precio: " + p.getPrecio()));
        Long id = InputUtil.leerLong("Ingrese ID del producto: ");
        Optional<Producto> p = productoRepo.buscarPorId(id);

        if (p.isEmpty()){
            System.out.println("Producto no se pudo encontrar.");
            Pausa();
            return;
        }

        Producto producto = p.get();
        producto.setNombre(InputUtil.leerStringOption("Nombre: ", producto.getNombre()));
        producto.setDescripcion(InputUtil.leerStringOption("Descripcion: ", producto.getDescripcion()));
        producto.setPrecio(InputUtil.leerDoubleOption("Precio: ",producto.getPrecio()));
        producto.setStock(InputUtil.leerEnteroOption("Stock: ",producto.getStock()));
        Producto productoGuardado = productoRepo.guardar(producto);

        System.out.println("Producto guardado exitosamente: " + productoGuardado);

    }

    public void listaActivos() {
        List<Producto> lista = productoRepo.listarActivos();
        System.out.println("=== Lista de productos  ===");

        if (lista.isEmpty()) {
            System.out.println("No existen productos registrados");
            Pausa();
            return;
        }

        lista.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Descripcion: " + p.getDescripcion() + " | Precio: " + p.getPrecio()));

    }

    public void Pausa(){
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }
}
