package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Servidor;
import servidor.ThreadServidorEnvia;

/**
 *
 * @author edilson
 */
public class ThreadClienteRecebe extends Thread{
    private Socket connection;
    private DataInputStream entrada;
    
    public ThreadClienteRecebe(Socket connection) throws IOException{
        this.connection = connection;
        entrada = new DataInputStream(connection.getInputStream());
        this.start();
    }
    
    public void run(){
        while(true){
            try {
                String texto = entrada.readUTF();
                ClienteView.jtfMensagem.setText(texto);
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidorEnvia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
