package medalert.unit.controller;

import jakarta.servlet.http.HttpSession;
import medalert.constants.Attribute;
import medalert.constants.Message;
import medalert.constants.ViewNames;
import medalert.controller.AdminController;
import medalert.controller.PatientController;
import medalert.model.Admin;
import medalert.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private Model model;


    @Mock
    private HttpSession session;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void testShowLoginPage_WhenErrorNotNull_ReturnsLogInView(){
        //Arrange
        String error="Error";

        // Act
        String viewName = adminController.showLoginPage(error, model);

        //Assert
        assertEquals(ViewNames.VIEW_LOGIN, viewName);
        verify(model,times(1)).addAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FAILEDTOLOGIN);


    }

    @Test
    void testShowLoginPage_WhenErrorIsNull_ReturnsLogInView(){
        //Arrange
        String error=null;

        // Act
        String viewName = adminController.showLoginPage(error, model);

        //Assert
        assertEquals(ViewNames.VIEW_LOGIN, viewName,"La page affichée doit être identique.");

    }

    @Test
    void testLogin_WhenOptionalEmpty_ReturnsLogInView(){
        //Arrange
        when(adminService.verifyAdminCredentials("admin", "pasmdp")).thenReturn(Optional.empty());

        //Act
        String viewName = adminController.login("admin", "pasmdp", model, session);

        //Assert
        verify(model).addAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FAILEDTOLOGIN);
        assertEquals(ViewNames.VIEW_LOGIN, viewName,"La page affichée doit être identique.");

    }

    @Test
    void testLogin_WhenOptionalIsPresent_ReturnsHomePageView(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "alice123");
        when(adminService.verifyAdminCredentials("alice123", "passe123")).thenReturn(Optional.of(admin));

        //Act
        String viewName = adminController.login("alice123", "passe123", model, session);

        //Assert
        verify(session).setAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE,admin);
        assertEquals(ViewNames.VIEW_DOCTOR_HOMEPAGE, viewName,"La page affichée doit être identique.");
    }

    @Test
    void testShowAdmins_WhenFound_ReturnsAdminsView(){
        //Arrange
        Admin admin = new Admin("Bob", "pass456", "Médecin", "Neurologie", "bob123");
        Admin admin1 = new Admin("Alice", "pass123", "Médecin", "Neurologie", "alice123");
        List<Admin> adminList = Arrays.asList(admin,admin1);
        when(adminService.getAllAdmins()).thenReturn(adminList);

        //Act
        String viewName = adminController.showAdmins(model);

        //Assert
        verify(model,times(1)).addAttribute(Attribute.ADMINS_ATTRIBUTE, adminList);
        assertEquals("Front/admin/admins", viewName,"La page affichée doit être identique.");
    }

    @Test
    void testShowAdmins_WhenNoneFound_ReturnsAdminsView(){
        //Arrange
        List<Admin> adminList = List.of();
        when(adminService.getAllAdmins()).thenReturn(adminList);

        //Act
        String viewName = adminController.showAdmins(model);

        //Assert
        verify(model,times(1)).addAttribute(Attribute.ADMINS_ATTRIBUTE, adminList);
        assertEquals("Front/admin/admins", viewName,"La page affichée doit être identique.");
    }

    @Test
    void testGetDoctorsByService() {
        //Arrange
        Admin admin = new Admin("Bob", "pass456", "Médecin", "Neurologie", "bob123");
        Admin admin1 = new Admin("Alice", "pass123", "Médecin", "Neurologie", "alice123");
        List<Admin> adminList = Arrays.asList(admin,admin1);
        when(adminService.findAdminBySpeciality("Neurologie")).thenReturn(adminList);

        //Act
        ResponseEntity<List<Admin>> response = adminController.getDoctorsByService("Neurologie");

        //Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(adminList, response.getBody());
    }





}
