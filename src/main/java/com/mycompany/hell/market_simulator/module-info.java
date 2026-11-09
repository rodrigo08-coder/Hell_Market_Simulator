module com.mycompany.hell.market_simulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.mycompany.hell.market_simulator to javafx.fxml;
    opens com.mycompany.hell.market_simulator.Controller to javafx.fxml;
    exports com.mycompany.hell.market_simulator;
}
