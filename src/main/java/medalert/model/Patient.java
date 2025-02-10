package medalert.model;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;



@Getter @Setter
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientid;
    private String nom;
    private String prenom;
    private Date datedenaissance;
    private String service;
    private String mail;
    private Integer adminid;

    public Patient() {}
    public Patient(String name, String prenom, Date datedenaissance, String service, String mail, Integer adminid) {
        this.nom = name;
        this.prenom = prenom;
        this.datedenaissance = datedenaissance;
        this.service = service;
        this.mail = mail;
        this.adminid = adminid;
    }
}
