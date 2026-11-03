package ma.fstt.ecommerce.service;

import ma.fstt.ecommerce.model.*;
import ma.fstt.ecommerce.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

/** Gestion du panier : chaque méthode = une transaction JPA. */
public class PanierService {

    public Panier getPanier(Long internauteId) {
        EntityManager em = JPAUtil.createEntityManager();
        try { return find(em, internauteId); } finally { em.close(); }
    }

    public void ajouter(Long internauteId, Long produitId, int qte) {
        final int q = qte < 1 ? 1 : qte;
        inTx(em -> {
            Panier panier = find(em, internauteId);
            if (panier == null) {
                panier = new Panier(em.find(Internaute.class, internauteId));
                em.persist(panier);
            }
            for (LignePanier l : panier.getLignes()) {
                if (l.getProduit().getId().equals(produitId)) { l.setQuantite(l.getQuantite() + q); return; }
            }
            Produit p = em.find(Produit.class, produitId);
            LignePanier l = new LignePanier(panier, p, q);
            panier.getLignes().add(l);
            em.persist(l);
        });
    }

    /** qte &lt; 1 : la ligne est retirée du panier. */
    public void modifierQuantite(Long internauteId, Long ligneId, int qte) {
        inTx(em -> {
            Panier panier = find(em, internauteId);
            if (panier == null) return;
            for (LignePanier l : panier.getLignes()) {
                if (l.getId().equals(ligneId)) {
                    if (qte < 1) panier.getLignes().remove(l); else l.setQuantite(qte);
                    return;
                }
            }
        });
    }

    public void retirer(Long internauteId, Long ligneId) { modifierQuantite(internauteId, ligneId, 0); }

    public void vider(Long internauteId) {
        inTx(em -> { Panier p = find(em, internauteId); if (p != null) p.getLignes().clear(); });
    }

    private Panier find(EntityManager em, Long internauteId) {
        List<Panier> l = em.createQuery("SELECT p FROM Panier p WHERE p.internaute.id = :id", Panier.class)
                .setParameter("id", internauteId).getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    private interface Work { void run(EntityManager em); }
    private void inTx(Work w) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            w.run(em);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }
}
