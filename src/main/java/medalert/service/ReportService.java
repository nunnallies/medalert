package medalert.service;
import lombok.Data;
import medalert.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medalert.repository.ReportRepository;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Optional<Report> getReport(final int id){
        return reportRepository.findById(id);
    }

    public Iterable<Report> getAllReports(){
        return reportRepository.findAll();
    }

    public void deleteReport(final int id){
        reportRepository.deleteById(id);
    }

    public Report addReport(final Report report){
        return reportRepository.save(report);
    }

    public List<Report> findReportsByAdmin(Integer adminid){
        return ((List<Report>) reportRepository.findByadminid(adminid));
    }

    public List<Report> findReportsByPatient(Integer patientid){
        return ((List<Report>) reportRepository.findBypatientid(patientid));
    }

    public List<Report> findReportsByType(String type){
        return ((List<Report>) reportRepository.findBytype(type));
    }

}
