/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rokin.netaporterscraper;

import java.io.FileOutputStream;
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
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 *
 * @author rokin
 */
public class Scraper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //FileWriter writer = new FileWriter("/home/rokin/Desktop/NetAPorter.csv");
            
            
            String url = "https://www.net-a-porter.com/np/en/d/shop/Beauty/Makeup?pn=1&npp=60&image_view=product&dScroll=0";
            Document doc = Jsoup.connect(url).timeout(0).get();
            
            
            Elements categories = doc.select("li.Makeup ul li");
            System.out.println(categories.size());
            for(int i=0; i<categories.size(); i++)
            {
                String productCategory = categories.get(i).text();
                
                for(int j=1; j<=17; j++ )
                {
                    url = "https://www.net-a-porter.com/np/en/d/Shop/Beauty/Makeup" + categories.get(i).select("a[href]").attr("href") + "pn=" + j + "&npp=60&dscroll=0";
                    try {
                        doc = Jsoup.connect(url).timeout(0).get();
                    } catch (IOException ex) {
                        System.out.println("Eta pugyo====================");
                        break;
                    }
                    
                    Elements products = doc.select("ul.products li");
                    //System.out.println(products.get(0));
                    for(Element product : products)
                    {   
                        String brand = product.select("span.designer").text();
                        Element br = product.select("br").first();
                        String price = product.select("span.price").text();
                        String imageURL = "http:" + product.select("img").attr("data-image-product");
                        
                        Node productName = br.nextSibling();
                        
                        
                        System.out.println(productCategory);
                        System.out.println(brand);
                        System.out.println(productName.toString());
                        System.out.println(price);
                        System.out.println(imageURL);
                        
                        //writer.append('"' + productCategory + '"' + "," + '"' + brand + '"' + "," + '"' + productName.toString() + '"' + "," + '"' + price + '"' + "," + '"' + imageURL + '"' + "," + "\n");
                        
                        
                        
                        URL imageDownloadURL = new URL(imageURL);
                        URLConnection urlConnection = imageDownloadURL.openConnection();
                        
                        FileOutputStream os = new FileOutputStream("/home/rokin/Desktop/NetAPorter/" + productCategory.replace("/", "-") + " - " + productName.toString().replace("/", "-") + ".jpg");
                        InputStream is = urlConnection.getInputStream();
                        
                        byte[] data = new byte[1024];
                        int imageBytes = 0;
                        while((imageBytes = is.read(data))!= -1)
                        {
                            os.write(data, 0, imageBytes);
                        }
                        is.close();
                        os.close();
                        
                    }
                    
                }
               
            }
            
            //writer.close();
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
}
