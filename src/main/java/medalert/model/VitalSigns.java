package medalert.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;  // Si tu veux garder Date, pas nécessaire de changer
import java.util.Objects;

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
    @Column(name = "SpO2")
    private float SpO2;
    @Column(name = "pouls")
    private float pulse;
    @Column(name = "glycemie")
    private float bloodGlucose;
    @Column(name = "imc")
    private float bmi;
    @Column(name = "albumine")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VitalSigns that = (VitalSigns) o;
        return Float.compare(that.temperature, temperature) == 0 &&
                Float.compare(that.SpO2, SpO2) == 0 &&
                Float.compare(that.pulse, pulse) == 0 &&
                Float.compare(that.bloodGlucose, bloodGlucose) == 0 &&
                Float.compare(that.bmi, bmi) == 0 &&
                Float.compare(that.albumin, albumin) == 0 &&
                Objects.equals(patientid, that.patientid) &&
                Objects.equals(measurementdate, that.measurementdate) &&
                Objects.equals(adminid, that.adminid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientid, measurementdate, temperature, SpO2, pulse, bloodGlucose, bmi, albumin, adminid);
    }


}

