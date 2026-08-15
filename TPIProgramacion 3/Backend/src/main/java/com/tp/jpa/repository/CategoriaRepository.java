package com.tp.jpa.repository;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repositorio de Categoria. Además del CRUD heredado implementa la consulta
 * de productos activos pertenecientes a una categoría.
 * <p>
 * Nota de diseño: como la relación es unidireccional y Categoria es la dueña
 * de la colección Set<Producto>, la navegación se hace desde Categoria hacia
 * sus productos (p. ej. JPQL con JOIN sobre c.productos).
 */
public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }


    public List<Producto> buscarProductosPorCategoria(Long categoriaId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {

            String jpql = """
                     SELECT p
                                   FROM Categoria c
                                   JOIN c.productos p
                                   WHERE c.id = :catId
                                   AND p.eliminado = false
                    """;
            return em.createQuery(jpql, Producto.class).setParameter("catId", categoriaId).getResultList();
        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.printf("Error el listar productos: " + e.getMessage());
            }

        } finally {
            em.close();
        }

        return List.of();
    }
}
