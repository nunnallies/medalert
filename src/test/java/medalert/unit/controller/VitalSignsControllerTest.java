package medalert.unit.controller;

import jakarta.servlet.http.HttpSession;
import medalert.constants.Attribute;
import medalert.constants.Message;
import medalert.constants.Redirect;
import medalert.controller.VitalSignsController;
import medalert.model.Admin;
import medalert.model.VitalSigns;
import medalert.service.VitalSignsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VitalSignsControllerTest {

    @Mock
    private VitalSignsService vitalSignsService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private VitalSignsController vitalSignsController;

    @Test
    void testGetVitalSignsFromPatient_ReturnsMappedDataList() {
        // Arrange
        VitalSigns vs = new VitalSigns();
        vs.setMeasurementdate(new Date());
        vs.setTemperature(36.5f);
        vs.setPulse(72f);
        vs.setSpO2(98f);
        vs.setBloodGlucose(1.1f);
        vs.setBmi(22.3f);
        vs.setAlbumin(4.4f);

        List<VitalSigns> vitalSignsList = List.of(vs);
        when(vitalSignsService.findVitalsSignByPatient(1)).thenReturn(vitalSignsList);

        // Act
        List<Map<String, Object>> result = vitalSignsController.getVitalSignsFromPatient(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Temp: 36.5°C\nPouls: 72.0 bpm\nIMC:22.3\nSpO2: 98.0%\nGlycémie:1.1g.L-1\nAlbumine: 4.4g.L-1\n", result.get(0).get("info"));
    }

    @Test
    void testAddVitalSigns_WhenAdminNotLoggedIn_RedirectsToLogin() {
        // Arrange
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(null);

        // Act
        String result = vitalSignsController.AddVitalSigns(model, session, "2025-04-15T10:00", 98f, 36.6f, 22.0f, 70f, 1.0f, 4.0f, 42, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
        assertEquals(Redirect.REDIRECT_CONNEXION, result);
    }

    @Test
    void testAddVitalSigns_WhenDateIsInvalid_RedirectsWithError() {
        // Arrange
        Admin admin = new Admin();
        admin.setAdminid(1);
        String invalidDate = "invalid-date";
        // Act
        String result = vitalSignsController.AddVitalSigns(model, session, invalidDate, 98f, 36.6f, 22.0f, 70f, 1.0f, 4.0f, 42, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_INVALIDDATEFORMAT);
        assertEquals(Redirect.REDIRECT_PATIENT_DETAILS + 42 + Redirect.ANCHOR_AJOUT_CONSTANTES, result);
    }

    @Test
    void testAddVitalSigns_WhenSuccess_ReturnsRedirectWithSuccessMessage() throws Exception {
        // Arrange
        Admin admin = new Admin();
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);

        VitalSigns vs = new VitalSigns();
        when(vitalSignsService.addVitalsSigns(any())).thenReturn(vs);

        String date = "2025-04-15T10:00";

        // Act
        String result = vitalSignsController.AddVitalSigns(model, session, date, 98f, 36.6f, 22.0f, 70f, 1.0f, 4.0f, 42, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute(Attribute.SUCCESS_ATTRIBUTE, Message.SUCESS_MESSSAGE_ADDEDVITALSIGN);
        assertEquals(Redirect.REDIRECT_PATIENT_DETAILS + 42 + Redirect.ANCHOR_AJOUT_CONSTANTES, result);
    }

    @Test
    void testAddVitalSigns_WhenServiceReturnsNull_ReturnsRedirectWithErrorMessage() throws Exception {
        // Arrange
        Admin admin = new Admin();
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);

        when(vitalSignsService.addVitalsSigns(any())).thenReturn(null);
        String date = "2025-04-15T10:00";

        // Act
        String result = vitalSignsController.AddVitalSigns(model, session, date, 98f, 36.6f, 22.0f, 70f, 1.0f, 4.0f, 42, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FAILEDTOADDVITALSSIGNS);
        assertEquals(Redirect.REDIRECT_PATIENT_DETAILS + 42 + Redirect.ANCHOR_AJOUT_CONSTANTES, result);
    }

}
