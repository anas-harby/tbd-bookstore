package jdbc;

import pojo.Book;
import pojo.Order;
import pojo.User;

import java.util.List;

public interface Connector {

    /* Normal user
      ------------ */

    // load homepage -> select

    void signUp(User user) throws DBException;

    void logIn(String username, String password) throws DBException;

    void logOut();

    // get user info

    void editInfo(User user) throws DBException;

    List<Book> search(Book book) throws DBException;

    void checkOut(int creditCardNum, String expiryDate) throws DBException;

    /* Manager Extra
      -------------- */

    void promoteUser(String promotedUsername) throws DBException;

    void addNewBook(Book book) throws DBException;

    void modifyBook(Book book) throws DBException;

    int placeOrder(Order order) throws DBException;

    void confirmOrder(int orderID) throws DBException;

    // view orders

    /* view reports */
}