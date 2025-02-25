package medalert.controller;
import jakarta.servlet.http.HttpSession;
import medalert.model.Admin;
import medalert.service.VitalSignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import medalert.model.Patient;
import medalert.model.Admin;
import medalert.model.VitalSigns;
import medalert.service.PatientService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.util.Optional;


@Controller
@RequestMapping("/admin/patient/")
public class VitalSignsController {

    @Autowired
    private VitalSignsService vitalSignsService;

    public void getVitalSignsFromPatient(Model model,int patientId) {
        List<VitalSigns> PatientVitalSigns = vitalSignsService.findConstantesByPatient(patientId);
        model.addAttribute("PatientVitalSigns", PatientVitalSigns);

    }

    public String AddVitalSigns(Model model,
                                HttpSession session,
                                @RequestParam String measureDate,
                                @RequestParam float SpO2,
                                @RequestParam float temperature,
                                @RequestParam float bmi,
                                @RequestParam float pulse,
                                @RequestParam float bloodGlucose,
                                @RequestParam float albumin,
                                @RequestParam int patientid,
                                RedirectAttributes redirectAttributes){
        VitalSigns addedVitalSigns = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            measureDate = measureDate.replace("T"," ");
            Date formattedDate = formatter.parse(measureDate);
            Admin admin = (Admin) session.getAttribute("loggedAdmin");
            if (admin == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Merci de vous connecter.");
                return "redirect:/login";
            }

            Integer adminid = admin.getAdminid();
            VitalSigns vitalSigns = new VitalSigns(adminid,patientid,formattedDate,temperature,SpO2,pulse,bloodGlucose,bmi,albumin);
            addedVitalSigns = vitalSignsService.addConstantes(vitalSigns);
            
        } catch (ParseException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Format de date invalide.");
            return "redirect:/Front/admin/patient-details?id=" + patientid + "#ajout-constantes";
        }

        if ( addedVitalSigns != null ){
            redirectAttributes.addFlashAttribute("successMessage", "Patient ajouté avec succès.");
            return "redirect:/Front/admin/patient-details?id="+patientid+"#ajout-constantes";

        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout des constantes.");
            return "redirect:/Front/admin/patient-details?id=" + patientid + "#ajout-constantes";
        }
        
    }
}
