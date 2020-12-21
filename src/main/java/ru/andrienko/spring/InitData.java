package ru.andrienko.spring;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.andrienko.spring.exceptions.DBException;
import ru.andrienko.spring.models.User;
import ru.andrienko.spring.service.DBService;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class InitData {
    private Faker faker = new Faker(new Locale("ru"));

    @Autowired
    private DBService userService;

    @PostConstruct
    public void initData() throws DBException {
        for (int i = 0; i < 10; i++) {
            userService.addUser(new User(faker.funnyName().name(),faker.superhero().power()));
        }

    }
}
