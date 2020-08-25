package com.ukrainer.infostroy.config;

public class DBException extends Throwable{
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
    public DBException(Throwable cause){
        this("Sorry database not available.", cause);
    }
}
