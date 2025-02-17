package medalert.controller;

import jakarta.servlet.http.HttpSession;
import medalert.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import medalert.service.AdminService;

import java.util.List;
import java.util.Optional;
import medalert.model.Admin;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
        }
        return "Front/connexion";
    }

    @PostMapping("/login")
    public String login(@RequestParam String identifiant, @RequestParam String password, Model model, HttpSession session) {
        Optional<Admin> adminOptional = adminService.verifyAdminCredentials(identifiant, password);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            session.setAttribute("loggedAdmin", admin);
            return "Front/AccueilMedecin";
        } else {
            model.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
            return "Front/connexion";  // Reste sur la même page en affichant l'erreur
        }
    }

    @GetMapping("/AjoutPatient")
    public String ShowPatientRegistrationForm(HttpSession session) {

        if (session == null){
            return "redirect:/login";
        }
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin.getStatus() == "Médecin") {
            return "Front/admin/add-patient-doctor";
        } else {
            return "Front/admin/add-patient-nurse";
        }
    }

    @GetMapping("/admins")
    public String showAdmins(Model model) {

        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("admins", admins);
        return "Front/admin/admins";

    }
}
