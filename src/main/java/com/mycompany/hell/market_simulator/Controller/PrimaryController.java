package com.mycompany.hell.market_simulator.Controller;

import com.mycompany.hell.market_simulator.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class PrimaryController {
    @FXML private void jogar() throws Exception { App.setRoot("jogo"); }
    @FXML private void comoJogar() throws Exception { App.setRoot("explicarjogo"); }
    @FXML private void sair() { System.exit(0); }
}
