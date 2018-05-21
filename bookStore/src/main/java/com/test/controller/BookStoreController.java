package com.test.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.bookStore.Book;
import com.test.bookStore.BookList;

@RestController
public class BookStoreController {

	@RequestMapping("/")
	public String index() {
		return "welcome to BookStoreController";
	}

	@Resource
	BookList bookList;

	@RequestMapping("/listOfBooks")
	public String listOfBooks(HttpServletRequest request, HttpServletResponse response) {
		String searchString = request.getParameter("searchString");
		System.out.println("searchString " + searchString);
		Book[] listOfBooks = bookList.list(searchString);

		StringBuilder stringBuilder = new StringBuilder();
		for (Book book : listOfBooks) {
			stringBuilder.append(
					book.getTitle() + ";" + book.getAuthor() + ";" + book.getPrice() + ";" + book.getQuantity());
			stringBuilder.append("<br>");
		}

		return stringBuilder.toString();
	}

	@RequestMapping("/addBook")
	public String addBook(HttpServletRequest request, HttpServletResponse response) {
		try {
			String title = request.getParameter("title");
			String author = request.getParameter("author");

			// check for price

			BigDecimal price = new BigDecimal(request.getParameter("price").replaceAll(",", ""));

			if (title == null || author == null || price == null) {
				return "mandatory data missing";
			}
			Book bookObj = new Book(title, author, price, 0);
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			bookList.add(bookObj, quantity);

		} catch (Exception ex) {
			return "bookNotAdded";
		}
		return "bookAdded";
	}

	@RequestMapping("/buyBook")
	public String buyBook(HttpServletRequest request, HttpServletResponse response) {
		String[] bookLineArray = request.getParameterValues("book");
		System.out.println("book array length" + bookLineArray.length);
		Book[] bookArray = new Book[bookLineArray.length];
		for (int i = 0; i < bookLineArray.length; i++) {
			String[] bookInfo = bookLineArray[i].split(";");
			if (null != bookInfo && bookInfo.length == 3) {
				String price = bookInfo[2].replace(",", "");
				Book book = new Book(bookInfo[0], bookInfo[1], new BigDecimal(price));
				bookArray[i] = book;
			} else {
				// wrong input
				bookArray[i] = null;
			}
		}
		int[] status = bookList.buy(bookArray);
		String returnState = "";
		for (int i : status) {
			returnState = returnState + Integer.toString(i) + "</br>";
		}
		return returnState;
	}
}
