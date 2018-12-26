package telran.ashkelon2018.books.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2018.books.dao.IRepository;
import telran.ashkelon2018.books.domain.Author;
import telran.ashkelon2018.books.domain.Book;
import telran.ashkelon2018.books.domain.Publisher;
import telran.ashkelon2018.books.dto.AuthorDto;
import telran.ashkelon2018.books.dto.BookDto;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	IRepository repository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if(repository.findBook(bookDto.getIsbn()) != null) {
			return false;
		}
		Publisher publisher = repository.findPublisher(bookDto.getPublisher());
		if (publisher == null) {
			publisher = new Publisher(bookDto.getPublisher());
			repository.addPublisher(publisher);
		}
		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		Set<Author> authors = new HashSet<>();
		for (AuthorDto authorDto : authorDtos) {
			Author author = repository.findAuthor(authorDto.getName());
			if (author == null) {
				author = new Author(authorDto.getName(), authorDto.getBirthDate());
				repository.addAuthor(author);
			}
			authors.add(author);
		}
		return repository.addBook(new Book(bookDto.getIsbn(),
				bookDto.getTitle(), authors, publisher));
	}

	@Override
	@Transactional
	public BookDto removeBook(Long isbn) {
		Book book = repository.removeBook(isbn);
		if (book == null) {
			return null;
		}
		return bookToBookDto(book);
	}

	@Override
	public BookDto getBookByIsbn(Long isbn) {
		Book book = repository.findBook(isbn);
		if (book == null) {
			return null;
		}
		return bookToBookDto(book);
	}

	@Override
	public Iterable<BookDto> getBooksByAuthor(String authorName) {
		Author author = repository.findAuthor(authorName);
		if (author == null) {
			return null;
		}
		return author.getBooks().stream()
				.map(this::bookToBookDto)
				.collect(Collectors.toSet());
	}

	@Override
	public Iterable<BookDto> getBooksByPublisher(String publisherName) {
		Publisher publisher = repository.findPublisher(publisherName);
		if (publisher == null) {
			return null;
		}
		return publisher.getBooks().stream()
				.map(this::bookToBookDto)
				.collect(Collectors.toSet());
	}

	@Override
	public Iterable<AuthorDto> getBookAuthors(Long isbn) {
		Book book = repository.findBook(isbn);
		if (book == null) {
			return null;
		}
		return book.getAuthors().stream()
				.map(this::authorToAuthorDto)
				.collect(Collectors.toSet());
	}

	@Override
	public Iterable<String> getPublishersByAuthor(String authorName) {
		return repository.getPublishersByAuthor(authorName).stream()
				.map(p -> p.getPublisherName())
				.collect(Collectors.toSet());
	}
	
	private BookDto bookToBookDto(Book book) {
		Set<AuthorDto> authors = book.getAuthors().stream()
				.map(this::authorToAuthorDto)
				.collect(Collectors.toSet());
		return new BookDto(book.getIsbn(), book.getTitle(),authors, book.getPublisher().getPublisherName());
	}
	
	private AuthorDto authorToAuthorDto(Author author) {
		return new AuthorDto(author.getName(), author.getBirthDate());
	}

}
