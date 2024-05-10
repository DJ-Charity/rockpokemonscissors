package com.firstjavafx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TrainerMenuController {
    @FXML
    private Label welcomeLabel;

    private Trainer currentTrainer;

    private Stage primaryStage;

    public void setData(Trainer curr, Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentTrainer = curr;
            welcomeLabel.setText("Welcome Trainer " + curr.getName() + ".");
        });  
    }

    @FXML
    public void rpsGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rpsgame.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        //Switch control to set up the game
        GameController controller = loader.getController();
        controller.setData(currentTrainer, primaryStage);
        primaryStage.show();
    }

    public void loadChangeFXML(String fname) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fname));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        //Switch control to change account details
        TrainerAccountController controller = loader.getController();
        controller.setData(currentTrainer, primaryStage);
        primaryStage.show();
    }

    @FXML
    public void changeUsername() throws IOException{
        loadChangeFXML("changetraineruname.fxml");
    }

   @FXML
    public void changePassword() throws IOException{
        loadChangeFXML("changetrainerpsswd.fxml");
    }

    @FXML
    public void logOut() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("picklogin.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    @FXML
    public void exitProgram() {
        System.exit(0);
    }
    
}
