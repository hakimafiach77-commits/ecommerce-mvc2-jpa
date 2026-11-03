package ma.fstt.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "panier")
public class Panier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();
    @OneToOne
    @JoinColumn(name = "internaute_id", unique = true)
    private Internaute internaute;
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LignePanier> lignes = new ArrayList<>();

    public Panier() {}
    public Panier(Internaute internaute) { this.internaute = internaute; }

    public double getTotal() {
        double t = 0;
        for (LignePanier l : lignes) t += l.getSousTotal();
        return t;
    }
    public int getNombreArticles() {
        int n = 0;
        for (LignePanier l : lignes) n += l.getQuantite();
        return n;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date d) { this.dateCreation = d; }
    public Internaute getInternaute() { return internaute; }
    public void setInternaute(Internaute internaute) { this.internaute = internaute; }
    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }
}
