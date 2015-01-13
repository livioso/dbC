package flightplanning.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.*;

import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;

public class FlightplanningController implements IFlightplanningController{
	
	/** This string gets set when we did not found something as ID */
	private final String NOT_FOUND = "NOT_FOUND";
	
	/** Session to be used.*/
	private Session mSession;
	
	public FlightplanningController (Session session) {
		this.mSession = session;
	}
	
	/** convenience - begins a
	 *  new transaction and commits
	 *  it in the commitTransaction method. */
	private void beginTransaction () {
		
		mSession.beginTransaction();
	}
	
	/** corresponding part to beginTransaction
	 *  see beginTransaction.
	 *  change simultaneously! */
	private void commitTransaction () {
		
		mSession.getTransaction().commit();
		mSession.flush();
	}

	public void addFlight (Flight flightToAdd) {
		
		beginTransaction();
		mSession.save(flightToAdd);
		commitTransaction();
	}
	
	public void addCrew (Crew crewToAdd) {
		
		beginTransaction();
		mSession.save(crewToAdd);
		commitTransaction();		
	}
	
	public void addAirplane (Airplane airplaneToAdd) {
		
		beginTransaction();
		mSession.save(airplaneToAdd);
		commitTransaction();		
	}
	
	@SuppressWarnings("unchecked")
	public Set<Crew> getCrewOfFlight (String withFlightId) {
		
		Set<Crew> crewOfFlightWithId = new HashSet<>();
		
		beginTransaction();
		// query and search for particular flight
		Query query = mSession.createQuery("from Flight WHERE FLIGHT_ID = :flightid");
		query.setParameter("flightid", withFlightId);
		List<Flight> flightsWithId = query.list();
		commitTransaction();
		
		if(!flightsWithId.isEmpty()) {
			crewOfFlightWithId = flightsWithId.get(0).getFlightCrew();
		}
		
		return crewOfFlightWithId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Crew> getCrewAll () {
		
		beginTransaction();
		List<Crew> crewAll = mSession.createQuery("from Crew").list();
		commitTransaction();
		
		return crewAll;
	}
	
	@SuppressWarnings("unchecked")
	public List<Flight> getFlightAll () {
		
		beginTransaction();
		List<Flight> flightAll = mSession.createQuery("from Flight").list();
		commitTransaction();
		
		return flightAll;
	}
	
	@SuppressWarnings("unchecked")
	public Flight getFlight (String withFlightId) {
		
		Flight flight = new Flight(NOT_FOUND, "NA", "NA", null);
		
		beginTransaction();
		Query query = mSession.createQuery("from Flight WHERE FLIGHT_ID = :flightid");
		query.setParameter("flightid", withFlightId);
		List<Flight> flightsWithId = query.list();
		commitTransaction();
		
		if(!flightsWithId.isEmpty()) {
			flight = flightsWithId.get(0);
		}
		
		return flight;
	}
	
	public void updateFlight (String withFlightId, String newOrigin, String newDestination) {
		
		Flight foundFlight = getFlight(withFlightId);
		
		// if found update, if not just do nothing
		if(!foundFlight.getFlightIdentifier().equals(NOT_FOUND)) {
			beginTransaction();
			
			foundFlight.updateOrigin(newOrigin);
			foundFlight.updateDestination(newDestination);
            
			// use saveOrUpdate because the
			//  object is detached 
			mSession.saveOrUpdate(foundFlight);
			
			commitTransaction();
		}
	}
	
	public void deleteFlight (String withFlightId) {
		
		beginTransaction();
		Query query = mSession.createQuery("delete from Flight where FLIGHT_ID = :flightid");
		query.setParameter("flightid", withFlightId);
		query.executeUpdate();
		commitTransaction();
	}
	
	public void deleteEverything () {
		
		beginTransaction();
		mSession.createQuery("delete from Flight").executeUpdate();
		mSession.createQuery("delete from Crew").executeUpdate();
		commitTransaction();
	}
}
