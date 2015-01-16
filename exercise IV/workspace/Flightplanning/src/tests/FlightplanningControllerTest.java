package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

import flightplanning.controller.*;
import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;
import flightplanning.model.Flightattendant;
import flightplanning.model.Pilot;


/** Create, Read, Update and Delete Test Cases. :-) */
public class FlightplanningControllerTest {
	
	private FlightplanningController classUnderTest;
	private ObjectContainer mObjectContainer;
	
	private final String OBJECTSTOREFILEPATH = "flightplanningTestEnvironment.data";
	
	@Before
	public void setUp () {
		
		mObjectContainer = createObjectContainer();
		
		// instantiate the class under test before each test case
		classUnderTest = new FlightplanningController(mObjectContainer);
		
		// setup test data:
		// so every test case has the 
		// same environment work with.
		createFlightplanningTestEnvironment();
	}

	@After
	public void tearDown () {
		// tear down test data that we added in setup
		tearDownFlightplanningTestEnvironment();
		
		mObjectContainer.close();
	}
	
	/** This tests verifies that the 
	 *  use case 'create' works properly
	 *  see createFLightplanningTestEnvironment()
	 */
	@Test
	public void testCreateCrew() {
		
		// get & check all the crew members
		List<Crew> allCrews = classUnderTest.getCrewAll();
		
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Fabian", "Affolter", "faff201402"))));
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Alex", "Meier", "amei201304"))));
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Nelson", "Allende", "nall201203"))));
		assertTrue(allCrews.contains(((Crew) new Pilot("Sepp", "Blatter", "sblat201004"))));
		assertTrue(allCrews.contains(((Crew) new Pilot("Marius", "Conti", "maconti"))));
	}
	
