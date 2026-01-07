package com.journalintime.ui;

import atlantafx.base.theme.PrimerLight;
import com.journalintime.JournalIntimeApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Interface utilisateur principale de l'application Journal Intime.
 * Utilise JavaFX avec AtlantaFX pour le théming.
 */
@Component
public class MainUI implements ApplicationListener<JournalIntimeApplication.StageReadyEvent> {

    @Override
    public void onApplicationEvent(JournalIntimeApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        Platform.runLater(() -> {
            initializeUI(stage);
        });
    }

    private void initializeUI(Stage stage) {
        // Appliquer le thème AtlantaFX
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        // Créer le layout principal
        BorderPane root = new BorderPane();

        // Menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Centre avec un placeholder
        VBox centerContent = createWelcomeView();
        root.setCenter(centerContent);

        // Créer la scène
        Scene scene = new Scene(root, 1200, 800);
        
        // Configurer la fenêtre
        stage.setTitle("Journal Intime - Mental Health Application");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Menu Fichier
        Menu fileMenu = new Menu("Fichier");
        MenuItem newNoteItem = new MenuItem("Nouvelle Note");
        MenuItem settingsItem = new MenuItem("Paramètres");
        MenuItem exitItem = new MenuItem("Quitter");
        exitItem.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(newNoteItem, new SeparatorMenuItem(), settingsItem, new SeparatorMenuItem(), exitItem);

        // Menu Notes
        Menu notesMenu = new Menu("Notes");
        MenuItem allNotesItem = new MenuItem("Toutes les notes");
        MenuItem searchItem = new MenuItem("Rechercher");
        notesMenu.getItems().addAll(allNotesItem, searchItem);

        // Menu Analyse
        Menu analysisMenu = new Menu("Analyse");
        MenuItem dashboardItem = new MenuItem("Tableau de bord");
        MenuItem trendsItem = new MenuItem("Tendances");
        analysisMenu.getItems().addAll(dashboardItem, trendsItem);

        // Menu Exercices
        Menu exercisesMenu = new Menu("Exercices");
        MenuItem recommendationsItem = new MenuItem("Recommandations");
        MenuItem completedItem = new MenuItem("Exercices complétés");
        exercisesMenu.getItems().addAll(recommendationsItem, completedItem);

        // Menu Aide
        Menu helpMenu = new Menu("Aide");
        MenuItem aboutItem = new MenuItem("À propos");
        MenuItem helpItem = new MenuItem("Documentation");
        helpMenu.getItems().addAll(aboutItem, helpItem);

        menuBar.getMenus().addAll(fileMenu, notesMenu, analysisMenu, exercisesMenu, helpMenu);
        return menuBar;
    }

    private VBox createWelcomeView() {
        VBox welcomeView = new VBox(20);
        welcomeView.setStyle("-fx-padding: 40; -fx-alignment: center;");

        Label titleLabel = new Label("Bienvenue dans Journal Intime");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Votre compagnon de santé mentale avec analyse IA");
        subtitleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");

        Button newNoteButton = new Button("Créer une nouvelle note");
        newNoteButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");
        newNoteButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nouvelle Note");
            alert.setHeaderText("Fonctionnalité en cours de développement");
            alert.setContentText("L'éditeur de notes sera bientôt disponible.");
            alert.showAndWait();
        });

        Button dashboardButton = new Button("Voir le tableau de bord");
        dashboardButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");

        HBox buttonBox = new HBox(15, newNoteButton, dashboardButton);
        buttonBox.setStyle("-fx-alignment: center;");

        // Informations sur les fonctionnalités
        GridPane featuresGrid = new GridPane();
        featuresGrid.setHgap(20);
        featuresGrid.setVgap(15);
        featuresGrid.setStyle("-fx-padding: 30; -fx-alignment: center;");

        addFeature(featuresGrid, 0, 0, "✍️ Éditeur Markdown", "Écrivez vos notes en Markdown");
        addFeature(featuresGrid, 1, 0, "🧠 Analyse IA", "Détection automatique des émotions");
        addFeature(featuresGrid, 0, 1, "📊 Suivi des tendances", "Analysez votre évolution");
        addFeature(featuresGrid, 1, 1, "🧘 Exercices", "Recommandations personnalisées");

        welcomeView.getChildren().addAll(titleLabel, subtitleLabel, buttonBox, featuresGrid);
        return welcomeView;
    }

    private void addFeature(GridPane grid, int col, int row, String title, String description) {
        VBox featureBox = new VBox(5);
        featureBox.setStyle("-fx-padding: 15; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-text-fill: gray;");
        
        featureBox.getChildren().addAll(titleLabel, descLabel);
        grid.add(featureBox, col, row);
    }
}
