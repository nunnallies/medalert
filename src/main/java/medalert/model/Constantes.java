package medalert.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;  // Si tu veux garder Date, pas nécessaire de changer

@Setter
@Getter
@Entity
@Table(name = "constantes")  // Ajout du nom de la table pour être explicite
public class Constantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idconstantes;  // Utilisation de Integer pour supporter les valeurs null

    private Integer patientid;     // Envisager @ManyToOne si c'est une relation avec Patient
    private Date datemesure;       // Si tu préfères LocalDateTime, fais les ajustements nécessaires

    private float temperature;
    private float SpO2;
    private float pouls;
    private float glycemie;
    private float imc;
    private float albumine;

    private Integer adminid;  // Utilisation de Integer pour supporter les valeurs null

    public Constantes() {}

    public Constantes(Integer adminid, Integer patientid, Date datemesure, float temperature, float SpO2, float pouls,
                      float glycemie, float imc, float albumine) {
        this.adminid = adminid;
        this.patientid = patientid;
        this.datemesure = datemesure;
        this.temperature = temperature;
        this.SpO2 = SpO2;
        this.pouls = pouls;
        this.glycemie = glycemie;
        this.imc = imc;
        this.albumine = albumine;
    }
}

