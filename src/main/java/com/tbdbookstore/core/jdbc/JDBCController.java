package com.tbdbookstore.core.jdbc;

import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import com.tbdbookstore.core.pojo.User;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCController implements Connector {

    private String username;
    private String password;

    private JDBCController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static JDBCController signUp(User user) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(JDBCLoader.getAdminUsername(), JDBCLoader.getAdminPassword());
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
            return new JDBCController(user.getUsername(), user.getPassword());
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    public static JDBCController logIn(String username, String password) throws DBException {
        Connection connection = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            return new JDBCController(username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(null, connection);
        }
    }

    @Override
    public User getUserInfo(String username) throws DBException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            statement = connection.prepareStatement("SELECT * FROM USER WHERE USERNAME = '" + username + "'");
            resultSet = statement.executeQuery();
            return getUser(resultSet);
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException ignored) {
            }
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void editUserInfo(User user) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            if (!user.getPassword().equals(password)) { // User needs to change his password (saved in mysql.user table)
                String query = "{CALL edit_user_password(?, ?)}";
                statement = connection.prepareCall(query);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.execute();
                password = user.getPassword();
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
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public HashMap<String, Book> search(Book book, int offset, int count) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            statement = connection.prepareStatement(buildSelectQuery(book, offset, count));
            resultSet = statement.executeQuery();
            return getBooks(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException ignored) {
            }
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void checkOut(List<Book> books) throws DBException {
        for (Book book: books)
            modifyBook(book);
    }

    @Override
    public void promoteUser(String promotedUsername) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            String query = "{CALL promote_user(?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, promotedUsername);
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void addNewBook(Book book) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            String query = "{CALL add_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, book.getISBN());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getPublisher());
            statement.setString(5, book.getPublicationYear());
            statement.setDouble(6, book.getSellingPrice());
            statement.setInt(7, book.getStockQuantity());
            statement.setInt(8, book.getMinQuantity());
            statement.execute();
            query = "{CALL add_book_author(?, ?)}";
            statement = connection.prepareCall(query);
            for (String author : book.getAuthors()) {
                statement.setString(1, book.getISBN());
                statement.setString(2, author);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void modifyBook(Book book) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            String query = "{CALL modify_book(?, ?, ?, ?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, book.getISBN());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getPublisher());
            statement.setString(5, book.getPublicationYear());
            statement.setDouble(6, book.getSellingPrice());
            statement.setInt(7, book.getStockQuantity());
            statement.setInt(8, book.getMinQuantity());
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public int placeOrder(Order order) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            String query = "{CALL place_order(?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, order.getISBN());
            statement.setInt(2, order.getQuantity());
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            return statement.getInt(3);
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public void confirmOrder(int orderID) throws DBException {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            String query = "{CALL confirm_order(?)}";
            statement = connection.prepareCall(query);
            statement.setInt(1, orderID);
            statement.execute();
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            cleanUpResources(statement, connection);
        }
    }

    @Override
    public List<Order> getOrders() throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getInstance().getConnection(username, password);
            statement = connection.prepareStatement("SELECT * FROM ORDER");
            resultSet = statement.executeQuery();
            return getOrders(resultSet);
        } catch (SQLException e) {
            throw new DBException(JDBCLoader.getErrorHandler().getError(e.getErrorCode()));
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException ignored) {
            }
            cleanUpResources(statement, connection);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User user  = new User(resultSet.getString("USERNAME"));
            user.setLastName(resultSet.getString("LAST_NAME"));
            user.setFirstName(resultSet.getString("FIRST_NAME"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setPhoneNumber(resultSet.getString("TELNO"));
            user.setShippingAddress(resultSet.getString("SHIPPING_ADDRESS"));
            return user;
        }
        throw new SQLException();
    }

    private HashMap<String, Book> getBooks(ResultSet resultSet) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        HashMap<String, Book> books = new HashMap<>();
        String bookISBN;
        Book book;
        while (resultSet.next()) {
            bookISBN = resultSet.getString("BOOK_ISBN");
            book = books.get(bookISBN);
            if (books.get(bookISBN) == null) {
                book = new Book(resultSet.getString("BOOK_ISBN"));
                book.setTitle(resultSet.getString("BOOK_TITLE"));
                book.addAuthor(resultSet.getString("AUTHOR_NAME"));
                book.setGenre(resultSet.getString("GENRE_NAME"));
                book.setPublisher(resultSet.getString("PUBLISHER_NAME"));
                book.setPublicationYear(dateFormat.format(resultSet.getDate("PUBLICATION_YEAR")));
                book.setSellingPrice(resultSet.getDouble("SELLING_PRICE"));
                book.setStockQuantity(resultSet.getInt("STOCK_QUANTITY"));
                book.setMinQuantity(resultSet.getInt("MIN_QUANTITY"));
                books.put(bookISBN, book);
            } else {
                book.addAuthor(resultSet.getString("AUTHOR_NAME"));
            }
        }
        return books;
    }

    private List<Order> getOrders(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order(resultSet.getString("BOOK_ISBN"), resultSet.getInt("QUANTITY"));
            order.setId(resultSet.getInt("ORDER_ID"));
            orders.add(order);
        }
        return orders;
    }

    private String buildSelectQuery(Book book, int offset, int count) {
        List<String> conditions = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT BOOK_ISBN, BOOK_TITLE, GENRE_NAME, AUTHOR_NAME, PUBLISHER_NAME"
                + ", PUBLICATION_YEAR, SELLING_PRICE, STOCK_QUANTITY, MIN_QUANTITY FROM BOOK NATURAL JOIN AUTHOR NATURAL JOIN PUBLISHER");
        if (book.getISBN() != null)
            conditions.add("BOOK_ISBN = " + "'" + book.getISBN() + "'");
        if (book.getTitle() != null)
            conditions.add("BOOK_TITLE = " + "'" + book.getTitle() + "'");
        if (book.getAuthors() != null)
            conditions.add("AUTHOR_NAME = " + "'" + book.getAuthors().get(0) + "'");
        if (book.getGenre() != null)
            conditions.add("GENRE_NAME = " + "'" + book.getGenre() + "'");
        if (book.getPublisher() != null)
            conditions.add("PUBLISHER_NAME = " + "'" + book.getPublisher() + "'");

        if (!conditions.isEmpty()) {
            int i = 0;
            query.append(" WHERE ").append(conditions.get(i++));
            while (i < conditions.size())
                query.append(" AND ").append(conditions.get(i++));
        }
        query.append(" LIMIT ").append(Integer.toString(offset)).append(", ").append(Integer.toString(count)).append(';');
        return query.toString();
    }

    private static void cleanUpResources(Statement statement, Connection connection) {
        if (statement != null) try {
            statement.close();
        } catch (SQLException ignored) {
        }
        if (connection != null) try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }
}