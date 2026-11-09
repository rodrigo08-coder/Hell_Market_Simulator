package com.mycompany.hell.market_simulator.DAO;

import com.mycompany.hell.market_simulator.Util.Conexao;
import java.sql.*;

public class PedidoDAO {
    public void registrar(int partidaId,int contratoId) throws SQLException {
        String sql="INSERT INTO pedidos(partida_id,contrato_id) VALUES(?,?)";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setInt(1,partidaId);s.setInt(2,contratoId);s.executeUpdate();
        }
    }
    public void concluir(int pedidoId) throws SQLException {
        String sql="UPDATE pedidos SET status='CONCLUIDO', data_conclusao=CURRENT_TIMESTAMP WHERE id=?";
        try(Connection c=Conexao.conectar(); PreparedStatement s=c.prepareStatement(sql)){
            s.setInt(1,pedidoId);s.executeUpdate();
        }
    }
}
