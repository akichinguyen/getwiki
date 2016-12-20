package WikiData;

import java.io.File;
import java.util.ArrayList;

public class CheckFile {
	public void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}
	public static void main(String[] args) {
		String fileName = "C:/Users/Thu/Desktop/vietnamnet.vn/vn/suc-khoe/an-toan-thuc-pham";
		ArrayList<File> f = new ArrayList<>();
		CheckFile c = new CheckFile();
		c.listf(fileName, f);
		for(File f1:f)
			System.out.println(f1.toString());
	}
}
