package com.tbdbookstore.core.jdbc;

import com.tbdbookstore.core.pojo.Book;
import com.tbdbookstore.core.pojo.User;
import com.tbdbookstore.core.shared.Error;

import java.util.HashMap;

public class JDBCMain {
    public static void main(String[] args) {
        try {
//            JDBCController headController = JDBCController.logIn("head", "head");
//            headController.deleteBook("12345678book3");
//            headController.deleteOrder(499);
//            Book bought1 = new Book("12345678book3");
//            bought1.setStockQuantity(95); // 5 in log
//            Book bought2 = new Book("12345678book2");
//            bought2.setStockQuantity(200); // 50 in log
//            HashMap<String, Book> bought = new HashMap<>();
//            bought.put("12345678book3", bought1);
//            bought.put("12345678book2", bought2);
            //headController.checkOut(bought);

            User user = new User("new1");
            user.setPassword("new");
            user.setLastName("Last");
            user.setFirstName("First");
            user.setEmail("new@gmail.com");
            user.setPhoneNumber("01001010101");
            user.setShippingAddress("Address, City, Country");
            JDBCController userController = JDBCController.signUp(user);
//
////            JDBCController managerController = JDBCController.logIn("head", "head");
////            managerController.promoteUser(user.getUsername());
////            managerController = null; // Manager logs out, ViewController should null the managerController object here
//
//            JDBCController userController = JDBCController.logIn(user.getUsername(), user.getPassword());
//            System.out.println(userController.getUserInfo("new2").getRole());
//
////            user.setPassword("newpass");
////            user.setLastName("Last Modified");
////            userController.editUserInfo(user);
//
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
//            //userController.addNewBook(book);
//            headController.addNewBook(book);
////
////            book.setTitle("Title3Modified");
////            userController.modifyBook(book);
////
////            Order order = new Order("12345678book2", 50);
////            int orderID = userController.placeOrder(order);
////            userController.confirmOrder(orderID);
//
//            //Book searchBook = new Book("12345678book2");
//            Book searchBook = new Book("0-00-019803-X");
//            //searchBook.addAuthor("Author3");
//            searchBook.setAuthors(null);
//            HashMap<String, Book> books = userController.search(searchBook, 0, 50);
//            System.out.println(books.size());
//            searchBook = books.get("0-00-019803-X");
//            System.out.println(searchBook.getTitle());
//            System.out.println(searchBook.getAuthors().size());
//            System.out.println(searchBook.getGenre());
//            System.out.println(searchBook.getPublisher());
//            System.out.println(searchBook.getPublicationYear());
//            System.out.println(searchBook.getSellingPrice());
//            System.out.println(searchBook.getStockQuantity());
//            System.out.println(searchBook.getMinQuantity());
//
//            System.out.println();
//            Order order = userController.getOrders().get(0);
//            System.out.println(order.getId() + " " + order.getISBN() + " " + order.getQuantity());

        } catch (DBException e) {
            System.out.println(e.getError());
        }
    }
}
