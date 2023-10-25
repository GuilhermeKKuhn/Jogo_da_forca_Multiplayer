package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private BufferedReader receber;
    private BufferedWriter enviar;
    private Socket socket;

    private String user;

    public Client(String user, Socket socket) {
        try{
            this.user = user;
            this.socket = socket;
            this.receber = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.enviar = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }


    public void enviarMensagem(){
        try{
            enviar.write(user);
            enviar.newLine();
            enviar.flush();

            while(socket.isConnected()){
                Scanner scan = new Scanner(System.in);
                String msg = scan.nextLine();
                enviar.write(msg);
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
        // digita nick
        System.out.println("Digite seu nome de usuário: ");
        String username = scan.nextLine();


        System.out.println("Digite play para iniciar a espera de jogadores, 2 players obrigatorio");
        // digita dif
        Socket socket = new Socket("localHost", 8080);
        Client cliente = new Client(username, socket);
        cliente.receberMsg();//Blpoco com thread, sempre irá executar a parte
        cliente.enviarMensagem();

    }

//    public void addDiff(){
//        Scanner scan = new Scanner(System.in);
//        boolean flag = false;
//        do{
//            if(flag){
//                System.out.println("Dificuldade invalida! tente novamente");
//            }
//            System.out.println("Escolha uma dificuldade:\n1 - Facil\n2 - Medio\n3 - Dificil");
//            String resp = scan.next();
//            if(!(resp.equals("1") || resp.equals("2") || resp.equals("3"))){
//                flag = true;
//            }else{
//                dificuldades.add(resp.equals("1") ? Difficulty.EASY : resp.equals("2") ? Difficulty.MEDIUM : Difficulty.HARD);
//            }
//        }while (flag);
//    }
}
