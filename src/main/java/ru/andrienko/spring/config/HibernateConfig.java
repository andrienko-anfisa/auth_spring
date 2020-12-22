package ru.andrienko.spring.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.andrienko.spring.models.User;

import java.util.Properties;


@Configuration
@ComponentScan(basePackages = "ru.andrienko.spring")
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
public class HibernateConfig {

    @Autowired
    Environment environment;

    @Bean
    public SessionFactory getSessionFactory() throws Exception {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setAnnotatedClasses(User.class);
        factoryBean.setDataSource(getDataSource());
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        SessionFactory sf = factoryBean.getObject();
        return sf;
    }

    @Bean
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource bds = new DriverManagerDataSource();
        bds.setDriverClassName(environment.getProperty("hibernate.connection.driver_class"));
        bds.setUrl(environment.getProperty("hibernate.connection.url"));
        bds.setUsername(environment.getProperty("hibernate.connection.username"));
        bds.setPassword(environment.getProperty("hibernate.connection.password"));

        return bds;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws Exception {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory());
        return transactionManager;
    }

}
