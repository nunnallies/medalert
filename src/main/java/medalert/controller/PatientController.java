package medalert.controller;
import jakarta.servlet.http.HttpSession;
import medalert.model.Admin;
import medalert.model.Report;
import medalert.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import medalert.model.Patient;
import medalert.service.PatientService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.util.Optional;
import medalert.constants.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
@RequestMapping("/admin")
public class PatientController {


    @Autowired
    private PatientService patientService;

    @Autowired
    private ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);


    @GetMapping("/patients")
    public String showPatients(Model model) {

        try{

        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute(Attribute.PATIENTS_ATTRIBUTE, patients);
        } catch (Exception e) {
            model.addAttribute(Attribute.ERROR_ATTRIBUTE,Message.ERROR_MESSAGE_LOADINGFAILED);
        }
        return ViewNames.VIEW_PATIENTS;

    }

    @GetMapping("/add-patient-form")
    public String returnViewAddPatient(HttpSession session,RedirectAttributes redirectAttributes) {
        Admin admin = (Admin) session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE);
        if (admin == null) {
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE,Message.ERROR_MESSAGE_NOTLOGGEDIN);
            return Redirect.REDIRECT_CONNEXION;
        }
        String status=admin.getStatus();

        return "Médecin".equalsIgnoreCase(status) ? ViewNames.VIEW_ADD_PATIENT_AS_DOCTOR : ViewNames.VIEW_ADD_PATIENT_AS_NURSE;

    }
    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String lastname,
                             @RequestParam String name,
                             @RequestParam String birthday,
                             @RequestParam String mail,
                             @RequestParam(required = false) String service,
                             @RequestParam(required = false) Integer adminid,
                             HttpSession session,
                             RedirectAttributes redirectAttributes){
        Patient addedPatient;
        Admin admin = (Admin) session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE);
        if (admin == null) {
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE,Message.ERROR_MESSAGE_NOTLOGGEDIN);
            return Redirect.REDIRECT_CONNEXION;
        }
        if (lastname.trim().isEmpty() || name.trim().isEmpty() || mail.trim().isEmpty() || birthday.trim().isEmpty()){
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);
            return Redirect.REDIRECT_ADD_PATIENT;
        }
        String status=admin.getStatus();
        Integer assignedAdminId = admin.getAdminid();
        String assignedService = admin.getSpeciality();

        if ("Infirmier".equalsIgnoreCase(status)) {
            if ( service==null || service.trim().isEmpty() || adminid == null) {
                redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FIELDSREQUIRED);
                return Redirect.REDIRECT_ADD_PATIENT;
            }
            assignedAdminId = adminid;
            assignedService = service;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date formattedDate = formatter.parse(birthday.replace("T"," "));
            Patient registeredPatient = new Patient(lastname, name, formattedDate, assignedService, mail, assignedAdminId);
            addedPatient = patientService.addPatient(registeredPatient);
        } catch (ParseException e) {
            logger.error("Erreur lors du parsing de la date", e);
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_INVALIDDATEFORMAT);
            return Redirect.REDIRECT_ADD_PATIENT;
        }

        if (addedPatient != null) {
            redirectAttributes.addFlashAttribute(Attribute.SUCCESS_ATTRIBUTE, Message.SUCCESS_MESSAGE_ADDEDPATIENT);
            return Redirect.REDIRECT_PATIENTS;
        }

        redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_PATIENTNOTADDED);
        return Redirect.REDIRECT_ADD_PATIENT;
    }


    @GetMapping("/patient-details")
    public String showPatientDetails(@RequestParam("id") int patientId, Model model) {
        Optional<Patient> patient = patientService.getPatient(patientId);
        if (patient.isEmpty()) {
            return Redirect.REDIRECT_PATIENTS;
        }
        model.addAttribute(Attribute.PATIENT_ATTRIBUTE, patient.get());
        List<Report> patientsReports= reportService.findReportsByPatient(patientId);
        model.addAttribute("patientsReports", patientsReports);
        return ViewNames.VIEW_PATIENT;
    }

    @GetMapping("/patients-from-service")
    public String showPatientsFromMyService(Model model,
                                            HttpSession session,
                                            RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE );
        if (admin == null) {
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
            return Redirect.REDIRECT_CONNEXION;
        }
        String service = admin.getSpeciality();
        List<Patient> patients = patientService.findPatientsByService(service);
        if (patients == null) {
            return Redirect.REDIRECT_PATIENTS;
        }
        model.addAttribute(Attribute.PATIENTS_ATTRIBUTE, patients);
        return "Front/admin/patients-from-service";
    }

    @GetMapping("/my-patients")
    public String showMyPatients(Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) session.getAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE);
        if (admin == null) {
            redirectAttributes.addFlashAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_NOTLOGGEDIN);
            return Redirect.REDIRECT_CONNEXION;
        }
        Integer adminid = admin.getAdminid();
        List<Patient> patients = patientService.findPatientsByAdmin(adminid);
        if (patients.isEmpty()) {
            return Redirect.REDIRECT_PATIENTS;
        }
        model.addAttribute(Attribute.PATIENTS_ATTRIBUTE, patients);
        return "Front/admin/my-patients";
    }








}




