package com.mycompany.hell.market_simulator.Model;

public class Contrato {
    private int id;
    private String demonio;
    private String descricao;
    private double recompensa;
    private String dificuldade;
    private boolean concluido;

    public Contrato(int id, String demonio, String descricao, double recompensa, String dificuldade, boolean concluido) {
        this.id=id; this.demonio=demonio; this.descricao=descricao; this.recompensa=recompensa;
        this.dificuldade=dificuldade; this.concluido=concluido;
    }
    public int getId(){return id;}
    public String getDemonio(){return demonio;}
    public String getDescricao(){return descricao;}
    public double getRecompensa(){return recompensa;}
    public String getDificuldade(){return dificuldade;}
    public boolean isConcluido(){return concluido;}
}
