package lecture;

import java.io.*;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class index {



	public void index(String filename) throws IOException, SAXException, ParserConfigurationException{
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    File collection = new File(filename);
	    Document doc = docBuilder.parse(collection);
		
//	    FileOutputStream fileStream = new FileOutputStream("./index.post");
//		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
//		HashMap index = new HashMap();
		
	    NodeList nList = doc.getElementsByTagName("body");
	    
	    String[] keywordset;   
    	HashMap<String, Integer[]> tf = new HashMap<>();     
        HashMap<String, Integer> idf = new HashMap<>();
        
        for (int i = 0; i < nList.getLength(); i++) {
            String content = nList.item(i).getTextContent();
            keywordset = content.split("#");
            
            //System.out.println(Arrays.toString(keywords));
            
            for (int j=0; j< keywordset.length; j++) {
            	
            	String keyword = keywordset[j].split(":")[0];// keyword
            	int numCount = Integer.parseInt(keywordset[j].split(":")[1]);		// value

            	if (tf.containsKey(keyword)) {		// if there is key
            		Integer[] tfArray = tf.get(keyword);		//
            		tfArray[i] = numCount;
            		tf.put(keyword, tfArray);
            	} else {
            		Integer[] tfArray = new Integer[] {0,0,0,0,0};
            		tfArray[i] = numCount;
            		tf.put(keyword, tfArray);
            	}
            	
            	if (idf.containsKey(keyword)) {
            		int idfCount = idf.get(keyword)+1;
                	idf.put(keyword, idfCount);            		
            	} else {
            		idf.put(keyword, 1);
            	}         	
            }
            
            
         
        }
        
        HashMap<String, List<String>> tfidf = new HashMap<>();
        Iterator<String> iterator = tf.keySet().iterator();
        
        while(iterator.hasNext() ) {
        	String key = iterator.next();
        	Integer[] value = tf.get(key);
        	List<String> tfidfList = new ArrayList<String>();
        	for (int i=0; i<value.length; i++) {
        		if (value[i] == 0) {
        			continue;
        		}
        		tfidfList.add(Integer.toString(i));
        		tfidfList.add(Double.toString(Math.round(value[i] * Math.log((double)5 / idf.get(key)))));
        	}
        	
        	tfidf.put(key, tfidfList);
        }
        
        //	output
        FileOutputStream fileStream = new FileOutputStream("./result/index2.xml");
    	ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
    	
    	objectOutputStream.writeObject(tfidf);
    	objectOutputStream.close();
    	
    	System.out.println("create index.post");
    	
    	//	input
    	FileInputStream filepath = new FileInputStream("./result/index2.xml");
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
    	
    	while(it.hasNext()) {
    		String key = it.next();
    		List<String> values = (List<String>) hashMap.get(key);
    		System.out.print(key + " [");
    		for (int i=0; i<values.size(); i++) {
    			if (i==(values.size()-1)) {
    				System.out.print(values.get(i));
    			} else {
        			System.out.print(values.get(i) + ", ");    				
    			}
    		}
    		System.out.println("]");
    	}

	}
}


