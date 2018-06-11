package com.tbdbookstore.core.util;

import com.tbdbookstore.core.pojo.Book;

import java.util.ArrayList;

public class BookSearchProcessor {
    public static Book process(String searchText) {
        Book ret = new Book(null);
        ret.setAuthors(null);
        if (searchText.trim().isEmpty())
            return ret;

        String[] entries = searchText.trim().split(",");

        for (String entry : entries) {
            if (entry.contains(":")) {
                String[] pair = entry.split(":");
                if (pair.length != 2)
                    continue;
                String key = pair[0].trim();
                String val = pair[1].trim();
                processUtil(key, val, ret);
            } else {
                if (isNumeric(entry.trim()) && entry.trim().length() == 13)
                    ret.setISBN(entry.trim());
                else
                    ret.setTitle(entry.trim());
            }
        }
        System.out.println(ret);
        return ret;
    }

    private static void processUtil(String key, String val, Book book) {
        switch (key) {
            case "isbn":
                book.setISBN(val);
                break;
            case "title":
                book.setTitle(val);
                break;
            case "publisher":
                book.setPublisher(val);
                break;
            case "genre":
                book.setGenre(val);
                break;
            case "author":
                if (book.getAuthors() == null)
                    book.setAuthors(new ArrayList<>());
                book.addAuthor(val);
            default:
                break;
        }
    }

    private static boolean isNumeric(String s) {
        for (char x : s.toCharArray())
            if (!Character.isDigit(x))
                return false;
        return true;
    }
}
