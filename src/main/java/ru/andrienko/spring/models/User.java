package ru.andrienko.spring.models;

public class User {
    private long id;
    private String login;
    private String password;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
