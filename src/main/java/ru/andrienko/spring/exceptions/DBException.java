package ru.andrienko.spring.exceptions;

public class DBException extends Exception {
    public DBException(Throwable e){
        super(e);
    }
    public DBException(String message) {
        super(message);
    }
}