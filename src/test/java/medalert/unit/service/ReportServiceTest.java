package medalert.unit.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import medalert.model.Report;
import medalert.service.ReportService;
import medalert.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void testGetReport_WhenFound_ReturnsReport() {
        //Arrange
        int reportId = 1;
        Date date = new Date();
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        report.setIdrapport(1);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        //Act
        Optional<Report> result = reportService.getReport(reportId);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(report, result.get());
        verify(reportRepository, times(1)).findById(reportId);


    }

    @Test
    void testGetReport_WhenNotFound_ReturnsEmptyOptional() {
        //Arrange
        int reportId = 77;
        when(reportRepository.findById(anyInt())).thenReturn(Optional.empty());

        //Act
        Optional<Report> result = reportService.getReport(reportId);

        //Assert
        assertFalse(result.isPresent(), "Il ne doit y avoir aucun rapport.");
        verify(reportRepository, times(1)).findById(reportId);
    }

    @Test
    void testGetAllReports_WhenFound_ReturnsAllReports() {
        //Arrange
        Date date = new Date();
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        Report report2 = new Report(2, 1, date, "Ordonnance", "Ceci est une ordonnance");
        List<Report> reports = Arrays.asList(report, report2);
        when(reportRepository.findAll()).thenReturn(reports);

        //Act
        List<Report> result = reportService.getAllReports();

        //Assert
        assertNotNull(result);
        assertEquals(reports.size(), result.size(), "Le nombre de rapports doit être identique");
        assertEquals(reports, result, "Les rapports doivent correspondre");
        verify(reportRepository, times(1)).findAll();

    }

    @Test
    void testGetAllReports_WhenNoneFound_ReturnsEmptyList() {
        //Arrange
        when(reportRepository.findAll()).thenReturn(List.of());

        //Act
        List<Report> result = reportService.getAllReports();

        //Assert
        assertTrue(result.isEmpty(),"Il ne doit y avoir aucun rapport");
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void testDeleteReport_CallsDeleteByIdOnce(){
        //Arrange
        int reportId = 1;

        //Act
        reportService.deleteReport(reportId);

        //Assert
        verify(reportRepository,times(1)).deleteById(1);
    }

    @Test
    void testAddReport_SavesAndReturnsReport(){
        //Arrange
        Date date = new Date();
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        //Act
        Report result = reportService.addReport(report);

        //assert
        assertNotNull(result);
        assertEquals(report,result,"Les rapports doivent correspondre");
        verify(reportRepository,times(1)).save(any(Report.class));

    }

    @Test
    void testFindReportsByAdmin_WhenFound_ReturnsReports(){
        //Arrange
        Date date = new Date();
        int adminId=1;
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        Report report2 = new Report(2, 1, date, "Ordonnance", "Ceci est une ordonnance");
        List<Report> reports = Arrays.asList(report, report2);
        when(reportRepository.findByadminid(adminId)).thenReturn(reports);

        //Act
        List<Report> result = reportService.findReportsByAdmin(adminId);

        //Arrange
        assertNotNull(result);
        assertEquals(reports.size(),result.size(),"Le nombre de rapport doit correspondre");
        assertEquals(reports,result,"Les rapports doivent correspondre");
        verify(reportRepository,times(1)).findByadminid(adminId);


    }

    @Test
    void testFindReportsByAdmin_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        int adminid=99;
        when(reportRepository.findByadminid(adminid)).thenReturn(List.of());

        //Act
        List<Report> result = reportService.findReportsByAdmin(adminid);

        //Assert
        assertTrue(result.isEmpty());
        verify(reportRepository,times(1)).findByadminid(adminid);
    }

    @Test
    void testFindReportsByPatient_WhenFound_ReturnsReports(){
        //Arrange
        Date date = new Date();
        int patientId=1;
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        Report report2 = new Report(1, 1, date, "Ordonnance", "Ceci est une ordonnance");
        List<Report> reports= Arrays.asList(report,report2);
        when(reportRepository.findBypatientid(1)).thenReturn(reports);

        //Act
        List<Report> result = reportService.findReportsByPatient(patientId);

        //Assert
        assertNotNull(result);
        assertEquals(reports.size(),result.size(),"Le nombre de rapport doit correspondre");
        assertEquals(reports,result,"Les rapports doivent correspondre");
        verify(reportRepository,times(1)).findBypatientid(patientId);

    }

    @Test
    void testFindReportsByPatient_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        int patientId=99;
        when(reportRepository.findBypatientid(patientId)).thenReturn(List.of());

        //Act
        List<Report> result = reportService.findReportsByPatient(patientId);

        //Assert
        assertTrue(result.isEmpty());
        verify(reportRepository,times(1)).findBypatientid(patientId);
    }

    @Test
    void testFindReportsByType_WhenFound_ReturnsReports(){
        //Arrange
        Date date = new Date();
        String reportType = "Commentaire";
        Report report = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        Report report2 = new Report(1, 1, date, "Commentaire", "Ceci est une observation");
        List<Report> reports= Arrays.asList(report,report2);
        when(reportRepository.findBytype(reportType)).thenReturn(reports);

        //Act
        List<Report> result = reportService.findReportsByType(reportType);

        //Assert
        assertNotNull(result);
        assertEquals(reports.size(),result.size(),"Le nombre de rapport doit correspondre");
        assertEquals(reports,result,"Les rapports doivent correspondre");
        verify(reportRepository,times(1)).findBytype(reportType);

    }

    @Test
    void testFindReportsByType_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        String reportType = "Commerce";
        when(reportRepository.findBytype(reportType)).thenReturn(List.of());

        //Act
        List<Report> result = reportService.findReportsByType(reportType);

        //Assert
        assertTrue(result.isEmpty());
        verify(reportRepository,times(1)).findBytype(reportType);
    }

}

