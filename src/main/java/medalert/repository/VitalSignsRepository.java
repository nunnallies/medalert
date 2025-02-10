package medalert.repository;
import medalert.model.VitalSigns;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalSignsRepository extends CrudRepository<VitalSigns,Integer>{

    List<VitalSigns> findByadminid(Integer adminid);
    List<VitalSigns> findBypatientid(Integer patientid);
}
