package com.security.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.security.entity.Book;
import com.security.repository.BookRepository;
import com.security.service.BookService;

@CrossOrigin("*")
@RestController
public class BookController {

	int i;

	@Autowired
	private BookService bookservice;

	@Autowired
	private BookRepository bookRepository;

	// search bOOK section
	// http://localhost:8080/search//keyword/java
	@GetMapping("/search/keyword/{searchkey}")
	ResponseEntity<List<Book>> searchByKeyWord(@PathVariable("searchkey") String searchKey)
			throws JsonParseException, JsonMappingException, IOException {

		List<Book> book_list = bookservice.searchbook(searchKey);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/search/dept/CSE
	@GetMapping("/search/dept/{deptname}")
	ResponseEntity<List<Book>> searchByDept(@PathVariable("deptname") String deptname)
			throws JsonParseException, JsonMappingException, IOException {

		System.out.println("searchKey " + deptname);
		List<Book> book_list = bookservice.searchByDept(deptname);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/search/location/khulna
	@GetMapping("/search/location/{location}")
	ResponseEntity<List<Book>> searchByLocation(@PathVariable("location") String location)
			throws JsonParseException, JsonMappingException, IOException {

		List<Book> book_list = bookservice.searchByLocation(location);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/search/price/200 - 300
	@GetMapping("/search/price/{price}")
	ResponseEntity<List<Book>> filterhByPrice(@PathVariable("price") String price)
			throws JsonParseException, JsonMappingException, IOException {

		List<Book> book_list = bookservice.searchByPrice(price);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/search/date/Old TO New
	@GetMapping("/search/date/{date}")
	ResponseEntity<List<Book>> filterByDate(@PathVariable("date") String date)
			throws JsonParseException, JsonMappingException, IOException {

		List<Book> book_list = bookservice.filterByDate(date);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/book
	@GetMapping("/book")
	public ResponseEntity<List<Book>> book() {

		List<Book> book_list = bookservice.fetchallbook();

//    	User principal = (User) SecurityContextHolder.
//    			getContext().getAuthentication().getPrincipal();
//    	System.out.println("principal " + principal.getUsername());

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/book/user/1
	@GetMapping("/book/user/{userid}")
	public ResponseEntity<List<Book>> userbook(@PathVariable(value = "userid") int userId) {

		// System.out.println("uset id " + userId);

		List<Book> book_list = bookservice.userBook(userId);

		return ResponseEntity.ok().body(book_list);
	}

	// http://localhost:8080/book/1
	@GetMapping("/book/{bookid}")
	public ResponseEntity<Book> fetchOneBook(@PathVariable(value = "bookid") int id) {

		Book book = bookservice.fetchOneBook(id);

		return ResponseEntity.ok().body(book);

	}

	// create Book
	// http://localhost:8080/book/1
//    @PostMapping("/book/{userId}")
//    ResponseEntity<?> savebook(@PathVariable(value = "userId") int userId, 
//    		@RequestParam("file") MultipartFile file, @RequestParam("book") String book) throws JsonParseException, JsonMappingException, IOException
//    {
//    	    	    	
//    	boolean result = bookservice.createBook(userId, book, file);
//		
//    	if(result) {
//    		return ResponseEntity.ok().body("seccessfully created");
//    	}
//    	else {
//    		return ResponseEntity.ok().body("Fail to create");
//    	}
//    }
//    
	
	
	
	
	
	@PostMapping("/book/userr/{userId}")
	ResponseEntity<?> savebookanother(@PathVariable(value = "userId") int userId,
			@RequestParam("book") String patientObject,
			@RequestParam("departmentList") String departmentList,
			@RequestParam("bookImage") MultipartFile bookImage)
					throws JsonParseException, JsonMappingException, IOException {

		System.out.println("function is called===" + userId + "  " + patientObject + " \n" + departmentList + " \n" + 
				bookImage.getSize());
		// Book resultBook = bookservice.createBook(userId, bookObject, bookImage,
		// department);
		int resultBook = 0;

		if (resultBook == 0) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.ok().body("Fail to create");
		}
	}
	
	

	// localhost:8080/book/user/31
	@PostMapping("/book/user/{userId}")
	ResponseEntity<?> savebook(@PathVariable(value = "userId") int userId,
			@RequestParam("bookImage") MultipartFile bookImage,
			@RequestParam("book") String bookObject,
			@RequestParam("departmentList") String department)
			throws JsonParseException, JsonMappingException, IOException {

		System.out.println("function is called===" + bookObject + "  \n" + department);
		Book resultBook = bookservice.createBook(userId, bookObject, bookImage, department);
		//Book resultBook = new Book();

		if (resultBook != null) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.ok().body("Fail to create");
		}
	}

	// http://localhost:8080/book/55
	@PutMapping("/book/user/{userId}")
	ResponseEntity<?> updateBook(@PathVariable(value = "userId") int userId,
			@RequestParam("bookImage") MultipartFile bookImage, @RequestParam("book") String bookObject)
			throws JsonParseException, JsonMappingException, IOException {

		Book resultBook = bookservice.updateBook(userId, bookObject, bookImage);

		if (resultBook != null) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.ok().body("Fail to create");
		}
	}

	
	// http://localhost:8080/search//keyword/java
	@GetMapping("/book/user/delete/{bookId}")
	ResponseEntity<String> deletePostedBook(@PathVariable("bookId") int bookId)
			throws JsonParseException, JsonMappingException, IOException {

		int result = bookservice.deleteBook(bookId);
		
		if (result != 1) {
			return ResponseEntity.ok().body("successfully deleted");
		} else {
			return ResponseEntity.ok().body("Fail to delete");
		}

	}
	
	
}

//  { "author" : "me", "date" : "11/11/2020", "filename" : "fff.jpg", "title" :
//  "python", "price" : "111", "visitbypeople" : "2" }

//{"bookid": 35, "author" : "me", "date" : "11/11/2020", "filename" : "fff.jpg", "title" :
//"python", "price" : "111", "visitbypeople" : "2" }
