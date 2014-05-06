/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edilson
 */
public class ThreadServidorRecebe implements Runnable {

    private Socket connection;
    private DataInputStream entrada;

    public ThreadServidorRecebe(Socket connection) throws IOException {
        this.connection = connection;
        entrada = new DataInputStream(connection.getInputStream());
    }

    public void run() {

        while (!connection.isClosed()) {
            try {
                String texto = entrada.readUTF();
                if (texto.equals("sair")) {
                    //System.out.println("TESTE SAIR");
                    //Thread.currentThread().destroy();
                    connection.close();
                } else {
                    Servidor.mensagem += "\n" + texto;
                    synchronized (Servidor.exec) {
                        Servidor.exec.notifyAll();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorEnvia.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(ThreadServidorRecebe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
