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

    @Autowired
    private PatientService patientService;

    @Autowired
    private ReportService reportService;


    @GetMapping("/patients")
    public String showPatients(Model model) {

        try{

        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur est survenue lors du chargement des patients.");
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
            redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter.");
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
            redirectAttributes.addFlashAttribute("errorMessage", "Format de date invalide.");
            return "redirect:/Front/admin/AjoutPatient";
        }
        if (addedPatient != null) {
            return "redirect:/admin/patients";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout du patient.");
            if (status=="Médecin"){
                return "redirect:/admin/add-patient-doctor";
            } else {
                return "redirect:/admin/add-patient-nurse";
            }

        }
    }

    @GetMapping("/patient-details")
    public String showPatientDetails(@RequestParam("id") int patientId, Model model) {
        Optional<Patient> patient = patientService.getPatient(patientId);
        if (patient == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patient", patient.get());
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
            redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter.");
            return "redirect:/connexion";
        }
        String service = admin.getSpeciality();

        System.out.print("Le service est : "+ service);
        List<Patient> patients = patientService.findPatientsByService(service);
        System.out.print("Les patients sont : "+patients);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patients", patients);
        return "Front/admin/patients-from-service";
    }

    @GetMapping("/my-patients")
    public String showMyPatients(Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter.");
            return "redirect:/connexion";
        }
        Integer adminid = admin.getAdminid();
        List<Patient> patients = patientService.findPatientsByAdmin(adminid);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patients", patients);
        return "Front/admin/my-patients";
    }




}




