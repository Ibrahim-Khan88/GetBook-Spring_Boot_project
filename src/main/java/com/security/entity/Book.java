package com.security.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Book_table")

public class Book {

	@Id
	@GeneratedValue
	int bookid;

	@NotBlank
	@Size(max = 50)
	String title;

	@NotBlank
	@Size(max = 50)
	String author;

	int price;

	@NotBlank
	@Size(max = 50)
	String date;

	// @NotBlank
	byte[] bookImage;

	@NotBlank
	String filename;

	// @NotBlank
	int visitbypeople;

	@Transient
	String image_encode;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private UserInfo user;

	@ManyToMany
	@JoinTable(name = "departmentBook")
	private List<Department> department;

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public int getBook() {
		return bookid;
	}

	public void setBook(int bookid) {
		this.bookid = bookid;
	}

	public int getVisitbypeople() {
		return visitbypeople;
	}

	public void setVisitbypeople(int visitbypeople) {
		this.visitbypeople = visitbypeople;
	}

	public String getImage_encode() {
		return image_encode;
	}

	public void setImage_encode(String image_encode) {
		this.image_encode = image_encode;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public byte[] getBookImage() {
		return bookImage;
	}

	public void setBookImage(byte[] bookImage) {
		this.bookImage = bookImage;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", title=" + title + ", author=" + author + ", price=" + price + ", date="
				+ date + ", bookImage=" + Arrays.toString(bookImage) + ", filename=" + filename + ", visitbypeople="
				+ visitbypeople + ", user=" + user + "]";
	}

}
