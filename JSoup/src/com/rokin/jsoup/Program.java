/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rokin.jsoup;

import java.io.FileWriter;
import java.io.IOException;
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
            String url = "http://www.clubsofaustralia.com.au/Netball/Clubs-in-Western-Australia.html";
            Document doc = Jsoup.connect(url).timeout(0).get();
            //System.out.println(doc);
            //Elements clubName = doc.select("p#middle-li-left");
            //System.out.println(clubName.html());
            
            Elements links = doc.select("a[href*=http].nav11");
            
//            System.out.println(links.select("a.class=landline"));
//            System.out.println("asdasdasdasd+++++++++asdasd+========");
            

            FileWriter writer = new FileWriter("/home/rokin/Desktop/ClubsOfAustralia.csv");

            for(int i = 0; i<links.size(); i++)
            {
                String crawledURL = links.get(i).attr("href");
                System.out.println(crawledURL + "\n\n");
                Document crawledDoc = Jsoup.connect(crawledURL).timeout(0).get();
                
                Elements elements = crawledDoc.select("div.middle ul#cat_cont li");
                
                
                for(Element element : elements)
                {
                    String[] clubNameVisited = element.select("p#middle-li-left").text().split("----");
                    String website = element.select("a[href*=http:]").attr("href");
                    String email = element.select("a[href*=mailto]").attr("href");
                    String landline = element.select("a.landline").text();
                    String mobile = element.select("a.mobile").text();
                    
                    System.out.println(clubNameVisited[0]);
                    System.out.println(website);
                    System.out.println(email);
                    System.out.println(landline);
                    System.out.println(mobile);
                    System.out.println(clubNameVisited[1]);
                    
                    writer.append('"' + clubNameVisited[0].trim() + '"' + "," + '"' + email.replace("mailto:", "").trim() + '"' + "," + '"' + landline + '"' + "," + '"' + mobile + '"' + "," + '"' + website.trim() + '"' + "," + '"' + clubNameVisited[1].replace("Visited:", "").replace("Times", "").trim() + '"' + "\n");
                }
                
                
                System.out.println("===================");
            }
            
            writer.close();
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
}
