package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forca {



    public void setPalavras(Difficulty diff, Palavra p){

        ArrayList<String> palavras = new ArrayList<>();

        // verifica a dificudade e o array da mesma e add na lista
        if(diff.equals(Difficulty.EASY)) {

            String[] palavrasF = {"casa", "sol", "lua", "gato", "amor", "mar", "flor", "paz", "pao", "azul",
                    "cao", "fogo", "riso", "olho", "mao", "bola", "luta", "rato",
                    "vida", "pele", "voz", "tempo", "agua", "peso", "neve", "boca", "porta",
                    "cama", "seda", "sino", "teto", "sal"};

            palavras.addAll(List.of(palavrasF));
        } else if (diff.equals(Difficulty.MEDIUM)) {
            String[] palavrasM ={
                    "abobora", "abraco", "alface", "amarelo", "atencao", "bandeja", "beleza", "cachorro",
                    "caminho", "cenario", "cerveja", "chocolate", "coracao", "espelho", "estrela", "felicidade",
                    "florido", "guarda-chuva", "guitarra", "ilusao", "janela", "liberdade", "luminoso", "maravilha",
                    "mergulho", "nuvem", "piscina", "quadrado", "saboroso", "sereia", "sorriso", "tesouro", "violino"};
            palavras.addAll(List.of(palavrasM));
        }else {
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
        p.setPalavraCorreta(palavras.get(num));

        //percorre a palavra completa separando por letra
        for (int i = 0; i < p.getPalavraCorreta().length(); i++) {
            //adiciona na lista de letras convertendo em char
            p.letrasCorretas.add(String.valueOf(p.getPalavraCorreta().charAt(i)));
            //adiciona na lista acertada o espaco correspondente
            p.letrasCorretas.add( "_ ");
        }

    }

    public String getForca(int erros, Palavra p){
        PrintForca forca = new PrintForca();
        String completa = forca.validaForca(erros);

        for(int i = 0; i < p.letrasCorretas.size(); i++){
            completa += p.letrasCorretas.get(i);
        }
        return  completa;
    }

}
