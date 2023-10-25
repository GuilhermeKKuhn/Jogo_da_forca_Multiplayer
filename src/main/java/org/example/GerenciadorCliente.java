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

    public static Palavra palavra;

    //Como não é servidor podemos usar o Socket socket
    public static ArrayList<GerenciadorCliente> clientes = new ArrayList<>();
    public static int start;
    public static ArrayList<GerenciadorCliente> playersAtivos = new ArrayList<>();
    public static int cont = 0;
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
            playersAtivos.add(this);
            if(jogadorVez == null){
                palavra = new Palavra();
                jogadorVez = playersAtivos.get(cont);
                transmitirAll("Forca do " + jogadorVez.player.getNome() + "\n" +
                        jogadorVez.player.getForca().getForca(jogadorVez.player.getErros(), palavra)
                        + "\n" + jogadorVez.player.getNome() +" Informe uma letra: ");

            }
        }catch (IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    @Override
    public void run() {

        while(socket.isConnected()){
            try{

                while(clientes.size() < 2){
                    try {

                        transmitirAll("Aguardando jogadores " + clientes.size() + " de 3");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                if(jogadorVez.player.getNome().equals(this.player.getNome())) {
                    String ret = jogadorVez.player.getForca().addChute(receber.readLine(), palavra);
                    //chama a forca do player
                    msgAtual = "Forca do " + jogadorVez.player.getNome() + "\n" +
                            jogadorVez.player.getForca().getForca(jogadorVez.player.getErros(), palavra);

                    //concatena a forca com o player e o retorno
                    msgAtual += "\n" + jogadorVez.player.getNome() + ": " + ret;

                    //trasmite

                    if (ret.contains("ERROUuu")) {
                        jogadorVez.player.setErros(jogadorVez.player.getErros() + 1);
                        jogadorVez.player.setVidas(jogadorVez.player.getVidas() - 1);
                        if (jogadorVez.player.getVidas() <= 0) {
                            transmitirAll("Jogador " + jogadorVez.player.getNome() + " perdeu, esta fora desta rodada");
                            transmitirAll("Jogador da Vez : " + jogadorVez.player.getNome());
                            playersAtivos.remove(jogadorVez);
//                            if(cont < playersAtivos.size()-1){
//                                this.cont++;
//                            }else{
//                                cont = 0;
//                            }
                        }
                    } else {
                        jogadorVez.player.setPontos(player.getPontos() + 10);
                        if (jogadorVez.player.getForca().palavraFinal(palavra)) {
                            transmitirAll(jogadorVez.player.getNome() + " Acertou a palavra e ganhou a rodada !");
                            jogadorVez.player.setPontos(player.getPontos() + 20);
                            String msg = ("Acompanhe a pontuacao\n");
                            for (GerenciadorCliente cliente : clientes) {
                                msg += cliente.player.getNome() + " com o total de " + cliente.player.getPontos() + ";\n";
                            }
                            transmitirAll(msg);
                            for(GerenciadorCliente cliente : clientes){
                                playersAtivos.remove(cliente);
                            }

                        }
                    }
                    if (cont < playersAtivos.size() - 1 ) {
                        cont++;
                    } else {
                        cont = 0;
                    }
                    jogadorVez = playersAtivos.get(cont);
                    transmitirAll(msgAtual + "\n" + jogadorVez.player.getNome() +" Informe uma letra: ");
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
        for(GerenciadorCliente cliente : playersAtivos){
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
        playersAtivos.remove(this);
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

