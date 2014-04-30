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
                System.out.println("Conexão recebida de: "
                        + connection.getInetAddress().getHostName());

                exec.execute(new ThreadServidorEnvia(connection));
                execR.execute(new ThreadServidorRecebe(connection));

            }
        } catch (IOException e) {
        }
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
