package medalert.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import medalert.model.Rapport;

import java.util.List;

@Repository
public interface RapportRepository extends CrudRepository<Rapport,Integer>{

     List<Rapport> findByadminid(Integer adminid);
     List<Rapport> findBypatientid(Integer patientid);
}
