package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import flightplanning.controller.FlightplanningController;
import flightplanning.model.Airplane;
import flightplanning.model.Crew;
import flightplanning.model.Flight;
import flightplanning.model.Flightattendant;
import flightplanning.model.Pilot;


/** Create, Read, Update and Delete Test Cases. :-) */
public class FlightplanningControllerTest {
	
	FlightplanningController classUnderTest;
	
	@Before
	public void setUp () {
		
		// instantiate the class under test before each test case
		classUnderTest = new FlightplanningController(createSession());
		
		// setup test data:
		// so every test case has the 
		// same environment work with.
		createFlightplanningTestEnvironment();
	}

	@After
	public void tearDown () {
		// tear down test data that we added in setup
		tearDownFlightplanningTestEnvironment();
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
				
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London", null)));
		assertTrue(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai", null)));
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
	public void testDeleteFlight () {
		
		// delete the flight ETD12
		classUnderTest.deleteFlight("ETD12");
		
		List<Flight> allFlights = classUnderTest.getFlightAll();
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London", null)));
		assertFalse(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai", null)));
	}
	
	public Session createSession () {
		
		Configuration config = new Configuration();
		
		config.addAnnotatedClass(Crew.class);
		config.addAnnotatedClass(Pilot.class);
		config.addAnnotatedClass(Flightattendant.class);
		config.addAnnotatedClass(Flight.class);
		config.addAnnotatedClass(Airplane.class);
		config.configure("hibernate.cfg.xml");
		
		// some exception handling here would be awesome
		new SchemaExport(config).create(true,true);
		
		ServiceRegistry serviceRegistry =
			new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		SessionFactory factory = config.buildSessionFactory(serviceRegistry);

		// first one should be openSession and not getSession
		// see: https://stackoverflow.com/questions/2378572/hibernate-session-is-closed
		return factory.openSession();	
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
		
		// ... and let Hibernate persist those objects
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
	}
}