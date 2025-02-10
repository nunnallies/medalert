package medalert.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import medalert.model.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Integer> {

    List<Patient> findByService(String service);
    List<Patient> findByadminid(Integer adminid);
}




