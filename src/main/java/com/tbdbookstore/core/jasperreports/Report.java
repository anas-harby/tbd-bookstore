package com.tbdbookstore.core.jasperreports;

import java.sql.Connection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Report implements JasperReport {

    private JasperPrint print;
    private String jasperSource;

    public Report(String jrxmlPath, Connection connection) {
        compileReport(jrxmlPath);
        jasperSource = getFileName(jrxmlPath) + ".jasper";
        fillReport(jasperSource,connection);
    }

    @Override
    public void compileReport(String jrxmlPath) {
        try {
            /**
             * Compile the report to a file name same as
             * the JRXML file name
             */
            JasperCompileManager.compileReportToFile(jrxmlPath);
            System.out.println("Report Compilation Done !");
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fillReport(String jasperPath, Connection connection) {

        try {
            Map parameters = new HashMap();
            print = JasperFillManager.fillReport(jasperPath,parameters,connection);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exportReport(Format format, String outputFileName) {
        try {
            switch (format) {
                /**
                 * export to PDF
                 */
                case PDF:
                    JasperExportManager.exportReportToPdfFile(print, outputFileName + ".pdf");
                    break;

                /**
                 * export to Excel sheet
                 */
                case XLS:
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, print);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName + ".xls");
                    exporter.exportReport();
                    break;
                /**
                 * export to HTML
                 */
                case HTML:
                    JasperExportManager.exportReportToHtmlFile(print, outputFileName + ".html");

                    break;
                case XML:

                default:
                    break;

            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewReport() {
        JFrame frame = new JFrame("Sales Report");
        frame.getContentPane().add(new JRViewer(print));
        frame.pack();
        frame.setVisible(true);

    }

    private String getFileName(String fileName){
        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
        return tokens[0];
    }
}
