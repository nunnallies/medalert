package medalert.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import medalert.model.Admin;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    List<Admin> findBySpecialite(String specialite);
    List<Admin> findByStatut(String statut);
    Optional<Admin> findByIdentifiantAndPassword(String identifiant, String password);
}

