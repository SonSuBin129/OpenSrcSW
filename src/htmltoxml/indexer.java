package htmltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	
	static void inverted(String path) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		
		int N=5;
		File file= new File(path);
		
		DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= docFactory.newDocumentBuilder();
		Document doc= builder.parse(file);
		doc.getDocumentElement().normalize();
		
		Element root= doc.getDocumentElement();
		NodeList childs=root.getElementsByTagName("doc");

		@SuppressWarnings("rawtypes")
		HashMap invert= new HashMap();
		String[][] strlist=new String[5][];
		
		for(int i=0;i<childs.getLength();i++) {	//각각의 문서 내용들이 "단어:빈도수" 형태로 저장되어있음
			Node child=childs.item(i);
			Node body= child.getLastChild();
			String test=body.getTextContent();
			strlist[i]=test.split("#");
		}

		for(int i=0;i<strlist.length;i++) {
			for(int j=0;j<strlist[i].length;j++) {
				String[] a=strlist[i][j].split(":");
				String target=a[0];
				int tfnum=Integer.valueOf(a[1]);
				int dfnum=makedf(strlist,target);
				double origin=tfnum*Math.log(N/dfnum);
				double weight=Math.round(origin*100)/100.0;	//소수점 두번째 자리까지 표현(소수점 세번째 자리에서 반올림)
				makeHashmap(target,weight,i,invert);	//hashmap.put하기 위해서
			}
		}
		
		FileOutputStream fileStream= new FileOutputStream(new File("C:\\Users\\손수빈\\SimpleIR\\index.post"));
		ObjectOutputStream objectOutputStream= new  ObjectOutputStream(fileStream);
		objectOutputStream.writeObject(invert);
		objectOutputStream.close();
		
	}
	
	static int makedf(String[][] strlist, String target) {	//df구하는 함수
		int df=0; //target인 단어가 있는 문서부터 다시 검토할거기 때문에 아예 초기화하고 생각
		for(int i=0;i<strlist.length;i++) {
			for(int j=0;j<strlist[i].length;j++) {
				String origin= strlist[i][j];
				String[] check=strlist[i][j].split(":");
				if(target.equals(check[0])) {
					df++;
				}
			}
		}
		
		return df;
	}
	
	static void makeHashmap(String target, double weight, int idnum, HashMap<String, ArrayList<Object>> invert) throws IOException {
		//HashMap에 추가
		
		if(invert.containsKey(target)) {
			ArrayList<Object> Value =(ArrayList<Object>) invert.get(target);
			Value.add(idnum);
			Value.add(weight);
			invert.put(target, Value);
		}else {
			ArrayList<Object> Value= new ArrayList<Object>();
			Value.add(idnum);
			Value.add(weight);
			invert.put(target,Value);
		}
	}
}
