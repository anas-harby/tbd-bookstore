package com.tbdbookstore.core.jdbc;

import com.tbdbookstore.core.pojo.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public interface Connector {

    /* Normal user
      ------------ */
    Connection getConnection() throws DBException;

    User getUserInfo() throws DBException;

    void editUserInfo(User user) throws DBException;

    HashMap<String, Book> search(Book book, Ordering ordering, int offset, int count) throws DBException;

    void checkOut(HashMap<String, Book> books) throws DBException;

    /* Manager Extra
      -------------- */
    void promoteUser(String promotedUsername) throws DBException;

    void addNewBook(Book book) throws DBException;

    void modifyBook(Book book) throws DBException;

    void deleteBook(String ISBN) throws DBException;

    int placeOrder(Order order) throws DBException;

    void confirmOrder(int orderID) throws DBException;

    void deleteOrder(int orderID) throws DBException;

    List<Order> getOrders() throws DBException;
}