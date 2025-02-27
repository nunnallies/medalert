package medalert.controller;
import jakarta.servlet.http.HttpSession;
import medalert.model.Admin;
import medalert.service.VitalSignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import medalert.model.Patient;
import medalert.model.Admin;
import medalert.model.VitalSigns;
import medalert.service.PatientService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;


@Controller
@RequestMapping("/admin/patient/")
public class VitalSignsController {

    @Autowired
    private VitalSignsService vitalSignsService;

    @GetMapping("/vital-signs/{patientId}")
    @ResponseBody
    public List<Map<String, Object>> getVitalSignsFromPatient(@PathVariable int patientId) {
        List<VitalSigns> PatientVitalSigns = vitalSignsService.findConstantesByPatient(patientId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (VitalSigns v : PatientVitalSigns) {
            Map<String, Object> map = new HashMap<>();
            map.put("measureDate",v.getMeasurementdate().toString());
            map.put("temperature",v.getTemperature());
            map.put("pulse",v.getPulse());
            map.put("SpO2",v.getSpO2());
            map.put("bloodGlucose",v.getBloodGlucose());
            map.put("bmi",v.getBmi());
            map.put("albumin", v.getAlbumin());
            map.put("info", "Temp: " + v.getTemperature() + "°C\nPouls: " + v.getPulse() + " bpm\nIMC:"+ v.getBmi()+ "\nSpO2: " + v.getSpO2() + "%\nGlycémie:"+v.getBloodGlucose()+"g.L-1\nAlbumine: " + v.getAlbumin()+"g.L-1\n");
            result.add(map);

        }
        return result;
    }


    @PostMapping("/vital-signs/add")
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
                return "redirect:/connexion";
            }

            Integer adminid = admin.getAdminid();
            VitalSigns vitalSigns = new VitalSigns(adminid,patientid,formattedDate,temperature,SpO2,pulse,bloodGlucose,bmi,albumin);
            addedVitalSigns = vitalSignsService.addConstantes(vitalSigns);
            
        } catch (ParseException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Format de date invalide.");
            return "redirect:/admin/patient-details?id=" + patientid + "#ajout-constantes";
        }

        if ( addedVitalSigns != null ){
            redirectAttributes.addFlashAttribute("successMessage", "Constantes ajoutées avec succès.");
            return "redirect:/admin/patient-details?id="+patientid+"#ajout-constantes";

        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout des constantes.");
            return "redirect:/admin/patient-details?id=" + patientid + "#ajout-constantes";
        }
        
    }
}
