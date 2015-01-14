package flightplanning.model;

import java.util.HashSet;
import java.util.Set;

public class Flight {

	private String mFlightIdentifier;
	
	private String mDestination;
	
	private String mOrigin;
	
    private Airplane mAssingedAirplane;
	
	private Set<Crew> mFlightCrew = new HashSet<>();
	
	public Flight (String flightIdentifier, String origin, String destination, Airplane assingedAirplane) {
		this.mFlightIdentifier = flightIdentifier;
		this.mOrigin = origin;
		this.mDestination = destination;
		this.mAssingedAirplane = assingedAirplane;
	}
	
	public void updateDestination (String newDestination) {
		mDestination = newDestination;
	}
	
	public void updateOrigin (String newOrigin) {
		mOrigin = newOrigin;
	}
	
	public String getFlightIdentifier () {
		return mFlightIdentifier;
	}
	
	public String getDestination () {
		return mDestination;
	}
	
	public String getOrigin () {
		return mOrigin;
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
	
	public Airplane getAssignedAirplane () {
		return mAssingedAirplane;
	}
	
	@Override
	public int hashCode () {
        int hash = 1;
        hash = hash * 17 + mFlightIdentifier.hashCode();
        return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
	       
		if (!(obj instanceof Flight)) {
			return false;
		}
		if (obj == this) {
			return true;
		}

		Flight rhs = (Flight) obj;
		boolean isEqual = this.mFlightIdentifier.compareTo(rhs.mFlightIdentifier) == 0;
		
		return isEqual;
	}
}
