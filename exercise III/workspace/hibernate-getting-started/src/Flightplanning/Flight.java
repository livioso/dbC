package Flightplanning;
import java.util.HashSet;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name="Flights")
public class Flight {

	@Id
	@Column(name = "FLIGHT_ID")  
	private String mFlightIdentifier;
	
	@Column(name = "DESTINATION") 
	private String mDestination;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Flights_Crews", 
		joinColumns={@JoinColumn(name="EMPLOYEE_ID")}, 
	    inverseJoinColumns={@JoinColumn(name="FLIGHT_ID")})
	private Set<Crew> mFlightCrew = new HashSet<>();
	
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
	
	public void addCrewMember (Crew crewMember) {
		mFlightCrew.add(crewMember);
	}
	
	public void removeCrewMember (Crew crewMember) {
		mFlightCrew.remove(crewMember);
	}
	
	public Set<Crew> getFlightCrew () {
		return mFlightCrew;
	}
}
