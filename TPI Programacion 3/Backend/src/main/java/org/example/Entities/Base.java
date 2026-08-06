package org.example.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


//Mapeo de JPA
@MappedSuperclass
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean eliminado;

    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDate.now();
    }

    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                '}';
    }
}
