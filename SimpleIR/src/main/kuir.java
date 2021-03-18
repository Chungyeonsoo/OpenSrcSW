package main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import lecture.makeCollection;
import lecture.makeKeyword;


public class kuir {
	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, XPathExpressionException, TransformerFactoryConfigurationError, SAXException {
		String dirname = "./directory";
		String filename = "./src/collection.xml";
		makeCollection collection = new makeCollection(dirname);
		makeKeyword keyword = new makeKeyword(filename);
		
	}

}