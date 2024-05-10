# Rock-Pokemon-Scissors
A Rock-Paper-Scissors inspired game based around your knowledge of Pokémon. Built with Java that utilizes JavaFX and FXML for UI and JDBC to store your user and game info. You can log into the application as an Professor/Administrator to add more options, or a Trainer/General user to play the game.


References:

    Used Jakob Jenkov's Javafx Tutorial for Javafx assistance: https://jenkov.com/tutorials/javafx/index.html
    Used Java Guides for database help: https://www.javaguides.net/2020/02/java-jdbc-postgresql-insert-example.html

Requirements:

    Necessary libraries include javafx, sql, util, fxml.
    Initially ran in VSCode.
    Uses PostgreSQL database. Username and password are hard coded.
    -There are three necessary tables in the database:
      -PROFESSORS(PID INTEGER, USERNAME VARCHAR, PSSWD VARCHAR)
      -TRAINERS(TID INTEGER, USERNAME VARCHAR, PSSWD VARCHAR, TRAINER_LEVEL VARCHAR)
      -POKEMON(PID INTEGER, POKEMON_NAME VARCHAR, REGION VARCHAR, TYPE VARCHAR)

How to Run:

    A GUI should appear for you to select what type of user you are and to log in.
    
    The credentials to log in are stored in the Professor and Trainer table in the database, so initial test values should be created before running
    
    When logged in, you will be taken to a menu with different button options to do.
    -Only Trainers will be given the option to play the game
    -Professors must ensure that the POKEMON table is populated with at least one Pokemon of every type so the game can run properly

    To quit the program, you can select the quit button option or you can click the x in the top right corner.
