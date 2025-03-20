package medalert.unit.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import medalert.model.Admin;
import medalert.model.Patient;
import medalert.service.PatientService;
import medalert.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;


    @Test
    void testGetPatient_WhenFound_ReturnsPatient(){

        //Arrange
        Patient patient = new Patient("Dubois", "thomas", new Date(), "Cardiologie", "dbt@gmail.com", 1);
        patient.setPatientid(1);
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.getPatient(1);

        // Assert
        assertTrue(result.isPresent());


    }

    @Test
    void getPatient_WhenNotFound_ReturnsEmptyOptional(){
        //Arrange
         when(patientRepository.findById(996)).thenReturn(Optional.empty());

         //Act
         Optional<Patient> result = patientService.getPatient(996);

         //Assert
         assertTrue(result.isEmpty());
         verify(patientRepository, times(1)).findById(996);
     }

     @Test
     void getAllPatients_WhenFound_ReturnsAllPatients(){
        //Arrange
         Patient patient = new Patient("Dubois", "thomas", new Date(), "Cardiologie", "dbt@gmail.com", 1);
         Patient patient2 = new Patient("Martin", "Sophie", new Date(), "Neurologie", "mts@gmail.com", 2);
         List<Patient> patients = Arrays.asList(patient, patient2);
         when(patientRepository.findAll()).thenReturn(patients);

         //Act
         List<Patient> result = patientService.getAllPatients();

         //Assert
         assertNotNull(result,"La liste ne doit pas être nulle");
         assertEquals(result.size(), patients.size(),"La liste doit contenir 2 patients");
         assertEquals(result, patients,"Les patients de la liste doivent correspondre");

     }
     @Test
     void getAllPatients_WhenNoneFound_ReturnsEmptyList(){

     }


}
