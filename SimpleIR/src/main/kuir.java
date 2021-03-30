package main;

import java.io.IOException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import lecture.makeCollection;
import lecture.makeKeyword;
import lecture.index;

public class kuir {
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, TransformerFactoryConfigurationError {

		String dirname = "./directory";
		String filename = "./src/collection.xml";
		
		if (args.length < 2) {
			System.out.println("error: Please give me 2 arguments");
		}
		if (args.length > 1) {
			String option = args[0];
			switch(option) {
	        case "-c":
				String dirName = args[1];
				makeCollection makeCollection = new makeCollection();
				makeCollection.makeCollection(dirName);	
	            break;
	        case "-k":
				String fileName = args[1];
				makeKeyword makeKeyword = new makeKeyword();
				makeKeyword.makeKeyword(fileName);
				break;
	        case "-i":
				String fileName2 = args[1];
				index index = new index();
				index.index(fileName2);
				break;
	        default:
	            System.out.println("error: wrong option");
	            break;
	        }		
		}
	}
	
}
	