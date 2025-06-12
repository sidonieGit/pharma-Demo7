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
- **Spring Framework** (`spring-context`, `spring-jdbc`)
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
        designationCategorie VARCHAR(255)
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
    git clone [https://github.com/votre_utilisateur/Pharma7.git](https://github.com/votre_utilisateur/Pharma7.git)
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
    - **(Optionnel mais recommandé)** : Pour référence, vous pouvez créer un fichier `datasource.properties.example` dans le même dossier `src/main/resources` (qui lui, sera committé sur Git) avec des placeholders pour indiquer la structure attendue.

8.  **Vérifier la configuration XML (`demo-beans.xml`) :**

    - Ouvrez `src/main/resources/demo-beans.xml`. Il devrait contenir la configuration pour charger les propriétés.

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans)"
        xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
        xmlns:context="[http://www.springframework.org/schema/context](http://www.springframework.org/schema/context)"
        xsi:schemaLocation="[http://www.springframework.org/schema/beans](http://www.springframework.org/schema/beans) [http://www.springframework.org/schema/beans/spring-beans.xsd](http://www.springframework.org/schema/beans/spring-beans.xsd)
            [http://www.springframework.org/schema/context](http://www.springframework.org/schema/context) [http://www.springframework.org/schema/context/spring-context.xsd](http://www.springframework.org/schema/context/spring-context.xsd)">

        <context:property-placeholder location="classpath:datasource.properties"/>

        <bean id="medicamentDao" class="com.sidoCop.sysPharma.dao.MedicamentDao" init-method="initialisation" destroy-method="destruction">
          <property name="dataSourceSk" ref="dataSourceSk"></property>
        </bean>

        <bean id="serviceMedicament" class="com.sidoCop.sysPharma.service.ServiceMedicament" init-method="initialisation" destroy-method="destruction">
          <property name="imedicamentDao" ref="medicamentDao" />
        </bean>

        <bean id="dataSourceSk" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
          <property name="driverClassName" value="${jdbc.driverClassName}" />
          <property name="jdbcUrl" value="${jdbc.url}" />
          <property name="username" value="${jdbc.username}" />
          <property name="password" value="${jdbc.password}" />
          <property name="maximumPoolSize" value="${hikari.maximumPoolSize}" />
          <property name="minimumIdle" value="${hikari.minimumIdle}" />
        </bean>
      </beans>
      ```

      _Assurez-vous que l'espace de nommage `context` est bien déclaré dans les balises `<beans>` (`xmlns:context` et `xsi:schemaLocation` pour `spring-context.xsd`)._

9.  **Construire le projet et télécharger les dépendances (via Maven) :**

    ```bash
    mvn clean install
    ```

    Cela compilera le code et téléchargera les dépendances nécessaires.

10. **Exécuter l'application (depuis l'IDE) :**
    - Importez le projet `Pharma7` dans votre IDE (IntelliJ IDEA, Eclipse, VS Code) comme un projet Maven existant.
    - Exécutez la classe `com.sidoCop.sysPharma.launcher.Laucher` en tant qu'application Java.
    - Vous devriez voir les messages de console indiquant le chargement du contexte Spring, l'utilisation du pool de connexions HikariCP, et les interactions réelles avec la base de données. Les logs de Log4j devraient également s'afficher.

## Prochaines Étapes Possibles

- **Supprimer `Class.forName` du DAO** : La ligne `Class.forName("com.mysql.cj.jdbc.Driver");` dans `MedicamentDao` est redondante car HikariCP (via Spring) gère déjà le chargement du driver. Elle peut être supprimée en toute sécurité.
- **Utilisation de Spring JDBC Template** : Pour simplifier davantage le code du DAO et réduire le boilerplate JDBC (gestion des `try-catch-finally`, `ResultSet`, `Statement`), vous pouvez introduire `JdbcTemplate` de Spring.
- **Migration vers Spring Data JPA / Hibernate** : Pour une approche ORM (Object-Relational Mapping) qui abstrait complètement la couche JDBC, permettant une gestion des données orientée objets.
- **Introduction des profils Spring** : Pour gérer différentes configurations (développement, production) plus facilement.
