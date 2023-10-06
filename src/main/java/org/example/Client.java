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

    /*Esse metódo envia uma mensagem para o gerenciador de clientes que distribuirá a mensagem para outros
    clientes do arraylist*/
    public void enviarMensagem(){
        try{
            //Quando o enviar mensagem for executado pela primeira vez
            enviar.write(user);
            enviar.newLine();
            enviar.flush();



            // thread fazer
            Scanner scan = new Scanner(System.in);
            while(socket.isConnected()){
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
        boolean flag = true;


        while(flag){
            System.out.println("Digite seu nome de usuário: ");
            String username = scan.nextLine();
            Socket socket = new Socket("localHost", 8080);
            Client cliente = new Client(username, socket);
            cliente.receberMsg();//Blpoco com thread, sempre irá executar a parte
            cliente.enviarMensagem();


            boolean flagDifi = true;
            do {
                String dificuldade = null;
                try{
                    System.out.println("escolha uma dificuldade");
                    System.out.println("1 - Facil");
                    System.out.println("2 - medio");
                    System.out.println("3 - dificil");
                    dificuldade = scan.next();
                    switch (dificuldade) {
                        case "1":

                            flagDifi = false;
                            break;
                        case "2":

                            flagDifi = false;
                            break;
                        case "3":

                            flagDifi = false;
                            break;
                        default:
                            System.out.println("Dificuldade invalida");
                            flagDifi = true;
                            break;
                    }
                }catch (Exception e){
                    dificuldade = null;
                    flagDifi = true;
                    System.out.println("opção invalida");
                }
            }while(flagDifi);
            boolean flagPlay = true;
            do{
                String play = null;
                System.out.println("1 - Para jogar.");
                System.out.println("2 - para esperar outro jogador");
                play = scan.next();
                switch (play){
                    case "1" :
                        flag = false;

                        flagPlay = false;
                        break;
                    case "2":
                        System.out.println(" 2 - Aguardando");
                        flagPlay = true;
                        break;
                    default:
                        System.out.println(" Valor invalido");
                        flagPlay = true;
                        break;
                }
            }while(flagPlay);



            System.out.println("usuario invalido!");
        }


    }
}