	@Test
	public void testCreateFlight() {
		
		// get & check all the flights
		List<Flight> allFlights = classUnderTest.getFlightAll();
		
		
		for(Flight each : allFlights) {
			System.out.println(each.getFlightIdentifier());
		}
		
				
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London", null)));
		assertTrue(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai", null)));
	}
	
	@Test
	public void testCreateSingleFlight() {
		
		// delete all existing flights (1 DB call)
		classUnderTest.deleteFlightAll();
				
		Flight newFlight = new Flight("CH021A", "Zurich", "London", null);
				
		// add a new airplane (1 DB call)
		classUnderTest.addFlight(newFlight);
				
		// get & check all the flights (1 DB call)
		List<Flight> allFlights = classUnderTest.getFlightAll();
		assertTrue(allFlights.contains(newFlight));
	}
	
	@Test
	public void testCreateSingleCrew() {
		
		// delete all existing crew (1 DB call)
		classUnderTest.deleteCrewAll();
				
		Pilot pilotAlex = new Pilot("Alex", "Blatter", "albl");
				
		// add a new crew (1 DB call)
		classUnderTest.addCrew(pilotAlex);
				
		// get & check all the crew (1 DB call)
		List<Crew> allFlights = classUnderTest.getCrewAll();
		assertTrue(allFlights.contains(pilotAlex));
	}
	
	@Test
	public void testCreateFlightCrews () {
		// verify flight crew is correct
		Set<Crew> flightcrewPH90102 = classUnderTest.getCrewOfFlight("PH90102");
		assertEquals(3, flightcrewPH90102.size());
		
		for(Crew each : flightcrewPH90102) {
			System.out.println(each.getEmployeeID());
		}
		
		// verify flight crew is correct
		Set<Crew> flightcrewETD12 = classUnderTest.getCrewOfFlight("ETD12");
		assertEquals(4, flightcrewETD12.size());
				
		for(Crew each : flightcrewETD12) {
			System.out.println(each.getEmployeeID());
		}
	}
	
	@Test
	public void testCreateSingleFlightCrews () {
		
		// delete all the flights and crews (2 DB calls)
		classUnderTest.deleteFlightAll();
		classUnderTest.deleteCrewAll();
		
		
		Pilot pilotAlex = new Pilot("Alex", "Blatter", "albl");
		Flightattendant fatOlivia = new Flightattendant("Oliva", "Buschor", "olbu");
		
		// add a new crew (1 DB call)
		classUnderTest.addCrew(pilotAlex);
		classUnderTest.addCrew(fatOlivia);
		
		
		Flight newFlight = new Flight("CH021A", "Zurich", "London", null);
		newFlight.addCrewMember(pilotAlex);
		newFlight.addCrewMember(fatOlivia);
		
		// add a new airplane (1 DB call)
		classUnderTest.addFlight(newFlight);
		
		// verify flight crew is correct (1 DB call)
		Set<Crew> flightcrew = classUnderTest.getCrewOfFlight("CH021A");
		assertEquals(2, flightcrew.size());
		
		for(Crew each : flightcrew) {
			System.out.println(each.getEmployeeID());
		}
	}
	
	@Test
	public void testGetFlight () {
		
		Flight flight = classUnderTest.getFlight("ETD12");
		assertEquals("ETD12", flight.getFlightIdentifier());
		assertEquals("Zurich", flight.getOrigin());
		assertEquals("Dubai", flight.getDestination());
		
		Flight flightNonExisting = classUnderTest.getFlight("Whatever");
		assertEquals("NOT_FOUND", flightNonExisting.getFlightIdentifier());
	}
	
	@Test
	public void testGetAirplane () {
		
		Flight flight = classUnderTest.getFlight("ETD12");
		
		Airplane assignedAirplane = flight.getAssignedAirplane();
		assertEquals("German Wings", assignedAirplane.getAircraftOwner());
		assertEquals("DE9021", assignedAirplane.getAircraftRegistrationID());
		assertEquals("Airbus A320", assignedAirplane.getAircraftType());
	}
	
	@Test
	public void testUpdateFlight () {
		
		// lets change destination and origin
		classUnderTest.updateFlight("ETD12", "Genf", "Doha");
		
		Flight updatedFlight = classUnderTest.getFlight("ETD12");
		assertEquals("ETD12", updatedFlight.getFlightIdentifier());
		assertEquals("Genf", updatedFlight.getOrigin());
		assertEquals("Doha", updatedFlight.getDestination());
		
		// should not throw exception here
		classUnderTest.updateFlight("NonExistingFlightId", "", "");
	}
	
	@Test
	public void testUpdateSingleFlight () {
		
		// delete all existing flights (1 DB call)
		classUnderTest.deleteFlightAll();
						
		Flight newFlight = new Flight("CH021A", "Zurich", "London", null);
						
		// add a new airplane (1 DB call)
		classUnderTest.addFlight(newFlight);
		
		// Precondition: No. of flights is <1>
		// make sure we don't just add another flight (1 DB call)
		assertEquals(1, classUnderTest.getFlightAll().size());
		
		// lets change destination and origin of this flight (2 DB call)
		classUnderTest.updateFlight("CH021A", "Genf", "Doha");
		
		// Precondition: No. of flights is unchanged
		assertEquals(1, classUnderTest.getFlightAll().size());
		
		// lets change destination and origin of this flight (1 DB call)
		Flight updatedFlight = classUnderTest.getFlight("CH021A");
		assertEquals("CH021A", updatedFlight.getFlightIdentifier());
		assertEquals("Genf", updatedFlight.getOrigin());
		assertEquals("Doha", updatedFlight.getDestination());
	}
	
	@Test
	public void testUpdatePilotLicenceNumber () {
		
		Pilot ourBest = new Pilot("James", "Bond", "007");
		
		// add him to the data store 
		classUnderTest.addCrew(ourBest);
		assertEquals("unknown", ourBest.getPilotLicenceNumber());
		
		classUnderTest.updatePilotLicenceNumber("007", "ABC");
		
		// check that this change also got persisted in the DB
		int indexOfOurBest = classUnderTest.getCrewAll().indexOf(ourBest);
		Pilot hopefullyOurBest = (Pilot) classUnderTest.getCrewAll().get(indexOfOurBest);
		assertEquals("ABC", hopefullyOurBest.getPilotLicenceNumber());
	}
	
	@Test
	public void testDeleteFlight () {
		
		// delete the flight ETD12
		classUnderTest.deleteFlight("ETD12");
		
		List<Flight> allFlights = classUnderTest.getFlightAll();
		
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London", null)));
		assertFalse(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai", null)));
	}
	
	@SuppressWarnings("deprecation")
	public ObjectContainer createObjectContainer () {
		
		// fix me: openFile is deprecated :(
		return Db4o.openFile(OBJECTSTOREFILEPATH);
	}
	
	public void createFlightplanningTestEnvironment () {
		
		// first we instantiate a few flight attendants
		Flightattendant flightAttendedFabian = new Flightattendant("Fabian", "Affolter", "faff201402");
		Flightattendant flightAttendedAlex = new Flightattendant("Alex", "Meier", "amei201304");
		Flightattendant flightAttendedNelson = new Flightattendant("Nelson", "Allende", "nall201203");
		
		// ... a few pilots
		Pilot pilotSepp = new Pilot("Sepp", "Blatter", "sblat201004");
		Pilot pilotMarius = new Pilot("Marius", "Conti", "maconti");
		
		//... create a few airplanes
		Airplane airplaneAirbusA380 = new Airplane("CH9289", "Swiss", "Airbus A380");
		Airplane airplaneAirbusA320 = new Airplane("DE9021", "German Wings", "Airbus A320");
		
		// ... create some flights: Zurich -> London
		Flight flightZurichLondon = new Flight("PH90102", "Zurich", "London", airplaneAirbusA380);
		flightZurichLondon.addCrewMember(flightAttendedFabian);
		flightZurichLondon.addCrewMember(flightAttendedAlex);
		flightZurichLondon.addCrewMember(pilotSepp);
		
		// ... Zurich -> Dubai
		Flight flightZurichDubai = new Flight("ETD12", "Zurich", "Dubai", airplaneAirbusA320);
		flightZurichDubai.addCrewMember(flightAttendedNelson);
		flightZurichDubai.addCrewMember(flightAttendedFabian);
		flightZurichDubai.addCrewMember(flightAttendedAlex);
		flightZurichDubai.addCrewMember(pilotMarius);
		
		// ... and let persist those objects
		classUnderTest.addFlight(flightZurichLondon);
		classUnderTest.addFlight(flightZurichDubai);
		classUnderTest.addFlight(flightZurichLondon);
		classUnderTest.addCrew(flightAttendedFabian);
		classUnderTest.addCrew(flightAttendedAlex);
		classUnderTest.addCrew(flightAttendedNelson);
		classUnderTest.addCrew(pilotSepp);
		classUnderTest.addCrew(pilotMarius);
		classUnderTest.addAirplane(airplaneAirbusA380);
		classUnderTest.addAirplane(airplaneAirbusA320);
	}
	
	public void tearDownFlightplanningTestEnvironment () {
		classUnderTest.deleteEverything();
		
		// always start fresh -> thus just delete it
		File databaseFile = new File(OBJECTSTOREFILEPATH);
		databaseFile.delete();
	}
}