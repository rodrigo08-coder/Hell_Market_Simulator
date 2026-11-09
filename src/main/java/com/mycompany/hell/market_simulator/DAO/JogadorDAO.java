package com.mycompany.hell.market_simulator.DAO;

import com.mycompany.hell.market_simulator.Model.Jogador;
import com.mycompany.hell.market_simulator.Util.Conexao;
import java.sql.*;

public class JogadorDAO {
    public void inserir(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogadores (nome, dinheiro) VALUES (?, ?)";
        try (Connection con=Conexao.conectar(); PreparedStatement stmt=con.prepareStatement(sql)) {
            stmt.setString(1,jogador.getNome()); stmt.setDouble(2,jogador.getDinheiro()); stmt.executeUpdate();
        }
    }
    public Jogador buscarPorId(int id) throws SQLException {
        String sql="SELECT * FROM jogadores WHERE id=?";
        try(Connection con=Conexao.conectar(); PreparedStatement stmt=con.prepareStatement(sql)){
            stmt.setInt(1,id); ResultSet rs=stmt.executeQuery();
            if(rs.next()) return new Jogador(rs.getInt("id"),rs.getString("nome"),rs.getDouble("dinheiro"));
        } return null;
    }
    public java.util.List<Jogador> listar() throws SQLException {
        java.util.List<Jogador> lista=new java.util.ArrayList<>();
        try(Connection con=Conexao.conectar(); PreparedStatement stmt=con.prepareStatement("SELECT * FROM jogadores ORDER BY id"); ResultSet rs=stmt.executeQuery()){
            while(rs.next()) lista.add(new Jogador(rs.getInt("id"),rs.getString("nome"),rs.getDouble("dinheiro")));
        } return lista;
    }
    public void atualizar(Jogador jogador) throws SQLException {
        String sql="UPDATE jogadores SET nome=?, dinheiro=? WHERE id=?";
        try(Connection con=Conexao.conectar(); PreparedStatement stmt=con.prepareStatement(sql)){
            stmt.setString(1,jogador.getNome()); stmt.setDouble(2,jogador.getDinheiro()); stmt.setInt(3,jogador.getId()); stmt.executeUpdate();
        }
    }
    public void excluir(int id) throws SQLException {
        try(Connection con=Conexao.conectar(); PreparedStatement stmt=con.prepareStatement("DELETE FROM jogadores WHERE id=?")){
            stmt.setInt(1,id); stmt.executeUpdate();
        }
    }
}
