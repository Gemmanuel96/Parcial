package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Base;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoty<T extends Base> {

    private final Class<T> entityClass;

    public BaseRepositoty(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T guardar(T entity) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            T resultado;

            if (entity.getId() == null) {
                em.persist(entity);
                resultado = entity;

            } else {
                resultado = em.merge(entity);
            }

            em.getTransaction().commit();
            return resultado;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Error al guardar entidad: " + e.getMessage());

        } finally {
            em.close();
        }
    }

    ;

    public boolean eliminarLogico(long id) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            T entity = em.find(entityClass, id);

            if (entity == null) {
                return false;
            }

            em.getTransaction().begin();
            entity.setEliminado(true);
            em.merge(entity);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al eliminar entidad: " + e.getMessage());

        } finally {
            em.close();
        }

    }

    ;

    public Optional<T> buscarPorId(long id) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            T entity = em.find(entityClass, id);

            if (entity == null) {
                return Optional.empty();
            }

            return Optional.of(entity);

        } finally {
            em.close();
        }
    }

    ;

    public List<T> listarActivos() {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            String nombreEntidad = entityClass.getSimpleName();
            String jpql = "SELECT e FROM " + nombreEntidad + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, entityClass).getResultList();

        } finally {
            em.close();
        }
    }

    ;
}
