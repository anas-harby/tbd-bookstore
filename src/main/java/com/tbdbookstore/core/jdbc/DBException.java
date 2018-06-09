package com.tbdbookstore.core.jdbc;

import com.tbdbookstore.core.shared.Error;

public class DBException extends Exception {

    private Error error;

    public DBException (Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
