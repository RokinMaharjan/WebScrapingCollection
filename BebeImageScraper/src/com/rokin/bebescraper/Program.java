/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rokin.bebescraper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            int imageNumber = 1;
            String url = "http://www.bebe.com/Dresses/197.sec";
            Document doc = Jsoup.connect(url).timeout(0).get();
            
            Elements elements = doc.select("a.jsImageDisplayAnchor");
            
            for(Element element : elements)
            {
                
                System.out.println("Link is " + element.attr("href") + "\n");
                String urlPage2 = "http://www.bebe.com" + element.attr("href");
                Document docPage2 = Jsoup.connect(urlPage2).timeout(0).get();
                Elements defaultImages = docPage2.select("div.defaultImage img");
                
                for(Element defaultImage : defaultImages)
                {
                    System.out.println(defaultImage.attr("src") + "\n");
                    String imageSRC = defaultImage.attr("src");
                   
                    for(int i = 1; i<5; i++)
                    {
                        
                        URL imageURL = new URL(imageSRC.replace("-i1", "-i"+i));
                        URLConnection imageURLConnection = imageURL.openConnection();
                        
                        FileOutputStream os = new FileOutputStream("/home/rokin/Desktop/Bebe/" + "/" + imageNumber + ".jpeg");
                        imageNumber++;
                        InputStream is = imageURLConnection.getInputStream();
                        byte[] image = new byte[1024];
                        int k = 0;
                        while((k = is.read(image))!=-1)
                        {
                            os.write(image, 0, k);
                        }
                        is.close();
                        os.close();
                    }
                    
                }
                
                

            }
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
