package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Palavra {
    public Random random = new Random();
    public static String palavraCorreta = "";
    public static ArrayList<String> letrasCorretas = new ArrayList<>();

    public static ArrayList<String> chutes = new ArrayList<>();
    public static ArrayList<String> acertos = new ArrayList<>();
    public static ArrayList<String> chutesAcertados = new ArrayList<>();

    public static String getPalavraCorreta() {
        return palavraCorreta;
    }

    public static void setPalavraCorreta(String palavraCorreta) {
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

        List<String> palavras = new ArrayList<>();
        if(diff != null) {


            // verifica a dificudade e o array da mesma e add na lista
            if (diff.equals(Difficulty.EASY)) {

                String[] palavrasF = {"casa", "sol", "lua", "gato", "amor", "mar", "flor", "paz", "pao", "azul",
                        "cao", "fogo", "riso", "olho", "mao", "bola", "luta", "rato",
                        "vida", "pele", "voz", "tempo", "agua", "peso", "neve", "boca", "porta",
                        "cama", "seda", "sino", "teto", "sal"};

                palavras.addAll(List.of(palavrasF));
            } else if (diff.equals(Difficulty.MEDIUM)) {
                String[] palavrasM = {
                        "abobora", "abraco", "alface", "amarelo", "atencao", "bandeja", "beleza", "cachorro",
                        "caminho", "cenario", "cerveja", "chocolate", "coracao", "espelho", "estrela", "felicidade",
                        "florido", "guarda-chuva", "guitarra", "ilusao", "janela", "liberdade", "luminoso", "maravilha",
                        "mergulho", "nuvem", "piscina", "quadrado", "saboroso", "sereia", "sorriso", "tesouro", "violino"};
                palavras.addAll(List.of(palavrasM));
            } else {
                String[] palavrasD = {"abrilhanto", "acidental", "aventura", "comemorar", "esqueleto", "exuberante",
                        "fantastico", "fascinante", "inocencia", "luminaria", "melodioso", "memoravel",
                        "necessario", "oportunidade", "permanente", "resplendor", "silencioso", "transparente",
                        "velocidade", "vulneravel", "revigorante", "multicolor", "espetacular", "deslumbrar",
                        "fascinacao", "complexidade", "fascinador", "considerado", "reconfortar", "impressionar",
                        "surpreender", "maravilhoso", "fascinativo"};
                palavras.toArray(palavrasD);
                palavras.addAll(List.of(palavrasD));

            }

            //inicia um random e sorteia um valor no dentre o tamanho
            Random random = new Random();
            int num = random.nextInt(palavras.size());

            // define palavra completa com base no random
            setPalavraCorreta(palavras.get(num));

            //percorre a palavra completa separando por letra
            for (int i = 0; i < getPalavraCorreta().length(); i++) {
                //adiciona na lista de letras convertendo em char
                letrasCorretas.add(String.valueOf(getPalavraCorreta().charAt(i)));
                //adiciona na lista acertada o espaco correspondente
                chutesAcertados.add("_ ");
            }
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
}
