package com.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<List<Book>> findBytitle(String title);

	@Query("select b from Book b ")
	List<Book> giveallbook();

	@Query("Select b from Book as b order by b.bookid desc ")
	List<Book> fetchBookByDec();

	List<Book> findByUserUserId(int userId);

	List<Book> findByDepartmentName(String deptName);

	List<Book> findByPriceBetween(int lower, int higher);

	List<Book> findByUserDistrict(String location);

	boolean existsBybookid(int bookId);

	Optional<List<Book>> findByFilename(int userId);
}
