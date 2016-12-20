package WikiData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Demo {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("linksWiki.txt"));
		BufferedWriter bf = new BufferedWriter(new FileWriter("Output1.txt", true));
		int i = 0;
		while(sc.hasNextLine()){
			String url = sc.nextLine();
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
		//	Document doc = Jsoup.connect(url).get();
		    Elements paragraphs = doc.select("p");
		    Elements refer = doc.select("sup");
		    refer.empty();
		    Elements extra = doc.select("a[rel=nofollow]");
		    extra.empty();
		    for(Element p : paragraphs){
		    	bf.write(p.text());
		    	System.out.println(p.text());
		    }
			System.out.println(++i);
		    bf.newLine();
		    bf.flush();
		}
		bf.close();
		
	}

}
