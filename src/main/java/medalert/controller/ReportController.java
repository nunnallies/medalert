package medalert.controller;
import jakarta.servlet.http.HttpSession;
import medalert.model.Admin;
import medalert.model.Report;
import medalert.service.ReportService;
import medalert.service.VitalSignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

@Controller
@RequestMapping("/admin/")
public class ReportController {
    @Autowired
    private ReportService reportService;

    public void getReportFromPatient(Model model,
                                     @RequestParam int patientid){
        List<Report> reports= reportService.findReportsByPatient(patientid);
        model.addAttribute("reports", reports);

    }
}
