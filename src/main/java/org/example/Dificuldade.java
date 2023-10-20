package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Dificuldade {

    public static ArrayList<Difficulty> dificuldades = new ArrayList<>();

    public void addDiff(){
        Scanner scan = new Scanner(System.in);
        boolean flag = false;
        do{
            if(flag){
                System.out.println("Dificuldade invalida! tente novamente");
            }
            System.out.println("Escolha uma dificuldade:\n1 - Facil\n2 - Medio\n3 - Dificil");
            String resp = scan.next();
            if(!(resp.equals("1") || resp.equals("2") || resp.equals("3"))){
                flag = true;
            }else{
                dificuldades.add(resp.equals("1") ? Difficulty.EASY : resp.equals("2") ? Difficulty.MEDIUM : Difficulty.HARD);
            }
        }while (flag);
    }
}
