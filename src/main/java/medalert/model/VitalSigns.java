package medalert.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;  // Si tu veux garder Date, pas nécessaire de changer

@Setter
@Getter
@Entity
@Table(name = "constantes")  // Ajout du nom de la table pour être explicite
public class VitalSigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconstantes")
    private Integer vitalSignsid;
    @Column(name = "patientid")
    private Integer patientid;
    @Column(name = "datemesure")
    private Date measurementdate;
    private float temperature;
    private float SpO2;
    private float pulse;
    private float bloodGlucose;
    private float bmi;
    private float albumin;
    private Integer adminid;
    public VitalSigns() {}

    public VitalSigns(Integer adminid, Integer patientid, Date measurementdate, float temperature, float SpO2, float pulse,
                      float bloodGlucose, float bmi, float albumin) {
        this.adminid = adminid;
        this.patientid = patientid;
        this.measurementdate = measurementdate;
        this.temperature = temperature;
        this.SpO2 = SpO2;
        this.pulse = pulse;
        this.bloodGlucose = bloodGlucose;
        this.bmi = bmi;
        this.albumin = albumin;
    }


}

