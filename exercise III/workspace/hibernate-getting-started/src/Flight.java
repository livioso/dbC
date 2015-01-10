import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Flights")  
public class Flight {

	@Id
	@Column(name = "FLIGHT_ID")  
	private String mFlightIdentifier;
	
	@Column(name = "DESTINATION") 
	private String mDestination;
	
	//@ManyToMany(mappedBy = "Flight")  
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Flights", 
		joinColumns={@JoinColumn(name="FLIGHT_ID")}, 
	    inverseJoinColumns={@JoinColumn(name="EMPLOYEE_ID")})
	private List<Crew> mFlightCrew = new ArrayList<>();
	
	public Flight (String flightIdentifier, String destination) {
		this.mFlightIdentifier = flightIdentifier;
		this.mDestination = destination;
	}
	
	public String getFlightIdentifier () {
		return mFlightIdentifier;
	}
	
	public String getDestination () {
		return mDestination;
	}
	
	public List<Crew> getFlightCrew () {
		return mFlightCrew;
	}
}
