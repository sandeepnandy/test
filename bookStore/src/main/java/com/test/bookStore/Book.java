package com.test.bookStore;

import java.math.BigDecimal;

public class Book {
	  private String title;

	  private String author;

	  private BigDecimal price;
	  
	  private int quantity;
	  
	  
  public Book(String title, String author, BigDecimal price) {
		super();
		this.title = title;
		this.author = author;
		this.price = price;
		
	}
	public Book(String title, String author, BigDecimal price,int quantity) {
		super();
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o==this)
			return true;
		
		if(!(o instanceof Book))
			return false;
		
		Book book = (Book) o;
		
		return book.title.equals(title) && book.author.equals(author) && book.price.intValue() ==price.intValue();
	}
	
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + price.intValue();
        System.out.println("result "+result);
        return result;
	}
	  
}
