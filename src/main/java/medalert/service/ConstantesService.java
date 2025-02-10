package medalert.service;
import java.util.List;
import java.util.Optional;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medalert.model.Constantes;
import medalert.repository.ConstantesRepository;


@Data
@Service
public class ConstantesService {


    private ConstantesRepository ConstantesRepository;

    public Optional<Constantes> getConstantes(final Integer id){
        return ConstantesRepository.findById(id);
    }

    public List<Constantes> getAllConstantes(){
        return ((List<Constantes>) ConstantesRepository.findAll());
    }

    public void deleteConstantes(final Integer id){
        ConstantesRepository.deleteById(id);
    }

    public Constantes addConstantes(final Constantes constantes){
        Constantes savedconstantes = ConstantesRepository.save(constantes);
        return ConstantesRepository.save(constantes);
    }

    public List<Constantes> findConstantesByPatient(Integer patientid){
        return ((List<Constantes>)ConstantesRepository.findBypatientid(patientid));
    }

    public List<Constantes> findConstantessByAdmin(Integer adminid){
        return ((List<Constantes>)ConstantesRepository.findByadminid(adminid));
    }
}
