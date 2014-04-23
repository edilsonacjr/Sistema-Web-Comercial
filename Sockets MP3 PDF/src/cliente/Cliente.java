package cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    private String mensagem;
    private DataOutputStream saida;
    private DataInputStream entrada;
    private Socket cliente;

    public Cliente(String IP, int porta) {

        //Passo 1: Criar socket
        try {

            cliente = new Socket(InetAddress.getByName(IP), porta);

        } catch (EOFException eof) {

            System.out.println("\nConexão finalizada com servidor - erro \n");
            eof.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void getFile() {
        File fileName;

        fileName = new File("teste.mp3");

        String nome = fileName.toString();

        try {
            FileInputStream io = new FileInputStream(fileName);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * *************************************************************************************
     * Efetua o tratamento para deixar o cliente conectado com o servidor *
     * ************************************************************************************
     */
    public void runCliente() {
        Scanner msn = new Scanner(System.in);

        try {
            //Passo 1: Inicia socket cliente
            //System.out.println("Conectando ....");

            //System.out.println("Conectando ao Servidor: " + cliente.getInetAddress().getHostName());

            //Passo 2: Obtendo os fluxos de entrada e de saída
            //System.out.println("Obtendo streans de entrada e saída");
            saida = new DataOutputStream(cliente.getOutputStream());
            entrada = new DataInputStream(cliente.getInputStream());
            saida.flush();

            //Passo 4: Estabelecendo conexão com o servidor
            //do {
                try {
                    //obtem mensagem enviada pelo servidor
                    //mensagem = (String) entrada.readUTF();
                    //System.out.println("SERVIDOR>>" + mensagem);
                    //inicia comunicação aguardando o cliente digitar a mensagem
                    //System.out.print("CLIENTE >>");
                    //mensagem = (String) msn.nextLine();
                    sendData(mensagem);

                } catch (Exception cnfex) {
                    System.out.println(cnfex);
                }

            //} while (!mensagem.equals("SAIR"));

            //PASSO 4: Fecha a conexão
            System.out.println("\nFechando conexão\n");
            entrada.close();
            saida.close();
            cliente.close();

        } catch (EOFException eof) {
            System.out.println("\nConexão finalizada com servidor\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String s) throws FileNotFoundException, IOException {
        File fileName;

        fileName = new File("teste.mp3");


        FileInputStream io = new FileInputStream(fileName);
        byte[] buf = new byte[io.available()];
        int tam = io.available();
        System.out.println(tam);
        int test = io.read(buf);
             
        try {
            saida.writeUTF("teste5.mp3");
            saida.flush();
            saida.write(buf);
            saida.flush();

        } catch (IOException cnfex) {
            System.out.println("\nErro ao gravar dados");
        }
        io.close();
    }

    public static void main(String args[]) {

        Cliente c = new Cliente("127.0.0.1", 5000);

        c.runCliente();
    }

}
