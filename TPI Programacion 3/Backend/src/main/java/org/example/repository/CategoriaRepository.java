package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Categoria;
import org.example.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

public class CategoriaRepository extends BaseRepositoty<Categoria> {


    @Override
    public void guardar(Categoria categoria) {

        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            System.out.println("Categoria guardada exitosamente!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean eliminarLogico(long id) {

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            Categoria categoria = em.find(Categoria.class, id);

            if (categoria != null) {
                categoria.setEliminado(true);
                em.merge(categoria);
                em.getTransaction().commit();
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Optional<Categoria> buscarPorId(long id) {

        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return Optional.ofNullable(em.find(Categoria.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Categoria> listarActivos() {

        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return em.createNamedQuery("Categoria.findAll", Categoria.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void modificar(Categoria categoria) {

        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}