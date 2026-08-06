package org.example.Entities;


import jakarta.persistence.*;
import org.example.Enums.Rol;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
//Jpa
@Entity
@Table(name = "USUARIOS")
@NamedQueries({
        @NamedQuery(
                name = "Usuario.findEmail",
                query = "select u from Usuario u where u.email =: email"
        )
})
public class Usuario extends Base{

    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contrasenia;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String celular, String contrasenia, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", rol=" + rol +
                '}';
    }
}
