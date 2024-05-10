package com.firstjavafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrainerAccountController {
    private Trainer currentTrainer;

    private Stage primaryStage;

    @FXML
    private TextField usernameField;

    @FXML
    private Label badUname;
    @FXML
    private Label unameFound;
    @FXML
    private Label unameUpdated;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label badPsswd;
    @FXML
    private Label psswdUpdated;

    public void setData(Trainer curr, Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentTrainer = curr;
        });    
    }

    public void checkUsername() throws IOException{
        badUname.setVisible(false);
        unameFound.setVisible(false);
        unameUpdated.setVisible(false);

        if((usernameField.getText().length() > 16) || (usernameField.getText().isEmpty())) {
            badUname.setVisible(true);
            return;
        }

        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TRAINERS WHERE USERNAME = ?");
            pstmt.setString(1, usernameField.getText());
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                /* If no username was found in the database, update user */
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE TRAINERS SET USERNAME = ? WHERE USERNAME = ?");
                updateStmt.setString(1, usernameField.getText());
                updateStmt.setString(2, currentTrainer.getName());
                
                updateStmt.executeUpdate();
                currentTrainer.setName(usernameField.getText());

                unameUpdated.setVisible(true);
        
            } else {
                unameFound.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkPassword() throws IOException{
        badPsswd.setVisible(false);
        psswdUpdated.setVisible(false);
        if((passwordField.getText().length() > 16) || (passwordField.getText().isEmpty())) {
            badPsswd.setVisible(true);
        }
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE TRAINERS SET PSSWD= ? WHERE USERNAME = ?");
            updateStmt.setString(1, passwordField.getText());
            updateStmt.setString(2, currentTrainer.getName());
            
            updateStmt.executeUpdate();
            currentTrainer.setPsswd(passwordField.getText());

            psswdUpdated.setVisible(true);
            
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() throws IOException{ 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("trainermenu.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        TrainerMenuController controller = loader.getController();
        controller.setData(currentTrainer, primaryStage);
        primaryStage.show();
    }
    
}
