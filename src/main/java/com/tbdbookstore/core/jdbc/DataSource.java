package com.tbdbookstore.core.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private final int MAX_STATEMENTS = 200; // maximum number of cached statements in the pool

    private static DataSource dataSource = new DataSource();
    private ComboPooledDataSource comboPooledDataSource;

    public static DataSource getInstance() {
        return dataSource;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return comboPooledDataSource.getConnection(username, password);
    }

    private DataSource() {
        comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(JDBCLoader.getDriver());
        } catch (PropertyVetoException e) {
            System.out.println(e.getMessage());
        }
        comboPooledDataSource.setJdbcUrl(JDBCLoader.getDbURL());
        comboPooledDataSource.setMaxStatements(MAX_STATEMENTS);
    }
}
