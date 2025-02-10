package medalert.service;
import lombok.Data;
import medalert.model.Rapport;
import org.springframework.stereotype.Service;
import medalert.repository.RapportRepository;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class RapportService {


    private RapportRepository RapportRepository;

    public Optional<Rapport> getRapport(final int id){
        return RapportRepository.findById(id);
    }

    public Iterable<Rapport> getAllRapports(){
        return RapportRepository.findAll();
    }

    public void deleteRapport(final int id){
        RapportRepository.deleteById(id);
    }

    public Rapport addRapport(final Rapport rapport){
        Rapport savedrapport = RapportRepository.save(rapport);
        return RapportRepository.save(rapport);
    }

    public List<Rapport> findRapportsByAdmin(Integer adminid){
        return ((List<Rapport>)RapportRepository.findByadminid(adminid));
    }

    public List<Rapport> findRapportsByPatient(Integer patientid){
        return ((List<Rapport>)RapportRepository.findBypatientid(patientid));
    }

}
