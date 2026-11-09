package com.mycompany.hell.market_simulator.Controller;

import com.mycompany.hell.market_simulator.App;
import com.mycompany.hell.market_simulator.Util.Conexao;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class UpgradesController {
    @FXML private ListView<String> lista;
    @FXML private Label mensagem;
    @FXML private void initialize(){
        try(Connection c=Conexao.conectar();PreparedStatement s=c.prepareStatement("SELECT nome,descricao,custo FROM upgrades ORDER BY id");ResultSet r=s.executeQuery()){
            while(r.next()) lista.getItems().add(r.getString("nome")+" | "+r.getString("descricao")+" | Custo: R$ "+r.getDouble("custo"));
        }catch(Exception e){mensagem.setText("Erro ao carregar upgrades: "+e.getMessage());}
    }
    @FXML private void voltar() throws Exception {App.setRoot("jogo");}
}
