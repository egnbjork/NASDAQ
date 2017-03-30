package berberyan.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HibernateConfig {
	@Bean
	public SessionFactory sessionFactory() { 
		return new org.hibernate.cfg.Configuration()
				.configure()
				.buildSessionFactory();
	}
}
