module com.liceolapaz.bcdnob.ahorca_do {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.desktop;
    requires java.naming;


    opens com.liceolapaz.bcdnob.ahorca_do to javafx.fxml;
    exports com.liceolapaz.bcdnob.ahorca_do;
    exports com.liceolapaz.bcdnob.ahorca_do.controllers;
    opens com.liceolapaz.bcdnob.ahorca_do.controllers to javafx.fxml;
    exports com.liceolapaz.bcdnob.ahorca_do.model;
    opens com.liceolapaz.bcdnob.ahorca_do.model to javafx.fxml;
}