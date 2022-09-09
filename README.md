# Openclassrooms - Projet 9 : Développer une solution en microservices de A à Z
## Contexte
Un client souhaite le développement d'une application Web à destination des docteurs afin d'effectuer un suivi de patient dans le dépistage et le traitement du diabète.

### Fonctionnalités
- Gestion des dossiers patient (CRUD)
- Gestion des notes liées au patient (CRUD)
- Dignostiquer les risques de diabète du patient à partir de mots-clé contenus dans les notes

## Choix techniques du Responsable projet
- Architecture en microservices - API RestFull
- Contenairisation des microservices
- Méthodes Agiles

## Processus
### Sprint 1
- Création des microservices (Patient, Note et Diagnostic)
- Création des bases de données :
  - Patient : PostgreSql
  - Notes : MongoDB
- Rédaction et validation des tests unitaires et d'intégration (TDD et Postman)

### Sprint 2
- Création du proxy reliant tous les microservices avec Spring Cloud OpenFeign

![archi_sans_docker](https://user-images.githubusercontent.com/41240871/189359113-dd9b20ad-51a0-41d7-b28f-4d9b1d76fbac.png)

- Contenairisation des microservices
- Contenairisation des bases de données avec la persistance

### Sprint 3
- Implémentation de l'interface Web avec Angular
- Connexion des services Angular avec le proxy 


![archi_avec_docker](https://user-images.githubusercontent.com/41240871/189359184-18820437-15d2-4cbf-82e0-873a2f9d99a6.png)


### Liste des microservices :

<ul>
  <li> <a href="https://github.com/Lyline/patientMicroservice">Patient API</a></li>
  <li> <a href="https://github.com/Lyline/noteMicroservice">Note API</a></li>
  <li> <a href="https://github.com/Lyline/diagnosticMicroservice">Diagnostic API</a></li>
  <li> <a href="https://github.com/Lyline/webDiabetes">Web Diabetes (Angular application)</a></li>
</ul>
