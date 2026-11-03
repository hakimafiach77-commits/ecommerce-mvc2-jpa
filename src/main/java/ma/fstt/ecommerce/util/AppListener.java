package ma.fstt.ecommerce.util;

import ma.fstt.ecommerce.model.Categorie;
import ma.fstt.ecommerce.model.Produit;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/** Crée la BDD au démarrage (via JPA) et insère des données de démonstration. */
@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent e) {
        EntityManager em = JPAUtil.createEntityManager();
        try {
            Long n = em.createQuery("SELECT COUNT(p) FROM Produit p", Long.class).getSingleResult();
            if (n == 0) {
                em.getTransaction().begin();
                Categorie info = new Categorie("Informatique");
                Categorie tel = new Categorie("Téléphonie");
                Categorie acc = new Categorie("Accessoires");
                em.persist(info); em.persist(tel); em.persist(acc);
                em.persist(p("Laptop Pro 15", "Portable 16 Go RAM, SSD 512 Go", 8990, 10, info));
                em.persist(p("Écran 27\" 4K", "Dalle IPS, HDMI/DisplayPort", 3200, 15, info));
                em.persist(p("Smartphone X12", "128 Go, double SIM", 4500, 25, tel));
                em.persist(p("Tablette T10", "10 pouces, Wi-Fi", 2100, 12, tel));
                em.persist(p("Souris sans fil", "Ergonomique, 2400 DPI", 150, 60, acc));
                em.persist(p("Clavier mécanique", "Switchs rouges, rétroéclairé", 480, 30, acc));
                em.getTransaction().commit();
            }
        } finally { em.close(); }
    }
    private Produit p(String d, String desc, double prix, int stock, Categorie c) {
        Produit p = new Produit();
        p.setDesignation(d); p.setDescription(desc); p.setPrix(prix); p.setStock(stock); p.setCategorie(c);
        return p;
    }
    @Override
    public void contextDestroyed(ServletContextEvent e) { JPAUtil.close(); }
}
