package medalert.unit.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        assertEquals(patient,result.get());
        verify(patientRepository,times(1)).findById(1);


    }

    @Test
    void testGetPatient_WhenNotFound_ReturnsEmptyOptional(){
        //Arrange
         when(patientRepository.findById(996)).thenReturn(Optional.empty());

         //Act
         Optional<Patient> result = patientService.getPatient(996);

         //Assert
         assertTrue(result.isEmpty());
         verify(patientRepository, times(1)).findById(996);
    }

     @Test
     void testGetAllPatients_WhenFound_ReturnsAllPatients(){
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
         verify(patientRepository, times(1)).findAll();

     }
     @Test
     void testGetAllPatients_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
         when(patientRepository.findAll()).thenReturn(List.of());

         //Act
         List<Patient> result = patientService.getAllPatients();

         //Assert
         assertTrue(result.isEmpty());
         verify(patientRepository, times(1)).findAll();

     }

     @Test
     void testDeletePatient_CallsDeleteByIdOnce(){
        //Arrange
        int   id = 1;
        //Act
        patientService.deletePatient(id);
        //Assert
        verify(patientRepository, times(1)).deleteById(id);

     }
    @Test
    void testAddPatient_SavesAndReturnsPatient(){
        //Arrange
        Patient patient = new Patient("Dubois", "thomas", new Date(), "Cardiologie", "dbt@gmail.com", 1);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        //Act
        Patient result = patientService.addPatient(patient);

        //Assert
        assertNotNull(result);
        assertEquals(patient,result,"Les patients doivent correspondre");
        verify(patientRepository,times(1)).save(patient);

    }

    @Test
    void testFindPatientsByService_WhenFound_ReturnsPatients(){
        //Arrange
        String service = "Médecine Générale";
        Patient patient = new Patient("Dubois", "thomas", new Date(), "Médecine Générale", "dbt@gmail.com", 4);
        Patient patient2 = new Patient("Martin", "Sophie", new Date(), "Médecine Générale", "mts@gmail.com", 4);
        List<Patient> patients = Arrays.asList(patient, patient2);
        when(patientRepository.findByService(service)).thenReturn(patients);

        //Act
        List<Patient> result = patientService.findPatientsByService(service);

        //Assert
        assertNotNull(result,"La liste ne peut pas être nulle");
        assertEquals(patients.size(),result.size(),"Le nombre de patient doit être identique");
        assertEquals(result,patients,"Les patients doivent correspondre");
        verify(patientRepository,times(1)).findByService(service);

    }

    @Test
    void testFindPatientsByService_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        String service = "Accueil";
        when(patientRepository.findByService(service)).thenReturn(List.of());

        //Act
        List<Patient> result = patientService.findPatientsByService(service);

        //Assert
        assertTrue(result.isEmpty());
        verify(patientRepository,times(1)).findByService(service);
    }

    @Test
    void testFindPatientsByAdminId_WhenFound_ReturnsPatients(){
        //Arrange
        int adminId = 1;
        Patient patient = new Patient("Dubois", "thomas", new Date(), "Médecine Générale", "dbt@gmail.com", 1);
        Patient patient2 = new Patient("Martin", "Sophie", new Date(), "Médecine Générale", "mts@gmail.com", 1);
        List<Patient> patients = Arrays.asList(patient, patient2);
        when(patientRepository.findByadminid(adminId)).thenReturn(patients);

        //Act
        List<Patient> result = patientService.findPatientsByAdmin(adminId);

        //Assert
        assertNotNull(result,"La liste de patients ne doit âs être vide");
        assertEquals(patients.size(),result.size(),"Les listes doivent contenir le même nombre de patient");
        assertEquals(patients,result,"Les patients de la liste doivent correspondre");
        verify(patientRepository,times(1)).findByadminid(adminId);
    }

    @Test
    void testFindPatientsByAdminId_WhenNoneFound_ReturnsEmptyList(){
        int adminId = 1;
        when(patientRepository.findByadminid(adminId)).thenReturn(List.of());

        //Act
        List<Patient> result = patientService.findPatientsByAdmin(adminId);

        //assert
        assertTrue(result.isEmpty());
        verify(patientRepository,times(1)).findByadminid(adminId);

    }

}
