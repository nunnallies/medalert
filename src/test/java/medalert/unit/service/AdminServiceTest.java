package medalert.unit.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import medalert.model.Admin;
import medalert.service.AdminService;
import medalert.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;


    // Structure AAA Arrange - Act - Assert
    @Test
    void testGetAdmin_WhenFound_ReturnsAdmin() {
        // Arrange
        Admin admin = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "alice123");
        admin.setAdminid(1);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        // Act
        Optional<Admin> result = adminService.getAdmin(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
        verify(adminRepository, times(1)).findById(1);
    }
    @Test
    void testGetAdmin_WhenNotFound_ReturnsEmptyOptional() {
        // Arrange
        when(adminRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Admin> result = adminService.getAdmin(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(adminRepository, times(1)).findById(999);
    }

    @Test
    void testAddAdmin_SavesAndReturnsAdmin() {
        // Arrange
        Admin admin = new Admin("Bob", "passe456", "Médecin", "Neurologie", "bob123");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // Act
        Admin savedAdmin = adminService.addAdmin(admin);

        // Assert
        assertNotNull(savedAdmin);
        assertEquals("Bob", savedAdmin.getName());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testGetAllAdmins_ReturnsAllAdmins() {
        //Arrange
        Admin admin = new Admin("Bob", "passe456", "Médecin", "Neurologie", "bob123");
        Admin admin1 = new Admin("Alice", "passe123", "Médecin", "Cardiologie", "alice123");
        List<Admin> adminList = Arrays.asList(admin,admin1);
        when(adminRepository.findAll()).thenReturn(adminList);

        //Act
        List<Admin> result = adminService.getAllAdmins();

        //Assert
        assertNotNull(result, "La liste ne doit pas être nulle");
        assertEquals(2, result.size(), "La liste doit contenir 2 admins");
        assertEquals(adminList, result, "Les admins de la liste doivent correspondre");
        verify(adminRepository, times(1)).findAll();


    }
    @Test
    void testFindAdminBySpeciality_WhenFound_ReturnsAdmins(){
        //Arrange
        String specialty="Neurologie";
        Admin admin = new Admin("Bob", "pass456", "Médecin", "Neurologie", "bob123");
        Admin admin1 = new Admin("Alice", "pass123", "Médecin", "Neurologie", "alice123");
        List<Admin> adminList = Arrays.asList(admin,admin1);
        when(adminRepository.findBySpeciality(specialty)).thenReturn(adminList);

        //Act
        List<Admin> result = adminService.findAdminBySpeciality(specialty);

        //Assert
        assertNotNull(result, "La liste ne doit pas être nulle");
        assertEquals(adminList, result, "Les admins de la liste doivent correspondre");
        assertEquals(2, result.size(),"La liste doit contenir 2 admins");
        verify(adminRepository, times(1)).findBySpeciality(specialty);

    }
    @Test
    void testFindAdminBySpeciality_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        String speciality="Cardiologie";
        when(adminRepository.findBySpeciality(speciality)).thenReturn(Collections.emptyList());

        //Act
        List<Admin> result =  adminService.findAdminBySpeciality(speciality);

        //Assert
        assertTrue(result.isEmpty());
        verify(adminRepository, times(1)).findBySpeciality(speciality);

    }

    @Test
    void testFindAdminByStatus_WhenFound_ReturnsAdmins(){
        //Arrange
        String status="Médecin";
        Admin admin = new Admin("Bob", "pass456", "Médecin", "Neurologie", "bob123");
        Admin admin1 = new Admin("Alice", "pass123", "Médecin", "Neurologie", "alice123");
        List<Admin> adminList = Arrays.asList(admin,admin1);
        when(adminRepository.findByStatus(status)).thenReturn(adminList);

        //Act
        List<Admin> result = adminService.findAdminByStatus(status);

        //Assert
        assertNotNull(result, "La liste ne doit pas être nulle");
        assertEquals(adminList, result, "Les admins de la liste doivent correspondre");
        assertEquals(2, result.size(),"La liste doit contenir 2 admins");
        verify(adminRepository, times(1)).findByStatus(status);


    }

    @Test
    void testFindByStatus_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        String status="Actif";
        when(adminRepository.findByStatus(status)).thenReturn(Collections.emptyList());

        //Act
        List<Admin> result = adminService.findAdminByStatus(status);

        //Assert
        assertTrue(result.isEmpty());
        verify(adminRepository, times(1)).findByStatus(status);


    }

    @Test
    void testVerifyAdminCredentials_WhenFound_ReturnsAdmin(){
        //Arrange
        String identifiant = "alice123";
        String password = "passe123";
        Admin admin = new Admin("Alice", "passe123", "Active", "Cardiologie", "alice123");
        when(adminRepository.findByIdentifiantAndPassword(identifiant, password)).thenReturn(Optional.of(admin));

        //Act
        Optional<Admin> result = adminService.verifyAdminCredentials(identifiant, password);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
        verify(adminRepository, times(1)).findByIdentifiantAndPassword(identifiant, password);

    }

    @Test
    void testVerifyAdminCredentials_WhenNoneFound_ReturnsEmptyList(){
        //Arrange
        String identifiant = "alice123";
        String password = "paslemdp";
        when(adminRepository.findByIdentifiantAndPassword(identifiant, password)).thenReturn(Optional.empty());

        //Act
        Optional<Admin> result = adminService.verifyAdminCredentials(identifiant, password);

        //Assert
        assertFalse(result.isPresent());
        verify(adminRepository, times(1)).findByIdentifiantAndPassword(identifiant, password);


    }

    @Test
    void testDeleteAdmin_CallsDeleteByIdOnce() {
        // Arrange
        Integer adminId = 1;

        // Act
        adminService.deleteAdmin(adminId);

        // Assert
        verify(adminRepository, times(1)).deleteById(adminId);
    }
}
