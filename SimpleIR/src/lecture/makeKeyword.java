package lecture;

import java.util.*;


import org.snu.*;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


  


public class makeKeyword {
	
    public void makeKeyword(String fileName) throws TransformerFactoryConfigurationError, TransformerException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        
    	String inputFile = fileName;
    	String outputFile = "./result/index.xml";
    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(inputFile));

    	    // locate the node(s)
    	XPath xpath = XPathFactory.newInstance().newXPath();
    	int number = 0;
    	for (number = 0; number <= 4; number++) {
    		NodeList nodes = (NodeList)xpath.evaluate("//*[@id='" + number + "']/body", doc, XPathConstants.NODESET);
    		for (int idx = 0; idx < nodes.getLength(); idx++) {
    			
    			String strToExtrtKwrd = nodes.item(idx).getTextContent();
        		// init KeywordExtractor
        		KeywordExtractor ke = new KeywordExtractor();
        		// extract keywords
        		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);
        		// print result
        		String set = "";
        		for( int i = 0; i < kl.size(); i++ ) {
        			Keyword kwrd = kl.get(i);
        			set = set + kwrd.getString() + ":" + kwrd.getCnt() + "#";
       			}
        		nodes.item(idx).setTextContent(set);
    		}
    	}

    	Transformer xformer = TransformerFactory.newInstance().newTransformer();
    	xformer.transform(new DOMSource(doc), new StreamResult(new File(outputFile)));
    	}
}
//
//    	
//
//                Element elementBody = result.createElement("body");        		
//        		//System.out.println(content);
//        		String strToExtrtKwrd = doc.getElementsByTag("body").text();
//        		// init KeywordExtractor
//        		KeywordExtractor ke = new KeywordExtractor();
//        		// extract keywords
//        		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);
//        		// print result
//        		String set = "";
//        		for( int i = 0; i < kl.size(); i++ ) {
//        			Keyword kwrd = kl.get(i);
//        			set = set + kwrd.getString() + ":" + kwrd.getCnt() + "#";
//        			}
//                System.out.print(doc.getElementsByTag("body").text());
//                elementBody.setTextContent(set);
//                elementDoc.appendChild(elementBody);
//                
//
//                number = number + 1;
//            }
//        
//        
//
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//
//        DOMSource source = new DOMSource(result);
//        StreamResult finalResult = new StreamResult(new FileOutputStream(new File("./src/index.xml")));
//
//        transformer.transform(source, finalResult);
//	
//	
//
//		//    	NodeList nodes = (NodeList)xpath.evaluate("//*[@id='" + number + "']/body", doc, XPathConstants.NODESET);
//
////System.out.println(xpath.evaluate("//*[@id='1']/body", doc, XPathConstants.STRING));
//
//
//for (int idx = 0; idx < nodes.getLength(); idx++) {
//	System.out.println(nodes.item(idx).getTextContent());
//	System.out.println(nodes.item(idx).getTextContent());
//	nodes.item(idx).setTextContent("Paul");
//}
// 
    // save the result
//		
//    }
//}
