package htmltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class searcher {
	void CalcSim(String path, String query) throws IOException, ClassNotFoundException, SAXException, ParserConfigurationException {
		File file= new File(path);
		FileInputStream fileStream= new FileInputStream(file);
		ObjectInputStream objectInputStream= new ObjectInputStream(fileStream);
		
		Object object= objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap=(HashMap)object;
		
		KeywordExtractor ke= new KeywordExtractor();
		KeywordList kl= ke.extractKeyword(query,true);
		
		double[] result= {0.0, 0.0, 0.0, 0.0, 0.0};	//다 0으로 초기화
		String[] title= new String[5];
		
		DocumentBuilderFactory docFactory= DocumentBuilderFactory.newInstance();
		DocumentBuilder builder= docFactory.newDocumentBuilder();
		Document doc= builder.parse(new File("C:\\\\Users\\\\손수빈\\\\SimpleIR\\\\collection.xml"));
		doc.getDocumentElement().normalize();
		
		Element root= doc.getDocumentElement();
		NodeList childs=root.getElementsByTagName("doc");
		
		for(int i=0;i<childs.getLength();i++) {//문서들 title 불러오기
			Node child=childs.item(i);
			Node tname=child.getFirstChild();
			title[i]=tname.getTextContent();
		}
		
		double Ql=0;	//query의 |Q|
		double idl=0;	//id의 |id|
		
		for(int i=0;i<kl.size();i++) {	//각 문서와의 유사도 계산
			Keyword kwrd=kl.get(i);
			String key=kwrd.getString();
			int weight=kwrd.getCnt();	//query의 keyword별 가중치
			ArrayList<Object> value= (ArrayList<Object>) hashMap.get(key);
			for(int j=0;j<value.size();j+=2) {
				int target=(int)value.get(j);
				double tf=(double)value.get(j+1);	// 문서별 tf-idf

				switch(target) {
				case 0:
					result[0]+=weight*tf;
					break;
				case 1:
					result[1]+=weight*tf;
					break;
				case 2:
					result[2]+=weight*tf;
					break;
				case 3:
					result[3]+=weight*tf;
					break;
				case 4:
					result[4]+=weight*tf;
					break;
				}
				Ql+=Math.pow(weight, 2);
				idl+=Math.pow(tf, 2);
			}
		}
		
		Ql=Math.sqrt(Ql);
		idl=Math.sqrt(idl);

		for(int i=0;i<result.length;i++) {//소수점 둘째 자리까지 표현, 코사인 유사도 계산
			double origin=Math.round(result[i]*100)/100.0;
			result[i]=Math.round((origin/(Ql*idl))*100)/100.0;
			}

		
		
		///////////상위3위 문서 title 출력////////////
		double max=0;
		int index;//큰 값을 가지는 인덱스를 저장할 변수
		System.out.println("========================유사도 상위 3위까지 출력========================");
		for(int i=0;i<3;i++) {
			max=result[0];
			index=0;
			for(int j=1;j<result.length;j++) {
				if(result[j]>max) {
					max=result[j];
					index=j;
				}else if(result[j]==max) {
					if(index>j) {
						max=result[j];
						index=j;
					}
				}
			}
			System.out.println((i+1)+"위 : "+title[index]+" => 유사도 : "+result[index]);
			result[index]=Integer.MIN_VALUE;
			
		}
	}
}
