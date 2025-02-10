package medalert.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import medalert.model.Constantes;

import java.util.List;

@Repository
public interface ConstantesRepository extends CrudRepository<Constantes,Integer>{

    List<Constantes> findByadminid(Integer adminid);
    List<Constantes> findBypatientid(Integer patientid);
}
