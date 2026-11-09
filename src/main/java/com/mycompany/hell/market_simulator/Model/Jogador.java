package com.mycompany.hell.market_simulator.Model;

public class Jogador {
    private int id;
    private String nome;
    private double dinheiro;

    public Jogador(int id, String nome, double dinheiro) {
        this.id = id; this.nome = nome; this.dinheiro = dinheiro;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getDinheiro() { return dinheiro; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDinheiro(double dinheiro) { this.dinheiro = dinheiro; }
}
