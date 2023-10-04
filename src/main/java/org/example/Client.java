package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Player player = new Player();
    private BufferedReader receber;
    private BufferedWriter enviar;
    private Socket socket;



    public Client(Player player, Socket socket) {
        try{
            this.player = player;
            this.socket = socket;
            this.receber = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.enviar = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    /*Esse metódo envia uma mensagem para o gerenciador de clientes que distribuirá a mensagem para outros
    clientes do arraylist*/
    public void enviarMensagem(){
        try{
            //Quando o enviar mensagem for executado pela primeira vez
            enviar.write(player.getNome());
            enviar.newLine();
            enviar.flush();

            Scanner scan = new Scanner(System.in);
            while(socket.isConnected()){
                String msg = scan.nextLine();
                enviar.write(player.getNome() +": "+ msg);
                enviar.newLine();
                enviar.flush();
            }
        }catch(IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    //Responsável por ficar ouvindo as mensagens
    public void receberMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgDoChat;
                while(socket.isConnected()){
                    try{
                        msgDoChat = receber.readLine();
                        System.out.println(msgDoChat);
                    }catch(IOException e){
                        fechaTudo(socket, receber, enviar);
                    }
                }
            }
        }).start();//Thread e Runneable
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

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite seu nome de usuário: ");
        String username = scan.nextLine();
        Socket socket = new Socket("localhost", 8080);
        Client cliente = new Client(username, socket);
        cliente.receberMsg();//Bloco com thread, sempre irá executar a parte
        cliente.enviarMensagem();
    }
}
