package flightplanning.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.*;

import flightplanning.model.Crew;
import flightplanning.model.Flight;

public class FlightplanningController {
	
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
}
