# Journal Intime - Architecture et Structure

## 📁 Structure du Projet

```
journal-intime/
│
├── pom.xml                         # Configuration Maven
├── Dockerfile                      # Build multi-stage avec Alpine
├── docker-compose.yml             # Orchestration PostgreSQL + App
├── README.md                      # Documentation complète
├── .gitignore                     # Fichiers ignorés par Git
│
├── src/
│   ├── main/
│   │   ├── java/com/journalintime/
│   │   │   │
│   │   │   ├── JournalIntimeApplication.java    # Point d'entrée JavaFX + Spring Boot
│   │   │   │
│   │   │   ├── ui/                               # Couche Présentation (JavaFX - MVVM)
│   │   │   │   ├── MainUI.java                   # Interface principale avec AtlantaFX
│   │   │   │   ├── controller/                   # Contrôleurs JavaFX (à venir)
│   │   │   │   ├── viewmodel/                    # ViewModels MVVM (à venir)
│   │   │   │   └── service/                      # Services UI (à venir)
│   │   │   │
│   │   │   ├── application/                      # Couche Application (Cas d'utilisation)
│   │   │   │   └── service/
│   │   │   │       ├── NoteService.java          # Gestion des notes avec analyse IA
│   │   │   │       ├── EmotionService.java       # Gestion des émotions
│   │   │   │       ├── AnalysisService.java      # Analyse des tendances et burnout
│   │   │   │       └── RecommendationService.java # Recommandations d'exercices
│   │   │   │
│   │   │   ├── domain/                           # Couche Domaine (Métier pur)
│   │   │   │   │
│   │   │   │   ├── entity/                       # Entités JPA
│   │   │   │   │   ├── BaseEntity.java          # Classe de base (soft delete, audit)
│   │   │   │   │   ├── User.java                # Utilisateur
│   │   │   │   │   ├── Note.java                # Note de journal (Markdown)
│   │   │   │   │   ├── Emotion.java             # Émotion détectée
│   │   │   │   │   ├── Exercise.java            # Exercice de relaxation
│   │   │   │   │   ├── MoodAnalysis.java        # Analyse d'humeur
│   │   │   │   │   └── NoteTag.java             # Tag de classification
│   │   │   │   │
│   │   │   │   ├── enums/                        # Énumérations métier
│   │   │   │   │   ├── EmotionType.java         # Types d'émotions
│   │   │   │   │   ├── MoodLevel.java           # Niveaux d'humeur
│   │   │   │   │   ├── ExerciseCategory.java    # Catégories d'exercices
│   │   │   │   │   ├── ExerciseLevel.java       # Niveaux de difficulté
│   │   │   │   │   └── ExerciseDuration.java    # Durées d'exercices
│   │   │   │   │
│   │   │   │   └── repository/                   # Interfaces de repository
│   │   │   │       ├── UserRepository.java
│   │   │   │       ├── NoteRepository.java
│   │   │   │       ├── EmotionRepository.java
│   │   │   │       ├── ExerciseRepository.java
│   │   │   │       └── MoodAnalysisRepository.java
│   │   │   │
│   │   │   ├── persistence/                      # Couche Persistence (Implémentations)
│   │   │   │   └── hibernate/                    # Implémentations Hibernate
│   │   │   │       ├── UserRepositoryImpl.java
│   │   │   │       ├── NoteRepositoryImpl.java
│   │   │   │       ├── EmotionRepositoryImpl.java
│   │   │   │       ├── ExerciseRepositoryImpl.java
│   │   │   │       └── MoodAnalysisRepositoryImpl.java
│   │   │   │
│   │   │   ├── infrastructure/                   # Couche Infrastructure (Technique)
│   │   │   │   │
│   │   │   │   ├── config/                       # Configuration
│   │   │   │   │   ├── ApplicationConfig.java   # Config Spring Boot principale
│   │   │   │   │   ├── JpaConfig.java           # Configuration JPA/Hibernate
│   │   │   │   │   ├── DataSourceConfig.java    # Configuration PostgreSQL
│   │   │   │   │   └── SecurityConfig.java      # Configuration sécurité (BCrypt)
│   │   │   │   │
│   │   │   │   ├── ia/                           # Intelligence Artificielle
│   │   │   │   │   ├── TextProcessor.java       # Traitement de texte NLP
│   │   │   │   │   ├── EmotionKeywordDictionary.java  # Dictionnaire français
│   │   │   │   │   ├── EmotionClassifier.java   # Classification d'émotions
│   │   │   │   │   ├── EmotionScoreCalculator.java    # Calcul de scores
│   │   │   │   │   ├── MoodEvaluator.java       # Évaluation d'humeur
│   │   │   │   │   └── ClassificationResult.java      # Résultat de classification
│   │   │   │   │
│   │   │   │   └── security/                     # Sécurité
│   │   │   │       └── EncryptionService.java   # Chiffrement AES-256-GCM
│   │   │   │
│   │   │   └── shared/                           # Couche Partagée
│   │   │       ├── dto/                          # Data Transfer Objects (à venir)
│   │   │       └── mapper/                       # Mappers Entity <-> DTO (à venir)
│   │   │
│   │   └── resources/
│   │       ├── fxml/                             # Fichiers FXML JavaFX (à venir)
│   │       ├── styles/                           # CSS pour JavaFX (à venir)
│   │       ├── application.properties            # Configuration Spring Boot
│   │       └── persistence.xml                   # Configuration JPA
│   │
│   └── test/
│       └── java/com/journalintime/
│           ├── JournalIntimeApplicationTests.java        # Tests basiques
│           └── infrastructure/ia/
│               ├── EmotionClassifierTest.java            # Tests de classification
│               └── TextProcessorTest.java                # Tests de traitement texte
│
└── target/                                       # Artefacts de build Maven
```

