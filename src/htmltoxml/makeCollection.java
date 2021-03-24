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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {

	
	static void Change(String path) throws ParserConfigurationException, IOException, TransformerException {
		
		File dir= new File(path);
		File[] fileList= dir.listFiles();
		
		DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder= docFactory.newDocumentBuilder();
		Document doc= docBuilder.newDocument();
		
		Element docs= doc.createElement("docs");
		doc.appendChild(docs);
		
		BufferedReader br= null;
		
		for(int i=0;i<fileList.length;i++) {
			String title="";
			String body="";
			
			try {
				
				br= new BufferedReader(new InputStreamReader(new FileInputStream(fileList[i])));
				String readLine=null;
				while((readLine=br.readLine())!=null) {
					if(readLine.contains("<title>")) {
						org.jsoup.nodes.Document doc1=Jsoup.parse(readLine);
						title=doc1.text();
					}
					else {
						org.jsoup.nodes.Document doc1=Jsoup.parse(readLine);
						body+=doc1.text();
						body+=" ";
					}
				}
				Element edoc= doc.createElement("doc");
				docs.appendChild(edoc);
				
				String num=String.valueOf(i);
				edoc.setAttribute("id", num);
				
				Element xtitle= doc.createElement("title");
				xtitle.appendChild(doc.createTextNode(title));
				edoc.appendChild(xtitle);
				
				Element xbody= doc.createElement("body");
				xbody.appendChild(doc.createTextNode(body));
				edoc.appendChild(xbody);
				
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				br.close();
			}
		}
		
		TransformerFactory transformerFactory= TransformerFactory.newInstance();
		Transformer transformer= transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source= new DOMSource(doc);
		StreamResult result= new StreamResult(new FileOutputStream(new File("C:\\Users\\손수빈\\SimpleIR\\collection.xml")));
		transformer.transform(source, result);
	}

}
