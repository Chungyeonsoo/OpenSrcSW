package lecture;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;

import org.jsoup.parser.Parser;
import org.snu.*;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class search {
public void calcSim(String docpath, String query) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true);
		
		Map<String, Integer> ktf = new HashMap<>();
		ArrayList<String> keyword = new ArrayList<String>();
		ArrayList<Integer> kwint = new ArrayList<Integer>();
		
		for( int i = 0; i < kl.size(); i++ ) {
			Keyword kwrd = kl.get(i);
			ktf.put(kwrd.getString(), kwrd.getCnt());
			keyword.add(kwrd.getString());
			kwint.add(kwrd.getCnt());
			
		}
		
		System.out.println("key int = " +keyword);

		double [] sims = {0,0,0,0,0};
		
    	FileInputStream filepath = new FileInputStream("docpath");
    	ObjectInputStream objectInputStream = new ObjectInputStream(filepath);
		Object object = null;
		try {
			object = objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	objectInputStream.close();
    	
    	HashMap hashMap = (HashMap) object;
    	Iterator<String> it = hashMap.keySet().iterator();
    	    	
    	double [][] tfset = new double [5][keyword.size()]; // [5 docs][keyword.size]
    	int o = 0;
    	
    	while (it.hasNext()) {
    		String key = it.next();
    		List<String> values = (List<String>) hashMap.get(key);
    		//System.out.print(key);
    		for(String data: keyword) {	//	query
    			if(data.equals(key)) {
    				
    				
    				for(int k = 0; k<values.size();k+=2) {
    					
    					int l = Integer.parseInt(values.get(k));
    					int j = k+1;
    					double[] arr = {0.0, 0.0, 0.0, 0.0, 0.0};
    					
    					switch (l) {
    					
    					case 0:
      						arr[0] = Double.parseDouble(values.get(j));
      						tfset[0][o] = arr[0];
    						break;
    					case 1:
      						arr[1] = Double.parseDouble(values.get(j));
      						tfset[1][o] = arr[1];
    						break;
    					case 2:
      						arr[2] = Double.parseDouble(values.get(j));
      						tfset[2][o] = arr[2];
    						break;
    					case 3:
      						arr[3] = Double.parseDouble(values.get(j));
      						tfset[3][o] = arr[3];
    						break;
    					case 4:
      						arr[4] = Double.parseDouble(values.get(j));
      						tfset[4][o] = arr[4];
    						break;
    					}
    					o++;    		  					
    				}
    			}
    		}
    	}
    	
    	//system
    	
    	Map<Integer, Double> result = new HashMap<>();
    	double [] fr = {0,0,0,0,0};
    	int [] docfr = {0,1,2,3,4};
    	
    	double denominator1 = 0;
    	double denominator2 = 0;
    	double [] denomset = {0,0,0,0,0};
    	
    	for (int documentnumber = 0; documentnumber<5; documentnumber++) {
    		for (int wordfrequency = 0; wordfrequency<keyword.size(); wordfrequency++) {
    			double denopow = Math.pow(tfset[documentnumber][wordfrequency], 2.0); // 제곱 저
    			denominator1 = denopow + denominator1;
    			denopow = 0.0;
    			double secondwf = Math.pow(kwint.get(wordfrequency), 2);
    			denominator2 = secondwf + denominator2;
    			secondwf = 0;
    		}
    		denomset[documentnumber] = Math.sqrt(denominator1) * Math.sqrt(denominator2);		    		
    	}

    	
    	double sum = 0;
    	for (int a = 0; a<5; a ++) {	//	document number
    		for (int b = 0; b<keyword.size(); b++) {
    			// multiply query frequency
    			double first = tfset[a][b] * kwint.get(b);
    			//System.out.println("ffff " + first);
    			sum = first + sum;
    			first = 0.0;		
    		}
    		fr[a] = sum;	//save doc num in fr[i]
    	}
    	
    	double [] sim = {0,0,0,0,0};
    	for (int forsim = 0; forsim < 5; forsim++) {
    		sim[forsim] = fr[forsim] / denomset[forsim];
    		if(Double.isNaN(sim[forsim])) {
                sim[forsim] = 0;
            }
    	}
    	
    	for (int adf = 0; adf<5; adf++) {
    		System.out.println(sim[adf]);
    	}
    	
//    	for(int c= 0; c<sim.length;c++) {
//    		double tmp = sim[c];	//	tmp에 현재
//    		int doctmp = c;
//    		for(int b = c+1; b<sim.length; b++) {
//    			
//    			if(sim[b] > tmp) {
//    				sim[c] = sim[b];
//    				docfr[c] = docfr[b];  
//    				
//    				sim[b] = tmp;
//    				docfr[b] = doctmp;
//    				
//    				tmp = 0;
//    				doctmp = 0;
//    			}
//    		}
////    		
//    	}
    	
    	
    	for(int c= 0; c<fr.length;c++) {
    		double tmp = fr[c];	//	tmp에 현재
    		int doctmp = c;
    		for(int b = c+1; b<fr.length; b++) {
    			
    			if(fr[b] > tmp) {
    				fr[c] = fr[b];
    				docfr[c] = docfr[b];  
    				
    				fr[b] = tmp;
    				docfr[b] = doctmp;
    				
    				tmp = 0;
    				doctmp = 0;
    			}
    		}
//    		
    	}

    		
    	String docpath2 = docpath;
    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("./result/index.xml");
    	XPath xpath = XPathFactory.newInstance().newXPath();
    	
    	Element root = doc.getDocumentElement();
        //System.out.println("Root : " + root.getTagName());
        // 각노드의 리스트 취득
        NodeList list = root.getElementsByTagName("title");
        
        for(int rank = 0; rank < 3; rank++) {
        	
        	Node item = list.item(docfr[rank]);
//        	if(sim[rank] == 0) {
//        		break;
//        	}
        	if(fr[rank] == 0) {
        		break;
        	}
        	if(rank == 2) {
        		System.out.print(rank+1 + "위 :" + item.getTextContent());
        	} else {
        		System.out.print(rank+1 + "위 :" + item.getTextContent() + " ");
        	}
    		//System.out.println("Rank " + output + " : " + docfr[rank]);
    		//NodeList nodes = (NodeList)xpath.evaluate("//*[@id='" + docfr[rank] + "']/body", doc, XPathConstants.NODESET);
    	}    	
        //System.out.println(item.getTextContent());
    	
//    	
//    	Document xmlDoc = null;
//    	xmlDoc = (Document) Parser.parse(doc, doc);
//    	Element root = xmlDoc.getDocumentElement();
//    	
//    	Document docs = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource("./result/collection.xml"));
// 
//    	XPath xpath = XPathFactory.newInstance().newXPath();
//
//    	
//    	Node col2 = (Node)xpath.evaluate("//*[@id='2']", doc, XPathConstants.NODE);
//    	System.out.println(col2.getAttributes().getNamedItem("title").getTextContent());

    	
        
       // NodeList n1 = root.getElementsByTagName("title");


    	
	}

