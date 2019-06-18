package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;

public class util {

	public static void saveImage(String imageUrl, String destinationPath, String fileName) throws IOException {

		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationPath + "/" + fileName);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public static File createFolder(String folderPath, String folderName) throws IOException {
		File folder;

		folder = new File(folderPath + folderName + "/");
		if (folder.exists()) {
			return folder;
		}
		else {
			folder.mkdir();
			folder.createNewFile();
			
			if (!folder.createNewFile()) {
				System.out.println(folderName + "File Created Succefully");
			} else {
				System.err.println("ERROR OCCURED WHILE CREATING FILE");
			}


	return folder;

		}
		
		
	}
	
	public static void deleteFolder(File folder)  {
		
		try {
			FileUtils.deleteDirectory(folder);
			



		} catch (IOException e) {
			System.out.println("Failed to delete " + folder);
			e.printStackTrace();
		}
		
	}
	
	public static String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		//System.out.println(dtf.format(localDate));
		
		return dtf.format(localDate);
	}
	
	
	
	
	
	
	
}
