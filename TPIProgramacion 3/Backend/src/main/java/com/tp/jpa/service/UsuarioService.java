package com.tp.jpa.service;

import com.tp.jpa.model.Usuario;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.InputUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsuarioService {
    private UsuarioRepository usuarioRepo = new UsuarioRepository();
    private Scanner input = new Scanner(System.in);

    public void crearUsuario() {
        System.out.println("=== Crear Usuario ===");

        System.out.println("\nIngrese los dstos de usuario: ");

        Usuario usuario = new Usuario();

        usuario.setNombre(InputUtil.leerString("Nombre: "));
        usuario.setApellido(InputUtil.leerString("Apellido: "));
        usuario.setCelular(InputUtil.leerString("Celular: "));
        usuario.setMail(InputUtil.leerString("Mail: "));
        usuario.setContraseña("Contraseña: ");
        Rol rol = InputUtil.leerRol("Seleccione Rol: ");
        usuario.setRol(rol);

        Usuario userGuardado = usuarioRepo.guardar(usuario);
        System.out.println("Usuario creado: " + userGuardado);
        Pausa();
    }

    public void eliminaUsuario() {
        List<Usuario> listaUsuarios = usuarioRepo.listarActivos();
        System.out.println("=== Eliminar Usuario ===");

        if (listaUsuarios.isEmpty()) {
            System.out.println("\nNo hay usuarios registrados.");
            Pausa();
            return;
        }

        System.out.println("\nLista de usuarios:");
        listaUsuarios.forEach(u -> System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombre() + " | Apellido: " + u.getApellido() + " | Email: " + u.getMail()));

        Long id = InputUtil.leerLong("\nIngrese ID del usuario: ");

        Optional <Usuario> optUser = usuarioRepo.buscarPorId(id);

        if (optUser.isPresent()) {
            Usuario usuario = optUser.get();

            boolean eliminado = usuarioRepo.eliminarLogico(id);

            if(eliminado){
                System.out.println("Usuario eliminado correctamente: " + usuario.getNombre() + " " + usuario.getApellido() + " " + usuario.getMail());
                Pausa();
            }else{
                System.out.println("Error al eliminar usuario.");
                Pausa();
            }

        }else{
            System.out.println("No existe usuario con ese ID: " + id);
            Pausa();
        }

    }

    public void editarUsuario() {
        List<Usuario> listaUsuarios = usuarioRepo.listarActivos();
        System.out.println("=== Editar Usuario ===");

        if (listaUsuarios.isEmpty()) {
            System.out.println("\nNo hay usuarios registrados.");
            Pausa();
            return;
        }

        System.out.println("\nLista de usuarios:");
        listaUsuarios.forEach(u -> {
            System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombre() + " | Apellido: " + u.getApellido() +  " | Email: " + u.getMail());
        });



    }

    private void Pausa() {
        input.nextLine();
    }
}