//	public void calcSim(String docpath, String query) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
//		
//		KeywordExtractor ke = new KeywordExtractor();
//		KeywordList kl = ke.extractKeyword(query, true);
//		
//		Map<String, Integer> ktf = new HashMap<>();
//		ArrayList<String> keyword = new ArrayList<String>();
//		ArrayList<Integer> kwint = new ArrayList<Integer>();
//		
//		for( int i = 0; i < kl.size(); i++ ) {
//			
//			Keyword kwrd = kl.get(i);
//			ktf.put(kwrd.getString(), kwrd.getCnt());
//			keyword.add(kwrd.getString());
//			kwint.add(kwrd.getCnt());
//			
//		}
//		
//		// System.out.println("key int = " + kwint);
//
//		double [] sims = {0,0,0,0,0};
//		
//    	FileInputStream filepath = new FileInputStream("./result/index.post");
//    	ObjectInputStream objectInputStream = new ObjectInputStream(filepath);
//		Object object = null;
//		try {
//			object = objectInputStream.readObject();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	objectInputStream.close();
//    	
//    	HashMap hashMap = (HashMap) object;
//    	Iterator<String> it = hashMap.keySet().iterator();
//    	    	
//    	double [][] tfset = new double [5][keyword.size()];
//    	int o = 0;
//    	
//    	while (it.hasNext()) {
//    		String key = it.next();
//    		List<String> values = (List<String>) hashMap.get(key);
//    		//System.out.print(key);
//    		for(String data: keyword) {
//    			
//    			
//    			if(data.equals(key)) {
//    				
//    				for(int k = 0; k<values.size();k+=2) {
//    					
//    					int l = Integer.parseInt(values.get(k));
//    					int j = k+1;
//    					double[] arr = {0.0, 0.0, 0.0, 0.0, 0.0};
//    					
//    					switch (l) {
//    					
//    					case 0:
//      						arr[0] = Double.parseDouble(values.get(j));
//      						tfset[0][o] = arr[0];
//    						break;
//    					case 1:
//      						arr[1] = Double.parseDouble(values.get(j));
//      						tfset[1][o] = arr[1];
//    						break;
//    					case 2:
//      						arr[2] = Double.parseDouble(values.get(j));
//      						tfset[2][o] = arr[2];
//    						break;
//    					case 3:
//      						arr[3] = Double.parseDouble(values.get(j));
//      						tfset[3][o] = arr[3];
//    						break;
//    					case 4:
//      						arr[4] = Double.parseDouble(values.get(j));
//      						tfset[4][o] = arr[4];
//    						break;
//    					}
//    					o++;    		  					
//    				}
//    			}
//    		}
//    	}
//    	
//    	Map<Integer, Double> result = new HashMap<>();
//    	double [] fr = {0,0,0,0,0};
//    	int [] docfr = {0,1,2,3,4};
//
//    	
//    	double sum = 0;
//    	for (int a = 0; a<5; a ++) {	//	document number
//    		for (int b = 0; b<keyword.size(); b++) {
//    			// multiply query frequency
//    			double first = tfset[a][b] * kwint.get(b);
//    			sum = first + sum;
//    			first = 0.0;
////    			if (b==(keyword.size()-1)) {
////    				System.out.print(sum);
////    			} else {
////    				System.out.print(sum + " "); 				
////    			} 			
//    		}
//    		fr[a] = sum;
//    	}
//    	
//    	for(int c= 0; c<fr.length;c++) {
//    		double tmp = fr[c];	//	tmp에 현재
//    		int doctmp = c;
//    		for(int b = c+1; b<fr.length; b++) {
//    			if(fr[b] > tmp) {
//    				fr[c] = fr[b];
//    				docfr[c] = docfr[b];
//    				
//    				fr[b] = tmp;
//    				docfr[b] = doctmp;
//    				
//    				tmp = 0;
//    				doctmp = 0;
//    			}
//    		}
////    		
//    	}
//
//    		
//    	String docpath2 = docpath;
//    	Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(docpath);
//    	XPath xpath = XPathFactory.newInstance().newXPath();
//    	
//    	Element root = doc.getDocumentElement();
//        //System.out.println("Root : " + root.getTagName());
//        // 각노드의 리스트 취득
//        NodeList list = root.getElementsByTagName("title");
//        
//        for(int rank = 0; rank < 3; rank++) {
//        	
//        	Node item = list.item(docfr[rank]);
//        	if(rank == 2) {
//        		System.out.print(item.getTextContent());
//        	} else {
//        		System.out.print(item.getTextContent() + ", ");
//        	}
//    		//System.out.println("Rank " + output + " : " + docfr[rank]);
//    		//NodeList nodes = (NodeList)xpath.evaluate("//*[@id='" + docfr[rank] + "']/body", doc, XPathConstants.NODESET);
//    	}    	
//        //System.out.println(item.getTextContent());
//    	
////    	
////    	Document xmlDoc = null;
////    	xmlDoc = (Document) Parser.parse(doc, doc);
////    	Element root = xmlDoc.getDocumentElement();
////    	
////    	Document docs = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource("./result/collection.xml"));
//// 
////    	XPath xpath = XPathFactory.newInstance().newXPath();
////
////    	
////    	Node col2 = (Node)xpath.evaluate("//*[@id='2']", doc, XPathConstants.NODE);
////    	System.out.println(col2.getAttributes().getNamedItem("title").getTextContent());
//
//    	
//        
//       // NodeList n1 = root.getElementsByTagName("title");
//
//
//    	
//	}

}

