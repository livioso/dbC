package flightplanning.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.db4o.*;
import com.db4o.query.Predicate;

import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;
import flightplanning.model.Pilot;

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
	
	@SuppressWarnings("serial")
	public Set<Crew> getCrewOfFlight (String withFlightId) {
		
		Set<Crew> crewOfFlightWithId = new HashSet<>();
		
		List<Flight> flightsWithId = mObjectContainer.query(new Predicate<Flight>() {
            public boolean match(Flight flight) {
            	return flight.getFlightIdentifier().equals(withFlightId);
            }
		});
		
		if(!flightsWithId.isEmpty()) {
			crewOfFlightWithId = flightsWithId.get(0).getFlightCrew();
		}
		
		return crewOfFlightWithId;
	}
	
	public List<Crew> getCrewAll () {
		
		List<Crew> crewAll = new ArrayList<>();

		for(Object each : mObjectContainer.queryByExample(Crew.class)) {
			crewAll.add((Crew) each);
		}
		
		return crewAll;
	}
	
	public List<Flight> getFlightAll () {
		
		List<Flight> flightAll = new ArrayList<>();

		for(Object each : mObjectContainer.queryByExample(Flight.class)) {
			flightAll.add((Flight) each);
		}
		
		return flightAll;
	}
	
	@SuppressWarnings("serial")
	public Flight getFlight (String withFlightId) {
		
		Flight flight = new Flight(NOT_FOUND, "NA", "NA", null);
		
		List<Flight> flightsWithId = mObjectContainer.query(new Predicate<Flight>() {
            public boolean match(Flight flight) {
            	return flight.getFlightIdentifier().equals(withFlightId);
            }
		});
		
		if(!flightsWithId.isEmpty()) {
			flight = (Flight) flightsWithId.get(0);
		}
		
		return flight;
	}
	
	public void updateFlight (String withFlightId, String newOrigin, String newDestination) {
		
		Flight foundFlight = getFlight(withFlightId);
		
		// if found update, if not just do nothing
		if(!foundFlight.getFlightIdentifier().equals(NOT_FOUND)) {
			
			foundFlight.updateOrigin(newOrigin);
			foundFlight.updateDestination(newDestination);
		
			mObjectContainer.store(foundFlight);
		}
	}
	
	@SuppressWarnings("serial")
	public void updatePilotLicenceNumber (String withEmployeeId, String newPilotLicenceNumber) {
		
		List<Pilot> pilotsWithId = mObjectContainer.query(new Predicate<Pilot>() {
            public boolean match(Pilot pilot) {
            	return pilot.getEmployeeID().equals(withEmployeeId);
            }
		});
		
		if(!pilotsWithId.isEmpty()) {
			
			Pilot pilotToUpdate = pilotsWithId.get(0);
			pilotToUpdate.updatePilotLicenceNumber(newPilotLicenceNumber);
		
			mObjectContainer.store(pilotToUpdate);
		}
	}
	
	@SuppressWarnings("serial")
	public void deleteFlight (String withFlightId) {
		
		List<Flight> flightsWithId = mObjectContainer.query(new Predicate<Flight>() {
            public boolean match(Flight flight) {
            	return flight.getFlightIdentifier().equals(withFlightId);
            }
		});
		
		if(!flightsWithId.isEmpty()) {
			// delete the flight if found 
			// otherwise simply do nothing
			mObjectContainer.delete(flightsWithId.get(0));
		}

	}
	
	public void deleteEverything () {
		
		deleteFlightAll();
		deleteCrewAll();
	
	}
	
	public void deleteFlightAll() {
		
		for(Object each : mObjectContainer.queryByExample(Flight.class)) {
			mObjectContainer.delete(each);
		}
		
	}

	public void deleteCrewAll() {
		
		for(Object each : mObjectContainer.queryByExample(Crew.class)) {
			mObjectContainer.delete(each);
		}
		
	}
}
