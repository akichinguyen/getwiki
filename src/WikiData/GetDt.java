package WikiData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.compress.compressors.FileNameUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDt {
	public static String extractText(Reader reader) throws IOException{
		StringBuilder sb = new StringBuilder();
	    BufferedReader br = new BufferedReader(reader);
	    String line;
	    while ( (line=br.readLine()) != null) {
	        sb.append(line);
	      }
	      String textOnly = Jsoup.parse(sb.toString()).text();
	      return textOnly;
	    }
	public final static void main(String[] args) throws Exception{
		String fileName = "C:/Users/Thu/Desktop/vietnamnet.vn/vn/suc-khoe";//folder cha chứa một loạt các file .html 
		ArrayList<File> f = new ArrayList<>();
		CheckFile c = new CheckFile();
		c.listf(fileName, f);
		BufferedWriter bw = new BufferedWriter(new FileWriter("Vnnet.txt",true));
////////Lay du lieu VNExpress
		/*for(File f1:f){
	//			System.out.println(f1.getPath());
				int i = f1.getPath().lastIndexOf('.');
				String extention ="";
				if (i > 0) {
				   extention = f1.getPath().substring(i+1);
				}
				if(!extention.equals("orig")&& !f1.getPath().contains("@")){
					System.out.println(f1.getPath());
					String url = f1.toURI().toURL().toString().replace("file:/C:/Users/Thu/Desktop/", "http://");
					Connection.Response res = Jsoup.connect(url)
							.method(Method.POST)
							.userAgent("Mozilla/5.0 (Window NT 6.0) Chrome/19.0.1084.46 Safari/536.5")
							.timeout(100 * 1000)
							.ignoreHttpErrors(true)
							.execute();
					Map<String, String> cookies = res.cookies();
					
					Document doc = Jsoup.connect(url)
							.method(Method.POST)
							.userAgent("Mozilla/5.0 (Window NT 6.0) Chrome/19.0.1084.46 Safari/536.5")
							.timeout(100 * 1000)
							.ignoreHttpErrors(true)
							.cookies(cookies)
							.get();
					bw.write(doc.select("h3[class=short_intro txt_666]").text());
					bw.newLine();
					Elements content = doc.select("p.Normal");
					Elements text = doc.select("p[align=right]");
					text.empty();
					Elements text2 = doc.select("p[style=text-align:right;]");
					text2.empty();
				    for(Element p : content){
				    	bw.write(p.text());
				    	bw.newLine();
				    }
				}
		}*/
		
//////////Lay du lieu Vnnet
			for(File f1: f){
				Document doc = Jsoup.parse(f1, "UTF-8");
				bw.write(doc.select("h1[class=title]").text());
				bw.newLine();
				Elements pic = doc.select("td[class=image_desc]");
				pic.empty();
				Elements content = doc.select("div[class=ArticleDetail] p");
				Elements del = doc.select("p > em");
				del.empty();
			    for(Element p : content){
			    	bw.write(p.text());
			    	bw.newLine();
			    }
			}

			System.out.println("done");
	      bw.close();
			
	    }
     
}
	
