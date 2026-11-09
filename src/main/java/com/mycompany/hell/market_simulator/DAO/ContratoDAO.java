package com.mycompany.hell.market_simulator.DAO;

import com.mycompany.hell.market_simulator.Model.Contrato;
import com.mycompany.hell.market_simulator.Util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {
    public List<Contrato> listarDisponiveis() throws SQLException {
        List<Contrato> lista=new ArrayList<>();
        String sql="SELECT * FROM contratos WHERE concluido=false ORDER BY id";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql); ResultSet r=s.executeQuery()){
            while(r.next()) lista.add(new Contrato(r.getInt("id"),r.getString("demonio"),r.getString("descricao"),r.getDouble("recompensa"),r.getString("dificuldade"),r.getBoolean("concluido")));
        } return lista;
    }
    public void inserir(String demonio,String descricao,double recompensa,String dificuldade) throws SQLException {
        String sql="INSERT INTO contratos(demonio,descricao,recompensa,dificuldade) VALUES(?,?,?,?)";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setString(1,demonio);s.setString(2,descricao);s.setDouble(3,recompensa);s.setString(4,dificuldade);s.executeUpdate();
        }
    }
    public void atualizar(int id,String demonio,String descricao,double recompensa,String dificuldade) throws SQLException {
        String sql="UPDATE contratos SET demonio=?,descricao=?,recompensa=?,dificuldade=? WHERE id=?";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setString(1,demonio);s.setString(2,descricao);s.setDouble(3,recompensa);s.setString(4,dificuldade);s.setInt(5,id);s.executeUpdate();
        }
    }
    public void excluir(int id) throws SQLException {
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement("DELETE FROM contratos WHERE id=?")){
            s.setInt(1,id);s.executeUpdate();
        }
    }
    public void concluir(int contratoId) throws SQLException {
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement("UPDATE contratos SET concluido=true WHERE id=?")){
            s.setInt(1,contratoId);s.executeUpdate();
        }
    }
}
