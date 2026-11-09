package com.mycompany.hell.market_simulator.Controller;

import com.mycompany.hell.market_simulator.App;
import com.mycompany.hell.market_simulator.DAO.ContratoDAO;
import com.mycompany.hell.market_simulator.Model.Contrato;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class ContratosController {
    @FXML private ListView<String> listaContratos;
    @FXML private Label mensagem;
    private List<Contrato> contratos;

    @FXML private void initialize(){ carregar(); }
    private void carregar(){
        try{
            contratos=new ContratoDAO().listarDisponiveis();
            listaContratos.setItems(FXCollections.observableArrayList());
            for(Contrato c:contratos) listaContratos.getItems().add(
                c.getId()+" - "+c.getDemonio()+" | "+c.getDescricao()+" | R$ "+c.getRecompensa()+" | "+c.getDificuldade());
            if(contratos.isEmpty()) mensagem.setText("Não há contratos disponíveis no banco.");
        }catch(Exception e){mensagem.setText("Erro ao carregar contratos: "+e.getMessage());}
    }
    @FXML private void concluir(){
        int i=listaContratos.getSelectionModel().getSelectedIndex();
        if(i<0){mensagem.setText("Selecione um contrato.");return;}
        try{
            new ContratoDAO().concluir(contratos.get(i).getId());
            mensagem.setText("Contrato marcado como concluído. A lógica de conferência dos itens será adicionada na próxima etapa.");
            carregar();
        }catch(Exception e){mensagem.setText("Erro: "+e.getMessage());}
    }
    @FXML private void voltar() throws Exception { App.setRoot("jogo"); }
}
