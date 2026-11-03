package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class InternauteDao extends GenericDao<Internaute> {
    public InternauteDao() { super(Internaute.class); }

    public Internaute findByEmail(String email) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            List<Internaute> l = em.createQuery("SELECT i FROM Internaute i WHERE i.email = :e", Internaute.class)
                    .setParameter("e", email).getResultList();
            return l.isEmpty() ? null : l.get(0);
        } finally { em.close(); }
    }
}
