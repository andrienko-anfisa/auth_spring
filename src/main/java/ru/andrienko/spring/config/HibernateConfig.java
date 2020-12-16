package ru.andrienko.spring.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.andrienko.spring.models.User;


@Configuration
@ComponentScan(basePackages = "ru.andrienko.spring")
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
public class HibernateConfig {
    private Environment environment;
//    private static final String hibernate_show_sql = "true";
//    private static final String hibernate_hbm2ddl_auto = "update";
    private static HibernateConfig instance;
    private final SessionFactory sessionFactory;
    private HibernateConfig() {
        this.sessionFactory = createSessionFactory(getPostgresConfiguration());
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    public static HibernateConfig getInstance() {
        if (instance == null) {
            instance = new HibernateConfig();
        }
        return instance;
    }

    public Session openSession(){
        return sessionFactory.openSession();
    }


    private Configuration getPostgresConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", environment.getRequiredProperty("hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url", environment.getRequiredProperty("hibernate.connection.url"));
        configuration.setProperty("hibernate.connection.username", environment.getRequiredProperty("hibernate.connection.username"));
        configuration.setProperty("hibernate.connection.password", environment.getRequiredProperty("hibernate.connection.password"));
        configuration.setProperty("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return configuration;
    }
    @Bean
    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);

    }
}
