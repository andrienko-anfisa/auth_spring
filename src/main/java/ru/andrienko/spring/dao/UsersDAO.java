package ru.andrienko.spring.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.andrienko.spring.models.User;
import java.util.List;

@Repository
public class UsersDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUser(long id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    public User getUser(String login) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User WHERE login='" + login + "'");
        User user = (User) query.list().get(0);
        session.close();
        return user;
    }


    public long insertUser(User user) {
        Session session = sessionFactory.openSession();
        long id = (Long) session.save(user);
        session.close();
        return id;
    }


    public void editUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public List<User> getUsers() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM User ORDER BY id ASC");
        List<User> users = query.list();
        session.close();
        return users;
    }

    public void deleteUser(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM User WHERE id=" + id).executeUpdate();
        transaction.commit();
        session.close();
    }
}
