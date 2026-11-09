package com.mycompany.hell.market_simulator.DAO;

import com.mycompany.hell.market_simulator.Util.Conexao;
import java.sql.*;

public class JornadaDAO {
    public int iniciar(int jogadorId) throws SQLException {
        String sql="INSERT INTO partidas(jogador_id,status) VALUES(?,'EM_ANDAMENTO') RETURNING id";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setInt(1,jogadorId); ResultSet r=s.executeQuery(); if(r.next()) return r.getInt(1);
        } return 0;
    }
    public void finalizar(int partidaId,String status) throws SQLException {
        String sql="UPDATE partidas SET status=?, data_fim=CURRENT_TIMESTAMP WHERE id=?";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setString(1,status);s.setInt(2,partidaId);s.executeUpdate();
        }
    }
}
