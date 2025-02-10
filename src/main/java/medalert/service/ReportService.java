package medalert.service;
import lombok.Data;
import medalert.model.Report;
import org.springframework.stereotype.Service;
import medalert.repository.ReportRepository;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ReportService {


    private ReportRepository ReportRepository;

    public Optional<Report> getRapport(final int id){
        return ReportRepository.findById(id);
    }

    public Iterable<Report> getAllRapports(){
        return ReportRepository.findAll();
    }

    public void deleteRapport(final int id){
        ReportRepository.deleteById(id);
    }

    public Report addRapport(final Report report){
        Report savedrapport = ReportRepository.save(report);
        return ReportRepository.save(report);
    }

    public List<Report> findRapportsByAdmin(Integer adminid){
        return ((List<Report>) ReportRepository.findByadminid(adminid));
    }

    public List<Report> findRapportsByPatient(Integer patientid){
        return ((List<Report>) ReportRepository.findBypatientid(patientid));
    }

}
