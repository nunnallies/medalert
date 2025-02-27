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
    @Column(name = "daterapport")
    private Date reportdate;
    private String type;
    @Column(name = "commentaire")
    private String content;

    public Report() {}
    public Report(int patientid, int adminid, Date reportdate, String type, String content) {
        this.patientid = patientid;
        this.adminid = adminid;
        this.reportdate = reportdate;
        this.type = type;
        this.content = content;
    }
}
