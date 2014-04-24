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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edilson
 */
public class Servidor {

    private ServerSocket servidor;
    private Socket connection;
    private int contador = 1;
    private String mensagem;
    private DataOutputStream saida;
    private DataInputStream entrada;

    public Servidor(int porta, int tamFila) throws IOException {
        servidor = new ServerSocket(porta, tamFila);
    }

    public void runServidor() {
        try {
            while (true) {
                System.out.println("Esperando Conexão...");
                connection = servidor.accept();
                System.out.println("Conexão  " + contador + " recebido de: "
                        + connection.getInetAddress().getHostName());

                saida = new DataOutputStream(connection.getOutputStream());
                entrada = new DataInputStream(connection.getInputStream());
                saida.flush();

                String task = entrada.readUTF();
                switch (task) {
                    case "receive":
                        receiveData();
                        break;
                    case "send":
                        break;
                }
                receiveData();

                System.out.println("Pegando streans de entrada e saída");

                System.out.println("FIM DE CONEXÃO COM CLIENTE "
                        + connection.getInetAddress().getHostName());
                ++contador;

            }
        } catch (IOException e) {

        }
    }

    public void receiveData() throws FileNotFoundException, IOException {
        int check = 0;
        File fileName;

        String nome = entrada.readUTF();
        fileName = new File(nome);

        FileOutputStream out = new FileOutputStream(fileName);
        do {
            out.write(check = entrada.read());
        } while (check != -1);
        out.close();
    }

    public void sendData() throws IOException {

        String nome = entrada.readUTF();

        File fileName = new File(nome);

        FileInputStream io = new FileInputStream(fileName);
        byte[] buf = new byte[io.available()];
        int test = io.read(buf);

        saida.writeUTF(nome);
        saida.flush();
        saida.write(buf);
        saida.flush();

        io.close();
    }

    public static void main(String args[]) {
        Servidor s;
        try {
            s = new Servidor(5000, 0);
            s.runServidor();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
