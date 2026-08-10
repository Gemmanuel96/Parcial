package org.example.service;

import org.example.model.Categoria;
import org.example.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CategoriaService {
    private CategoriaRepository categoriaRepository = new CategoriaRepository();
    private Scanner input = new Scanner(System.in);


    public void alta() {

        System.out.print("Digite el nombre del categoria: ");
        String nombre = input.nextLine();

        System.out.print("Digite el descripcion del categoria: ");
        String descripcion = input.nextLine();

        Categoria categoria = new Categoria(nombre, descripcion);

        categoriaRepository.(categoria);
        System.out.println("\n==== ENTER para continuar ====");
        input.nextLine();
    }

    public void modificar() {
        List <Categoria> categoriaList = categoriaRepository.listarActivos();

        if (!categoriaList.isEmpty()) {
            System.out.print("===== Modificar Categoria ======");
            System.out.println("\nLista de categorias:");
            categoriaList.forEach(c -> System.out.println(c.getId() + ") "+ c.getNombre()));

            System.out.println("Ingrese ID del categoria: ");
            Long IdCategoria = Long.parseLong(input.nextLine());

            Optional<Categoria> c = categoriaRepository.buscarPorId(IdCategoria);

            if (c.isPresent()){
                Categoria categoria = c.get();

                System.out.println("Categoria: " + categoria.getId() + " " + categoria.getNombre());
                System.out.println("\nIngrese nombre (enter para mantener)");
                String nombre = input.nextLine();
                if(!nombre.isBlank()) categoria.setNombre(nombre);

                System.out.println("\nIngrese descripcion (enter para mantener)");
                String descripcion = input.nextLine();
                if(!descripcion.isBlank()) categoria.setDescripcion(descripcion);

                categoriaRepository.guardar(categoria);
                System.out.println("Categoria guardada correctamente");
                System.out.println("\n==== ENTER para continuar ====");
                input.nextLine();
            }else{
                System.out.println("\nLa categoria no existe");
                System.out.println("\n==== ENTER para continuar ====");
                input.nextLine();
            }

        }else{
            System.out.println("\nNo existen categorias");
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();
        }

    }

    public void eliminar() {
        System.out.println("\n===== Eliminar Categoria =====");
        System.out.println("\nIngrese el id del categoria: ");
        Long id = input.nextLong();

        boolean v = categoriaRepository.eliminarLogico(id);

        if (v){
            System.out.println("\nCategoria eliminada correctamente");
            System.out.println("==== ENTER para continuar ====");
            input.nextLine();
        }else{
            System.out.println("\nCategoria no existe");
            System.out.println("\n==== ENTER para continuar ====");
            input.nextLine();
        }
    }

    public void listarActivos() {
        List<Categoria> lista = categoriaRepository.listarActivos();

        if (lista.isEmpty()) {
        input.nextLine();
    }
}
