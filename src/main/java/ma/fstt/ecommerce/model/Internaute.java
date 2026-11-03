package ma.fstt.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "internaute")
public class Internaute implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String motDePasse;
    private String adresse;

    public Internaute() {}
    public Internaute(String nom, String prenom, String email, String motDePasse, String adresse) {
        this.nom = nom; this.prenom = prenom; this.email = email;
        this.motDePasse = motDePasse; this.adresse = adresse;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
}
