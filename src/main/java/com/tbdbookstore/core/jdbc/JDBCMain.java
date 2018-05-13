package com.tbdbookstore.core.jdbc;

import java.util.HashMap;

public class JDBCMain {
    public static void main(String[] args) {
        JDBCController controller = new JDBCController();
        try {
            //controller.main();
            controller.logIn("new", "new");
        } catch (DBException e) {
            System.out.println(e.getError());
        }
    }
}
