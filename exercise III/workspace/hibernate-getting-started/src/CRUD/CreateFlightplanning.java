package CRUD;

import org.hibernate.Session;

import CRUD.Helper.SessionBuilderFlightplanning;
import Flightplanning.Flight;
import Flightplanning.Flightattendant;
import Flightplanning.Pilot;


public class CreateFlightplanning {

	public static void main(String[] args) {

		Session session = SessionBuilderFlightplanning.buildSession();
		
		session.beginTransaction();
		
		Flightattendant s1 = new Flightattendant("Fabian", "Affolter", "faff201402");
		Flightattendant s2 = new Flightattendant("Alexander", "Meier", "amei201304");
		Flightattendant s3 = new Flightattendant("Nelson", "Allende", "nall201203");
		Pilot p1 = new Pilot("Sepp", "Blatter", "sblat201004");
		
		Flight f = new Flight("f01", "Paris");
		f.addCrewMember(s1);
		f.addCrewMember(s2);
		
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(p1);
		session.save(f);
		
		session.getTransaction().commit();
	
	}

}
