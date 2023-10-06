package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GerenciadorCliente implements Runnable{
    //Como não é servidor podemos usar o Socket socket
    private Socket socket;
    public static ArrayList<GerenciadorCliente> clientes = new ArrayList<>();

    public Forca forca = new Forca();

    public Palavra palavra = new Palavra();

    public static String ultimoJogado;
    public static String forcaAtual;

    private BufferedReader receber;
    private BufferedWriter enviar;

    private String username;



    public GerenciadorCliente(Socket socket){
        try{
            this.socket = socket;
            //Stream e buffer para leitura e escrita na rede
            this.receber = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.enviar = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Ao atribuir um valor para a String username, ela será lida pelo buffer
            this.username = receber.readLine();
            this.forca.setDifficulty(palavra);
        }catch (IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    @Override
    public void run() {

        while(socket.isConnected()){
            try{
                boolean logado = false;
                for(GerenciadorCliente cliente : clientes){
                    if(cliente.username.equals(username)){
                        logado = true;
                    }
                }
                if(!logado){
                    clientes.add(this);
                }else{
                    if(username.equals(ultimoJogado)) {
                        transmitirUnico(this);
                        esperaVez(this);
                    }else{
                        String msg = receber.readLine();
                        transmitirAll(msg);
                    }
                }






//                if(forca.palavraFinal(palavra)){
//                    msg = "GANHOU";
//                }

            }catch(IOException e){
                fechaTudo(socket, receber, enviar);
            }
        }
    }

    /// se nao for o usuario a vez nao passa
    private synchronized void transmitirAll(String msg) {
        //Para cada cliente no loop do while no método RUN cria-se uma interação
        for(GerenciadorCliente cliente : clientes){
            try{
                ultimoJogado = username;
                String aux = this.forca.addChute(msg, this.palavra);
                this.forcaAtual = forca.getForca(palavra, forca.getErros());
                msg = forcaAtual + "\n" + username + ": " + aux;
                cliente.enviar.write(msg);
                cliente.enviar.newLine();
                cliente.enviar.flush();
            }catch (IOException e){
                fechaTudo(socket, receber, enviar);
            }
        }

    }

    private void transmitirUnico(GerenciadorCliente cliente) {
        try {
            cliente.enviar.write("ESPERE SUA VEZ...");
            cliente.enviar.newLine();
            cliente.enviar.flush();
        } catch (IOException e) {
            fechaTudo(socket, receber, enviar);
        }
    }

    public synchronized void esperaVez(GerenciadorCliente cliente){
        while (ultimoJogado.equals(this.username)) {
            try {
                cliente.enviar.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try{
               Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void removerCliente(){
        clientes.remove(this);
        transmitirAll("Sevidor: "+username+ "Saiu do chat");
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

