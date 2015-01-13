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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import flightplanning.controller.FlightplanningController;
import flightplanning.model.Crew;
import flightplanning.model.Flight;
import flightplanning.model.Flightattendant;
import flightplanning.model.Pilot;


/** Create, Read, Update and Delete Test Cases. :-) */
public class FlightplanningControllerTest {
	
	FlightplanningController classUnderTest;
	
	@BeforeClass
	static public void beforeClass () { 
		
	}
	
	@AfterClass
	static public void afterClass () {

	}
	
	@Before
	public void setUp () {
		
		// instantiate the class under test before each test case
		classUnderTest = new FlightplanningController(createSession());
		
		// setup test data:
		// so every test case has the 
		// same environment work with.
		createFLightplanningTestEnvironment();
	}

	@After
	public void tearDown () {
		
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
				
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London")));
		assertTrue(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai")));
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
	public void testRead () {
		
	}
	
	public Session createSession () {
		
		Configuration config = new Configuration();
		
		config.addAnnotatedClass(Crew.class);
		config.addAnnotatedClass(Pilot.class);
		config.addAnnotatedClass(Flightattendant.class);
		config.addAnnotatedClass(Flight.class);
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
	
	public void createFLightplanningTestEnvironment () {
		
		// first we instantiate a few flight attendants
		Flightattendant flightAttendedFabian = new Flightattendant("Fabian", "Affolter", "faff201402");
		Flightattendant flightAttendedAlex = new Flightattendant("Alex", "Meier", "amei201304");
		Flightattendant flightAttendedNelson = new Flightattendant("Nelson", "Allende", "nall201203");
		
		// ... a few pilots
		Pilot pilotSepp = new Pilot("Sepp", "Blatter", "sblat201004");
		Pilot pilotMarius = new Pilot("Marius", "Conti", "maconti");
		
		// ... create some flights: Zurich -> London
		Flight flightZurichLondon = new Flight("PH90102", "Zurich", "London");
		flightZurichLondon.addCrewMember(flightAttendedFabian);
		flightZurichLondon.addCrewMember(flightAttendedAlex);
		flightZurichLondon.addCrewMember(pilotSepp);
		
		// ... Zurich -> Dubai
		Flight flightZurichDubai = new Flight("ETD12", "Zurich", "Dubai");
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
	}
}
