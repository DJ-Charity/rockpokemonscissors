module com.firstjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.firstjavafx to javafx.fxml;
    exports com.firstjavafx;
}
