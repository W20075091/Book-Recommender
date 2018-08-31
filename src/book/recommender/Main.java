package book.recommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	
	public static int numberOfRatings = 0;
	public static int numberOfBooks = 0;
	public static int numberOfUsers = 0;
	
	public static Rating[] ratings = new Rating[100];
	public static Book[] books = new Book[100];
	public static User[] users = new User[100];
	
	public static void main(String[] args) {
		load();
		getTopFiveBooks();
	}
	
	public static boolean load() {
		try {
			loadRatings();
			loadBooks();
			loadUsers();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	public static void addUser(String firstName, String surname, int age, String gender, String occupation, int zipCode) {
		int id = numberOfUsers + 1;
		User user = new User(id, firstName, surname, age, gender, occupation, zipCode);
		users[numberOfUsers] = user;
		numberOfUsers++;
	}
	
	public static void removeUser(int userId) {
		for (int index = 0; index < numberOfUsers; index++) {
			if (users[index] == null)
				continue;
			if (users[index].id == userId) {
				users[index] = null;
				break;
			}
		}
	}
	
	public static void addBook(String title, String year, String url) {
		int id = numberOfBooks + 1;
		Book book = new Book(id, title, year, url);
		books[numberOfBooks] = book;
		numberOfBooks++;
	}
	
	public static void addRating(int userId, int bookId, int ratingValue) {
		int timestamp = (int) System.currentTimeMillis() / 1000;
		Rating rating = new Rating(userId, bookId, ratingValue, timestamp);
		ratings[numberOfRatings] = rating;
		numberOfRatings++;
	}
	
	public static void getBook(int bookId) {
		for (int index = 0; index < numberOfBooks; index++) {
			if (books[index] == null)
				continue;
			if (books[index].id == bookId) {
				System.out.println("ID: " + books[index].id);
				System.out.println("Title: " + books[index].title);
				System.out.println("Author: " + books[index].author);
				System.out.println("Release Date: " + books[index].releaseDate);
			}
		}
	}
	
	public static Book getBookInstance(int bookId) {
		for (int index = 0; index < numberOfBooks; index++) {
			if (books[index] == null)
				continue;
			if (books[index].id == bookId)
				return books[index];
		}
		return null;
	}
	
	public static void getUserRatings(int userId) {
		for (int index = 0; index < numberOfRatings; index++) {
			if (ratings[index] == null)
				continue;
			if (ratings[index].userId == userId) {
				System.out.println("-- Rating --");
				System.out.println("Book ID: " + ratings[index].bookId);
				System.out.println("Rating: " + ratings[index].rating);
				System.out.println("Timestampt: " + ratings[index].timestamp);
			}
		}
	}
	
	public static void getUserRecommendations(int userId) {
		
	}
	
	public static void getTopFiveBooks() {
		int numberOfRatings = 0;
		int[] allBookRatings = new int[100];
		for (int index = 0; index < numberOfBooks; index++) {
			if (books[index] == null)
				continue;
			int bookId = books[index].id;
			allBookRatings[index] = getAverageRatingOfBookAndId(bookId);
			numberOfRatings++;
		}
		if (numberOfRatings < 5) {
			System.out.println("Not enough books rated.");
			return;
		}
		Arrays.sort(allBookRatings);
		for (int index = numberOfRatings - 5; index < numberOfRatings; index++) {
			double ratingAndId = Math.abs(allBookRatings[index]) / 100;
			System.out.println(ratingAndId);
			int bookId = (int) (ratingAndId % 1) * 100;
			System.out.println(bookId);
			if (bookId < 1)
				continue;
			System.out.println("-- Book --");
			String bookTitle = getBookInstance(bookId).title;
			System.out.println("Book ID: " + bookId);
			System.out.println("Title: " + bookTitle);
		}
	}
	
	public static int getAverageRatingOfBookAndId(int bookId) {
		int numberOfCurrentRatings = 0;
		int[] bookRatings = new int[100];
		for (int index = 0; index < numberOfRatings; index++) {
			if (ratings[index] == null)
				continue;
			if (ratings[index].bookId == bookId) {
				bookRatings[numberOfCurrentRatings] = ratings[index].rating;
				numberOfCurrentRatings++;
			}
		}
		if (numberOfCurrentRatings == 0)
			return 0;
		int averageRating = 0;
		for (int index = 0; index < numberOfCurrentRatings; index++) {
			averageRating += bookRatings[index];
		}
		averageRating = averageRating / numberOfCurrentRatings;
		averageRating = averageRating * 1000;
		averageRating += bookId;
		return averageRating;
	}
	
	public static void write() {
		
	}
	
	public static void loadRatings() throws Exception {
		URL url = Main.class.getResource("/data/ratings.dat");
		BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
		try {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("[|]");
		    	Rating rating = new Rating(
		    		Integer.parseInt(parts[0]),
		    		Integer.parseInt(parts[1]),
		    		Integer.parseInt(parts[2]),
		    		Integer.parseInt(parts[3])
		    	);
		    	ratings[numberOfRatings] = rating;
		    	numberOfRatings++;
		    }
		} finally {
		    br.close();
		}
	}
	
	public static void loadBooks() throws Exception {
		URL url = Main.class.getResource("/data/books.dat");
		BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
		try {
			String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("[|]");
		    	Book book = new Book(
		    		Integer.parseInt(parts[0]),
		    		parts[1],
		    		parts[2],
		    		parts[3]
		    	);
		    	books[numberOfBooks] = book;
		    	numberOfBooks++;
		    }
		} finally {
		    br.close();
		}
	}
	
	public static void loadUsers() throws Exception {
		URL url = Main.class.getResource("/data/users.dat");
		BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
		try {
			String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("[|]");
		    	User user = new User(
		    		Integer.parseInt(parts[0]),
		    		parts[1],
		    		parts[2],
		    		Integer.parseInt(parts[3]),
		    		parts[4],
		    		parts[5],
		    		Integer.parseInt(parts[6])
		    	);
		    	users[numberOfUsers] = user;
		    	numberOfUsers++;
		    }
		} finally {
		    br.close();
		}
	}

}
