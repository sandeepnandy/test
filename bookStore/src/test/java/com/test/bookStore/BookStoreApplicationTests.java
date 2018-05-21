package com.test.bookStore;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookStoreApplicationTests {

	@Resource
	BookList bookList;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testList() {
		Book[] bookArr =bookList.list("");
		assertNotNull("Search resul is empty", bookArr);
		assertTrue("search result not correct", bookArr.length>6);
		
		bookArr =bookList.list("Author");
		assertTrue("search result not correct", bookArr.length==2);
	}
	
	@Test
	public void testAdd() {
		Book book = new Book("Indian History", "Ravi Kher", new BigDecimal(1000.00));
		boolean status =bookList.add(book, 10);
		assertTrue("Book does not added to store", status);
	}
	
	@Test
	public void testBuy() {
		//Generic Title;First Author;185.50;5
		Book book1 = new Book("Generic Title","First Author", new BigDecimal(185.50));
		Book book2 = new Book("Desired","Rich Bloke", new BigDecimal(564.50));
		Book book3 = new Book("Generic Title not present", "First Author", new BigDecimal(185.50));
		
		Book[] bookArr = {book1};
		
		int[] statusArr =bookList.buy(bookArr);
		assertTrue("Buy not successfull", statusArr[0] == 0);
		
		Book[] bookArr1 = {book2,book3};
		int[] statusArr1 =bookList.buy(bookArr1);
		assertTrue("Book should out of Stock", statusArr1[0] == 1);
		assertTrue("Book does not exists", statusArr1[1] == 2);
		
	}
}
