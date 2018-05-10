package jdbc;

import pojo.Book;
import pojo.Order;
import pojo.User;

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
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, "root", "root"); // TODO: Remove the hardcoded "root"
            String query = "{CALL add_user(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, user.getUsername());
            callableStmt.setString(2, user.getPassword());
            callableStmt.setString(3, user.getLastName());
            callableStmt.setString(4, user.getFirstName());
            callableStmt.setString(5, user.getEmail());
            callableStmt.setString(6, user.getPhoneNumber());
            callableStmt.setString(7, user.getShippingAddress());
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void logIn(String username, String password) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            currentUser = username;
            userPassword = password;
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void logOut() {
        currentUser = null;
        userPassword = null;
    }

    @Override
    public void editInfo(User user) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, this.currentUser, userPassword);
            if (!user.getPassword().equals(userPassword)) { // User needs to change his password (saved in mysql.user table)
                String query = "{CALL edit_user_password(?, ?)}";
                CallableStatement callableStmt = conn.prepareCall(query);
                callableStmt.setString(1, user.getUsername());
                callableStmt.setString(2, user.getPassword());
                callableStmt.execute();
            }
            String query = "{CALL edit_user_info(?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, user.getUsername());
            callableStmt.setString(2, user.getLastName());
            callableStmt.setString(3, user.getFirstName());
            callableStmt.setString(4, user.getEmail());
            callableStmt.setString(5, user.getPhoneNumber());
            callableStmt.setString(6, user.getShippingAddress());
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
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
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL promote_user(?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, promotedUsername);
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void addNewBook(Book book) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL add_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, book.getISBN());
            callableStmt.setString(2, book.getTitle());
            callableStmt.setInt(3, book.getPublisherID());
            callableStmt.setString(4, book.getGenre());
            callableStmt.setString(5, book.getPublicationYear());
            callableStmt.setInt(6, book.getSellingPrice());
            callableStmt.setInt(7, book.getStockQuantity());
            callableStmt.setInt(8, book.getMinQuantity());
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void modifyBook(Book book) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL modify_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, book.getISBN());
            callableStmt.setString(2, book.getTitle());
            callableStmt.setInt(3, book.getPublisherID());
            callableStmt.setString(4, book.getGenre());
            callableStmt.setString(5, book.getPublicationYear());
            callableStmt.setInt(6, book.getSellingPrice());
            callableStmt.setInt(7, book.getStockQuantity());
            callableStmt.setInt(8, book.getMinQuantity());
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public int placeOrder(Order order) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL place_order(?, ?, ?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setString(1, order.getISBN());
            callableStmt.setInt(2, order.getQuantity());
            callableStmt.registerOutParameter(3, Types.INTEGER);
            callableStmt.execute();
            return callableStmt.getInt(3);
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
            return 0;
        }
    }

    @Override
    public void confirmOrder(int orderID) throws DBException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, currentUser, userPassword);
            String query = "{CALL confirm_order(?)}";
            CallableStatement callableStmt = conn.prepareCall(query);
            callableStmt.setInt(1, orderID);
            callableStmt.execute();
        } catch (SQLException e) {
            throw new DBException(errorHandler.getError(e.getErrorCode()));
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }
}
