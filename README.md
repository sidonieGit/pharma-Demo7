# Projet Pharma7 - Application de Gestion de Médicaments (Spring XML, HikariCP, Externalisation des Propriétés DB)

Ce dépôt contient la septième itération du projet "Pharma". Cette version améliore la gestion des informations de connexion à la base de données en les **externalisant vers un fichier de propriétés séparé (`datasource.properties`)**. Spring charge ces propriétés et les utilise pour configurer le pool de connexions HikariCP. La configuration des beans reste en XML et l'application interagit avec une base de données MySQL réelle.

## Contexte

- **Pharma1-5** : Progression de l'injection de dépendances et de la configuration Spring.
- **Pharma6** : Première utilisation d'une base de données réelle avec HikariCP et configuration Spring XML, mais les identifiants DB étaient codés en dur dans le fichier XML, nécessitant son exclusion de Git.
- **Pharma7** : **Meilleure gestion de la sécurité et de la configuration** :
  - Les informations sensibles de la base de données (`jdbcUrl`, `username`, `password`) sont déplacées dans `src/main/resources/datasource.properties`.
  - Le fichier `demo-beans.xml` utilise désormais un `PropertySourcesPlaceholderConfigurer` pour lire ces propriétés et les injecter dans le bean `HikariDataSource`.
  - Le fichier `datasource.properties` est exclu du contrôle de version (`.gitignore`) pour des raisons de sécurité.
  - L'application continue d'utiliser HikariCP pour un pool de connexions performant et interagit avec une base de données MySQL réelle.
  - Log4j est toujours intégré pour la journalisation.

## Fonctionnalités (Démo 7)

- **Gestion des Médicaments** : Opérations CRUD (Création, Lecture, Mise à jour, Suppression) entièrement fonctionnelles et persistées dans une base de données MySQL.
- **Configuration de la Base de Données via Fichier de Propriétés** :
  - Les détails de connexion à la base de données sont définis dans `datasource.properties`.
  - Spring injecte dynamiquement ces valeurs dans le bean `HikariDataSource` via un `PropertySourcesPlaceholderConfigurer` configuré dans `demo-beans.xml`.
- **Pool de Connexions HikariCP** : Gestion efficace et performante des connexions à la base de données.
- **Conteneur Spring avec Configuration XML** : Le fichier `demo-beans.xml` définit la structure des beans et leur interconnexion.
- **Gestion des Logs avec Log4j**.

## Technologies Utilisées

- Java (JDK 8 ou supérieur recommandé)
- **Maven** (pour la gestion des dépendances et du build)
- **Spring Framework** (`spring-context`)
- **HikariCP** (pool de connexions JDBC)
- **MySQL Database** (le projet attend une base de données MySQL locale).
- **MySQL Connector/J** (dépendance Maven pour le driver JDBC)
- **Log4j 2** (pour la journalisation)

## Préparation de l'Environnement et Exécution (Instructions pour l'utilisateur)

Pour tester cette application, suivez ces étapes :

1.  **Installer et Démarrer MySQL :** Assurez-vous d'avoir un serveur de base de données MySQL opérationnel et accessible sur `localhost:3306`.

2.  **Créer la Base de Données :** Connectez-vous à MySQL et créez une base de données nommée `syspharma`.

    ```sql
    CREATE DATABASE syspharma;
    USE syspharma;
    ```

3.  **Créer la Table `Medicament` :** Exécutez le script SQL suivant dans votre base de données `syspharma` :

    ```sql
    CREATE TABLE Medicament (
        id INT AUTO_INCREMENT PRIMARY KEY,
        designation VARCHAR(255) NOT NULL,
        prix DOUBLE NOT NULL,
        description TEXT,
        image VARCHAR(255),
        categorie VARCHAR(255)
    );
    ```

4.  **Créer l'Utilisateur MySQL :** Créez un utilisateur MySQL et donnez-lui les droits nécessaires sur la base `syspharma`. Par exemple, si votre utilisateur est `springuser` et son mot de passe est `springpassword` :

    ```sql
    CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'springpassword';
    GRANT ALL PRIVILEGES ON syspharma.* TO 'springuser'@'localhost';
    FLUSH PRIVILEGES;
    ```

    _C'est cet utilisateur et ce mot de passe que vous configurerez dans `datasource.properties`._

5.  **Prérequis Java/Maven :**

    - Assurez-vous d'avoir le [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) installé (version 8 ou supérieure).
    - Assurez-vous d'avoir [Maven](https://maven.apache.org/download.cgi) installé et configuré.

6.  **Cloner le dépôt :**

    ```bash
    git clone https://github.com/sidonieGit/pharma-Demo7.git
    cd Pharma7
    ```

7.  **Créer le fichier `datasource.properties` localement :**

    - Créez un nouveau fichier nommé `datasource.properties` dans le dossier `src/main/resources`.
    - Ajoutez-y le contenu suivant, en remplaçant les placeholders par vos informations MySQL réelles :
      ```properties
      jdbc.driverClassName=com.mysql.cj.jdbc.Driver
      jdbc.url=jdbc:mysql://localhost:3306/syspharma
      jdbc.username=springuser # REMPLACEZ PAR VOTRE UTILISATEUR MYSQL
      jdbc.password=springpassword # REMPLACEZ PAR VOTRE MOT DE PASSE MYSQL
      hikari.maximumPoolSize=10
      hikari.minimumIdle=2
      ```

8.  **Construire le projet et télécharger les dépendances (via Maven) :**

    ```bash
    mvn clean install
    ```

    Cela compilera le code et téléchargera les dépendances nécessaires.

9.  **Exécuter l'application (depuis l'IDE) :**
    - Importez le projet `Pharma7` dans votre IDE (IntelliJ IDEA, Eclipse, VS Code) comme un projet Maven existant.
    - Exécutez la classe `com.sidoCop.sysPharma.launcher.Laucher` en tant qu'application Java.
    - Vous devriez voir les messages de console indiquant le chargement du contexte Spring, l'utilisation du pool de connexions HikariCP, et les interactions réelles avec la base de données. Les logs de Log4j devraient également s'afficher.

## Prochaines Étapes

- **Supprimer `Class.forName` du DAO** : La ligne `Class.forName("com.mysql.cj.jdbc.Driver");` dans `MedicamentDao` est redondante car HikariCP (via Spring) gère déjà le chargement du driver. Elle peut être supprimée en toute sécurité.
- **Utilisation de Spring JDBC Template** : Pour simplifier davantage le code du DAO et réduire le boilerplate JDBC (gestion des `try-catch-finally`, `ResultSet`, `Statement`), vous pouvez introduire `JdbcTemplate` de Spring.
  -intègre des **tests unitaires avec JUnit 5** pour garantir la qualité et la fiabilité du code.
