package lk.ijse.config;

import lk.ijse.config.entity.Author;
import lk.ijse.config.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;


    private FactoryConfiguration(){
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Book.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static  FactoryConfiguration getInstance(){
        return factoryConfiguration == null ?  factoryConfiguration=new FactoryConfiguration(): factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
