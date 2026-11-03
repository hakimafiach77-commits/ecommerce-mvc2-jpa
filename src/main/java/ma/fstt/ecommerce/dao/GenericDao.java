package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

/** DAO générique : chaque opération = un EntityManager + une transaction JPA. */
public class GenericDao<T> {
    private final Class<T> type;
    public GenericDao(Class<T> type) { this.type = type; }

    public T save(T entity) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            T merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    public T findById(Long id) {
        EntityManager em = JPAUtil.createEntityManager();
        try { return em.find(type, id); } finally { em.close(); }
    }

    public List<T> findAll() {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            T e = em.find(type, id);
            if (e != null) em.remove(e);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }
}
