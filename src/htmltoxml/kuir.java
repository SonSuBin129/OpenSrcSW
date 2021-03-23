package htmltoxml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws SAXException {
		// TODO Auto-generated method stub
		makeCollection mc= new makeCollection();
		makeKeyword mk= new makeKeyword();
		try {
			mc.Change("C:\\Users\\손수빈\\SimpleIR\\src\\html");
			mk.extract("C:\\Users\\손수빈\\SimpleIR\\collection.xml");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
