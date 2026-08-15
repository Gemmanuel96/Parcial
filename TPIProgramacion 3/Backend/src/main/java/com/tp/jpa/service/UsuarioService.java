package com.tp.jpa.service;

import com.tp.jpa.model.Usuario;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.InputUtil;

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
        Rol rol = InputUtil.leerRol("Rol: ");
        usuario.setRol(rol);

        usuarioRepo.guardar(usuario);
        System.out.println("Usuario creado: " + usuario);
        Pausa();
    }

    private void Pausa() {
        input.nextLine();
    }
}
