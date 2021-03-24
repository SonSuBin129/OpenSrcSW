package htmltoxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	
	static void extract(String path) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		File file= new File(path);
		
		DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= docFactory.newDocumentBuilder();
		Document doc= builder.parse(file);
		doc.getDocumentElement().normalize();
		
		Element root= doc.getDocumentElement();
		NodeList childs=root.getElementsByTagName("doc");
		for(int i=0;i<childs.getLength();i++) {
			Node child=childs.item(i);
			Node body= child.getLastChild();

			String teststr=body.getTextContent();
			KeywordExtractor ke= new KeywordExtractor();
			KeywordList kl= ke.extractKeyword(teststr, true);
			String str="";
			for(int j=0;j<kl.size();j++) {
				Keyword kwrd= kl.get(j);
				str+=(kwrd.getString()+":"+kwrd.getCnt()+"#");
			}
			body.setTextContent(str);
		}
		
		TransformerFactory transformerFactory= TransformerFactory.newInstance();
		Transformer transformer= transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source= new DOMSource(doc);
		StreamResult result= new StreamResult(new FileOutputStream(new File("C:\\Users\\손수빈\\SimpleIR\\index.xml")));
		transformer.transform(source, result);
				
	}
}
