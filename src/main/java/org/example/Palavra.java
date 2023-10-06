package org.example;

import java.util.ArrayList;
import java.util.List;

public class Palavra {
    public static String palavraCorreta;
    public static ArrayList<String> letrasCorretas = new ArrayList<>();
    public static ArrayList<String> chutes = new ArrayList<>();
    public static ArrayList<String> acertos = new ArrayList<>();
    public static ArrayList<String> chutesAcertados = new ArrayList<>();

    public static ArrayList<Difficulty> dificudade = new ArrayList<>();

    public Palavra() {
    }

    public String getPalavraCorreta() {
        return palavraCorreta;
    }

    public void setPalavraCorreta(String palavraCorreta) {
        this.palavraCorreta = palavraCorreta;
    }
}
