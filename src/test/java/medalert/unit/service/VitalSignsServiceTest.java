package medalert.unit.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import medalert.model.VitalSigns;
import medalert.service.VitalSignsService;
import medalert.repository.VitalSignsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VitalSignsServiceTest {

    @Mock
    private VitalSignsRepository vitalSignsRepository;

    @InjectMocks
    private VitalSignsService vitalSignsService;

    @Test
    void testGetVitalSigns_WhenFound_ReturnVitalSigns(){
        //Arrange
        int vitalSignsId = 1;
        VitalSigns vitalSigns = new VitalSigns(1, 2, new Date(), 36.5f, 98.0f, 75.0f,
                1.1f, 22.5f, 3.5f);
        when(vitalSignsRepository.findById(vitalSignsId)).thenReturn(Optional.of(vitalSigns));

        //Act
        Optional<VitalSigns> result = vitalSignsService.getVitalsSigns(vitalSignsId);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(vitalSigns, result.get());
        verify(vitalSignsRepository,times(1)).findById(vitalSignsId);

    }

    @Test
    void testGetVitalSigns_WhenNotFound_ReturnEmptyOptional(){
        //Arrange
        when(vitalSignsRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<VitalSigns> result = vitalSignsService.getVitalsSigns(1);

        //Assert
        assertFalse(result.isPresent(),"Les constantes doivent être vides");
        verify(vitalSignsRepository,times(1)).findById(1);
    }

    @Test
    void testGetAllVitalSigns_WhenFound_ReturnAllVitalSigns(){
        //Arrange
        VitalSigns vitalSigns = new VitalSigns(1, 2, new Date(), 36.5f, 98.0f, 75.0f,
                1.1f, 22.5f, 3.5f);
        VitalSigns vitalSigns1 = new VitalSigns(1, 2, new Date(), 36.5f, 99.0f, 85.0f,
                1.4f, 22.5f, 3.5f);
        List<VitalSigns> vitals = Arrays.asList(vitalSigns, vitalSigns1);
        when(vitalSignsRepository.findAll()).thenReturn(vitals);

        //Act
        List<VitalSigns>  result = vitalSignsService.getAllVitalsSigns();

        //Assert
        assertNotNull(result,"La liste ne peut pas être vide");
        assertEquals(vitals.size(),result.size(),"Le nombre de constantes doivent être identiques");
        assertEquals(vitals,result,"Les constantes doivent correspondre");
        verify(vitalSignsRepository,times(1)).findAll();

    }

    @Test
    void  testGetAllVitalSigns_WhenNotFound_ReturnEmptyList(){
        //Arrange
        when(vitalSignsRepository.findAll()).thenReturn(List.of());

        //Act
        List<VitalSigns>  result = vitalSignsService.getAllVitalsSigns();

        //Assert
        assertTrue(result.isEmpty());
        verify(vitalSignsRepository,times(1)).findAll();
    }

    @Test
    void testDeleteVitalSigns_CallsDeleteByIdOnce(){
        //Assert
        int vitalSignsId = 1;

        //Act
        vitalSignsService.deleteVitalsSigns(vitalSignsId);

        //Assert
        verify(vitalSignsRepository,times(1)).deleteById(vitalSignsId);
    }

    @Test
    void testAddConstantes_SavesAndReturnsVitalSigns(){
        //Arrange
        VitalSigns vitalSigns = new VitalSigns(1, 2, new Date(), 36.5f, 98.0f, 75.0f,
                1.1f, 22.5f, 3.5f);
        when(vitalSignsRepository.save(any(VitalSigns.class))).thenReturn(vitalSigns);

        //Act
        VitalSigns result = vitalSignsService.addVitalsSigns(vitalSigns);

        //Assert
        assertNotNull(result);
        assertEquals(vitalSigns, result,"Les constantes doivent correspondre");
        verify(vitalSignsRepository,times(1)).save(any(VitalSigns.class));

    }

    @Test
    void testFindVitalsSignByPatientId_WhenFound_ReturnsVitalsSigns(){
        //Arrange
        int patientId = 2;
        VitalSigns vitalSigns = new VitalSigns(1, 2, new Date(), 36.5f, 98.0f, 75.0f,
                1.1f, 22.5f, 3.5f);
        VitalSigns vitalSigns1 = new VitalSigns(1, 2, new Date(), 36.5f, 99.0f, 85.0f,
                1.4f, 22.5f, 3.5f);
        List<VitalSigns> vitals = Arrays.asList(vitalSigns, vitalSigns1);
        when(vitalSignsRepository.findBypatientid(2)).thenReturn(vitals);

        //Act
        List<VitalSigns> result = vitalSignsService.findVitalsSignByPatient(patientId);

        //Assert
        assertNotNull(result,"La liste ne doit pas être nulle");
        assertEquals(vitals.size(),result.size(),"Le nombre de constantes doit correspondre");
        assertEquals(vitals,result,"Les constantes doivent correspondre");

    }

    @Test
    void testFindVitalsSignByPatientId_WhenNoneFound_ReturnEmptyList(){
        //Arrange
        int patientId = 99;
        when(vitalSignsRepository.findBypatientid(patientId)).thenReturn(List.of());

        //Act
        List<VitalSigns> result = vitalSignsService.findVitalsSignByPatient(patientId);

        //Assert
        assertTrue(result.isEmpty());
        verify(vitalSignsRepository,times(1)).findBypatientid(patientId);
    }

    @Test
    void testFindVitalsSignsByAdminId_WhenFound_ReturnsVitalsSigns(){
        //Arrange
        int adminId= 1;
        Date fixedDate = new Date();
        VitalSigns vitalSigns = new VitalSigns(1, 2, fixedDate, 36.5f, 98.0f, 75.0f,
                1.1f, 22.5f, 3.5f);
        VitalSigns vitalSigns1 = new VitalSigns(1, 2, fixedDate, 36.5f, 99.0f, 85.0f,
                1.4f, 22.5f, 3.5f);
        List<VitalSigns> vitals = Arrays.asList(vitalSigns, vitalSigns1);
        when(vitalSignsRepository.findByadminid(adminId)).thenReturn(Arrays.asList(
                new VitalSigns(1, 2, fixedDate, 36.5f, 98.0f, 75.0f, 1.1f, 22.5f, 3.5f),
                new VitalSigns(1, 2, fixedDate, 36.5f, 99.0f, 85.0f, 1.4f, 22.5f, 3.5f)
        ));


        //Act
        List<VitalSigns> result = vitalSignsService.findVitalsSignsByAdmin(adminId);

        //Assert
        assertNotNull(result,"La liste ne doit pas être vide");
        assertEquals(vitals.size(),result.size(),"Le nombre de constantes doit correspondre");
        assertEquals(vitals,result,"Les constantes doivent correspondre");
        verify(vitalSignsRepository, times(1)).findByadminid(adminId);


    }

    @Test
    void testFindVitalsSignsByAdminId_WhenNoneFound_ReturnEmptyList(){
        //Arrange
        int adminId = 99;
        when(vitalSignsRepository.findByadminid(adminId)).thenReturn(List.of());

        //Act
        List<VitalSigns> result =  vitalSignsService.findVitalsSignsByAdmin(adminId);

        //Assert
        assertTrue(result.isEmpty());
        verify(vitalSignsRepository,times(1)).findByadminid(adminId);
    }
}
