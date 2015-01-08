
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;


public class test {

	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.addAnnotatedClass(College.class);
		config.addAnnotatedClass(Student.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true,true);
		
		ServiceRegistry serviceRegistry =
			new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		SessionFactory factory = config.buildSessionFactory(serviceRegistry);
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		College FHNW = new College();
		FHNW.setCollegeName("Fachhochschule Nordwerstschweiz");
		
		Student s1 = new Student();
		s1.setStudentName("Fabian Affolter");
				
		Student s2 = new Student();
		s2.setStudentName("Alexander Meier");
		
		Student s3 = new Student();
		s3.setStudentName("Nelson Allende");
		
		s1.setCollege(FHNW);
		s2.setCollege(FHNW);
		s3.setCollege(FHNW);
		
		session.save(FHNW);
		session.save(s1);
		session.save(s2);
		session.save(s3);
		
		session.getTransaction().commit();
	
	}

}
