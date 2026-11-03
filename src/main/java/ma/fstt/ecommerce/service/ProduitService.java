package ma.fstt.ecommerce.service;

import ma.fstt.ecommerce.dao.CategorieDao;
import ma.fstt.ecommerce.dao.ProduitDao;
import ma.fstt.ecommerce.model.Categorie;
import ma.fstt.ecommerce.model.Produit;

import java.util.List;

public class ProduitService {
    private final ProduitDao produitDao;
    private final CategorieDao categorieDao;
    public ProduitService(ProduitDao produitDao, CategorieDao categorieDao) {
        this.produitDao = produitDao; this.categorieDao = categorieDao;
    }
    public List<Produit> rechercher(String mc, Long cat) { return produitDao.rechercher(mc, cat); }
    public Produit findById(Long id) { return id == null ? null : produitDao.findById(id); }
    public Produit enregistrer(Produit p) { return produitDao.save(p); }
    public void supprimer(Long id) { produitDao.delete(id); }
    public List<Categorie> categories() { return categorieDao.findAll(); }
    public Categorie categorie(Long id) { return id == null ? null : categorieDao.findById(id); }
}
