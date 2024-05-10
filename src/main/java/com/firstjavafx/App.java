package com.firstjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;
    private static Scene scene;

    //start goes first
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("picklogin.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());

        LoginController controller = loader.getController();
        controller.setData(primaryStage);
        
        primaryStage.setTitle("Pokemon Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //launches program
    public static void main(String[] args) {
        launch();
    }

}