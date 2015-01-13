package flightplanning.controller;

import java.util.List;
import java.util.Set;

import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;

public interface IFlightplanningController {

	public void addFlight (Flight flightToAdd);
	public void addCrew (Crew crewToAdd);
	public void addAirplane (Airplane airplaneToAdd);
	public Set<Crew> getCrewOfFlight (String withFlightId);
	public List<Crew> getCrewAll ();
	public List<Flight> getFlightAll ();
	public Flight getFlight (String withFlightId);
	public void updateFlight (String withFlightId, String newOrigin, String newDestination);
	public void deleteFlight (String withFlightId);
	public void deleteFlightAll ();
	public void deleteCrewAll ();
}
