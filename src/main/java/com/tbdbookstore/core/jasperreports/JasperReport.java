package com.tbdbookstore.core.jasperreports;

import java.sql.Connection;

public interface JasperReport {

    public enum Format{
        PDF,
        HTML,
        XML,
        XLS

    }
    void compileReport(String jrxmlPath);

    void fillReport(String jasperPath,Connection connection);

    void viewReport();

    void exportReport(Format format, String outputFileName);
}
