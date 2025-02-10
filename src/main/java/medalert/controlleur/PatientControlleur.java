package medalert.controlleur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import medalert.model.Patient;
import medalert.service.PatientService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PatientControlleur {

    @Autowired
    private PatientService patientservice;

    @GetMapping("/patients")
    public String afficherPatients(Model model) {
        System.out.println("test réussi 1");
        List<Patient> patients = patientservice.getAllPatients();
        model.addAttribute("patients", patients);
        System.out.println("test réussi 2");

        // Retourner le nom du fichier template Thymeleaf
        return "Front/admin/patients";  // Cette vue sera un fichier HTML dans templates/
    }

}
