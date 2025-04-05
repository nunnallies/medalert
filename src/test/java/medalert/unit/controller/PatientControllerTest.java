package medalert.unit.controller;

import jakarta.servlet.http.HttpSession;
import medalert.constants.Attribute;
import medalert.constants.Message;
import medalert.constants.Redirect;
import medalert.constants.ViewNames;
import medalert.controller.PatientController;
import medalert.model.Admin;
import medalert.model.Patient;
import medalert.model.Report;
import medalert.service.PatientService;
import medalert.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private ReportService reportService;

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

    @Test
    void testAddPatient_WhenNotLoggedIn_ReturnsLogInView(){
        //Arange
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(null);

        //Act
        String result = patientController.addPatient("Dupont","Rémi","2000-01-01T12:00","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_CONNEXION,result,"L'endpoint de retpur doit être identique");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
    }

    @Test
    void testAddPatient_WhenLastNameEmpty_ReturnsAddPatientFormEndPoint(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);

        //Act
        String result = patientController.addPatient("","Rémi","2000-01-01T12:00","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT,result,"L'endpoint doit être identique");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);


    }

    @Test
    void testAddPatient_WhenNameIsEmpty_ReturnsAddPatientFormEndPoint(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        //Act
        String result = patientController.addPatient("Dupont","","2000-01-01T12:00","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT,result,"L'endpoint doit être identique");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);


    }

    @Test
    void testAddPatient_WhenBirthdayIsEmpty_ReturnsAddPatientFormEndPoint(){

        //Arranage
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);

        //Act
        String result = patientController.addPatient("Dupont","Rémi","","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT,result,"L'endpoint doit être identique");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);


    }

    @Test
    void testAddPatient_WhenMailIsEmpty_ReturnsAddPatientFormEndPoint(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        //Act
        String result = patientController.addPatient("Dupont","Rémi","2000-01-01T12:00","",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT,result,"L'endpoint doit être identique");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);

    }

    @Test
    void testAddPatient_LoggedAsDoctorWhenAdded_ReturnsPatientsEndpoint(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        Patient patient = new Patient("Dupont", "Rémi", new Date(), "Cardiologie", "dr@gmail.com", 1);
        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        //Act
        String result = patientController.addPatient("Dupont","Rémi","2000-01-01T12:00","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_PATIENTS,result,"Les endpoints doivent être identique.");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.SUCCESS_ATTRIBUTE,Message.SUCCESS_MESSAGE_ADDEDPATIENT);

    }

    @Test
    void testAddPatient_LoggedAsDoctorWhenFailToAddPatient_ReturnsPatientsEndpoint(){
        //Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        when(patientService.addPatient(any(Patient.class))).thenReturn(null);

        //Act
        String result = patientController.addPatient("Dupont","Rémi","2000-01-01T12:00","dr@gmail.com",null,null,session,redirectAttributes);

        //Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT,result,"Les endpoints doivent être identique.");
        verify(redirectAttributes,times(1)).addFlashAttribute(Attribute.ERROR_ATTRIBUTE,Message.ERROR_MESSAGE_PATIENTNOTADDED);

    }

    @Test
    void testAddPatient_WhenDateIsInvalid_ReturnsAddPatientFormEndPoint() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);

        String invalidDate = "01-01-2000";
        // Act
        String result = patientController.addPatient("Dupont", "Rémi", invalidDate, "dr@gmail.com", null, null, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT, result, "Les endpoints doivent être identique");
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_INVALIDDATEFORMAT);
    }

    @Test
    void testAddPatient_LoggedAsNurseWhenAdminIdIsNull_ReturnsAddPatientFormEndPoint() {
        // Arrange
        Admin nurse = new Admin("Chris", "pass456", "Infirmier", null, "rchris");
        nurse.setAdminid(2);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(nurse);
        String service = "Cardiologie";

        // Act
        String result = patientController.addPatient("Dupont", "Rémi", "2000-01-01T12:00", "dr@gmail.com", service, null, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);
    }

    @Test
    void testAddPatient_LoggedAsNurseWhenServiceIsNull_ReturnsAddPatientFormEndPoint() {
        // Arrange
        Admin nurse = new Admin("Chris", "pass456", "Infirmier", null, "rchris");
        nurse.setAdminid(2);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(nurse);

        Integer adminid = 1;
        String service = null;

        // Act
        String result = patientController.addPatient("Dupont", "Rémi", "2000-01-01T12:00", "dr@gmail.com", service, adminid, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);
    }

    @Test
    void testAddPatient_LoggedAsNurseWhenServiceIsEmpty_ReturnsAddPatientFormEndPoint() {
        // Arrange
        Admin nurse = new Admin("Chris", "pass456", "Infirmier", null, "rchris");
        nurse.setAdminid(2);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(nurse);

        Integer adminid = 1;
        String service = "";

        // Act
        String result = patientController.addPatient("Dupont", "Rémi", "2000-01-01T12:00", "dr@gmail.com", service, adminid, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);
    }

    @Test
    void testAddPatient_LoggedAsNurseWhenPatientAdded_ReturnsPatientsEndPoint() {
        // Arrange
        Admin nurse = new Admin("Bob", "pass456", "Infirmier", null, "bob");
        nurse.setAdminid(2);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(nurse);
        Patient patient = new Patient("Dupont", "Rémi", new Date(), "Cardiologie", "dr@gmail.com", 1);
        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        Integer adminid = 1;
        String service = "Cardiologie";

        // Act
        String result = patientController.addPatient("Dupont", "Rémi", "2000-01-01T12:00", "dr@gmail.com", service, adminid, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_PATIENTS, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.SUCCESS_ATTRIBUTE, Message.SUCCESS_MESSAGE_ADDEDPATIENT);
    }

    @Test
    void testAddPatient_LoggedAsNurseWhenFailToAddPatient_ReturnsAddPatientFormEndPoint() {
        // Arrange
        Admin nurse = new Admin("Bob", "pass456", "Infirmier", null, "bob");
        nurse.setAdminid(2);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(nurse);
        when(patientService.addPatient(any(Patient.class))).thenReturn(null);

        Integer adminid = 1;
        String service = "Cardiologie";

        // Act
        String result = patientController.addPatient("Dupont", "Rémi", "2000-01-01T12:00", "dr@gmail.com", service, adminid, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_ADD_PATIENT, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_PATIENTNOTADDED);
    }

    @Test
    void testShowPatientDetails_WhenPatientExists_ReturnsPatientView() {
        // Arrange
        int patientId = 1;
        Patient patient = new Patient("Dupont", "Rémi", new Date(), "Cardiologie", "dr@gmail.com", 1);
        when(patientService.getPatient(patientId)).thenReturn(Optional.of(patient));
        List<Report> patientsReports = new ArrayList<>();
        when(reportService.findReportsByPatient(patientId)).thenReturn(patientsReports);


        // Act
        String result = patientController.showPatientDetails(patientId, model);

        // Assert
        assertEquals(ViewNames.VIEW_PATIENT, result);
        verify(model).addAttribute(Attribute.PATIENT_ATTRIBUTE, patient);
        verify(model).addAttribute("patientsReports", patientsReports);
    }

    @Test
    void testShowPatientDetails_WhenPatientDoesNotExist_ReturnsRedirectToPatients() {
        // Arrange
        int patientId = 1;
        when(patientService.getPatient(patientId)).thenReturn(Optional.empty());

        // Act
        String result = patientController.showPatientDetails(patientId, mock(Model.class));

        // Assert
        assertEquals(Redirect.REDIRECT_PATIENTS, result);
    }

    @Test
    void testShowPatientsFromMyService_WhenLoggedInAndPatientsFound_ReturnsPatientsFromService() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        List<Patient> patients = Arrays.asList(new Patient("Dupont", "Rémi", new Date(), "Cardiologie", "dr@gmail.com", 1));
        when(patientService.findPatientsByService(admin.getSpeciality())).thenReturn(patients);



        // Act
        String result = patientController.showPatientsFromMyService(model, session, redirectAttributes);

        // Assert
        assertEquals("Front/admin/patients-from-service", result);
        verify(model).addAttribute(Attribute.PATIENTS_ATTRIBUTE, patients);
    }

    @Test
    void testShowPatientsFromMyService_WhenNotLoggedIn_ReturnsLoginPage() {
        // Arrange
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(null);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Act
        String result = patientController.showPatientsFromMyService(model, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_CONNEXION, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
    }

    @Test
    void testShowPatientsFromMyService_WhenNoPatientsFound_ReturnsRedirectToPatients() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        when(patientService.findPatientsByService(admin.getSpeciality())).thenReturn(null);


        // Act
        String result = patientController.showPatientsFromMyService(model, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_PATIENTS, result);
    }

    @Test
    void testShowMyPatients_LoggedInWhenPatientsFound_ReturnsMyPatientsView() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        List<Patient> patients = Arrays.asList(new Patient("Dupont", "Rémi", new Date(), "Cardiologie", "dr@gmail.com", 1));
        when(patientService.findPatientsByAdmin(admin.getAdminid())).thenReturn(patients);



        // Act
        String result = patientController.showMyPatients(model, session, redirectAttributes);

        // Assert
        assertEquals("Front/admin/my-patients", result);
        verify(model).addAttribute(Attribute.PATIENTS_ATTRIBUTE, patients);
    }

    @Test
    void testShowMyPatients_WhenNotLoggedIn_ReturnsReturnsLogInView() {
        // Arrange
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(null);

        // Act
        String result = patientController.showMyPatients(model, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_CONNEXION, result);
        verify(redirectAttributes).addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
    }

    @Test
    void testShowMyPatients_WhenNoPatientsFound_ReturnsRedirectToPatients() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "aalice");
        admin.setAdminid(1);
        when(session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE)).thenReturn(admin);
        when(patientService.findPatientsByAdmin(admin.getAdminid())).thenReturn(Collections.emptyList());


        // Act
        String result = patientController.showMyPatients(model, session, redirectAttributes);

        // Assert
        assertEquals(Redirect.REDIRECT_PATIENTS, result);
    }













}
