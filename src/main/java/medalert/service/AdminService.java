package medalert.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medalert.model.Admin;
import medalert.repository.AdminRepository;


@Service
public class AdminService {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());
    @Autowired
    private AdminRepository adminRepository;


    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        logger.info("✅ AdminRepository a été injecté dans AdminService !");
    }

    public Optional<Admin> getAdmin(final Integer id){
        return adminRepository.findById(id);
    }

    public List<Admin> getAllAdmins(){
        return ((List<Admin>)adminRepository.findAll());
    }

    public void deleteAdmin(final Integer id){
        adminRepository.deleteById(id);
    }

    public Admin addAdmin(final Admin admin){
        return adminRepository.save(admin);
    }

    public List<Admin> findAdminBySpeciality(String speciality){
        return adminRepository.findBySpeciality(speciality);
    }

    public List<Admin> findAdminByStatus(String status){
        return adminRepository.findByStatus(status);
    }

    public Optional<Admin> verifyAdminCredentials(String identifiant, String password) {
        return adminRepository.findByIdentifiantAndPassword(identifiant, password);
    }
}
