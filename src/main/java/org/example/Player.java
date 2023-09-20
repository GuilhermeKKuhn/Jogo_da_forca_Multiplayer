package org.example;

public class Player {
    private String nome;
    private String ip;
    private int vidas;
    private double pontos;

    public Player(String nome, String ip) {
        this.nome = nome;
        this.ip = ip;
        this.vidas = 5;
        this.pontos = 0;
    }

    public Player() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

}
