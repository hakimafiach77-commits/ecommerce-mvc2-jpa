package ma.fstt.ecommerce.dao;

import ma.fstt.ecommerce.model.Produit;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProduitDao extends GenericDao<Produit> {
    public ProduitDao() { super(Produit.class); }

    /** Recherche multicritère pour la vitrine (mot-clé et/ou catégorie). */
    public List<Produit> rechercher(String motCle, Long categorieId) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            boolean mc = motCle != null && !motCle.trim().isEmpty();
            StringBuilder jpql = new StringBuilder("SELECT p FROM Produit p WHERE 1=1");
            if (mc) jpql.append(" AND LOWER(p.designation) LIKE :mc");
            if (categorieId != null) jpql.append(" AND p.categorie.id = :cat");
            jpql.append(" ORDER BY p.designation");
            TypedQuery<Produit> q = em.createQuery(jpql.toString(), Produit.class);
            if (mc) q.setParameter("mc", "%" + motCle.trim().toLowerCase() + "%");
            if (categorieId != null) q.setParameter("cat", categorieId);
            return q.getResultList();
        } finally { em.close(); }
    }
}
