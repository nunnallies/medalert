package medalert.service;
import java.util.List;
import java.util.Optional;
import lombok.Data;

import medalert.model.VitalSigns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medalert.repository.VitalSignsRepository;


@Data
@Service
public class VitalSignsService {

    @Autowired
    private VitalSignsRepository VitalSignsRepository;

    public Optional<VitalSigns> getVitalsSigns(final Integer id){

        return VitalSignsRepository.findById(id);
    }

    public List<VitalSigns> getAllVitalsSigns(){

        return ((List<VitalSigns>) VitalSignsRepository.findAll());
    }

    public void deleteVitalsSigns(final Integer id){
        VitalSignsRepository.deleteById(id);
    }

    public VitalSigns addVitalsSigns(final VitalSigns vitalSigns){
        return VitalSignsRepository.save(vitalSigns);
    }

    public List<VitalSigns> findVitalsSignByPatient(Integer patientid){
        return ((List<VitalSigns>) VitalSignsRepository.findBypatientid(patientid));
    }

    public List<VitalSigns> findVitalsSignsByAdmin(Integer adminid){
        return ((List<VitalSigns>) VitalSignsRepository.findByadminid(adminid));
    }
}
