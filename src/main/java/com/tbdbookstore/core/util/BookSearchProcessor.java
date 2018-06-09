package com.tbdbookstore.core.util;

import com.tbdbookstore.core.pojo.Book;

public class BookSearchProcessor {
    public static Book process(String searchText) {
        Book ret = new Book(null);
        ret.setAuthors(null);
        if (searchText.trim().isEmpty())
            return ret;
        // "key1:val1 | key2:val2  | key3:val31 val32"
        if (isNumeric(searchText.trim()) && searchText.trim().length() == 13) {
            ret.setISBN(searchText.trim());
        } else {
            ret.setTitle(searchText);
        }
        return ret;
    }

    private static boolean isNumeric(String s) {
        for (char x : s.toCharArray())
            if (!Character.isDigit(x))
                return false;
        return true;
    }
}
