

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Midterm {
	
	public static void main(String[] args) {
		
		String [] word = new String[5];
		
		try{
            File file = new File("./input.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                System.out.println(line);
                int i = 0;
                word[i] = line;
                
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
        }catch(IOException e){
            System.out.println(e);
        }
		
		System.out.println("1 :" + word[0]);

		
	}
	
	

	

}
