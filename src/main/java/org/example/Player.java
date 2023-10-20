package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nome;
    private int vidas;
    private double pontos;
    private int erros;
    private Forca forca = new Forca();

    public Player(String nome) {

        this.nome = nome;
        this.vidas = 5;
        this.pontos = 0;
    }

    public Player() {}
    public Forca getForca() {return forca;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getErros() {return erros;}

    public void setErros(int erros) {this.erros = erros;}
}
