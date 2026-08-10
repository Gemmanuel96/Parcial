package com.tp.jpa.repository;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repositorio de Categoria. Además del CRUD heredado implementa la consulta
 * de productos activos pertenecientes a una categoría.
 *
 * Nota de diseño: como la relación es unidireccional y Categoria es la dueña
 * de la colección Set<Producto>, la navegación se hace desde Categoria hacia
 * sus productos (p. ej. JPQL con JOIN sobre c.productos).
 */
public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }


    public List<Producto> buscarProductosPorCategoria(Long categoriaId) {

        return List.of();
    }
}
