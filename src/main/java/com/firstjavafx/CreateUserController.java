package com.firstjavafx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;


public class CreateUserController {
    private Professor currentProfessor;

    private Stage primaryStage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField levelField;

    @FXML
    private TextField regionField;

    @FXML
    private ChoiceBox userTypes;

    @FXML
    private ChoiceBox types;

    @FXML
    private Label resLabel;

    @FXML
    private Label userCreated;

    @FXML
    private Label userFound;

    @FXML
    private Label badCredentials;

    public void setData(Professor curr, Stage currStage, boolean trainer) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentProfessor = curr;
        });  

        if(trainer) {
            UnaryOperator<TextFormatter.Change> filter = c -> {
                if (c.getControlNewText().matches("\\d*")) {
                    return c;
                }
                return null;
            };
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            levelField.setTextFormatter(textFormatter);
        }  
    }

    @FXML
    public void decideUser() throws IOException{
        if(((String)userTypes.getValue()) == null || usernameField.getText() == null) {
            return;
        }
        if(((String)userTypes.getValue()).equals("Professor")) {
            checkNewProf();
        } else {
            checkNewTrainer();
        }
    }

    @FXML
    public void checkNewProf() throws IOException{
        resLabel.setVisible(false);

        if((usernameField.getText().length() > 16 || usernameField.getText().isEmpty()) || (passwordField.getText().length() > 16 || passwordField.getText().isEmpty())) {
            resLabel.setVisible(true);
            resLabel.setText("All fields must be 16 characters or less and not empty.");
            return;
        } 
        //pull from database to make sure there are no professors in there with that name
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PROFESSORS WHERE USERNAME = ?");
            pstmt.setString(1, usernameField.getText());
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                /* If no username was found in the database, create new entry */
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO PROFESSORS(USERNAME, PSSWD) "
                                                                                   + "VALUES( ?, ?)");
                insertStmt.setString(1, usernameField.getText());
                insertStmt.setString(2, passwordField.getText());
                insertStmt.executeUpdate();

                resLabel.setText("Successfully created new Professor!");
                resLabel.setVisible(true);
            } else {
                resLabel.setText("This Professor already exists, please try again.");
                resLabel.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void checkNewTrainer() throws IOException{
        
        resLabel.setVisible(false);

        if((usernameField.getText().length() > 16 || usernameField.getText().isEmpty()) || (passwordField.getText().length() > 16 || passwordField.getText().isEmpty())) {
            resLabel.setVisible(true);
            resLabel.setText("All fields must be 16 characters or less and not empty.");
            return;
        } 
        
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TRAINERS WHERE USERNAME = ?");
            pstmt.setString(1, usernameField.getText());
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                /* If no username was found in the database, create new entry */
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO TRAINERS(USERNAME, PSSWD, TRAINER_LEVEL) "
                                                                                 + "VALUES( ?, ?, ?)");
                insertStmt.setString(1, usernameField.getText());
                insertStmt.setString(2, passwordField.getText());
                insertStmt.setInt(3, Integer.parseInt(levelField.getText())); 
                insertStmt.executeUpdate();
        
                resLabel.setText("Successfully created new Trainer!");
                resLabel.setVisible(true);
            } else {
                resLabel.setText("This Professor already exists, please try again.");
                resLabel.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void checkNewPokemon() throws IOException{
        badCredentials.setVisible(false);
        userFound.setVisible(false); 
        userCreated.setVisible(false);

        if((usernameField.getText().length() > 20 || usernameField.getText().isEmpty()) || (regionField.getText().length() > 10 || regionField.getText().isEmpty())) {
            badCredentials.setVisible(true);
            return;
        } 
        
        String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
        try {
            Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM POKEMON WHERE POKEMON_NAME = ?");
            pstmt.setString(1, usernameField.getText());
            ResultSet rs = pstmt.executeQuery();
            if(!(rs.next())) {
                
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO POKEMON(POKEMON_NAME, REGION, POKEMON_TYPE) "
                                                                                + "VALUES( ?, ?, ?)");
                insertStmt.setString(1, usernameField.getText());
                insertStmt.setString(2, regionField.getText());
                insertStmt.setString(3, (String)types.getValue());
                insertStmt.executeUpdate();
        
                userCreated.setVisible(true);
            } else {
                userFound.setVisible(true);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
