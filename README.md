# Journal Intime - Mental Health Desktop Application

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Description

Journal Intime est une application de bureau professionnelle pour la santé mentale, développée avec Java 21, Spring Boot 3, et JavaFX. Elle utilise l'intelligence artificielle pour analyser le sentiment et détecter les tendances de burnout, offrant des recommandations personnalisées d'exercices de relaxation.

## ✨ Fonctionnalités Principales

### 🔐 Sécurité
- **Chiffrement AES-256-GCM** pour les données sensibles
- **BCrypt** pour le hachage des mots de passe (12 rounds)
- Bouncy Castle pour une sécurité renforcée

### 📝 Gestion des Notes
- Éditeur Markdown intégré
- Soft delete avec support de restauration
- Recherche full-text avec Hibernate Search/Lucene
- Audit automatique avec Hibernate Envers

### 🧠 Intelligence Artificielle
- Analyse de sentiment basée sur NLP
- Détection automatique des émotions
- Calcul du risque de burnout
- Suivi des tendances d'humeur

### 🧘 Recommandations
- Exercices de relaxation personnalisés
- Règles basées sur l'analyse IA
- Suggestions de méditation, respiration, activités physiques

### 💾 Persistance
- PostgreSQL comme base de données principale
- Hibernate 6 avec JPA
- Repository pattern avec implémentations séparées
- Support transactionnel complet

## 🏗️ Architecture

```
journal-intime/
├── ui/                      # JavaFX Presentation Layer (MVVM)
├── application/            # Use Cases / Services
├── domain/                 # Business Logic (Entities, Enums, Repository Interfaces)
├── persistence/            # Hibernate Implementations
├── infrastructure/         # Technical Configuration & AI
└── shared/                 # DTOs and Mappers
```

### Architecture en Couches

1. **UI (Présentation)**: JavaFX avec AtlantaFX, MVVM pattern
2. **Application**: Services métier et cas d'utilisation
3. **Domain**: Entités JPA, énumérations, interfaces de repository
4. **Persistence**: Implémentations Hibernate des repositories
5. **Infrastructure**: Configuration technique, classification IA
6. **Shared**: DTOs et mappers pour le transfert de données

## 🛠️ Stack Technique

- **Java**: 21 (LTS)
- **Framework**: Spring Boot 3.2.1
- **UI**: JavaFX 21 + AtlantaFX 2.0.1
- **ORM**: Hibernate 6.4.1
- **Base de données**: PostgreSQL 16
- **Audit**: Hibernate Envers
- **Recherche**: Hibernate Search 7.0 + Apache Lucene
- **Sécurité**: Spring Security, Bouncy Castle
- **IA/ML**: Deep Java Library (DJL) - Support BERT/PyTorch
- **Build**: Maven 3.9+
- **Tests**: JUnit 5, Mockito
- **Conteneurisation**: Docker + Docker Compose

## 🚀 Installation

### Prérequis

- Java 21 ou supérieur
- Maven 3.9+
- PostgreSQL 16 (ou Docker)
- Git

### Installation Locale

1. **Cloner le repository**
```bash
git clone https://github.com/0xPacman/Java-APP.git
cd Java-APP
```

2. **Configurer PostgreSQL**

Créer la base de données:
```sql
CREATE DATABASE journal_intime;
CREATE USER journal_user WITH PASSWORD 'journal_pass';
GRANT ALL PRIVILEGES ON DATABASE journal_intime TO journal_user;
```

Ou utiliser Docker Compose (voir section suivante).

3. **Configuration**

Modifier `src/main/resources/application.properties` si nécessaire:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/journal_intime
spring.datasource.username=journal_user
spring.datasource.password=journal_pass
```

4. **Build et exécution**
```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

### Installation avec Docker

1. **Build et démarrage avec Docker Compose**
```bash
docker-compose up -d
```

Cette commande lance:
- PostgreSQL sur le port 5432
- L'application sur le port 8080

2. **Arrêt**
```bash
docker-compose down
```

3. **Avec pgAdmin (optionnel)**
```bash
docker-compose --profile dev up -d
```
Accès pgAdmin: http://localhost:5050

## 📊 Modèle de Données

### Entités Principales

- **User**: Utilisateurs avec authentification
- **Note**: Entrées de journal en Markdown
- **Emotion**: Émotions détectées par IA
- **Exercise**: Exercices de relaxation recommandés
- **MoodAnalysis**: Analyses d'humeur agrégées
- **NoteTag**: Tags pour classifier les notes

### Relations

- User 1:N Note
- Note 1:N Emotion
- User 1:N Exercise
- User 1:N MoodAnalysis
- Note N:N NoteTag

## 🧪 Tests

### Exécuter les tests
```bash
mvn test
```

### Coverage
```bash
mvn test jacoco:report
```

Le rapport sera disponible dans `target/site/jacoco/index.html`.

## 🔒 Sécurité

### Chiffrement
- AES-256-GCM pour le chiffrement des notes sensibles
- Clé unique par utilisateur
- IV aléatoire pour chaque opération

### Authentification
- Mots de passe hachés avec BCrypt (12 rounds)
- Sessions sécurisées avec Spring Security

### Audit
- Toutes les modifications sont tracées avec Hibernate Envers
- Soft delete pour éviter la perte de données

## 🤖 Intelligence Artificielle

### Analyse de Sentiment
- Classification basée sur NLP
- Dictionnaire d'émotions en français
- Score de sentiment de -1 (négatif) à +1 (positif)

### Détection de Burnout
- Analyse des tendances sur 7 jours
- Facteurs: stress, sentiment négatif, fréquence
- Score de risque de 0 (faible) à 1 (élevé)

### Recommandations
- Règles basées sur le profil utilisateur
- 8 catégories d'exercices
- Personnalisation par niveau et durée

## 📈 Roadmap

- [ ] Support OpenAI GPT pour analyse avancée
- [ ] Export PDF des analyses
- [ ] Graphiques et visualisations
- [ ] Mode sombre
- [ ] Support multi-langue (anglais, espagnol)
- [ ] Application mobile (React Native)
- [ ] Synchronisation cloud

## 👥 Contribution

Les contributions sont les bienvenues! Veuillez:

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📝 License

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👨‍💻 Auteurs

- **0xPacman** - *Travail initial* - [GitHub](https://github.com/0xPacman)

## 🙏 Remerciements

- Spring Framework team
- JavaFX community
- AtlantaFX pour le theming moderne
- Hibernate team
- Deep Java Library (DJL) project

## 📞 Support

Pour toute question ou problème:
- Ouvrir une [issue](https://github.com/0xPacman/Java-APP/issues)
- Email: support@journalintime.com

---

**Note**: Cette application est conçue comme un outil de bien-être personnel et ne remplace pas un avis médical professionnel. En cas de problèmes de santé mentale graves, veuillez consulter un professionnel de santé.