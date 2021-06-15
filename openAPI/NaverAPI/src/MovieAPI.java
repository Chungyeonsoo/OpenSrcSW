import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// 네이버 기계번역 (Papago SMT) API 예제
public class MovieAPI {

    public static void main(String[] args) {
    	
    	Scanner sc = new Scanner(System.in);
		System.out.printf("검색어를 입력하세요 : ");
		String Movie = sc.next();
		
		String text = null;
		
        String clientId = "5QzAB77rOfRoRsfqGet4";
        String clientSecret = "c6oh0xvuZA";
        
        try {
            text = URLEncoder.encode(Movie, "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        String APIURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text;
        
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		
		String response = get(APIURL,requestHeaders);
		
		JSONParser Parser = new JSONParser();
		JSONObject Object = null;
		
		try {
			Object = (JSONObject) Parser.parse(response);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray Array = (JSONArray) Object.get("items");
		
		for(int i=0; i<Array.size(); i++){
			System.out.println("Item"+i+" ============================================");
			JSONObject itemObject = (JSONObject) Array.get(i);
			System.out.println("Title:      "+itemObject.get("title"));
			System.out.println("SubTitle:   "+itemObject.get("subtitle"));
			System.out.println("Director:   "+itemObject.get("director"));
			System.out.println("Actor:      "+itemObject.get("actor"));
			System.out.println("Rating: "+itemObject.get("userRating")+"\n");
		}
    }

	private static String get(String APIURL, Map<String, String> requestHeaders) {
		// TODO Auto-generated method stub
		HttpURLConnection con = connect(APIURL);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return Read(con.getInputStream());
            } else { // 에러 발생
                return Read(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API failed", e);
        } finally {
            con.disconnect();
        }
	}

	private static String Read(InputStream inputStream) {
		// TODO Auto-generated method stub
		InputStreamReader ISR = new InputStreamReader(inputStream);

        try (BufferedReader lineReader = new BufferedReader(ISR)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API response failed", e);
        }
    }
	

	private static HttpURLConnection connect(String APIurl) {
		// TODO Auto-generated method stub
		try {
            URL url = new URL(APIurl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Wrong API url. : " + APIurl, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed connection: " + APIurl, e);
        }
	}
}