/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edilson
 */
public class ThreadServidorEnvia implements Runnable{
    private Socket connection;
    private DataOutputStream saida;
    
    public ThreadServidorEnvia(Socket connection) throws IOException{
        this.connection = connection;
        saida = new DataOutputStream(connection.getOutputStream());
    }
    
    public void run(){
        while(true){
            try {
                saida.writeUTF(Servidor.mensagem);
                saida.flush();
                synchronized (Servidor.exec) {
                    Servidor.exec.wait();                                        
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorEnvia.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadServidorEnvia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
