package com.example.CRUDapplication.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.CRUDapplication.model.Book;
import com.example.CRUDapplication.repo.Bookrepo;


@RestController
public class BookController {
    
	@Autowired
	private Bookrepo bookrepo;
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {
			List<Book> booklist= new ArrayList<>();
			bookrepo.findAll().forEach(booklist::add);
			
			if(booklist.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(booklist,HttpStatus.OK);
		}catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getBookById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> bookData=bookrepo.findById(id);
		
		if(bookData.isPresent()) {
			return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/addBook")
	public  ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book bookObj= bookrepo.save(book);
		
		return new ResponseEntity<>(bookObj,HttpStatus.OK);
	}
	
	@PostMapping("/updateBookById/{id}")
	public ResponseEntity<Book> updateBookbyId(@PathVariable long id,@RequestBody Book book) {
		Optional<Book> oldbookData = bookrepo.findById(id);
		
		if(oldbookData.isPresent()) {
			Book updatedbookdata = oldbookData.get();
//			updatedbookdata.setTitle(book.getTitle());
//			updatedbookdata.setAuthor(book.getAuthor());
			
			Book bookObj=bookrepo.save(updatedbookdata);
			return new ResponseEntity<>(bookObj,HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteBookbyId/{id}")
	public ResponseEntity<HttpStatus> deleteBookbyId(@PathVariable Long id) {
		bookrepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
				
	}
}
