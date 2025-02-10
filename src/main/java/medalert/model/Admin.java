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
    private String status;
    @Column(name="specialite")
    private String speciality;
    @Column(name="identifiant")
    private String identifiant;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminid;

    public Admin(){}
    public Admin(String name, String password, String status, String speciality, String identifiant) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.speciality = speciality;
        this.identifiant = identifiant;
    }



}
