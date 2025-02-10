package medalert.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


@Setter @Getter
@Entity
@Table(name="administrateur")
public class Admin {
    @Column(name = "nom")
    private String name;
    @Column(name = "mdp")
    private String password;
    @Column(name="statut")
    private String statut;
    @Column(name="specialite")
    private String specialite;
    @Column(name="identifiant")
    private String identifiant;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminid;

    public Admin(){}
    public Admin(String name, String password, String statut, String specialite, String identifiant) {
        this.name = name;
        this.password = password;
        this.statut = statut;
        this.specialite = specialite;
        this.identifiant = identifiant;
    }



}
