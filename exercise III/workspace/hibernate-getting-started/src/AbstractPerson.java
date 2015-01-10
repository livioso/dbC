import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Column;

@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPerson {

	@Id
	@Column(name = "FIRSTNAME")
	protected String mFirstName;
	
	@Column(name = "LASTNAME")
	protected String mLastName;
	
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
