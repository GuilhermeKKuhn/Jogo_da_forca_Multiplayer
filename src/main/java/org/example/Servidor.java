package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
public class Servidor {

    //Como é um TCP devemos usar um ServerSocket e não somente um socket
    private ServerSocket socket;

    public Servidor(ServerSocket socket) {
        this.socket = socket;
    }

    public void iniciarSessao() throws IOException {
        while(!socket.isClosed()){
            //Nesse ponto o servidor aguarda uma conexão quando um cliente se conecta o .accept retorna o socket
            //O clinte usa o Objeto conexao para se conectar
            Socket conexao = socket.accept();
            System.out.println("Um cliente se conectou");

            //Como teremos vários
            GerenciadorCliente gc = new GerenciadorCliente(conexao);
            Thread t = new Thread(gc);
            t.start();

        }
    }

    public void fecharSessao() throws IOException{
        if(socket != null){
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(8080);//Seto a porta
//        Servidor servidor = new Servidor(serverSocket);//Instâncio um Objeto
//        System.out.println("Servidor subiu!");
//        servidor.iniciarSessao();//Chamo o método de iniciar a sessão
//        Socket socket = serverSocket.accept();
//        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        String msg = (String) entrada.readLine();
//        BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        ServerSocket socket = new ServerSocket(8080);//Seto a porta
        Servidor servidor = new Servidor(socket);//Instâncio um Objeto
        System.out.println("Servidor subiu!");
        servidor.iniciarSessao();//Chamo o método de iniciar a sessão

    }
}
