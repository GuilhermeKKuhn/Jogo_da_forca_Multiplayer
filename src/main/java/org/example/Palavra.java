package org.example;

import java.util.ArrayList;
import java.util.List;

public class Palavra {
    public String palavraCorreta;
    public ArrayList<String> letrasCorretas = new ArrayList<>();
    public ArrayList<String> chutes = new ArrayList<>();
    public ArrayList<String> acertos = new ArrayList<>();
    public ArrayList<String> chutesAcertados = new ArrayList<>();

    public ArrayList<Difficulty> dificudade = new ArrayList<>();

    public Palavra() {
    }

    public String getPalavraCorreta() {
        return palavraCorreta;
    }

    public void setPalavraCorreta(String palavraCorreta) {
        this.palavraCorreta = palavraCorreta;
    }
}
