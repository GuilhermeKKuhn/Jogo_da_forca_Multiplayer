package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCliente implements Runnable{
    //Como não é servidor podemos usar o Socket socket
    private Socket socket;
    /*Criamos um array para cada instância dessa classe o objetivo principal desse array é
    ficar de olho em cada cliente, para quando um cliente mandar uma mensagem possamos percorrer o array e
    mandar uma mensagem para todos os outros clientes do array*/
    public static ArrayList<GerenciadorCliente> clientes = new ArrayList<>();

    public Forca forca = new Forca();

    public Palavra palavra = new Palavra();


    public static String ultimoJogado;


    //Buffers de transportes tanto de envio como de recebimento
    private BufferedReader receber;
    private BufferedWriter enviar;
    public List<String> jogadores = new ArrayList<>(List.of("Lucas", "Alexandre", "Guilherme"));
    private String username;

    public GerenciadorCliente(Socket socket){
        try{
            this.socket = socket;
            //Stream e buffer para leitura e escrita na rede
            this.receber = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.enviar = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Ao atribuir um valor para a String username, ela será lida pelo buffer
            this.username = receber.readLine();
            clientes.add(this);
        }catch (IOException e){
            fechaTudo(socket, receber, enviar);
        }
    }

    @Override
    public void run() {
        /*Toda vez que estamos ouvindo a mensagem nosso programa bloqueia o restante do código.
        Porém, precisamos ser capaz de continuar a enviar a mensagem e ainda assim ficar ouvindo,
        para isso, precisamos trabalhar com Threads, do qual são capazes de executar o nosso
        código em blocos*/
        ;
        while(socket.isConnected()){
            try{
                String msg = receber.readLine();

                for(String j : jogadores){
                    if(msg.equals(j)){
                        msg += this.username +  " logou";
                        transmitir(msg);
                    }
                }

//                if (msg.equals("EASY")) {
//                    palavra.dificudade.add(Difficulty.EASY);
//                }else if (msg.equals("MEDIUM")) {
//                    palavra.dificudade.add(Difficulty.MEDIUM);
//                }else if(msg.equals("HARD")){
//                    palavra.dificudade.add(Difficulty.HARD);
//                }
//
//                msg +=  " foi escolhida por " +this.username;

//                transmitir(msg);
            }catch(IOException e){
                fechaTudo(socket, receber, enviar);
            }
        }
    }

    /// se nao for o usuario a vez nao passa
    private void transmitir(String msg) {
        //Para cada cliente no loop do while no método RUN cria-se uma interação
        for(GerenciadorCliente cliente : clientes){
            try{
                if(!cliente.username.equals(username)){//Ignora o cliente que enviou a mensagem
                    cliente.enviar.write(msg);//Serealização da mensagem e envio pela rede
                    cliente.enviar.newLine();/*Quando o cliente apertar "enter" o programa entende que a
                    mensagem foi finalizada. Dessa forma, o buffer se adapta ao tamanho da mensagem e
                    não fica sobrecarregado e depois de enviado ele se esvazia automaticamente*/

                    cliente.enviar.flush();//Assim garantimos que o buffer será esvaziado
                }
            }catch (IOException e){
                fechaTudo(socket, receber, enviar);
            }
        }
    }

    public void removerCliente(){
        clientes.remove(this);
        transmitir("Sevidor: "+username+ "Saiu do chat");
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
