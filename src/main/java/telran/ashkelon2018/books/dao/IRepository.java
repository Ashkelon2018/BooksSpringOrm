package telran.ashkelon2018.books.dao;

import java.util.List;

import telran.ashkelon2018.books.domain.Author;
import telran.ashkelon2018.books.domain.Book;
import telran.ashkelon2018.books.domain.Publisher;

public interface IRepository {
	
	boolean addBook(Book book);
	
	boolean addAuthor(Author author);
	
	boolean addPublisher(Publisher publisher);
	
	Book removeBook(long isbn);
	
	Book findBook(long isbn);
	
	Author findAuthor(String authorName);
	
	Publisher findPublisher(String publisherName);

	List<Publisher> getPublishersByAuthor(String authorName);

}
