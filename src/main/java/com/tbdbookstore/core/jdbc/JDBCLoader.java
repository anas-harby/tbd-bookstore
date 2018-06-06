package com.tbdbookstore.core.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JDBCLoader {

    private static String driver;
    private static String dbURL;
    private static String adminUsername;
    private static String adminPassword;
    private static ErrorHandler errorHandler;

    static {
        setProperties();
        errorHandler = ErrorHandler.getInstance();
    }

    public static String getDriver() { return driver; }

    public static String getDbURL() { return dbURL; }

    public static String getAdminUsername() { return adminUsername; }

    public static String getAdminPassword() { return adminPassword; }

    public static ErrorHandler getErrorHandler() { return errorHandler; }

    private static void setProperties() {
        // TODO: Log errors
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            driver = prop.getProperty("driver");
            dbURL = prop.getProperty("dbURL");
            adminUsername = prop.getProperty("admin-username");
            adminPassword = prop.getProperty("admin-password");
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
