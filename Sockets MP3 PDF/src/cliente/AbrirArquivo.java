/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josepedro
 */
public class AbrirArquivo {

    private File arquivo;
    private FileOutputStream out;
    private InputStream in;

    public AbrirArquivo(InputStream in) {
        this.in = in;
    }

    public void open() {

        try {
            arquivo = File.createTempFile("temp", null);
            out = new FileOutputStream(arquivo);
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = in.read(bytes)) > -1) {
                out.write(bytes, 0, read);
                out.flush();
            }

            out.close();

            in.close();
            Desktop.getDesktop().open(arquivo);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbrirArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AbrirArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
