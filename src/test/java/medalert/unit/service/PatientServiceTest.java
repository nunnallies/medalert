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


    }

}
