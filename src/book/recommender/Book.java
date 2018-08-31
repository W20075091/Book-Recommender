package book.recommender;

public class Book {
	
	public int id;
	public String title;
	public String releaseDate;
	public String author;
	
	public Book(int id, String title, String releaseDate, String author) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.author = author;
	}
	
}
