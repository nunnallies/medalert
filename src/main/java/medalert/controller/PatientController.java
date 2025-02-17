package medalert.controller;
import jakarta.servlet.http.HttpSession;
import medalert.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import medalert.model.Patient;
import medalert.service.PatientService;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/patients")
    public String showPatients(Model model) {

        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "Front/admin/patients";

    }

    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String lastname, @RequestParam String name, @RequestParam String birthday, @RequestParam String mail,HttpSession session){
        Patient addedPatient = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            birthday = birthday.replace("T"," ");
            Date formattedDate = formatter.parse(birthday);
            Admin admin = (Admin) session.getAttribute("loggedAdmin");
            Integer adminid = admin.getAdminid();
            String service = admin.getSpeciality();
            Patient registeredPatient= new Patient(lastname,name,formattedDate,service,mail,adminid);
            addedPatient = patientService.addPatient(registeredPatient);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (addedPatient != null) {
            return "redirect:/admin/patients";
        } else {
            return "redirect:/admin/AjoutPatient";
        }
    }

    @GetMapping("/patient-details")
    public String showPatientDetails(@RequestParam("id") int patientId, Model model) {
        Optional<Patient> patient = patientService.getPatient(patientId);
        if (patient == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patient", patient.get());
        return "Front/admin/patient-details";
    }

    @GetMapping("/patients-from-service")
    public String showPatientsFromMyService(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        String service = admin.getSpeciality();
        List<Patient> patients = patientService.findPatientsByService(service);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patient", patients);
        return "Front/admin/patient-details";
    }

    @GetMapping("/my-patients")
    public String showMyPatients(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        Integer adminid = admin.getAdminid();
        List<Patient> patients = patientService.findPatientsByAdmin(adminid);
        if (patients == null) {
            return "redirect:/admin/patients";
        }
        model.addAttribute("patient", patients);
        return "Front/admin/my-patients";
    }




}




