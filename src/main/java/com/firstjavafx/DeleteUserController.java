package com.firstjavafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class DeleteUserController {
    private Professor currentProfessor;

    private Stage primaryStage;

    @FXML
    private TextField namField;

    @FXML
    private ChoiceBox userTypes;

    @FXML
    private Label resLabel;

    public void setData(Professor curr, Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentProfessor = curr;
        });  

    }

    @FXML
    public void decideUser() {
        resLabel.setVisible(false);
        if((userTypes.getUserData().toString()).equals("Professor")) {
            //Delete professor
            String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
            try {
                Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM PROFESSORS WHERE USERNAME = ?");
                pstmt.setString(1, namField.getText());
                int rs = pstmt.executeUpdate();
                if(rs == 0) {
                    resLabel.setText("That Professor does not exist");
                    resLabel.setVisible(true);
                } else {
                    resLabel.setText(namField.getText() + " was deleted.");
                    resLabel.setVisible(true);
                }
                
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //Delete Trainer
            String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
            try {
                Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM TRAINERS WHERE USERNAME = ?");
                pstmt.setString(1, namField.getText());
                int rs = pstmt.executeUpdate();
                if(rs == 0) {
                    resLabel.setText("That Trainer does not exist");
                    resLabel.setVisible(true);
                } else {
                    resLabel.setText(namField.getText() + " was deleted.");
                    resLabel.setVisible(true);
                }
                
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void goBack() throws IOException{ 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profmenu.fxml"));
        Parent pane = (Parent)loader.load();
        Scene scene = new Scene(pane, 1200, 800);
        scene.getStylesheets().add(App.class.getResource("fancyeffects.css").toExternalForm());
        primaryStage.setScene(scene);
        ProfessorMenuController controller = loader.getController();
        controller.setData(currentProfessor, primaryStage);
        primaryStage.show();
    }
}
