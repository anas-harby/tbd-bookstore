package com.tbdbookstore.core.jasperreports;

public interface JasperReport {

    public enum Format{
        PDF,
        HTML,
        XML,
        XLS

    }
    void compileReport(String jrxmlPath);

    void fillReport(String jasperPath);

    void viewReport();

    void exportReport(Format format, String outputFileName);
}
