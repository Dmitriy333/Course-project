package by.brashevets.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private HibernateUtil() {
	}

	static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
//			System.out.println("good initialization");
		} catch (Throwable e) {
			 System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
