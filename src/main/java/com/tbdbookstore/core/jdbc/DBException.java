package com.tbdbookstore.core.jdbc;

public class DBException extends Exception {

    private Error error;

    public DBException (Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
