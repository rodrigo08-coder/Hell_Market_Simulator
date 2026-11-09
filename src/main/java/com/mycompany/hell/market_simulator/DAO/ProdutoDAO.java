package com.mycompany.hell.market_simulator.DAO;

import com.mycompany.hell.market_simulator.Model.Produto;
import com.mycompany.hell.market_simulator.Util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public void inserir(Produto p) throws SQLException {
        String sql="INSERT INTO produtos (nome,categoria,preco) VALUES (?,?,?)";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setString(1,p.getNome()); s.setString(2,p.getCategoria()); s.setDouble(3,p.getPreco()); s.executeUpdate();
        }
    }
    public List<Produto> listar() throws SQLException {
        List<Produto> lista=new ArrayList<>();
        String sql="SELECT p.id,p.nome,p.categoria,p.preco,COALESCE(e.quantidade,0) quantidade FROM produtos p LEFT JOIN estoque e ON e.produto_id=p.id ORDER BY p.nome";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql); ResultSet r=s.executeQuery()){
            while(r.next()) lista.add(new Produto(r.getInt("id"),r.getString("nome"),r.getString("categoria"),r.getDouble("preco"),r.getInt("quantidade")));
        } return lista;
    }
    public void atualizar(Produto p) throws SQLException {
        String sql="UPDATE produtos SET nome=?,categoria=?,preco=? WHERE id=?";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setString(1,p.getNome()); s.setString(2,p.getCategoria()); s.setDouble(3,p.getPreco()); s.setInt(4,p.getId()); s.executeUpdate();
        }
    }
    public void excluir(int id) throws SQLException {
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement("DELETE FROM produtos WHERE id=?")){
            s.setInt(1,id); s.executeUpdate();
        }
    }
    public void alterarEstoque(int produtoId, int quantidade) throws SQLException {
        String sql="INSERT INTO estoque(produto_id,quantidade) VALUES(?,?) ON CONFLICT(produto_id) DO UPDATE SET quantidade=EXCLUDED.quantidade";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setInt(1,produtoId); s.setInt(2,quantidade); s.executeUpdate();
        }
    }
}
