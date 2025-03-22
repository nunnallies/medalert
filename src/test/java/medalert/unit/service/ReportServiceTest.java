package medalert.unit.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    void testGetReport_WhenFound_ReturnsReport(){
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
        assertEquals(report,result.get());
        verify(reportRepository,times(1)).findById(reportId);


    }
}
