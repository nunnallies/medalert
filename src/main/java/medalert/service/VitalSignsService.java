package medalert.service;
import java.util.List;
import java.util.Optional;
import lombok.Data;

import medalert.model.VitalSigns;
import org.springframework.stereotype.Service;
import medalert.repository.VitalSignsRepository;


@Data
@Service
public class VitalSignsService {


    private VitalSignsRepository VitalSignsRepository;

    public Optional<VitalSigns> getConstantes(final Integer id){
        return VitalSignsRepository.findById(id);
    }

    public List<VitalSigns> getAllConstantes(){
        return ((List<VitalSigns>) VitalSignsRepository.findAll());
    }

    public void deleteConstantes(final Integer id){
        VitalSignsRepository.deleteById(id);
    }

    public VitalSigns addConstantes(final VitalSigns vitalSigns){
        VitalSigns savedconstantes = VitalSignsRepository.save(vitalSigns);
        return VitalSignsRepository.save(vitalSigns);
    }

    public List<VitalSigns> findConstantesByPatient(Integer patientid){
        return ((List<VitalSigns>) VitalSignsRepository.findBypatientid(patientid));
    }

    public List<VitalSigns> findConstantessByAdmin(Integer adminid){
        return ((List<VitalSigns>) VitalSignsRepository.findByadminid(adminid));
    }
}
