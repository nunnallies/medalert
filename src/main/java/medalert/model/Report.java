package medalert.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;


@Table(name="rapports")
@Getter @Setter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrapport;
    private Integer patientid;
    private Integer adminid;
    private Date daterapport;
    private String nomfichier;
    private String lien;

    public Report() {}
    public Report(int patientid, int adminid, Date daterapport, String nomfichier, String lien) {
        this.patientid = patientid;
        this.adminid = adminid;
        this.daterapport = daterapport;
        this.nomfichier = nomfichier;
        this.lien = lien;
    }
}
