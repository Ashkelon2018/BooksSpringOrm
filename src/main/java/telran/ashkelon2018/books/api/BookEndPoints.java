package telran.ashkelon2018.books.api;

public interface BookEndPoints {
	String BOOK = "/book";
	String AUTHOR = BOOK + "/author";
	String PUBLISHER = BOOK+"/publisher";
	String AUTHORS = "/authors/book";
	String PUBLISHERS = "/publishers/author";

}
