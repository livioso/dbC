import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Column;

@Entity
@Table(name = "Crews")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Crew {

	@Id
	@Column(name = "EMPLOYEE_ID")
	protected String mEmployeeID;
	
	@Column(name = "FIRSTNAME")
	protected String mFirstName;
	
	@Column(name = "LASTNAME")
	protected String mLastName;
	
	public Crew(String firstName, String lastName, String employeeID) {
		this.mFirstName = firstName;
		this.mLastName = lastName;
		this.mEmployeeID = employeeID;
	}
	
	String getEmployeeID() {
		return mEmployeeID;
	}
	
	String getFirstName () { 
		return mFirstName;
	}
	
	String getLastName () {
		return mLastName;
	}
}
