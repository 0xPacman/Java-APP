package com.journalintime;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * Main application class for Journal Intime - Mental Health Desktop Application.
 * Integrates Spring Boot 3 with JavaFX 21.
 */
public class JournalIntimeApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(com.journalintime.infrastructure.config.ApplicationConfig.class)
                .headless(false)
                .run();
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Event fired when the JavaFX stage is ready.
     */
    public static class StageReadyEvent extends ApplicationEvent {
        
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        
        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
