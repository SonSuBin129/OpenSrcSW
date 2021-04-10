package htmltoxml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws SAXException {
		// TODO Auto-generated method stub
		
		try {
			makeCollection.Change(args[1]);
		} catch (ParserConfigurationException | IOException | TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			makeKeyword.extract(args[3]);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			indexer.inverted(args[5]);
		} catch (TransformerException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		searcher sc= new searcher();
		try {
			sc.CalcSim(args[7], args[9]);
		} catch (ClassNotFoundException | IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}