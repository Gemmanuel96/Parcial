package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Categoria;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class CategoriaRepository extends BaseRepositoty<Categoria> {


    public CategoriaRepository(Class<Categoria> entityClass) {
        super(entityClass);
    }

    @Override
    public Categoria guardar(Categoria entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            em.getTransaction().begin();

            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }
            em.getTransaction().commit();

            return entity;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();
            throw new RuntimeException("No se puede guardar el registro");

        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminarLogico(long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            Categoria categoria = em.find(Categoria.class, id);

            if (categoria == null) {
                return false;
            }

            em.getTransaction().begin();
            categoria.setEliminado(true);
            em.merge(categoria);
            em.getTransaction().commit();

            return true;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();
            throw new RuntimeException("No se puede eliminar el registro");

        } finally {
            em.close();
        }

    }

    @Override
    public Optional<Categoria> buscarPorId(long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            Categoria categoria = em.find(Categoria.class, id);
            return Optional.of(categoria);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Categoria> listarActivos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Categoria> listCategorias = em.createNamedQuery("Categoria.findActivos", Categoria.class).getResultList();
            return listCategorias;
        } finally {
            em.close();
        }
    }
}