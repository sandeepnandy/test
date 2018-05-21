package com.test.bookStore;

import java.util.ArrayList;

public class BookListImpl implements BookList {

	@Override
	public Book[] list(String searchString) {
	  ArrayList<Book> searchBookArray = new ArrayList<Book>();
	  
	  if(null ==searchString || "".equals(searchString)) {
		  searchBookArray =BookStoreApplication.bookSelfArrayList;
	  }
	  else {
		 // System.out.println("searchString  "+searchString);
		  //search with title and author
		  for(Book book:BookStoreApplication.bookSelfArrayList) {
			  if(book.getTitle().toUpperCase().contains(searchString.toUpperCase())) {
				  searchBookArray.add(book);
			  }
			  if(book.getAuthor().toUpperCase().contains(searchString.toUpperCase())) {
				  searchBookArray.add(book);
			  }
		  }
		  
	  }
		
		
		return searchBookArray.toArray(new Book[searchBookArray.size()]);
	}

	@Override
	public boolean add(Book book, int quantity) {
		
		//First check the list if book is already there or not
		 if(BookStoreApplication.bookSelfArrayList.contains(book)) {
			 for(Book bookExists:BookStoreApplication.bookSelfArrayList) {
				 if(bookExists.equals(book)) {
					 bookExists.setQuantity(bookExists.getQuantity() +quantity);
				 }
			 }
		 }
		 else {
			 book.setQuantity(quantity);
			 BookStoreApplication.bookSelfArrayList.add(book);
		 }
		
		return true;
	}

	@Override
	public int[] buy(Book[] books) {
		int[] statusArray = new int[books.length];
		System.out.println("books length"+books.length);
		for(int i=0; i<books.length; i++) {
			if(books[i] == null) {
				statusArray[i] =2;			//DOES_NOT_EXISTS
			}
			else if(BookStoreApplication.bookSelfArrayList.contains(books[i])){
				System.out.println("books line 63 "+books[i]);
				Book buyBook =books[i];
				for(Book bookExists:BookStoreApplication.bookSelfArrayList) {
					System.out.println("books line 66 "+bookExists.getTitle());
					if(bookExists.equals(buyBook)) {
						//System.out.println("books line 68 "+bookExists.getTitle());
						if(bookExists.getQuantity() >0) {
						 bookExists.setQuantity(bookExists.getQuantity() -1);
						 statusArray[i] =0;  //OK
						}
						else {
							System.out.println("books line 76 "+bookExists.getTitle());
							statusArray[i] =1;  //NOT_IN_STOCK
						}
					}	
				}
			}
			else {
				//System.out.println("books line 82 "+books[i].getTitle());
				statusArray[i] =2;				//DOES_NOT_EXISTS
			}
		}
		return statusArray;
	}

}
