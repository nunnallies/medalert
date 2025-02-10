#------------------------------------------------------------
#        Script MySQL.
Logique :
Un admin a un ou plusieurs patient
Un patient est pris en charge par un seul admin
Un patient a une ou plusieurs vitalSigns (pour dresser un dashboard/historique/evolution des vitalSigns)
Un admin peut generer un ou plusieurs report pour un seul patient
Un patient peut avoir un ou plusieurs report
Les vitalSigns sont mesurées par un seul admin qui peut être différent de celui qui prends en charge le patient
#------------------------------------------------------------


#------------------------------------------------------------
# Table: ADMINISTRATEUR
#------------------------------------------------------------

CREATE TABLE ADMINISTRATEUR(
                               adminid     Int  Auto_increment  NOT NULL ,
                               identifiant Varchar (50) NOT NULL ,
                               nom         Varchar (50) NOT NULL ,
                               mdp         Varchar (50) NOT NULL ,
                               statut      Varchar (50) NOT NULL ,
                               specialite  Varchar (50) NOT NULL
    ,CONSTRAINT ADMINISTRATEUR_PK PRIMARY KEY (adminid)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: PATIENT
#------------------------------------------------------------

CREATE TABLE PATIENT(
                        patientid       Int  Auto_increment  NOT NULL ,
                        nom             Varchar (50) NOT NULL ,
                        prenom          Varchar (50) NOT NULL ,
                        service         Varchar (50) NOT NULL ,
                        mail            Varchar (50) NOT NULL ,
                        datedenaissance Date NOT NULL ,
                        adminid         Int NOT NULL
    ,CONSTRAINT PATIENT_PK PRIMARY KEY (patientid)

    ,CONSTRAINT PATIENT_ADMINISTRATEUR_FK FOREIGN KEY (adminid) REFERENCES ADMINISTRATEUR(adminid)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CONSTANTES
#------------------------------------------------------------

CREATE TABLE CONSTANTES(
                           idconstantes Int  Auto_increment  NOT NULL ,
                           datemesure   Date NOT NULL ,
                           temperature  Float ,
                           pouls        Float ,
                           SpO2         Float ,
                           glycemie     Float ,
                           imc          Float ,
                           albumine     Float ,
                           patientid    Int NOT NULL ,
                           adminid      Int NOT NULL
    ,CONSTRAINT CONSTANTES_PK PRIMARY KEY (idconstantes)

    ,CONSTRAINT CONSTANTES_PATIENT_FK FOREIGN KEY (patientid) REFERENCES PATIENT(patientid)
    ,CONSTRAINT CONSTANTES_ADMINISTRATEUR0_FK FOREIGN KEY (adminid) REFERENCES ADMINISTRATEUR(adminid)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: RAPPORTS
#------------------------------------------------------------

CREATE TABLE RAPPORTS(
                         idrapport   Int  Auto_increment  NOT NULL ,
                         daterapport Date NOT NULL ,
                         nomfichier  Varchar (300) NOT NULL ,
                         lien        Varchar (300) NOT NULL ,
                         adminid     Int NOT NULL ,
                         patientid   Int NOT NULL
    ,CONSTRAINT RAPPORTS_PK PRIMARY KEY (idrapport)

    ,CONSTRAINT RAPPORTS_ADMINISTRATEUR_FK FOREIGN KEY (adminid) REFERENCES ADMINISTRATEUR(adminid)
    ,CONSTRAINT RAPPORTS_PATIENT0_FK FOREIGN KEY (patientid) REFERENCES PATIENT(patientid)
)ENGINE=InnoDB;

