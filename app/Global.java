import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import play.Application;
import play.GlobalSettings;


/**
 * Application wide behaviour. We establish a Spring application context for the
 * dependency injection system and configure Spring Data.
 */
public class Global extends GlobalSettings
{

	/**
	 * The name of the persistence unit we will be using.
	 */
	static final String DEFAULT_PERSISTENCE_UNIT = "default";

	/**
	 * Declare the application context to be used - a Java annotation based
	 * application context requiring no XML.
	 */
	final private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

	/**
	 * Sync the context lifecycle with Play's.
	 */
	@Override
	public void onStart(final Application app)
	{
		super.onStart(app);

		// AnnotationConfigApplicationContext can only be refreshed once, but we
		// do it here even though this method
		// can be called multiple times. The reason for doing during startup is
		// so that the Play configuration is
		// entirely available to this application context.
		ctx.register(SpringDataJpaConfiguration.class);

		// provide all packages which need to springized
		// ctx.scan("controllers.rest", "models", "services.service",
		// "services.serviceimpl");

		ctx.refresh();

		// This will construct the beans and call any construction lifecycle
		// methods e.g. @PostConstruct
		ctx.start();

	}

	/**
	 * Sync the context lifecycle with Play's.
	 */
	@Override
	public void onStop(final Application app)
	{
		// This will call any destruction lifecycle methods and then release the
		// beans e.g. @PreDestroy
		ctx.close();
		super.onStop(app);
	}

	/**
	 * Controllers must be resolved through the application context. There is a
	 * special method of GlobalSettings that we can override to resolve a given
	 * controller. This resolution is required by the Play router.
	 */
	@Override
	public <A> A getControllerInstance(Class<A> aClass)
	{
		return ctx.getBean(aClass);
	}

	/**
	 * This configuration establishes Spring Data concerns including those of
	 * JPA.
	 */
	@Configuration
	//@EnableJpaRepositories("com.frugalbin.integration.repositories")
	@ComponentScan(basePackages = { "com.frugalbin.integration.controllers", "com.frugalbin.integration.controllers.rest" })
	// , "models", "services.service", "services.serviceimpl",
	// "controllers.bootstrap"})
	@EnableTransactionManagement
	public static class SpringDataJpaConfiguration
	{

		/*private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
		private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
		private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
		private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
		private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
		private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";*/

		/*@Bean
		public DataSource dataSource() {
			BoneCPDataSource dataSource = new BoneCPDataSource();
			dataSource.setDriverClass("org.h2.Driver");
			dataSource.setJdbcUrl("jdbc:h2:mem:play");
			//dataSource.setUsername(username);
			//dataSource.setPassword(password);
			return dataSource;
		}*/

		/*@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {*/
		//@Bean
		//public EntityManagerFactory entityManagerFactory()
		//{
			//return Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT);
			/*LocalContainerEntityManagerFactoryBean entiryManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
			
			entiryManagerFactoryBean.setDataSource(dataSource());
			entiryManagerFactoryBean.setPackagesToScan("models");
			entiryManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
			
			Properties jpaProperties = new Properties();
			
			//The classname of a Hibernate org.hibernate.dialect.Dialect which allows Hibernate to generate SQL optimized for a particular relational database.
			jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.H2Dialect");
			
			//Pretty print the SQL in the log and console.
			jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
			
			//Automatically validates or exports schema DDL to the database when the SessionFactory is created. With create-drop, the database schema will be dropped when the SessionFactory is closed explicitly.
			//e.g. validate | update | create | create-drop
			jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
			
			jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, "org.hibernate.cfg.ImprovedNamingStrategy");
			
			//Write all SQL statements to console.
			jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");

			entiryManagerFactoryBean.setJpaProperties(jpaProperties);
			
			//return entiryManagerFactoryBean.getNativeEntityManagerFactory();
			return entiryManagerFactoryBean;*/
		//}

		/*@Bean
		public HibernateExceptionTranslator hibernateExceptionTranslator()
		{
			return new HibernateExceptionTranslator();
		}

		@Bean
		public JpaTransactionManager transactionManager()
		{
			return new JpaTransactionManager();
		}*/
	}
}
