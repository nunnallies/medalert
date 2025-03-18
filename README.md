
![Status](https://img.shields.io/badge/status-WIP-yellow) Medalert

Projet dans le cadre d'une candidature pour un poste de developpeuse java sur la plateforme MEDITWIN. 

Plateforme de gestion des patients avec suivi des constantes, et système d'alertes en fonction des évolutions des constantes. 

Languages : Java , HTML , CSS, Javascript
Dépendances : Spring-boot

Fonctionnalités : 

- Ajout de Patient
- Ecriture dans une BDD locale
- Connexion Admin
- Visualisation de l'ensemble des patients
- Gestion des cookies et sessions 
- Ajout de suivi constantes
- Ajout de Rapport (A faire) 
- Docker file 
- Oauth (A faire)
- Rédaction de tests unitaires (A faire)

Logique de la base de données relationnelle : 



Un admin a un ou plusieurs patient
Un patient est pris en charge par un seul admin
Un patient a une ou plusieurs vitalSigns (pour dresser un dashboard/historique/evolution des vitalSigns)
Un admin peut generer un ou plusieurs report pour un seul patient
Un patient peut avoir un ou plusieurs report
Les vitalSigns sont mesurées par un seul admin qui peut être différent de celui qui prends en charge le patient

![image](https://github.com/user-attachments/assets/318930d2-fd7c-4d08-b0da-bd5d2400bc12)

En cours de développement. Le front est rudimentaire pour l'instant mais sera amélioré lorsque le back-end sera finalisé/bien avancé. 

TBD.
