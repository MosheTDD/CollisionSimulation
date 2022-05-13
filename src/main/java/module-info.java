module me.moshe.collisionsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.moshe.collisionsimulation to javafx.fxml;
    exports me.moshe.collisionsimulation;
}