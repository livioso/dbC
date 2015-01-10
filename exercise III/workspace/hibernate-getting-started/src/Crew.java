import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Flight_Crew", 
		joinColumns={@JoinColumn(name="EMPLOYEE_ID")}, 
	    inverseJoinColumns={@JoinColumn(name="FLIGHT_ID")})
	private Set<Flight> flightAssignments = new HashSet<Flight>();
	
	public Crew(String firstName, String lastName, String employeeID) {
		this.mFirstName = firstName;
		this.mLastName = lastName;
		this.mEmployeeID = employeeID;
	}
	
	public void addFlight(Flight flight) {
		flightAssignments.add(flight);
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
