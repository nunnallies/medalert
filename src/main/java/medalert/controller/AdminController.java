package medalert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import medalert.service.AdminService;
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
    public String login(@RequestParam String identifiant, @RequestParam String password, Model model) {
        Optional<Admin> admin = adminService.verifyAdminCredentials(identifiant, password);

        if (admin.isPresent()) {
            System.out.println("Test admin réussi");
            return "Front/AccueilMedecin";
        } else {
            model.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
            return "Front/connexion";  // Reste sur la même page en affichant l'erreur
        }
    }
}
