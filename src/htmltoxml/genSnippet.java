package htmltoxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class genSnippet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		makeInput();
		check(args[1]);
	}
	
	public static void makeInput() {
		File input= new File("C:\\Users\\손수빈\\SimpleIR\\input.txt");
		BufferedWriter br=null;
		String str="라면 밀가루 달걀 밥 생선"+"\n"+"라면 물 소금 반죽"+"\n"+"첨부 봉지면 인기"+"\n"+"초밥 라면 밥물 채소 소금"+"\n"+"초밥 종류 활어";
		try {
			FileOutputStream fout=new FileOutputStream(input);
			br= new BufferedWriter(new OutputStreamWriter(fout));
			br.write(str);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void check(String check) {
		String[] check_a= check.split(" ");
		File check_file= new File("C:\\Users\\손수빈\\SimpleIR\\input.txt");
		BufferedReader br=null;
		FileInputStream finput;
		try {
			finput = new FileInputStream(check_file);
			br= new BufferedReader(new InputStreamReader(finput));
			String readLine=null;
			int[] count=new int[5];
			int line_num=0;
			while((readLine=br.readLine())!=null) {
				int check_count=0;
				String[] test= readLine.split(" "); //공백으로 분리
				for(int i=0;i<check_a.length;i++) {
					for(int j=0;j<test.length;j++) {
						if(test[i].equals(check_a[j])) {
							check_count++;
						}
					}
				}line_num++;
				count[line_num]=check_count;
			}
				br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
