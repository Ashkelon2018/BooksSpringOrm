package telran.ashkelon2018.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.books.dto.AuthorDto;
import telran.ashkelon2018.books.dto.BookDto;
import telran.ashkelon2018.books.service.BookService;
import static telran.ashkelon2018.books.api.BookEndPoints.*;

@RestController
public class BooksController {

	@Autowired
	BookService bookService;

	@PostMapping(BOOK)
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@DeleteMapping(BOOK + "/{isbn}")
	public BookDto removeBook(@PathVariable Long isbn) {
		return bookService.removeBook(isbn);
	}

	@GetMapping(BOOK + "/{isbn}")
	public BookDto getBookByIsbn(@PathVariable Long isbn) {
		return bookService.getBookByIsbn(isbn);
	}

	@GetMapping(AUTHOR + "/{name}")
	public Iterable<BookDto> getBooksByAuthor(@PathVariable String name) {
		return bookService.getBooksByAuthor(name);
	}

	@GetMapping(PUBLISHER + "/{name}")
	public Iterable<BookDto> getBooksByPublisher(@PathVariable String name) {
		return bookService.getBooksByPublisher(name);
	}

	@GetMapping(AUTHORS + "/{isbn}")
	public Iterable<AuthorDto> getBookAuthors(@PathVariable Long isbn) {
		return bookService.getBookAuthors(isbn);
	}

	@GetMapping(PUBLISHERS + "/{name}")
	public Iterable<String> getPublishersByAuthor(@PathVariable String name) {
		return bookService.getPublishersByAuthor(name);
	}

}
