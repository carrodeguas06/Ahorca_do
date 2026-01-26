module com.liceolapaz.bcdnob.ahorca_do {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.liceolapaz.bcdnob.ahorca_do to javafx.fxml;
    exports com.liceolapaz.bcdnob.ahorca_do;
}