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

@Controller
@RequestMapping("/admin")
public class PatientController {
    private static final String PATIENTS_ATTRIBUTE = "patients";
    private static final String PATIENT_ATTRIBUTE = "patient";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ERROR_MESSAGE_LOADINGFAILED = "Une erreur est survenue lors du chargement des patients.";
    private static final String ERROR_MESSAGE_NOTLOGGEDIN = "Merci de vous connecter.";
    private static final String ERROR_MESSAGE_INVALIDDATEFORMAT="Format de date invalide.";
    private static final String ERROR_MESSAGE_PATIENTNOTADDED="Erreur survenue lors de l'ajout du patient";

    @Autowired
    private PatientService patientService;

    @Autowired
    private ReportService reportService;


    @GetMapping("/patients")
    public String showPatients(Model model) {

        try{

        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute(PATIENTS_ATTRIBUTE, patients);
        } catch (Exception e) {
            model.addAttribute(ERROR_ATTRIBUTE,ERROR_MESSAGE_LOADINGFAILED);
        }
        return "Front/admin/patients";

    }

    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String lastname,
                             @RequestParam String name,
                             @RequestParam String birthday,
                             @RequestParam String mail,
                             HttpSession session,
                             RedirectAttributes redirectAttributes){
        Patient addedPatient = null;
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE,ERROR_MESSAGE_NOTLOGGEDIN);
            return "redirect:/connexion";
        }
        String status=admin.getStatus();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            birthday = birthday.replace("T"," ");
            Date formattedDate = formatter.parse(birthday);
            Integer adminid = admin.getAdminid();
            String service = admin.getSpeciality();
            Patient registeredPatient= new Patient(lastname,name,formattedDate,service,mail,adminid);
            addedPatient = patientService.addPatient(registeredPatient);
        } catch (ParseException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE_INVALIDDATEFORMAT);
            return "redirect:/Front/admin/AjoutPatient";
        }
        if (addedPatient != null) {
            redirectAttributes.addFlashAttribute("success", "Patient ajouté avec succès !");
            return "redirect:/admin/patients";
        } else {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE_PATIENTNOTADDED);
            if ("Médecin".equalsIgnoreCase(status)){
                return "redirect:/admin/add-patient-doctor";
            } else {
                return "redirect:/admin/add-patient-nurse";
            }

        }
    }

    @GetMapping("/patient-details")
    public String showPatientDetails(@RequestParam("id") int patientId, Model model) {
        Optional<Patient> patient = patientService.getPatient(patientId);
        if (patient.isEmpty()) {
            return "redirect:/admin/patients";
        }
        model.addAttribute(PATIENT_ATTRIBUTE, patient.get());
        List<Report> patientsReports= reportService.findReportsByPatient(patientId);
        model.addAttribute("patientsReports", patientsReports);
        return "Front/admin/patient-details";
    }

    @GetMapping("/patients-from-service")
    public String showPatientsFromMyService(Model model,
                                            HttpSession session,
                                            RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE_NOTLOGGEDIN);
            return "redirect:/connexion";
        }
        String service = admin.getSpeciality();
        List<Patient> patients = patientService.findPatientsByService(service);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute(PATIENTS_ATTRIBUTE, patients);
        return "Front/admin/patients-from-service";
    }

    @GetMapping("/my-patients")
    public String showMyPatients(Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, ERROR_MESSAGE_NOTLOGGEDIN);
            return "redirect:/connexion";
        }
        Integer adminid = admin.getAdminid();
        List<Patient> patients = patientService.findPatientsByAdmin(adminid);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute(PATIENTS_ATTRIBUTE, patients);
        return "Front/admin/my-patients";
    }




}




