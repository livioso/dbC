public abstract class AbstractPerson {

	private String mFirstName;
	private String mLastName;
	
	public AbstractPerson(String firstName, String lastName) {
		this.mFirstName = firstName;
		this.mLastName = lastName;
	}
	
	String getFirstName () { 
		return mFirstName;
	}
	
	String getLastName () {
		return mLastName;
	}
}
