package medalert.controller;
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

@Controller
@RequestMapping("/admin")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public String afficherPatients(Model model) {
        System.out.println("test réussi 1");
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        System.out.println("test réussi 2");


        return "Front/admin/patients";
    }

    @PostMapping("/add-patient")
    public String addPatient(@RequestParam String lastname, @RequestParam String name, @RequestParam String birthday, @RequestParam String service, @RequestParam String mail){
        Patient addedPatient = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            birthday = birthday.replace("T"," ");
            Date formattedDate = formatter.parse(birthday);
            Patient registeredPatient= new Patient(lastname,name,formattedDate,service,mail,1);
            addedPatient = patientService.addPatient(registeredPatient);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (addedPatient != null) {
            return "redirect:/admin/patients";
        } else {
            return "redirect:/admin/AjoutPatient";
        }
    }}


