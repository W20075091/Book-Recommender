package book.recommender;

public class Rating {
	
	public int userId;
	public int bookId;
	public int rating;
	public int timestamp;
	
	public Rating(int userId, int bookId, int rating, int timestamp) {
		this.userId = userId;
		this.bookId = bookId;
		this.rating = rating;
		this.timestamp = timestamp;
	}

}
