package com.test.bookStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@ComponentScan(basePackages = { "com.test.controller" })
public class BookStoreApplication {
	public static ArrayList<Book> bookSelfArrayList = new ArrayList<Book>();

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Bean
	public ArrayList<Book> loadInitialData() {
		String httpUrl = "https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt";

		try {
			ArrayList<String> listOfLines = new ArrayList<String>();
			URL requestUrl = new URL(httpUrl);
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
						// System.out.println(inputLine);
						listOfLines.add(inputLine);

					}
				}
			}
			// get Out put
			for (String line : listOfLines) {
				System.out.println(line);
				String[] arrElements = line.split(";");
				if (null != arrElements && arrElements.length == 4) {

					String price = arrElements[2].replace(",", "");
					Book bookObj = new Book(arrElements[0], arrElements[1], new BigDecimal(price),
							Integer.parseInt(arrElements[3]));
					bookSelfArrayList.add(bookObj);
				} else {
					System.out.println("Incorrect Data");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookSelfArrayList;

	}

	@Bean
	public BookList getBookList() {
		return new BookListImpl();
	}

}
