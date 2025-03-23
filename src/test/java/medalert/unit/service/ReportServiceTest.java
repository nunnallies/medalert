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


}

