package org.example;

import org.example.View.PalavraView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Palavra {
    public Random random = new Random();
    public static PalavraView palavraCorreta;
    public static ArrayList<String> letrasCorretas = new ArrayList<>();

    public static ArrayList<String> chutes = new ArrayList<>();
    public static ArrayList<String> acertos = new ArrayList<>();
    public static ArrayList<String> chutesAcertados = new ArrayList<>();

    public static Integer indexDica = -1;

    public static PalavraView getPalavraCorreta() {
        return palavraCorreta;
    }

    public static void setPalavraCorreta(PalavraView palavraCorreta) {
        Palavra.palavraCorreta = palavraCorreta;
    }

    public static ArrayList<String> getLetrasCorretas() {
        return letrasCorretas;
    }

    public static void setLetrasCorretas(ArrayList<String> letrasCorretas) {
        Palavra.letrasCorretas = letrasCorretas;
    }

    public static ArrayList<String> getChutes() {
        return chutes;
    }

    public static void setChutes(ArrayList<String> chutes) {
        Palavra.chutes = chutes;
    }

    public static ArrayList<String> getAcertos() {
        return acertos;
    }

    public static void setAcertos(ArrayList<String> acertos) {
        Palavra.acertos = acertos;
    }

    public static ArrayList<String> getChutesAcertados() {
        return chutesAcertados;
    }

    public static void setChutesAcertados(ArrayList<String> chutesAcertados) {
        Palavra.chutesAcertados = chutesAcertados;
    }

    public void setPalavras(Difficulty diff){

        List<PalavraView> palavras = new ArrayList<>();


        palavras.add(
                new PalavraView(Difficulty.EASY, "Macaco")
                        .addDica("É marrom")
                        .addDica("Tem Pelo")
                        .addDica("Tem 2 olhos"));


        //inicia um random e sorteia um valor no dentre o tamanho
        Random random = new Random();
        int num = random.nextInt(palavras.size());

        // define palavra completa com base no random
        setPalavraCorreta(palavras.get(num));

        //percorre a palavra completa separando por letra
        for (int i = 0; i < getPalavraCorreta().getPalavra().length(); i++) {
            //adiciona na lista de letras convertendo em char
            letrasCorretas.add(String.valueOf(getPalavraCorreta().getPalavra().charAt(i)));
            //adiciona na lista acertada o espaco correspondente
            chutesAcertados.add("_ ");
        }



    }
    public void setDifficulty(){
        //inicia um random e sorteia um valor no dentre o tamanho

            int num = random.nextInt(4);
        // define palavra completa com base no random
            Difficulty d = num == 1 ? Difficulty.EASY : num == 2 ? Difficulty.MEDIUM : num == 3 ? Difficulty.HARD : Difficulty.EASY;
            this.setPalavras(d );
//            dif.dificuldades.remove(num);
    }

    public Palavra() { setDifficulty();}

    public String getDica() {
        if (palavraCorreta.getDicas().size() >= indexDica) {
           indexDica ++;
           return palavraCorreta.getDicas().get(indexDica);
        } else {
            return "Não tem mais dicas";
        }
    }

    public boolean temDica() {
        return this.palavraCorreta.getDicas().size() > indexDica;
    }
}
