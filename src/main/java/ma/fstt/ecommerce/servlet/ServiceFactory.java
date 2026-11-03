package ma.fstt.ecommerce.servlet;

import ma.fstt.ecommerce.dao.*;
import ma.fstt.ecommerce.service.*;

/** Injection des dépendances (DAO -> Service) pour les contrôleurs. */
public final class ServiceFactory {
    private static final InternauteService INTERNAUTE = new InternauteService(new InternauteDao());
    private static final ProduitService PRODUIT = new ProduitService(new ProduitDao(), new CategorieDao());
    private static final PanierService PANIER = new PanierService();
    private ServiceFactory() {}
    public static InternauteService internauteService() { return INTERNAUTE; }
    public static ProduitService produitService() { return PRODUIT; }
    public static PanierService panierService() { return PANIER; }
}
