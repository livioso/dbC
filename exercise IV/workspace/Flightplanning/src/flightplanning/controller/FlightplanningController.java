package flightplanning.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.db4o.*;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;

public class FlightplanningController implements IFlightplanningController{
	
	/** This string gets set when we did not found something as ID */
	private final String NOT_FOUND = "NOT_FOUND";
	
	/** Database to be used.*/
	private ObjectContainer mObjectContainer;
	
	public FlightplanningController (ObjectContainer objectContainer) {
		this.mObjectContainer = objectContainer;
	}
	
	private void commitTransaction () {
		mObjectContainer.commit();
	}

	public void addFlight (Flight flightToAdd) {
		mObjectContainer.store(flightToAdd);
		commitTransaction();
	}
	
	public void addCrew (Crew crewToAdd) {
		mObjectContainer.store(crewToAdd);
		commitTransaction();		
	}
	
	public void addAirplane (Airplane airplaneToAdd) {
		mObjectContainer.store(airplaneToAdd);
		commitTransaction();		
	}
	
	public Set<Crew> getCrewOfFlight (String withFlightId) {
		
		Set<Crew> crewOfFlightWithId = new HashSet<>();
		
		/*beginTransaction();
		// query and search for particular flight
		Query query = mSession.createQuery("from Flight WHERE FLIGHT_ID = :flightid");
		query.setParameter("flightid", withFlightId);
		List<Flight> flightsWithId = query.list();
		commitTransaction();
		*/
		
		List<Flight> flightsWithId = new ArrayList<>();;
		
		if(!flightsWithId.isEmpty()) {
			crewOfFlightWithId = flightsWithId.get(0).getFlightCrew();
		}
		
		return crewOfFlightWithId;
	}
	
	public List<Crew> getCrewAll () {
		
		ObjectSet<Crew> crewAll = mObjectContainer.queryByExample(Crew.class);
		return crewAll;
	}
	
	public List<Flight> getFlightAll () {
		
		List<Flight> flightAll = mObjectContainer.queryByExample(Flight.class);
		return flightAll;
	}
	
	public Flight getFlight (String withFlightId) {
		
		Flight flight = new Flight(NOT_FOUND, "NA", "NA", null);
		
		List<Flight> flightsWithId = mObjectContainer.query(
			new Predicate<Flight>() {
			    /**
				 * 
				 */
				private static final long serialVersionUID = 2615422327381350324L;

				public boolean match(Flight flight) {
			        return flight.getFlightIdentifier() == withFlightId;
			    }});
		
		if(!flightsWithId.isEmpty()) {
			flight = flightsWithId.get(0);
		}
		
		return flight;
	}
	
	public void updateFlight (String withFlightId, String newOrigin, String newDestination) {
		
		Flight foundFlight = getFlight(withFlightId);
		
		// if found update, if not just do nothing
		if(!foundFlight.getFlightIdentifier().equals(NOT_FOUND)) {
			
			/*beginTransaction();
			
			foundFlight.updateOrigin(newOrigin);
			foundFlight.updateDestination(newDestination);
            
			// use saveOrUpdate because the
			//  object is detached 
			mSession.saveOrUpdate(foundFlight);
			
			commitTransaction(); */
		}
	}
	
	public void deleteFlight (String withFlightId) {
		
		/*Query query = mSession.createQuery("delete from Flight where FLIGHT_ID = :flightid");
		query.setParameter("flightid", withFlightId);
		query.executeUpdate();
		commitTransaction(); */
		
	}
	
	public void deleteEverything () {
		
		/*beginTransaction();
		mSession.createQuery("delete from Flight").executeUpdate();
		mSession.createQuery("delete from Crew").executeUpdate();
		commitTransaction(); */
	}
	
	public void deleteFlightAll() {
		
	}

	public void deleteCrewAll() {
		
	}
}
