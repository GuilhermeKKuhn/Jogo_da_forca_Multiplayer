package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forca {



    public String getForca( int erros,Palavra palavra){
        //inicia a forca
        PrintForca prtForca = new PrintForca();
        // chama forca para concatenar com as letras acertadas
        String letras  = prtForca.validaForca(erros);

        // for percorendo letras acertadas para adicionar na forca
        for(int i =0; i < palavra.letrasCorretas.size();i++){
            //concatena
            letras += palavra.chutesAcertados.get(i);
        }
        //retorna a forca ja concatenada com as letras
        return letras;
    }

    private String buscaChutes(Palavra palavra){
        String msg = "Chutes realizados ";
        for(String v : palavra.chutes){
            msg += v + " - ";
        }
        return msg;
    }


    public String addChute(String chute,Palavra palavra){
        String msg = "";
        if(chute.length() > 1){
            return msg = "Chute invalido";
        }
        boolean existe = false;
        if(palavra.chutes.size() > 0){
            for(String v: palavra.chutes) {
                if(v.equalsIgnoreCase(chute)) {
                    existe = true;
                }
            }
        }
        if(existe){
            msg = "Esse chute ja foi realizado :\n" + buscaChutes(palavra)+"\n";
            return msg;
        }else{
            palavra.chutes.add(chute);
            if(atualizaLetras(chute,palavra)){
                msg = "ACERTOUuu \n" + buscaChutes(palavra);
            }else{
                msg = "ERROUuu \n" + buscaChutes(palavra) + "\n" ;

            }
        }
        return msg;
    }
    private Boolean atualizaLetras(String letra,Palavra palavra){
        int cont = 0;
        Boolean flag = false;
        for(String v: palavra.letrasCorretas){
            if(v.equalsIgnoreCase(letra)){
                palavra.chutesAcertados.set(cont, letra + ' ');
                flag = true;
            }
            cont++;
        }
        return flag;
    }

    public boolean palavraFinal(Palavra p){
        String palavra = "";
        for(String v : p.chutesAcertados){
            palavra += v.replace(" ","");
        }
        return palavra.equalsIgnoreCase(p.palavraCorreta.getPalavra());
    }
}
