package medalert.controller;

import jakarta.servlet.http.HttpSession;
import medalert.constants.Attribute;
import medalert.constants.Message;
import medalert.constants.Redirect;
import medalert.constants.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FAILEDTOLOGIN);
        }
        return ViewNames.VIEW_LOGIN;
    }

    @PostMapping("/login")
    public String login(@RequestParam String identifiant, @RequestParam String password, Model model, HttpSession session) {
        Optional<Admin> adminOptional = adminService.verifyAdminCredentials(identifiant, password);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            session.setAttribute(Attribute.LOGGEDADMIN_ATTRIBUTE, admin);
            return ViewNames.VIEW_DOCTOR_HOMEPAGE;
        } else {
            model.addAttribute(Attribute.ERROR_ATTRIBUTE, Message.ERROR_MESSAGE_FAILEDTOLOGIN);
            return ViewNames.VIEW_LOGIN;
        }
    }


    @GetMapping("/admins")
    public String showAdmins(Model model) {

        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute(Attribute.ADMINS_ATTRIBUTE, admins);
        return "Front/admin/admins";

    }

    @GetMapping("/get-doctors")
    public ResponseEntity<List<Admin>> getDoctorsByService(@RequestParam String service) {
        List<Admin> doctors = adminService.findAdminBySpeciality(service);
        return ResponseEntity.ok(doctors);
    }
}
