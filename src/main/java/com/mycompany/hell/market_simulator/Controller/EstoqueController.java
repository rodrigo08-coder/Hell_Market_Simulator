package com.mycompany.hell.market_simulator.Controller;

import com.mycompany.hell.market_simulator.App;
import com.mycompany.hell.market_simulator.DAO.ProdutoDAO;
import com.mycompany.hell.market_simulator.Model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class EstoqueController {
    @FXML private ListView<String> listaProdutos;
    @FXML private TextField nome,categoria,preco,quantidade;
    @FXML private Label mensagem;
    private List<Produto> produtos;
    @FXML private void initialize(){ carregar(); }
    private void carregar(){
        try{
            produtos=new ProdutoDAO().listar();
            listaProdutos.setItems(FXCollections.observableArrayList());
            for(Produto p:produtos) listaProdutos.getItems().add(p.getId()+" - "+p.getNome()+" | "+p.getCategoria()+" | R$ "+p.getPreco()+" | Estoque: "+p.getQuantidade());
        }catch(Exception e){mensagem.setText("Erro ao carregar: "+e.getMessage());}
    }
    @FXML private void selecionar(){
        int i=listaProdutos.getSelectionModel().getSelectedIndex();
        if(i>=0){Produto p=produtos.get(i);nome.setText(p.getNome());categoria.setText(p.getCategoria());preco.setText(String.valueOf(p.getPreco()));quantidade.setText(String.valueOf(p.getQuantidade()));}
    }
    @FXML private void inserir(){
        try{
            validar(); Produto p=new Produto(0,nome.getText().trim(),categoria.getText().trim(),Double.parseDouble(preco.getText()),Integer.parseInt(quantidade.getText()));
            ProdutoDAO dao=new ProdutoDAO();dao.inserir(p);
            // Localiza o produto pelo nome para cadastrar sua quantidade inicial.
            for(Produto item:dao.listar()) if(item.getNome().equals(p.getNome())) dao.alterarEstoque(item.getId(),p.getQuantidade());
            limpar();carregar();mensagem.setText("Produto cadastrado.");
        }catch(Exception e){mensagem.setText("Confira os campos. "+e.getMessage());}
    }
    @FXML private void atualizar(){
        int i=listaProdutos.getSelectionModel().getSelectedIndex();
        if(i<0){mensagem.setText("Selecione um produto.");return;}
        try{
            validar();Produto antigo=produtos.get(i);
            Produto p=new Produto(antigo.getId(),nome.getText().trim(),categoria.getText().trim(),Double.parseDouble(preco.getText()),Integer.parseInt(quantidade.getText()));
            ProdutoDAO dao=new ProdutoDAO();dao.atualizar(p);dao.alterarEstoque(p.getId(),p.getQuantidade());
            carregar();mensagem.setText("Produto atualizado.");
        }catch(Exception e){mensagem.setText("Erro ao atualizar: "+e.getMessage());}
    }
    @FXML private void excluir(){
        int i=listaProdutos.getSelectionModel().getSelectedIndex();
        if(i<0){mensagem.setText("Selecione um produto.");return;}
        try{new ProdutoDAO().excluir(produtos.get(i).getId());carregar();limpar();mensagem.setText("Produto excluído.");}
        catch(Exception e){mensagem.setText("Erro ao excluir: "+e.getMessage());}
    }
    private void validar(){
        if(nome.getText().trim().isEmpty()||categoria.getText().trim().isEmpty())throw new IllegalArgumentException("Nome e categoria são obrigatórios.");
        if(Double.parseDouble(preco.getText())<0||Integer.parseInt(quantidade.getText())<0)throw new IllegalArgumentException("Preço e quantidade não podem ser negativos.");
    }
    private void limpar(){nome.clear();categoria.clear();preco.clear();quantidade.clear();}
    @FXML private void voltar() throws Exception {App.setRoot("jogo");}
}
