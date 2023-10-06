package org.example;

import org.example.GerenciadorCliente;
import org.example.Player;

import java.util.ArrayList;

public class threadPlayer  implements Runnable{
    public static ArrayList<Player> players = new ArrayList<>();

    public threadPlayer(){
        new Thread(this).start();
    }
    private boolean stop;
    @Override
    public void run() {

    }
}
