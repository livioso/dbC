package CRUD;

import java.util.List;

import org.hibernate.Session;

import CRUD.Helper.SessionBuilderFlightplanning;
import Flightplanning.*;


public class CreateFlightplanning {

	public static void main(String[] args) {

		Session session = SessionBuilderFlightplanning.buildSession();
		
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
		
		List<Crew> allFlights = session.createQuery("from Crew").list();
		
		for(Crew each : allFlights) {
			System.out.println(each);
		}
		
		session.getTransaction().commit();
	}
}