package ma.fstt.ecommerce.service;

import ma.fstt.ecommerce.dao.InternauteDao;
import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.util.PasswordUtil;

import java.util.List;

public class InternauteService {
    private final InternauteDao dao;                       // injecté par constructeur
    public InternauteService(InternauteDao dao) { this.dao = dao; }

    /** @return null si l'email existe déjà */
    public Internaute inscrire(Internaute i) {
        if (dao.findByEmail(i.getEmail()) != null) return null;
        i.setMotDePasse(PasswordUtil.hash(i.getMotDePasse()));
        return dao.save(i);
    }
    public Internaute authentifier(String email, String motDePasse) {
        Internaute i = dao.findByEmail(email);
        return (i != null && i.getMotDePasse().equals(PasswordUtil.hash(motDePasse))) ? i : null;
    }
    public Internaute findById(Long id) { return dao.findById(id); }
    public List<Internaute> findAll() { return dao.findAll(); }
    public Internaute modifier(Internaute i) { return dao.save(i); }
    public void supprimer(Long id) { dao.delete(id); }
}
