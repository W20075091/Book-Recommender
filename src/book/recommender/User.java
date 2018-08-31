package book.recommender;

public class User {
	
	public int id;
	public String firstName;
	public String surname;
	public int age;
	public String gender;
	public String occupation;
	public int zipCode;
	
	public User(int id, String firstName, String surname, int age, String gender, String occupation, int zipCode) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.zipCode = zipCode;
	}

}
