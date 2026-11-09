package com.mycompany.hell.market_simulator.Controller;

import com.mycompany.hell.market_simulator.App;
import com.mycompany.hell.market_simulator.DAO.JogadorDAO;
import com.mycompany.hell.market_simulator.DAO.JornadaDAO;
import com.mycompany.hell.market_simulator.Model.Jogador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JogoController {
    @FXML private TextField nomeJogador;
    @FXML private Label mensagem;
    @FXML private Label saldo;
    private int jogadorId;
    private int partidaId;

    @FXML private void initialize() { mensagem.setText("Cadastre o jogador para iniciar sua jornada."); }

    @FXML private void iniciar() {
        try {
            String nome=nomeJogador.getText().trim();
            if(nome.isEmpty()){ mensagem.setText("Digite o nome do jogador."); return; }
            JogadorDAO dao=new JogadorDAO();
            dao.inserir(new Jogador(0,nome,500.00));
            // Recupera o jogador recém-criado pelo último ID para iniciar a partida.
            try(java.sql.Connection c=com.mycompany.hell.market_simulator.Util.Conexao.conectar();
                java.sql.PreparedStatement s=c.prepareStatement("SELECT id FROM jogadores WHERE nome=? ORDER BY id DESC LIMIT 1")){
                s.setString(1,nome); java.sql.ResultSet r=s.executeQuery();
                if(r.next()) jogadorId=r.getInt("id");
            }
            partidaId=new JornadaDAO().iniciar(jogadorId);
            saldo.setText("Saldo inicial: R$ 500,00");
            mensagem.setText("Jornada iniciada! Escolha um contrato.");
        } catch(Exception e){ mensagem.setText("Erro ao iniciar: "+e.getMessage()); }
    }
    @FXML private void contratos() throws Exception { App.setRoot("contratos"); }
    @FXML private void estoque() throws Exception { App.setRoot("estoque"); }
    @FXML private void upgrades() throws Exception { App.setRoot("upgrades"); }
    @FXML private void menu() throws Exception { App.setRoot("primary"); }
}
