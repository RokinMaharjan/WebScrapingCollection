/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rokin.asosscraper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
public class AsosScraper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //FileWriter writer = new FileWriter("/home/rokin/Desktop/asos.csv");
            String url = "http://www.asos.com/women/beauty/make-up/cat/?cid=5020&cr=6&CTARef=shop|beauty|makeup";
            Document doc = Jsoup.connect(url).timeout(0).get();
            
            
            
            
            Elements categories = doc.select("div.options.scrollable ul li");
            for(int i =0; i<24; i++)
            {
                
                String category = categories.get(i).text().replace("ticked", "");
                String realCategory = category.substring(0, category.length() - 4).trim();
                if(!(i==0 || i==5 || i==17 || i==20 || i==23))
                {
                    for(int j = 0; j<5;j++)
                    {
                        url = "http://www.asos.com/women/beauty/make-up/cat/" + categories.get(i).select("a[href]").attr("href").replace("pge=0", "pge=" + j);
                        doc = Jsoup.connect(url).timeout(0).get();
                        Elements products = doc.select("li.product-container");
                        for(Element product : products)
                        {
                            String image = product.select("img").attr("src");
                            String name = product.select("span.name").text();
                            String previousPrice = product.select("div.price-previous span.price").text().replace("&#163;", "£");
                            String newPrice = product.select("div.price-current span.price").text().replace("&#163;", "£");

                            System.out.println(name);
                            System.out.println(previousPrice);
                            System.out.println(newPrice);
                            System.out.println(image);
                            //System.out.println("========");

                            //writer.append('"' + category + '"' + "," + '"' + name + '"' + "," + '"' + previousPrice + '"' + "," + '"' + newPrice + '"' + "," + '"' + image + '"' + "," + "\n");
                            
                            
                            
                            URL imageUrl = new URL(image);
                            URLConnection urlConnection = imageUrl.openConnection();

                            FileOutputStream outputStream = new FileOutputStream("/home/rokin/Desktop/Asos/" + realCategory.replace("/", "").replace("(", "") + " - " + name.replace("/", "").replace("(", "") + ".jpg");
                            InputStream inputStream = urlConnection.getInputStream();

                            byte[] data = new byte[1024];
                            int imageBytes = 0;
                            while((imageBytes = inputStream.read(data)) != -1)
                            {
                                outputStream.write(data, 0, imageBytes);
                            }
                            outputStream.close();
                            inputStream.close();
                        }
                    }
                }
                System.out.println("==============================");
            }
            
            
            //writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AsosScraper.class.getName()).log(Level.SEVERE, null, ex);
        }





//       IMAGE SCRAPING

//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("/home/rokin/Desktop/asos.csv"));
//            String line = "";
//            while((line=reader.readLine()) != null)
//            {
//                String[] tokens = line.split(",");
//                
//                URL url = new URL(tokens[4]);
//                URLConnection urlConnection = url.openConnection();
//                
//                FileOutputStream outputStream = new FileOutputStream("/home/rokin/Desktop/Asos/" + tokens[1] + ".jpg");
//                InputStream inputStream = urlConnection.getInputStream();
//                
//                byte[] data = new byte[1024];
//                int imageBytes = 0;
//                while((imageBytes = inputStream.read(data)) != -1)
//                {
//                    outputStream.write(data, 0, imageBytes);
//                }
//                outputStream.close();
//                inputStream.close();
//            }
//            
//            
//        } catch (IOException ex) {
//            System.out.println("Exception: " + ex.getMessage());
//        }

    }

}
