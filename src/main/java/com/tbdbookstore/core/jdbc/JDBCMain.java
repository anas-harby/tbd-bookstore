package com.tbdbookstore.core.jdbc;

import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.Order;
import com.tbdbookstore.core.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JDBCMain {
    public static void main(String[] args) {
        try {
            User user = new User("new1");
            user.setPassword("new");
            user.setLastName("Last");
            user.setFirstName("First");
            user.setEmail("new@gmail.com");
            user.setPhoneNumber("01001010101");
            user.setShippingAddress("Address, City, Country");
            //JDBCController controller = JDBCController.signUp(user);
//            JDBCController managerController = JDBCController.logIn("head", "head");
//            managerController.promoteUser(user.getUsername());
//            managerController = null; // Manager logs out, ViewController should null the managerController object here

            JDBCController userController = JDBCController.logIn("new1", "new");
            Book search = new Book("0-00-188745-9");
            search.setAuthors(null);
            HashMap<String, Book> hm = userController.search(search, 0, 10);
            System.out.println(hm);

//            user.setPassword("newpass");
//            user.setLastName("Last Modified");
//            userController.editUserInfo(user);

//            Book book = new Book("12345678book3");
//            List<String> authors = new ArrayList<>();
//            authors.add("Author2");
//            book.setTitle("Title3");
//            book.setAuthors(authors);
//            book.setGenre("History");
//            book.setPublisher("Al-Dar Al-Arabeya");
//            book.setPublicationYear("2000");
//            book.setSellingPrice(200);
//            book.setStockQuantity(120);
//            book.setMinQuantity(50);
//            userController.addNewBook(book);

//            book.setTitle("Title3Modified");
//            userController.modifyBook(book);
//
//            Order order = new Order("12345678book2", 50);
//            int orderID = userController.placeOrder(order);
//            userController.confirmOrder(orderID);


        } catch (DBException e) {
            System.out.println(e.getError());
        }
    }
}
