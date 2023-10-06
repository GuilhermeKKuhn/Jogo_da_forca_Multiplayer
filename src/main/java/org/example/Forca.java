package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forca {

    private int erros = 0;

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

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
            p.chutesAcertados.add( "_ ");
        }

    }
    public void setDifficulty(Palavra palavra){
            //inicia um random e sorteia um valor no dentre o tamanho
//            Random random = new Random();
//            int num = random.nextInt(3);
        this.setPalavras(Difficulty.EASY,palavra);
            // define palavra completa com base no random
//            this.setPalavras(palavra.dificudade.get(num),palavra);
//            palavra.dificudade.remove(num);
//            this.setPalavras(Difficulty.EASY,palavra);
    }

    public String getForca(Palavra p, int erros){
        //inicia a forca
        PrintForca prtForca = new PrintForca();
        // chama forca para concatenar com as letras acertadas
        String letras  = prtForca.validaForca(erros);

        // for percorendo letras acertadas para adicionar na forca
        for(int i =0; i < p.letrasCorretas.size();i++){
            //concatena
            letras += p.chutesAcertados.get(i);
        }
        //retorna a forca ja concatenada com as letras
        return letras;
    }

    private String buscaChutes(Palavra p){
        String msg = "Chutes realizados ";
        for(String v : p.chutes){
            msg += v + " - ";
        }
        return msg;
    }


    public String addChute(String chute, Palavra p){
        String msg = "";
        if(chute.length() > 1){
            return msg = "Chute invalido";
        }
        boolean existe = false;
        if(p.chutes.size() > 0){
            for(String v: p.chutes) {
                if(v.equals(chute)) {
                    existe = true;
                }
            }
        }
        if(existe == true){
            msg = "Esse chute ja foi realizado :" ;
            return msg;
        }else{
            p.chutes.add(chute);
            if(atualizaLetras(chute,p)){
                msg = "ACERTOUuu \n" + buscaChutes(p);
            }else{
                setErros(getErros()+1);
                msg = "ERROUuu \n" + buscaChutes(p) + "\n" ;

            }
        }
        return msg;
    }
    private Boolean atualizaLetras(String letra, Palavra p){
        int cont = 0;
        Boolean flag = false;
        for(String v: p.letrasCorretas){
            if(v.equals(letra)){
                p.chutesAcertados.set(cont, letra + ' ');
                flag = true;
            }
            cont++;
        }
        return flag;
    }

    public boolean palavraFinal(Palavra p){
        String palavra = "";
        for(String v : p.letrasCorretas){
            palavra += v.replace(" ","");
        }
        return palavra.equals(p.palavraCorreta) ? true : false;
    }

}
