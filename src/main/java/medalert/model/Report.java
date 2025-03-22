package medalert.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(patientid, report.patientid) &&
                Objects.equals(adminid, report.adminid) &&
                Objects.equals(reportdate, report.reportdate) &&
                Objects.equals(type, report.type) &&
                Objects.equals(content, report.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientid, adminid, reportdate, type, content);
    }
}
