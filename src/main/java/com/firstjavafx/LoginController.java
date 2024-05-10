package com.firstjavafx;

import javafx.stage.Stage;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import java.util.*;
import java.sql.*;

//imported and set up postgresql successfully, now need to add code to access and edit it
public class LoginController {    

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label incorrectLogin;

    public Professor currentProfessor;

    public Trainer currentTrainer;

    private static Stage primaryStage;

    public void setData(Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
        });  
    }

    @FXML
    private void switchToProfessor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("proflogin.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    @FXML
    private void checkProfLogin(ActionEvent event) throws IOException{
        String uname = usernameField.getText();
        String psswd = passwordField.getText();
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            //check
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PROFESSORS WHERE USERNAME = ?");
            pstmt.setString(1, uname);
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                /* If no username was found in the database, it will tell the professor that the username is wrong */
                incorrectLogin.setVisible(true);     
                    
            } else {
                /* If username was found, it will check to see if the entered password matches the password in the database */
                /* If the password matches, another function is called to go to the professor's main menu */
                if(psswd.equals(rs.getString("PSSWD"))) {
                    incorrectLogin.setVisible(false);
                    currentProfessor = new Professor(uname, psswd, Integer.parseInt(rs.getString("PID")));
                    /* Go to professor's menu by loading new scene that will carry over account info */
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("profmenu.fxml"));
                    Parent pane = (Parent)loader.load();
                    Scene scene = new Scene(pane, 1200, 800);
                    scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
                    primaryStage.setScene(scene);
                    ProfessorMenuController controller = loader.getController();
                    controller.setData(currentProfessor, primaryStage);
                    primaryStage.show();
                    
                } else {
                    /* If not, it will tell the professor that the password is incorrect */
                    incorrectLogin.setVisible(true);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void switchToTrainer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("trainerlogin.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void checkTrainerLogin() throws IOException {
        String uname = usernameField.getText();
        String psswd = passwordField.getText();
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            //check
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TRAINERS WHERE USERNAME = ?");
            pstmt.setString(1, uname);
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                /* If no username was found in the database, it will tell the trainer that the username is wrong */
                incorrectLogin.setVisible(true);     
                    
            } else {
                /* If username was found, it will check to see if the entered password matches the password in the database */
                /* If the password matches, another function is called to go to the trainer's main menu */
                if(psswd.equals(rs.getString("PSSWD"))) {
                    incorrectLogin.setVisible(false);
                    currentTrainer = new Trainer(rs.getInt("tid"), uname, psswd, rs.getInt("trainer_level"));
                    /* Go to trainer's menu by loading new scene that will carry over account info */
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("trainermenu.fxml"));
                    Parent pane = (Parent)loader.load();
                    Scene scene = new Scene(pane, 1200, 800);
                    scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
                    primaryStage.setScene(scene);
                    TrainerMenuController controller = loader.getController();
                    controller.setData(currentTrainer, primaryStage);
                    primaryStage.show();
                    
                } else {
                    /* If not, it will tell the trainer that the password is incorrect */
                    incorrectLogin.setVisible(true);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("picklogin.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
