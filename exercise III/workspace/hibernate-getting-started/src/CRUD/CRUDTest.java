package CRUD;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Flightplanning.Crew;
import Flightplanning.Flight;
import Flightplanning.Flightattendant;
import Flightplanning.Pilot;


public class CRUDTest {
	
	/** This is the one session we work on */
	Session session;
	
	@BeforeClass
	static public void beforeClass () { 
		
	}
	
	@AfterClass
	static public void afterClass () {

	}
	
	@Before
	public void setUp () {
		
		// reset session - drops tables
		session = createSession();
		
		// setup test data:
		// so every test case has the 
		// same environment work with.
		setupFLightplanningTestEnvironment();
		
	}

	/** This tests verifies that the 
	 *  setup / insertion of the test data
	 *  was successful.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testSetup() {
		
		session.beginTransaction();
		
		// get & check all the crew members
		List<Crew> allCrews = session.createQuery("from Crew").list();
		
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Fabian", "Affolter", "faff201402"))));
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Alex", "Meier", "amei201304"))));
		assertTrue(allCrews.contains(((Crew) new Flightattendant("Nelson", "Allende", "nall201203"))));
		assertTrue(allCrews.contains(((Crew) new Pilot("Sepp", "Blatter", "sblat201004"))));
		assertTrue(allCrews.contains(((Crew) new Pilot("Marius", "Conti", "maconti"))));
		
		
		// get & check all the flights
		List<Flight> allFlights = session.createQuery("from Flight").list();
		
		assertTrue(allFlights.contains(new Flight("PH90102", "Zurich", "London")));
		assertTrue(allFlights.contains(new Flight("ETD12", "Zurich", "Dubai")));
		
		session.getTransaction().commit();
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
	
	public void setupFLightplanningTestEnvironment () {
		
		session.beginTransaction();
		
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
		flightZurichDubai.addCrewMember(flightAttendedAlex);
		flightZurichDubai.addCrewMember(pilotMarius);
		
		// ... and let Hibernate persist those objects
		session.save(flightZurichLondon);
		session.save(flightZurichDubai);
		session.save(flightAttendedFabian);
		session.save(flightAttendedAlex);
		session.save(flightAttendedNelson);
		session.save(pilotSepp);
		session.save(pilotMarius);
		
		session.getTransaction().commit();
	}
}
