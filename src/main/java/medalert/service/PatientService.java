package medalert.service;

import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medalert.model.Patient;
import medalert.repository.PatientRepository;

@Data
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;



    public Optional<Patient> getPatient(final int id){
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients(){
        return ((List<Patient>) patientRepository.findAll());
    }

    public void deletePatient(final int id){
        patientRepository.deleteById(id);
    }

    public Patient addPatient(final Patient patient){
        Patient savedpatient = patientRepository.save(patient);
        return patientRepository.save(patient);
    }

    public List<Patient> findPatientsByService(String service){
        return ((List<Patient>)patientRepository.findByService(service));
    }

    public List<Patient> findPatientsByAdmin(Integer adminid){
        return ((List<Patient>)patientRepository.findByadminid(adminid));
    }
}
