package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Producto;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class ProductoRepository extends BaseRepositoty<Producto> {

    @Override
    public void guardar(Producto entity) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            System.out.println("¡Producto guardado exitosamente!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean eliminarLogico(long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);

            if (producto != null) {
                producto.setEliminado(true);
                em.merge(producto);
                em.getTransaction().commit();
                return true;

            }else{
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public Optional<Producto> buscarPorId(long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return Optional.ofNullable(em.find(Producto.class, id));
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public List<Producto> listarActivos() {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            List<Producto> productos = em.createNamedQuery("Producto.findActivos", Producto.class).getResultList();
            return productos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    public void modificar(Producto entity) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            System.out.println("Producto modificado exitosamente!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
