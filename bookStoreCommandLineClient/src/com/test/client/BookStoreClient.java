package com.test.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookStoreClient {

	public static void main(String[] args) {
			
		String typeOfRequest ="";
		String queryParameter ="";
			if(null !=args && args.length >0) {
				if("list".equals(args[0])) {
					typeOfRequest ="listOfBooks";
					if(args.length ==2) {
						queryParameter ="searchString="+args[1];
					}
				}
				else if("add".equals(args[0])) {
					typeOfRequest ="addBook";
					if(args.length ==5) {
						//for(int i=1;i<5;i++) {
							queryParameter ="title="+args[1]+"&author="+args[2]+"&price="+args[3]+"&quantity="+args[4];
						//}
						
					}
				}
				else if("buy".equals(args[0])) {
					typeOfRequest ="buyBook";
					if(args.length >1) {
						for(int i=1;i<args.length;i++) {
							queryParameter +="book="+args[i]+"&";
						}
						queryParameter =queryParameter.substring(0, queryParameter.length()-1);
					}
				}
			}
		String httpUrl = "http://localhost:8080/"+typeOfRequest+"?"+queryParameter;
		System.out.println("httpUrl "+httpUrl);
		readDataFromServer(httpUrl);
			
	}
	
	public static void readDataFromServer(String httpUrl)  {
		URL requestUrl;
		try {
			requestUrl = new URL(httpUrl);
		

		HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("content-type", "charset=utf-8");

		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			InputStream in = con.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
			try (BufferedReader br = new BufferedReader(inputStreamReader)) {
				String inputLine;

				while ((inputLine = br.readLine()) != null) {
					System.out.println(inputLine.replace("<br>", "\n"));

				}
			}
		}
		else {
			System.out.println("Error in response"+con.getResponseCode());
		}
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
}
