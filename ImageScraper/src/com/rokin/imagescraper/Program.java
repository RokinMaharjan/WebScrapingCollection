/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rokin.imagescraper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rokin
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL url = new URL("http://ep.yimg.com/ay/yhst-62808405243919/modern-chandeliers-2.png");
            URLConnection urlConnection = url.openConnection();
            
            FileOutputStream os = new FileOutputStream("/home/rokin/Desktop/image.jpg");
            InputStream is = urlConnection.getInputStream();
            byte[] data = new byte[1024];
            int i=0;
            while((i=is.read(data))!=-1)
            {
                os.write(data,0,i);
            }
            
            is.close();
            os.close();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
