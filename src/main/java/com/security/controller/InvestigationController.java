package com.security.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.security.entity.Book;
import com.security.service.BookService;

@RestController
public class InvestigationController {

	@Autowired
	private BookService bookservice;

//	@PostMapping("/book/userr/{userId}")
//	ResponseEntity<?> savebookanother(@PathVariable(value = "userId") int userId,
//			@RequestParam("patient") String patientObject) throws JsonParseException, JsonMappingException, IOException {
//
//		System.out.println("function is called===" + userId + "  " + patientObject);
//		// Book resultBook = bookservice.createBook(userId, bookObject, bookImage,
//		// department);
//		int resultBook = 0;
//
//		if (resultBook == 0) {
//			return ResponseEntity.ok().body("successfully created");
//		} else {
//			return ResponseEntity.ok().body("Fail to create");
//		}
//	}

	@PostMapping("/test")
	ResponseEntity<?> testtt(@RequestBody boolean value) throws JsonParseException, JsonMappingException, IOException {

		System.out.println("function is called===" + "  " + value + " ");

		int resultInvestigation = 1;

		if (resultInvestigation == 1) {
			return ResponseEntity.ok().body("successfully created vv");
		} else if (resultInvestigation == 2) {
			return ResponseEntity.badRequest().body("This investigation name is already exists");
		} else {
			return ResponseEntity.badRequest().body("Fail to create");
		}
	}

	// localhost:8080/userr/31
	@PostMapping("/userr/{userId}")
	ResponseEntity<?> savebook(@PathVariable(value = "userId") int userId)
			throws JsonParseException, JsonMappingException, IOException {

		// Book resultBook = bookservice.createBook(userId, bookObject, bookImage,
		// department);
		Book resultBook = new Book();

		if (resultBook != null) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.ok().body("Fail to create");
		}
	}

	// http://localhost:8080/book/user/delete/1
	@CrossOrigin("*")
	@DeleteMapping("/book/user/delete/{bookId}")
	ResponseEntity<?> deletebook(@PathVariable int bookId) {

		int delete = bookservice.deleteBook(bookId);
		if (delete == 0) {
			return ResponseEntity.badRequest().body("Book is not present in database");
		}

		return ResponseEntity.ok().body("Book is successfully deleted");

	}

}

//
//{
//	"name":"name",
//	"description":"description",
//	"note":"note",
//	"rate":200
//}
