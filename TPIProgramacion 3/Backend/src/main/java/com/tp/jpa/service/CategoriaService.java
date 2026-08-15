package com.tp.jpa.service;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.util.InputUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CategoriaService {
    private final ProductoRepository productoRepository = new ProductoRepository();
    private final CategoriaRepository categoriaRepository = new CategoriaRepository();
    private final Scanner input = new Scanner(System.in);

    public void crearCategoria() {
        Categoria categoria = new Categoria();

        System.out.println("=== CREAR CATEGORIA ===");
        categoria.setNombre(InputUtil.leerString("Nombre: "));
        categoria.setDescripcion(InputUtil.leerString("Descripcion: "));

        Categoria categoriaSave = categoriaRepository.guardar(categoria);
        System.out.println("Categoria creada correctamente: " + categoriaSave);
    }

    public void listarCategorias() {
        List<Categoria> listaCategorias = categoriaRepository.listarActivos();
        System.out.println("=== LISTA DE CATEGORIAS ===");

        if (listaCategorias.isEmpty()) {
            System.out.println("\nLista de categorias vacia.");
            Pausa();
            return;
        }
        System.out.println("\nLista de categorias activas:");
        listaCategorias.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Descripcion: " + p.getDescripcion()));
    }

    public void eliminarCategoria() {
        List<Categoria> listaCategorias = categoriaRepository.listarActivos();
        System.out.println("=== ELIMINAR CATEGORIA ===");

        if (listaCategorias.isEmpty()) {
            System.out.println("\nLista de categorias vacia.");
            Pausa();
            return;
        }
        System.out.println("\nLista de categorias activas: ");
        listaCategorias.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre()));
        Long id = InputUtil.leerLong("\nIngrese el ID de la categoria a eliminar: ");
        Optional<Categoria> cat = categoriaRepository.buscarPorId(id);

        if (cat.isPresent()) {
            Categoria catDelete = cat.get();
            catDelete.setEliminado(true);
            categoriaRepository.guardar(catDelete);
            System.out.println("\nCategoria eliminada correctamente: " + catDelete);
            Pausa();
        }else{
            System.out.println("\nCategoria no encontrada don ese ID: " + id);
            Pausa();
        }
    }

    public void editarCategoria() {
        List<Categoria> listaCategorias = categoriaRepository.listarActivos();

        System.out.println("=== EDITAR CATEGORIA ===");

        if (listaCategorias.isEmpty()) {
            System.out.println("\nLista de categorias vacia.");
            Pausa();
            return;
        }

        listaCategorias.forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre()));
        Long id = InputUtil.leerLong("\nIngrese el ID de la categoria a editar: ");
        Optional<Categoria> cat = categoriaRepository.buscarPorId(id);

        if (cat.isPresent()) {

            Categoria catEdit = cat.get();
            catEdit.setNombre(InputUtil.leerStringOption("Nombre: (ENTER para mantener) ", catEdit.getNombre()));
            catEdit.setDescripcion(InputUtil.leerStringOption("Descripcion: (ENTER para mantener) ", catEdit.getDescripcion()));
            categoriaRepository.guardar(catEdit);
            System.out.println("\nCategoria editada correctamente: " + catEdit);
            Pausa();

        }else{
            System.out.println("\nCategoria no encontrada don ese ID: " + id);
            Pausa();
        }
    }


    private void Pausa() {
        input.nextLine();
    }
}
