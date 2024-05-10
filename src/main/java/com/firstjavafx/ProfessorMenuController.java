package com.firstjavafx;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class ProfessorMenuController {
    @FXML
    private Label welcomeLabel;

    private Professor currentProfessor;

    private Stage primaryStage;

    public void setData(Professor curr, Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentProfessor = curr;
            welcomeLabel.setText("Welcome Professor " + curr.getName() + ".");
        });  
    }

    public void loadCreateFXML(String fname, boolean isTrainer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fname));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        //Switch control to create new users and pokemon
        CreateUserController controller = loader.getController();
        controller.setData(currentProfessor, primaryStage, isTrainer);
        primaryStage.show();
    }

    public void loadDeleteFXML(String fname, boolean isTrainer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fname));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        //Switch control to delete new users and pokemon
        DeleteUserController controller = loader.getController();
        controller.setData(currentProfessor, primaryStage);
        primaryStage.show();
    }

    @FXML
    public void newUser() throws IOException{
        loadCreateFXML("newuser.fxml", false);
    }

    @FXML
    public void deleteUser() throws IOException{
        loadDeleteFXML("deleteuser.fxml", false);
    }
    
    @FXML
    public void newPokemon() throws IOException{
        loadCreateFXML("newpoke.fxml", false);
    }

    public void loadChangeFXML(String fname) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fname));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        //Switch control to change account details
        ProfAccountController controller = loader.getController();
        controller.setData(currentProfessor, primaryStage);
        primaryStage.show();
    }

    @FXML
    public void changeUsername() throws IOException{
        loadChangeFXML("changeprofuname.fxml");
    }

   @FXML
    public void changePassword() throws IOException{
        loadChangeFXML("changeprofpsswd.fxml");
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
