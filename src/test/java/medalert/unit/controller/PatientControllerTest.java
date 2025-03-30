package medalert.unit.controller;

import jakarta.servlet.http.HttpSession;
import medalert.controller.PatientController;
import medalert.model.Patient;
import medalert.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private Model model;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;



    @Test
    void testShowPatients_WhenPatientsExist_ReturnsView() {
        //Arrange
        List<Patient> patients = List.of(
                new Patient("Dubois", "thomas", new Date(), "Médecine Générale", "dbt@gmail.com", 1),
                new Patient("Martin", "Sophie", new Date(), "Médecine Générale", "mts@gmail.com", 1)
                );
        when(patientService.getAllPatients()).thenReturn(patients);

        //Act
        String viewName = patientController.showPatients(model);

        //Assert
        assertEquals("Front/admin/patients", viewName);
        verify(patientService, times(1)).getAllPatients();
        verify(model, times(1)).addAttribute("patients",patients);
    }

    @Test
    void testShowPatients_WhenNoPatientsExist_ReturnsView() {
        //Arrange
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        //Act
        String viewName = patientController.showPatients(model);

        //Assert
        assertEquals("Front/admin/patients", viewName);
        verify(patientService, times(1)).getAllPatients();
        verify(model, times(1)).addAttribute("patients", Collections.emptyList());
    }

//    @Test
//    void showPatients_WhenServiceFails_ThrowException() {
//        //Arrange
//        when(patientService.getAllPatients()).thenThrow(new RuntimeException("Erreur serveur"));
//
//        //Act
//        Executable executable = () -> patientController.showPatients(model);
//
//        //Assert
//        assertThrows(RuntimeException.class, executable);
//        verify(patientService, times(1)).getAllPatients();
//        verify(model, times(1)).addAttribute("errorMessage", "Erreur lors de la récupération des patients.");
//    }
//
//    @Test
//    void testAddPatient_WhenAdminNotLoggedIn_RedirectToLoginPage(){
//        //Arrange
//        when(session.getAttribute("adminId")).thenReturn(null);
//
//        //Act
//        String result = patientController.addPatient("Dubois", "John", "1990-05-01 12:00", "john@example.com", session, redirectAttributes);
//
//
//    }




}
