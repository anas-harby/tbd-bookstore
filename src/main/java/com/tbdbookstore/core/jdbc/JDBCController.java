package com.tbdbookstore.core.jdbc;


import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import com.tbdbookstore.core.pojo.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class JDBCController implements Connector {

    private String driver;
    private String dbURL;
    private String currentUser;
    private String userPassword;

    private ErrorHandler errorHandler;

    public JDBCController() {
        setProperties();
        errorHandler = ErrorHandler.getInstance();
        try { // TODO: Replace when using DataSource for Connection Pooling
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void main() throws DBException {
        String sql = "CREATE USER 'new'@'localhost' IDENTIFIED BY 'new';";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void signUp(User user) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, "root", "root"); // TODO: Remove the hardcoded "root"
            String query = "{CALL add_user(?, ?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getShippingAddress());
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void logIn(String username, String password) throws DBException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            currentUser = username;
            userPassword = password;
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(null, connection);
        }
    }

    @Override
    public void logOut() {
        currentUser = null;
        userPassword = null;
    }

    @Override
    public void editInfo(User user) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, this.currentUser, userPassword);
            if (!user.getPassword().equals(userPassword)) { // User needs to change his password (saved in mysql.user table)
                String query = "{CALL edit_user_password(?, ?)}";
                statement = connection.prepareCall(query);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.execute();
            }
            String query = "{CALL edit_user_info(?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getShippingAddress());
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public List<Book> search(Book book) throws DBException {
        return null;
    }

    @Override
    public void checkOut(int creditCardNum, String expiryDate) throws DBException {

    }

    @Override
    public void promoteUser(String promotedUsername) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL promote_user(?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, promotedUsername);
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void addNewBook(Book book) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL add_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, book.getISBN());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getPublisherID());
            statement.setString(4, book.getGenre());
            statement.setString(5, book.getPublicationYear());
            statement.setInt(6, book.getSellingPrice());
            statement.setInt(7, book.getStockQuantity());
            statement.setInt(8, book.getMinQuantity());
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void modifyBook(Book book) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL modify_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, book.getISBN());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getPublisherID());
            statement.setString(4, book.getGenre());
            statement.setString(5, book.getPublicationYear());
            statement.setInt(6, book.getSellingPrice());
            statement.setInt(7, book.getStockQuantity());
            statement.setInt(8, book.getMinQuantity());
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public int placeOrder(Order order) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL place_order(?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, order.getISBN());
            statement.setInt(2, order.getQuantity());
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            return statement.getInt(3);
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void confirmOrder(int orderID) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL confirm_order(?)}";
            statement = connection.prepareCall(query);
            statement.setInt(1, orderID);
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    private void setProperties() {
        // TODO: Log errors
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            driver = prop.getProperty("driver");
            dbURL = prop.getProperty("dbURL");
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

    private void cleanUpResources(CallableStatement statement, Connection connection) {
        if (statement != null) try {
            statement.close();
        } catch (SQLException ignored) {}
        if (connection != null) try {
            connection.close();
        } catch (SQLException ignored) {}
    }
}
