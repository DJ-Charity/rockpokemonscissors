package com.firstjavafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Popup;

public class GameController {
    private Trainer currentTrainer;

    private Stage primaryStage;

    private Pokemon opp;
    private Pokemon weaker;
    private Pokemon neutral;
    private Pokemon stronger;

    @FXML
    private Label opponentLabel;
    @FXML
    private Button optionOne;
    @FXML
    private Label oneType;
    @FXML
    private Button optionTwo;
    @FXML
    private Label twoType;

    @FXML
    private Label resultLabel;

    public void setData(Trainer curr, Stage currStage) {
        Platform.runLater(() -> {
            primaryStage = currStage;
            currentTrainer = curr;

            ///Connect to the database to set up label and the three options
            String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
            try {
                Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet gettingCount = stmt.executeQuery("SELECT COUNT(*) AS total FROM POKEMON");
                gettingCount.next();
                int count = gettingCount.getInt("total");
                
                /* Randomly chooses a pokemon from the database to attack */
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM POKEMON WHERE PID = ?");
                pstmt.setInt(1, (int)Math.floor((Math.random() * (count))));
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                opp = new Pokemon(rs.getString("POKEMON_NAME"), rs.getString("REGION"), rs.getString("POKEMON_TYPE"));
                opponentLabel.setText("A wild " + opp.getName() + " with Type " + opp.getType() + " appeared.");

                /* Select Pokemon thats stronger against to attacker */
                ArrayList<String> weakAgainast = opp.getWeaknesses();
                pstmt = conn.prepareStatement("SELECT * FROM POKEMON WHERE POKEMON_TYPE = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                pstmt.setString(1, weakAgainast.get((int)Math.floor((Math.random() * (weakAgainast.size())))) );
                ResultSet weakRS = pstmt.executeQuery();
                weakRS.next();

                /* Select Pokemon thats weaker against to attacker */
                ArrayList<String> strongAgainast = opp.getStrengths();
                pstmt = conn.prepareStatement("SELECT * FROM POKEMON WHERE POKEMON_TYPE = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                pstmt.setString(1, strongAgainast.get((int)Math.floor((Math.random() * (strongAgainast.size())))) );
                ResultSet strongRS = pstmt.executeQuery();
                strongRS.next();
                
                weakRS.last(); 
                int weakSize = weakRS.getRow(); 
                weakRS.beforeFirst(); 

                strongRS.last(); 
                int strongSize = strongRS.getRow(); 
                strongRS.beforeFirst(); 

                //Randolmly choose which button gets set
                if( (int)Math.floor(Math.random() * 2 ) == 1 ) {
                    //randomly choose pokemon from weakRS and strongRS
                    weakRS.absolute((int)Math.floor((Math.random() * weakSize)));
                    weakRS.next();
                    optionOne.setText(weakRS.getString("POKEMON_NAME"));
                    oneType.setText("Type: " + weakRS.getString("POKEMON_TYPE"));
                    
                    weaker = new Pokemon(weakRS.getString("POKEMON_NAME"), weakRS.getString("REGION"), weakRS.getString("POKEMON_TYPE"));
                    
                    strongRS.absolute((int)Math.floor((Math.random() * strongSize)));
                    strongRS.next();
                    stronger = new Pokemon(strongRS.getString("POKEMON_NAME"), strongRS.getString("REGION"), strongRS.getString("POKEMON_TYPE"));
                    optionTwo.setText(strongRS.getString("POKEMON_NAME"));
                    twoType.setText("Type: " + strongRS.getString("POKEMON_TYPE"));

                } else {
                    weakRS.absolute((int)Math.floor((Math.random() * weakSize)));
                    weakRS.next();
                    weaker = new Pokemon(weakRS.getString("POKEMON_NAME"), weakRS.getString("REGION"), weakRS.getString("POKEMON_TYPE"));
                    optionTwo.setText(weakRS.getString("POKEMON_NAME"));
                    twoType.setText("Type: " + weakRS.getString("POKEMON_TYPE"));

                    strongRS.absolute((int)Math.floor((Math.random() * strongSize)));
                    strongRS.next();
                    stronger = new Pokemon(strongRS.getString("POKEMON_NAME"), strongRS.getString("REGION"), strongRS.getString("POKEMON_TYPE"));
                    optionOne.setText(strongRS.getString("POKEMON_NAME"));
                    oneType.setText("Type: " + strongRS.getString("POKEMON_TYPE"));

                }             
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });  
    }

    @FXML
    public void checkOneChoice() {
        optionOne.setDisable(true);
        optionTwo.setDisable(true);
        if(optionOne.getText().equals(weaker.getName())) {
            //win
            currentTrainer.levelUp();
            resultLabel.setText("You won! And you have leveled up to level " + currentTrainer.getLevel());
            String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
            try {
                Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE TRAINERS SET TRAINER_LEVEL = ? WHERE USERNAME = ?");
                pstmt.setInt(1, currentTrainer.getLevel()+1);
                pstmt.setString(2, currentTrainer.getName());
                ResultSet rs = pstmt.executeQuery();
                rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //lose
            resultLabel.setText("Better luck next time! "+ stronger.getType() + " is weak to " + opp.getType() + "!");
        } 
    }

    @FXML
    public void checkTwoChoice() {
        optionOne.setDisable(true);
        optionTwo.setDisable(true);
        if(optionTwo.getText().equals(weaker.getName())) {
            //win
            currentTrainer.levelUp();
            resultLabel.setText("You won! And you have leveled up to level " + currentTrainer.getLevel());
            String dbURL = "jdbc:postgresql:rps?user=postgres&password=postpsswd";
            try {
                Connection conn = DriverManager.getConnection(dbURL);
                PreparedStatement pstmt = conn.prepareStatement("UPDATE TRAINERS SET TRAINER_LEVEL = ? WHERE USERNAME = ?");
                pstmt.setInt(1, currentTrainer.getLevel()+1);
                pstmt.setString(2, currentTrainer.getName());
                pstmt.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //lose
            resultLabel.setText("Better luck next time! "+ stronger.getType() + " is weak to " + opp.getType() + "!");
        } 
        
    }

    @FXML
    public void giveHelp() throws IOException { 
        //popup
        Popup helpScreen = new Popup();
        VBox containImage = new VBox();

        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("typeChart.jpg")));
        containImage.getChildren().add(iv);
        helpScreen.getContent().add(containImage);

        helpScreen.setAutoHide(true);
        helpScreen.show(primaryStage);
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
