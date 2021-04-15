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
		ArrayList<Object> input=InnerProduct(path,query);
		double[] result=(double[]) input.get(0);	//내적한값
		String[] title=(String[]) input.get(1);	//문서 타이틀
		double[] idl=(double[]) input.get(2);	//문서별 tf-idf의 제곱들의 합 ex)idl[0]: query 에 대한 0번문서의 tf-idf들의 제곱의 합
		double Ql=(double) input.get(3);	// query의 제곱의 합
		
		double[] cosine= new double[5];	//cosine유사도 계산한 값을 넣을 배열
		
		Ql=Math.sqrt(Ql);	//루트 씌워줌
		for(int i=0;i<title.length;i++) {
			idl[i]=Math.sqrt(idl[i]);
			cosine[i]=Math.round((result[i]/(Ql*idl[i]))*100)/100.0;
		}
		
		
		///////////상위3위 문서 title 출력////////////
		double max=0;
		int index;//큰 값을 가지는 인덱스를 저장할 변수
		System.out.println("========================유사도 상위 3위까지 출력========================");
		for(int i=0;i<3;i++) {
			max=cosine[0];
			index=0;
			for(int j=1;j<cosine.length;j++) {
				if(result[j]>max) {
					max=cosine[j];
					index=j;
				}else if(cosine[j]==max) {
					if(index>j) {
						max=cosine[j];
						index=j;
					}
				}
			}
			System.out.println((i+1)+"위 : "+title[index]+" => 유사도 : "+cosine[index]);
			cosine[index]=Integer.MIN_VALUE;
		
		}
	}


	
	ArrayList<Object> InnerProduct(String path, String query) throws IOException, ClassNotFoundException, SAXException, ParserConfigurationException {
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
		
		double Ql=0;	//query의 가중치들의 제곱의 합을 저장할 변수
		double[] idl=new double[5];	//idl은 문서별 tf-idf의 제곱의 합을 저장할 변수
		
		ArrayList<Object> output=new ArrayList<Object>();
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
					result[0]+=Math.round((weight*tf)*100)/100.0;
					idl[0]+=Math.pow(tf, 2);
					break;
				case 1:
					result[1]+=Math.round((weight*tf)*100)/100.0;
					idl[1]+=Math.pow(tf, 2);
					break;
				case 2:
					result[2]+=Math.round((weight*tf)*100)/100.0;
					idl[2]+=Math.pow(tf, 2);
					break;
				case 3:
					result[3]+=Math.round((weight*tf)*100)/100.0;
					idl[3]+=Math.pow(tf, 2);
					break;
				case 4:
					result[4]+=Math.round((weight*tf)*100)/100.0;
					idl[4]+=Math.pow(tf, 2);
					break;
				}
			}
			Ql+=Math.pow(weight, 2);
		}
	
		output.add(result);
		output.add(title);
		output.add(idl);
		output.add(Ql);
		
		return output;
	}
}


