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
import lecture.search;

public class kuir {
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, TransformerFactoryConfigurationError {


//		index index = new index();
//		String fileName2 = "./result/index.xml";
//		index.index(fileName2);
//		search searching = new search();
//		String filepath3 = "./result/index.post";
//		String query = "라면에는 면, 분말 스프가 있다.";
//		searching.calcSim(filepath3, query);
//		String dirname = "./directory";
//		String filename = "./src/collection.xml";
		
//		if (args.length < 2) {
//			System.out.println("error: Please give me 2 arguments");
//		}
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
				System.out.println("indexdone");
				break;
	        case "-s":
				String filepath3 = args[1];
				String query = args[2];
				search searching = new search();
				searching.innerproduct(filepath3, query);
				break;
	        default:
	            System.out.println("error");
	            break;
	        }		
		}
	}
	
}
	