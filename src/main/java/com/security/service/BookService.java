package com.security.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.entity.Book;
import com.security.entity.Department;
import com.security.entity.UserInfo;
import com.security.repository.BookRepository;
import com.security.repository.DepartmentRepository;
import com.security.repository.UserRepositoty;

@Service
public class BookService {

	int i;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepositoty userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	String image_encode;

	public Book save(Book book) {
		return bookRepository.save(book);
	}

	public Book createBook(int userId, String requestbook, MultipartFile bookImage, String department)
			throws IOException {

		LocalDate myObj = LocalDate.now();
		String departmentArray[] = department.split(",");
		Book book = new ObjectMapper().readValue(requestbook, Book.class);
		book.setFilename(bookImage.getOriginalFilename());
		book.setBookImage(bookImage.getBytes());

		List<Department> departmentList = new ArrayList<Department>();

		for (int i = 0; i < departmentArray.length; i++) {
			departmentList.add(departmentRepository.findByName(departmentArray[i]));
		}

		Optional<UserInfo> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			book.setUser(userOptional.get());
			book.setDepartment(departmentList);
			book.setDate(myObj.toString());
			return bookRepository.save(book);
		}

		return null;
	}

	public List<Book> userBook(int userId) {

		List<Book> bookList = bookRepository.findByUserUserId(userId);

		if (!bookList.isEmpty()) {

			for (i = 0; i < bookList.size(); i++) {

				image_encode = convertToBase64(bookList.get(i).getFilename(), bookList.get(i).getBookImage());
				bookList.get(i).setImage_encode(image_encode);

			}

			return bookList;
		}

		return null;
	}

	public List<Book> searchbook(String searchKey) {

		Optional<List<Book>> bookList = bookRepository.findBytitle(searchKey);
		List<Book> book_list = null;

		if (bookList.isPresent()) {

			book_list = bookList.get();

			for (i = 0; i < book_list.size(); i++) {

				image_encode = convertToBase64(book_list.get(i).getFilename(), book_list.get(i).getBookImage());
				book_list.get(i).setImage_encode(image_encode);

			}

			return book_list;
		}

		return null;
	}

	public List<Book> searchByDept(String deptName) {

		List<Book> bookList = bookRepository.findByDepartmentName(deptName);
		// List<Book> book_list = null;

		if (bookList.size() > 0) {

			for (i = 0; i < bookList.size(); i++) {

				image_encode = convertToBase64(bookList.get(i).getFilename(), bookList.get(i).getBookImage());
				bookList.get(i).setImage_encode(image_encode);

			}

			return bookList;
		}

		return null;
	}

	public List<Book> searchByPrice(String price) {

		String priceArray[] = new String[3];

		if (price.equals("500+")) {
			priceArray[0] = "500";
			priceArray[2] = "100000";
		} else {
			priceArray = price.split(" ");
		}

		List<Book> bookList = bookRepository.findByPriceBetween(Integer.parseInt(priceArray[0]),
				Integer.parseInt(priceArray[2]));

		// List<Book> bookList = bookRepository.findByPriceBetween(priceArray[0],
		// priceArray[2]);

		// List<Book> book_list = null; 100 - 200

		if (bookList.size() > 0) {

			for (i = 0; i < bookList.size(); i++) {

				image_encode = convertToBase64(bookList.get(i).getFilename(), bookList.get(i).getBookImage());
				bookList.get(i).setImage_encode(image_encode);

			}

			return bookList;
		}

		return null;
	}

	public List<Book> searchByLocation(String location) {

		List<Book> bookList = bookRepository.findByUserDistrict(location);
		// List<Book> book_list = null; 100 - 200

		if (bookList.size() > 0) {

			for (i = 0; i < bookList.size(); i++) {

				image_encode = convertToBase64(bookList.get(i).getFilename(), bookList.get(i).getBookImage());
				bookList.get(i).setImage_encode(image_encode);

			}

			return bookList;
		}

		return null;
	}

	public List<Book> fetchallbook() {

		List<Book> book_list = bookRepository.giveallbook();

		for (i = 0; i < book_list.size(); i++) {

			image_encode = convertToBase64(book_list.get(i).getFilename(), book_list.get(i).getBookImage());
			book_list.get(i).setImage_encode(image_encode);
		}

		return book_list;
	}

	public Book fetchOneBook(int id) {

		Optional<Book> book = bookRepository.findById(id);

		if (book.isPresent()) {

			image_encode = convertToBase64(book.get().getFilename(), book.get().getBookImage());
			book.get().setImage_encode(image_encode);

			return book.get();
		}

		return null;
	}

	public int deleteBook(int booId) {

		if (bookRepository.existsBybookid(booId)) {
			bookRepository.deleteById(booId);
			return 1;
		}

		return 0;
	}

	public Book updateBook(int userId, String requestbook, MultipartFile bookImage) throws IOException {

		Book book = new ObjectMapper().readValue(requestbook, Book.class);
		book.setFilename(bookImage.getOriginalFilename());
		book.setBookImage(bookImage.getBytes());

		Optional<UserInfo> userOptional = userRepository.findById(userId);

		System.out.println("userId " + userId + ' ' + book.getBookid());

		boolean existBook = bookRepository.existsBybookid(book.getBookid());

		if (existBook && userOptional.isPresent()) {

			book.setUser(userOptional.get());
			return bookRepository.save(book);

		}

		return null;

	}

	public String convertToBase64(String fileName, byte[] logo) {

		String extension = FilenameUtils.getExtension(fileName);
		String encodeBase64 = Base64.getEncoder().encodeToString(logo);

		return "data:image/" + extension + ";base64," + encodeBase64;

	}

	public List<Book> filterByDate(String date) {

		List<Book> bookList = null;

		if (date.equals("Old TO New")) {
			bookList = bookRepository.fetchBookByDec();
		} else {
			bookList = bookRepository.giveallbook();
		}

		if (bookList.size() > 0) {

			for (i = 0; i < bookList.size(); i++) {

				image_encode = convertToBase64(bookList.get(i).getFilename(), bookList.get(i).getBookImage());
				bookList.get(i).setImage_encode(image_encode);

			}

			return bookList;
		}

		return null;
	}

}
