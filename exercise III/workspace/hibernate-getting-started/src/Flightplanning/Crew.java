package Flightplanning;

import javax.persistence.*;

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
	
	public String getEmployeeID() {
		return mEmployeeID;
	}
	
	public String getFirstName () { 
		return mFirstName;
	}
	
	public String getLastName () {
		return mLastName;
	}
	
	@Override
	public boolean equals(Object obj) {
	       
		if (!(obj instanceof Crew)) {
			return false;
		}
		if (obj == this) {
			return true;
		}

		Crew rhs = (Crew) obj;
		boolean isEqual = this.mEmployeeID.compareTo(rhs.mEmployeeID) == 0;
		
		return isEqual;
	}
}
