package CRUD.Helper;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import Flightplanning.Crew;
import Flightplanning.Flight;
import Flightplanning.Flightattendant;
import Flightplanning.Pilot;

public class SessionBuilderFlightplanning {

	public static Session buildSession () {
		
		Configuration config = new Configuration();
		config.addAnnotatedClass(Crew.class);
		config.addAnnotatedClass(Pilot.class);
		config.addAnnotatedClass(Flightattendant.class);
		config.addAnnotatedClass(Flight.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true,true);
		
		ServiceRegistry serviceRegistry =
			new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		SessionFactory factory = config.buildSessionFactory(serviceRegistry);
		Session session = factory.getCurrentSession();
		
		return session;
	}
	
}
