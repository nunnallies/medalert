
![Status](https://img.shields.io/badge/status-WIP-yellow) Medalert

Projet dans le cadre d'une candidature pour un poste de developpeuse java sur la plateforme MEDITWIN. 

Plateforme de gestion des patients avec suivi des constantes, et système d'alertes en fonction des évolutions des constantes. 

Languages : Java , HTML , CSS, Javascript
Dépendances : Spring-boot, Jacoco, SonarQube

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
- Rédaction de tests unitaires (En cours)

# Couverture du Code - Medalert

Ce projet utilise JaCoCo pour mesurer la couverture des tests automatisés du code source. Ce tableau présente la couverture actuelle et les domaines où des améliorations sont possibles. L'objectif est d'assurer que notre code est suffisamment testé et robuste pour une utilisation en production.

## Résumé de la Couverture de Code

| **Élément**              | **Instructions Manquantes** | **Couverture des Instructions (%)** | **Branches Manquantes** | **Couverture des Branches (%)** | **Méthodes Manquantes** | **Couverture des Méthodes** | **Lignes Manquantes** | **Couverture des Lignes** | **Classes Manquantes** | **Couverture des Classes** |
|--------------------------|-----------------------------|-------------------------------------|--------------------------|---------------------------------|-------------------------|----------------------------|------------------------|---------------------------|------------------------|---------------------------|
| **medalert.model**        | 311 / 188                   | 37%                                 | 60 / 16                  | 21%                             | 43                      | 56%                        | 29                     | 81                       | 6                      | 6%                        |
| **medalert.controller**   | 296 / 254                   | 46%                                 | 18 / 30                  | 62%                             | 19                      | 45%                        | 69                     | 136                     | 10                     | 5%                        |
| **medalert**              | 5 / 7                       | 58%                                 | n/a                      | n/a                             | 1                       | 3%                         | 2                      | 5                       | 1                      | 1%                        |
| **medalert.service**      | 152 / 0                     | 100%                                | n/a                      | n/a                             | 0                       | 28%                        | 0                      | 0%                        | 0                      | 4%                        |
| **Total**                | **612 / 1,213**             | **49%**                             | **78 / 124**             | **37%**                         | **63**                  | **132**                    | **100**                | **257**                   | **17**                 | **70**                    |

## Explications

- **Lignes Totales** : Nombre total de lignes de code dans le package.
- **Couverture (%)** : Pourcentage de lignes couvertes par des tests automatisés.
- **Instructions Manquantes** : Nombre d'instructions non couvertes.
- **Branches Manquantes** : Nombre de branches de code (comme les conditions) non couvertes.
- **Lignes Manquantes** : Nombre total de lignes non couvertes par les tests.
- **Méthodes Manquantes** : Nombre de méthodes non couvertes par les tests.
- **Classes Manquantes** : Nombre de classes non couvertes par les tests.
- **Complexité Manquante** : Nombre de points de complexité cyclomatique non couverts.

## Objectif 

Couverture du code >= 80%
  
# Base de donnée - SQL
Logique de la base de données relationnelle actuelle : 



Un admin a un ou plusieurs patient
Un patient est pris en charge par un seul admin
Un patient a une ou plusieurs vitalSigns (pour dresser un dashboard/historique/evolution des vitalSigns)
Un admin peut generer un ou plusieurs report pour un seul patient
Un patient peut avoir un ou plusieurs report
Les vitalSigns sont mesurées par un seul admin qui peut être différent de celui qui prends en charge le patient

![image](https://github.com/user-attachments/assets/318930d2-fd7c-4d08-b0da-bd5d2400bc12)

En cours de développement. Le front est rudimentaire pour l'instant mais sera amélioré lorsque le back-end sera finalisé/bien avancé. 

TBD.
