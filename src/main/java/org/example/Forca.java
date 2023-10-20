package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forca {
    private static String palavraCorreta;
    private static ArrayList<String> letrasCorretas = new ArrayList<>();
    private static ArrayList<String> chutes = new ArrayList<>();
    private static ArrayList<String> acertos = new ArrayList<>();
    private static ArrayList<String> chutesAcertados = new ArrayList<>();


    public static String getPalavraCorreta() {
        return palavraCorreta;
    }

    public static void setPalavraCorreta(String palavraCorreta) {
        Forca.palavraCorreta = palavraCorreta;
    }

    public static ArrayList<String> getLetrasCorretas() {
        return letrasCorretas;
    }

    public static void setLetrasCorretas(ArrayList<String> letrasCorretas) {
        Forca.letrasCorretas = letrasCorretas;
    }

    public static ArrayList<String> getChutes() {
        return chutes;
    }

    public static void setChutes(ArrayList<String> chutes) {
        Forca.chutes = chutes;
    }

    public static ArrayList<String> getAcertos() {
        return acertos;
    }

    public static void setAcertos(ArrayList<String> acertos) {
        Forca.acertos = acertos;
    }

    public static ArrayList<String> getChutesAcertados() {
        return chutesAcertados;
    }

    public static void setChutesAcertados(ArrayList<String> chutesAcertados) {
        Forca.chutesAcertados = chutesAcertados;
    }


    public void setPalavras(Difficulty diff){

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
        setPalavraCorreta(palavras.get(num));

        //percorre a palavra completa separando por letra
        for (int i = 0; i < getPalavraCorreta().length(); i++) {
            //adiciona na lista de letras convertendo em char
            letrasCorretas.add(String.valueOf(getPalavraCorreta().charAt(i)));
            //adiciona na lista acertada o espaco correspondente
            chutesAcertados.add( "_ ");
        }

    }
    public void setDifficulty(){
        //inicia um random e sorteia um valor no dentre o tamanho
//            Random random = new Random();
//            int num = random.nextInt(3);
        this.setPalavras(Difficulty.EASY);
        // define palavra completa com base no random
//            this.setPalavras(palavra.dificudade.get(num),palavra);
//            palavra.dificudade.remove(num);
//            this.setPalavras(Difficulty.EASY,palavra);
    }

    public String getForca( int erros){
        //inicia a forca
        PrintForca prtForca = new PrintForca();
        // chama forca para concatenar com as letras acertadas
        String letras  = prtForca.validaForca(erros);

        // for percorendo letras acertadas para adicionar na forca
        for(int i =0; i < letrasCorretas.size();i++){
            //concatena
            letras += chutesAcertados.get(i);
        }
        //retorna a forca ja concatenada com as letras
        return letras;
    }

    private String buscaChutes(){
        String msg = "Chutes realizados ";
        for(String v : chutes){
            msg += v + " - ";
        }
        return msg;
    }


    public String addChute(String chute){
        String msg = "";
        if(chute.length() > 1){
            return msg = "Chute invalido";
        }
        boolean existe = false;
        if(chutes.size() > 0){
            for(String v: chutes) {
                if(v.equals(chute)) {
                    existe = true;
                }
            }
        }
        if(existe == true){
            msg = "Esse chute ja foi realizado :" ;
            return msg;
        }else{
            chutes.add(chute);
            if(atualizaLetras(chute)){
                msg = "ACERTOUuu \n" + buscaChutes();
            }else{
                msg = "ERROUuu \n" + buscaChutes() + "\n" ;

            }
        }
        return msg;
    }
    private Boolean atualizaLetras(String letra){
        int cont = 0;
        Boolean flag = false;
        for(String v: letrasCorretas){
            if(v.equals(letra)){
                chutesAcertados.set(cont, letra + ' ');
                flag = true;
            }
            cont++;
        }
        return flag;
    }

    public boolean palavraFinal(){
        String palavra = "";
        for(String v : letrasCorretas){
            palavra += v.replace(" ","");
        }
        return palavra.equals(palavraCorreta) ? true : false;
    }
}
