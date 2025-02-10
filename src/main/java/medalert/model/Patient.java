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
    @Column(name = "nom")
    private String lastname;
    @Column(name = "prenom")
    private String name;
    @Column(name = "datedenaissance")
    private Date birthday;
    @Column(name = "service")
    private String service;
    @Column(name = "mail")
    private String mail;
    @Column(name = "adminid")
    private Integer adminid;

    public Patient() {}
    public Patient(String lastname, String name, Date birthday, String service, String mail, Integer adminid) {
        this.lastname = lastname;
        this.name = name;
        this.birthday = birthday;
        this.service = service;
        this.mail = mail;
        this.adminid = adminid;
    }
}
