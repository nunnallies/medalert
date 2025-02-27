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
    private ReportRepository ReportRepository;

    public Optional<Report> getReport(final int id){
        return ReportRepository.findById(id);
    }

    public Iterable<Report> getAllReports(){
        return ReportRepository.findAll();
    }

    public void deleteReport(final int id){
        ReportRepository.deleteById(id);
    }

    public Report addRport(final Report report){
        Report savedreport = ReportRepository.save(report);
        return ReportRepository.save(report);
    }

    public List<Report> findReportsByAdmin(Integer adminid){
        return ((List<Report>) ReportRepository.findByadminid(adminid));
    }

    public List<Report> findReportsByPatient(Integer patientid){
        return ((List<Report>) ReportRepository.findBypatientid(patientid));
    }

    public List<Report> findRapportsByType(String type){
        return ((List<Report>) ReportRepository.findBytype(type));
    }

}
