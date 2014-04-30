package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edilson
 */
public class Servidor extends Thread {

    private ServerSocket servidor;
    private Socket connection;
    private int contador = 1;
    public static String mensagem;
    private DataOutputStream saida;
    private DataInputStream entrada;
    public static ExecutorService exec;
    public static ExecutorService execR;

    public Servidor(int porta, int tamFila) throws IOException {
        servidor = new ServerSocket(porta, tamFila);
        mensagem = "BEM VINDO";
        exec = Executors.newCachedThreadPool();
        execR = Executors.newCachedThreadPool();
    }

    public void runServidor() throws InterruptedException {
        try {
            while (true) {
                System.out.println("Esperando Conexão...");
                connection = servidor.accept();
                System.out.println("Conexão  " + contador + " recebido de: "
                        + connection.getInetAddress().getHostName());

                exec.execute(new ThreadServidorEnvia(connection));
                execR.execute(new ThreadServidorRecebe(connection));

                //saida = new DataOutputStream(connection.getOutputStream());
                //entrada = new DataInputStream(connection.getInputStream());
                //saida.flush();
                //String task;
                //System.out.println("FIM DE CONEXÃO COM CLIENTE "
                //        + connection.getInetAddress().getHostName());
                //++contador;
            }
        } catch (IOException e) {
        }
    }

    public void sendList() throws IOException {
        File arquivo = new File("dir");

        File[] files = arquivo.listFiles();

        saida.writeInt(files.length);
        saida.flush();
        for (File f : files) {
            String nome = f.toString();
            nome = nome.substring(nome.lastIndexOf("/") + 1);
            saida.writeUTF(nome);
            saida.flush();
        }
    }

    public void receiveData() throws FileNotFoundException, IOException {
        int check;
        File fileName;

        String nome = entrada.readUTF();
        fileName = new File("dir/" + nome);

        int tam = entrada.readInt();

        FileOutputStream out = new FileOutputStream(fileName);
        do {
            out.write(check = entrada.read());
        } while (tam-- > 1);
        out.close();
    }

    public void sendData() throws IOException {
        String nome = entrada.readUTF();

        File fileName = new File("dir/" + nome);

        FileInputStream io = new FileInputStream(fileName);

        int tam = io.available();

        byte[] buf = new byte[io.available()];

        int test = io.read(buf);

        try {
            saida.writeUTF(nome);
            saida.flush();
            saida.writeInt(tam);
            saida.flush();
            saida.write(buf);
            saida.flush();
        } catch (IOException cnfex) {
        }
        io.close();
    }

    public static void main(String args[]) {
        Servidor s;
        try {
            s = new Servidor(5000, 0);
            s.runServidor();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
