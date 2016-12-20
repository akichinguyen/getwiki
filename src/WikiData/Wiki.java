package WikiData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wiki {
	private String url;
	private String title;

	private Queue<String> subCatalogies;
	private Hashtable<String, Integer> subCatalogiesChecked;
	private Hashtable<String, Integer> links;
	private int threshold = 0;
	
	public Wiki(String url) {
		subCatalogies = new LinkedList<>();
		subCatalogies.add(url);
		subCatalogiesChecked = new Hashtable<>();
		subCatalogiesChecked.put(url, 1);
		links = new Hashtable<>();
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	//Lay cac link con va them cac trang trong the loai
	public void getSubLink() throws IOException{
		//Neu hang doi van con phan tu va so the loai duoc xet < 500(cai nay tuy minh muon lay sau den muc nao thi se 
		// tang nguong do len)
		if(!subCatalogies.isEmpty() && threshold < 500){
			String url = subCatalogies.poll();
			threshold ++;
			System.out.println(threshold);
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
			//Lay link cac the loai con
			Elements subs = doc.select("div[id=\"mw-subcategories\"]").select("a");
			for (Element sub : subs) {
				String link = "https://vi.wikipedia.org" + sub.attr("href");
				//Neu link chua duoc xet lan nao
				if(subCatalogiesChecked.get(link) == null){
					subCatalogies.add("https://vi.wikipedia.org" + sub.attr("href"));
					subCatalogiesChecked.put(link, 1);
				}
			}
			//Lay link cac trang co trong the loai
			Elements pages = doc.select("div[id=\"mw-pages\"]").select("a");
			for (Element page : pages) {
				String link = "https://vi.wikipedia.org" + page.attr("href");
//				System.out.println(link);
				if(links.get(link) == null){
					links.put(link, 1);
				} else {
					links.put(link, links.get(link) + 1);
				}
			}
			
			getSubLink();
		} else {
			System.out.println("Get Links Comlete");
			BufferedWriter bw = new BufferedWriter(new FileWriter("linksWiki.txt", true));
			Enumeration<String> enumeration = links.keys();
			int i = 0;
			while(enumeration.hasMoreElements()){
				String link = enumeration.nextElement();
				bw.write(link + "\n");
				System.out.println(link);
				i++;
			}
			bw.close();
			System.out.println("Total link: " + i);
		}
		
		
//		Elements link = doc.select("div[id=\"mw-subcategories\"]").select("a[class=\"CategoryTreeLabel  CategoryTreeLabelNs14 CategoryTreeLabelCategory\"]");
//		Elements page = doc.select("div[id=\"mw-pages\"]").select("a");
//		int i = 0;
//		for (Element element : link) {
//			System.out.println(element.attr("href"));
//			i++;
//		}
//		System.out.println(i);
	}
	//Lay ra danh sach cac link dung cho kiem dinh theo so thu tu
	public ArrayList<Integer> randList(){
		ArrayList<Integer> list = new ArrayList<>();
		Random rand = new Random();
		for(int i = 0; i < 100;){
			int randNumber = rand.nextInt(1421);
			if(list.indexOf(randNumber) == -1){
				list.add(randNumber);
				i++;
			}
		}
		return list;
	}
	public static void main(String[] args) throws IOException {
		Wiki wiki = new Wiki("https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Y_h%E1%BB%8Dc");
		try {
			wiki.getSubLink();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Doan nay la lay nhung link da duoc lay roi kiem tra thu cong the loai cua link do.
		//Bai toan: H0: p >= p0 voi do tin cay 5%;
//		ArrayList<Integer> list = wiki.randList();
////		for (Integer integer : list) {
////			System.out.println(integer);
////		}
//		BufferedReader br = new BufferedReader(new FileReader("linksWiki.txt"));
//		ArrayList<String> listLink = new ArrayList<>();
//		String line = "";
//		while((line = br.readLine()) != null){
//			listLink.add(line);
//		}
//		br.close();
//		for(int i = 0; i < list.size(); i++){
//			System.out.println(listLink.get(list.get(i)));
//		}
	}
}
