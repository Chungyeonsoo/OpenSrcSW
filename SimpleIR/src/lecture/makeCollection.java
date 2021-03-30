package lecture;


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;

public class makeCollection {

	public void makeCollection(String dirName) throws IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        org.w3c.dom.Document result = docBuilder.newDocument();

        Element docs = result.createElement("docs");
        result.appendChild(docs);

        File dir = new File(dirName);
        File[] fileList = dir.listFiles();
        int idnumber = 0;

        for (File input : fileList) {
            if (input.isFile()) {

                Document doc = Jsoup.parse(input, "UTF-8");

                Element elementDoc = result.createElement("doc");
                elementDoc.setAttribute("id", Integer.toString(idnumber));
                docs.appendChild(elementDoc);

                Element elementTitle = result.createElement("title");
                elementTitle.setTextContent(doc.getElementsByTag("title").text());
                elementDoc.appendChild(elementTitle);

                Element elementBody = result.createElement("body");
                elementBody.setTextContent(doc.getElementsByTag("p").text());
                elementDoc.appendChild(elementBody);

                idnumber = idnumber + 1;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(result);
        StreamResult finalResult = new StreamResult(new FileOutputStream(new File("./result/collection.xml")));

        transformer.transform(source, finalResult);
    }
}
