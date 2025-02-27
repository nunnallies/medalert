package medalert.repository;
import medalert.model.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<Report,Integer>{

     List<Report> findByadminid(Integer adminid);
     List<Report> findBypatientid(Integer patientid);
     List<Report> findBytype(String type);
}
