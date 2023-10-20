package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GerenciadorCliente implements Runnable{
    public Dificuldade dif = new Dificuldade();

    //Como não é servidor podemos usar o Socket socket
    public static ArrayList<GerenciadorCliente> clientes = new ArrayList<>();
    public static int cont;
    private static String msgAtual;
    public static GerenciadorCliente jogadorVez;
    private Socket socket;
    private BufferedReader receber;
    private BufferedWriter enviar;
    private Player player;

    public GerenciadorCliente(Socket socket){
        try{
            this.socket = socket;
            //Stream e buffer para leitura e escrita na rede
            this.receber = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.enviar = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Ao atribuir um valor para a String username, ela será lida pelo buffer
            player = new Player(receber.readLine()) ;
            clientes.add(this);

        }catch (IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    @Override
    public void run() {

        while(socket.isConnected()){
            try{
//                while(clientes.size() < 1){
//                    try {
//                        transmitirAll("Aguardando jogadores " + clientes.size() + " de 3");
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
                jogadorVez = clientes.get(cont);

                if(jogadorVez.player.getNome().equals(this.player.getNome())){
                    transmitirAll("Jogador da Vez : " + jogadorVez.player.getNome());
                    String ret = jogadorVez.player.getForca().addChute(receber.readLine());
                    if(ret.contains("ERROUuu")){
                        jogadorVez.player.setErros(jogadorVez.player.getErros() + 1);
                    }
                    msgAtual = "Forca do " + jogadorVez.player.getNome() + " " +
                            jogadorVez.player.getForca().getForca(jogadorVez.player.getErros());
                    msgAtual += "\n" + jogadorVez.player.getNome() + ": " + ret;
                    transmitirAll(msgAtual);
                    if(cont < clientes.size()-1){
                        this.cont++;
                    }else{
                        cont = 0;
                    }
                }else{
//                  receber.skip(receber.ready() ? receber.readLine().length() : 0); funciona mas pula linha
                    while (receber.ready()) {
                        receber.readLine();
                    }
                }
            }catch(IOException e){
                fechaTudo(socket, receber, enviar);
            }
            //fazer a outra tread receber o item e essa fica esperando
        }
    }

    /// se nao for o usuario a vez nao passa
    public  void transmitirAll(String msg) {
        //Para cada cliente no loop do while no método RUN cria-se uma interação
        for(GerenciadorCliente cliente : clientes){
            try{
                cliente.enviar.write(msg);
                cliente.enviar.newLine();
                cliente.enviar.flush();

            }catch (IOException e){
                fechaTudo(socket, receber, enviar);
            }
        }

    }

    public void removerCliente(){
        clientes.remove(this);
        transmitirAll("Sevidor: "+ player.getNome() + "Saiu do chat");
    }

    public void fechaTudo(Socket socket, BufferedReader receber, BufferedWriter enviar){
        try{
            if(socket != null){
                socket.close();
            }
            if(enviar != null){
                enviar.close();
            }
            if(receber != null){
                receber.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

