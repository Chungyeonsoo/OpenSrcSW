import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class main {
	public static void main(String[] args) {
		String filename = "/Users/chungyeons/Downloads/2주차 실습 html/떡.html";
		String getline = "";
		String setLine = getSource(filename, getline);
		System.out.println(setLine);
	}
	
	public static String getSource(String filename, String reLine) {
		try {
			File file = new File(filename);
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while((line = bufReader.readLine()) != null) {
				reLine = reLine + line;
				line = "";
			}
			
			bufReader.close();
		} catch (FileNotFoundException e) {
			
		} catch(IOException e) {
			System.out.println(e);
		}
		return reLine;
	}
}