## 🏛️ Principes Architecturaux

### 1. Architecture en Couches (Layered Architecture)

**UI (Présentation)**
- Responsabilité: Interface utilisateur, interaction avec l'utilisateur
- Technologies: JavaFX 21, AtlantaFX
- Pattern: MVVM (Model-View-ViewModel)

**Application (Cas d'utilisation)**
- Responsabilité: Logique applicative, orchestration des opérations
- Services métier: NoteService, EmotionService, AnalysisService, RecommendationService
- Transactions et coordination

**Domain (Métier)**
- Responsabilité: Modèle métier, règles métier
- Entités JPA avec relations
- Interfaces de repository (abstraction)
- Énumérations métier

**Persistence**
- Responsabilité: Accès aux données
- Implémentations Hibernate des repositories
- Requêtes JPQL/HQL

**Infrastructure**
- Responsabilité: Configuration technique, services transversaux
- Configuration Spring Boot, JPA, Security
- AI/ML avec classification NLP
- Chiffrement et sécurité

### 2. Domain-Driven Design (DDD)

- **Entities**: Classes métier avec identité (User, Note, Emotion, etc.)
- **Value Objects**: Énumérations (EmotionType, MoodLevel, etc.)
- **Repositories**: Abstraction de la persistance
- **Services**: Logique métier complexe

### 3. Repository Pattern

- **Interfaces dans domain**: Contrat métier, indépendant de l'implémentation
- **Implémentations dans persistence**: Détails techniques Hibernate
- **Séparation des préoccupations**: Le domaine ne connaît pas Hibernate

### 4. Dependency Inversion Principle (SOLID)

- Les couches hautes ne dépendent pas des couches basses
- Dépendance sur des abstractions (interfaces)
- Spring Boot gère l'injection de dépendances

## 🔑 Fonctionnalités Clés Implémentées

### ✅ Sécurité
- **AES-256-GCM**: Chiffrement des notes sensibles avec IV aléatoire
- **BCrypt (12 rounds)**: Hachage des mots de passe
- **Bouncy Castle**: Provider de sécurité renforcée
- **Spring Security**: Gestion de l'authentification

### ✅ Persistance
- **Hibernate 6**: ORM moderne avec JPA
- **Hibernate Envers**: Audit automatique de toutes les modifications
- **Hibernate Search + Lucene**: Recherche full-text performante
- **Soft Delete**: Suppression logique avec restauration possible
- **PostgreSQL**: Base de données relationnelle robuste

### ✅ Intelligence Artificielle
- **Analyse de sentiment**: NLP basé sur dictionnaire de mots-clés français
- **Classification d'émotions**: 10 types d'émotions détectées
- **Détection de burnout**: Analyse des tendances sur 7 jours
- **Calcul de risque**: Score de 0 à 1 basé sur stress et sentiment
- **Support DJL**: Infrastructure pour modèles BERT/PyTorch (extensible)

### ✅ Recommandations
- **Règles métier**: Basées sur analyse IA
- **8 catégories d'exercices**: Respiration, méditation, activité physique, etc.
- **Personnalisation**: Par niveau et durée
- **Déclencheurs automatiques**: Stress élevé, burnout, sentiment négatif

### ✅ Interface Utilisateur
- **JavaFX 21**: Framework UI moderne et performant
- **AtlantaFX**: Thème moderne et élégant
- **MVVM**: Architecture claire et testable
- **Responsive**: Interface adaptative

### ✅ DevOps
- **Docker Multi-stage**: Build optimisé avec Alpine Linux
- **Docker Compose**: Orchestration PostgreSQL + App + pgAdmin
- **Maven**: Build automation avec gestion des dépendances
- **JUnit 5**: Infrastructure de tests unitaires

## 📊 Modèle de Données

### Entités et Relations

```
User (1) ----< (N) Note
                   |
                   +----< (N) Emotion
                   |
                   +----M (M) NoteTag

User (1) ----< (N) Exercise

User (1) ----< (N) MoodAnalysis
```

### Caractéristiques Techniques

- **Soft Delete**: Toutes les entités héritent de BaseEntity
- **Audit**: Hibernate Envers trace toutes les modifications
- **Timestamps**: createdAt, updatedAt automatiques
- **Cascade**: Gestion automatique des relations
- **Lazy Loading**: Optimisation des performances

## 🚀 Commandes Utiles

### Développement

```bash
# Compiler le projet
mvn clean compile

# Exécuter les tests
mvn test

# Créer le JAR
mvn clean package

# Exécuter l'application
mvn spring-boot:run
```

### Docker

```bash
# Build et lancement
docker-compose up -d

# Logs
docker-compose logs -f app

# Arrêt
docker-compose down

# Rebuild
docker-compose up -d --build
```

### Base de données

```bash
# Se connecter à PostgreSQL
docker-compose exec postgres psql -U journal_user -d journal_intime

# Backup
docker-compose exec postgres pg_dump -U journal_user journal_intime > backup.sql

# Restore
docker-compose exec -T postgres psql -U journal_user journal_intime < backup.sql
```

## 🧪 Tests

Le projet inclut des tests unitaires pour:
- Classification d'émotions
- Traitement de texte NLP
- Tests basiques de l'application

Pour exécuter les tests avec coverage:
```bash
mvn test jacoco:report
```

## 📈 Prochaines Étapes

1. **FXML Views**: Créer les vues FXML pour chaque écran
2. **Controllers**: Implémenter les contrôleurs JavaFX
3. **ViewModels**: Créer les ViewModels MVVM
4. **DTOs & Mappers**: Couche de transfert de données
5. **Markdown Editor**: Intégrer un éditeur Markdown
6. **Graphiques**: Visualisation des tendances
7. **Export PDF**: Génération de rapports
8. **Tests d'intégration**: Tests avec base de données

## 🎯 Objectifs Atteints

✅ Architecture en couches propre et maintenable
✅ Séparation claire des responsabilités
✅ Sécurité enterprise-grade (AES-256, BCrypt)
✅ Persistance robuste avec audit et recherche
✅ IA pour analyse de sentiment et détection de burnout
✅ Recommandations personnalisées
✅ Conteneurisation Docker
✅ Tests unitaires
✅ Documentation complète

Le projet est maintenant prêt pour le développement des interfaces utilisateur et l'enrichissement des fonctionnalités!
