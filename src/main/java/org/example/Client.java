package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Player player = new Player();
    private BufferedReader receber;
    private BufferedWriter enviar;
    private Socket socket;



    public Client(String player, Socket socket) {
        try{
            this.player.setNome(player);
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
                enviar.write(player.getNome() +" : "+ msg);
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
            Player player = new Player();
            for(String p: player.jogadores){
                if(p.equals(username)){
                        Palavra palavra = new Palavra();
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
                                        palavra.dificudade.add(Difficulty.EASY);
                                        flagDifi = false;
                                        break;
                                    case "2":
                                        palavra.dificudade.add(Difficulty.MEDIUM);
                                        flagDifi = false;
                                        break;
                                    case "3":
                                        palavra.dificudade.add(Difficulty.HARD);
                                        flagDifi = false;
                                        break;
                                    default:
                                        System.out.println("Dificuldade invalida");
                                        flagDifi = true;
                                        break;
                                }
                            }catch (Exception e){
                                System.out.println("opção invalida");
                                flagDifi = true;
                                dificuldade = null;
                            }

                        }while(flagDifi);
                    flag = false;
                    Socket socket = new Socket("192.168.22.69", 8080);
                    Client cliente = new Client(username, socket);
                    cliente.receberMsg();//Bloco com thread, sempre irá executar a parte
                    cliente.enviarMensagem();
                }
            }
            System.out.println("usuario invalido!");
        }


    }
}
