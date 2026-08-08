package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Producto;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class ProductoRepository extends BaseRepositoty<Producto> {


    public ProductoRepository(Class<Producto> entityClass) {
        super(entityClass);
    }

    @Override
    public Producto guardar(Producto entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            //Inicio de transaccion
            em.getTransaction().begin();

            if (entity.getId() == null) {
                em.persist(entity);

            } else {
                em.merge(entity);
            }
            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("No se puede guardar el registro");

        } finally {
            em.close();
        }

        return entity;
    }


    @Override
    public boolean eliminarLogico(long id) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            Producto p = em.find(Producto.class, id);

            if (p == null) {
                return false;
            }

            em.getTransaction().begin();
            p.setEliminado(true);
            em.merge(p);
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
    public Optional<Producto> buscarPorId(long id) {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            Producto p = em.find(Producto.class, id);
            return Optional.ofNullable(p);
        } finally {
            em.close();
        }

    }

    @Override
    public List<Producto> listarActivos() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {

            List<Producto> productos = em.createNamedQuery("Producto.findActivos").getResultList();
            return productos;

        } finally {
            em.close();
        }
    }
}
