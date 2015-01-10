
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class test {

	public static void main(String[] args) {

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
		session.beginTransaction();
		
		Flightattendant s1 = new Flightattendant("Fabian", "Affolter", "faff201402");
		Flightattendant s2 = new Flightattendant("Alexander", "Meier", "amei201304");
		Flightattendant s3 = new Flightattendant("Nelson", "Allende", "nall201203");
		Pilot p1 = new Pilot("Sepp", "Blatter", "sblat201004");
		
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(p1);
		
		Flight 
		
		session.getTransaction().commit();
	
	}

}
