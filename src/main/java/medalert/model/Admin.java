package medalert.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Objects;


@Setter @Getter
@Entity
@Table(name="administrateur")
public class Admin {
    @Column(name = "nom")
    private String name;
    @Column(name = "mdp")
    private String password;
    @Column(name="statut")
    private String status;
    @Column(name="specialite")
    private String speciality;
    @Column(name="identifiant")
    private String identifiant;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminid;

    public Admin(){}
    public Admin(String name, String password, String status, String speciality, String identifiant) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.speciality = speciality;
        this.identifiant = identifiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return adminid == admin.adminid &&
                Objects.equals(name, admin.name) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(status, admin.status) &&
                Objects.equals(speciality, admin.speciality) &&
                Objects.equals(identifiant, admin.identifiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminid, name, password, status, speciality, identifiant);
    }



}
