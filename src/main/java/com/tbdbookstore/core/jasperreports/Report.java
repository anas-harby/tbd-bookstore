package com.tbdbookstore.core.jasperreports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import java.util.HashMap;
import java.util.Map;

public class Report implements JasperReport {

    String printFileName;
    String jasperSource;

    public Report(String jrxmlPath) {
        compileReport(jrxmlPath);
    }

    @Override
    public void compileReport(String jrxmlPath) {
        try {
            /**
             * Compile the report to a file name same as
             * the JRXML file name
             */
            JasperCompileManager.compileReportToFile(jrxmlPath);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fillReport(String jasperPath) {


        Map parameters = new HashMap();
        printFileName = JasperFillManager.fillReportToFile(jasperSource, parameters,  );

    }

    @Override
    public void exportReport(Format format, String outputFileName) {
        try {
            switch (format) {
                /**
                 * export to PDF
                 */
                case PDF:
                    JasperExportManager.exportReportToPdfFile(printFileName, outputFileName + ".pdf");
                    break;

                /**
                 * export to Excel sheet
                 */
                case XLS:
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, printFileName);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName + ".xls");
                    exporter.exportReport();
                    break;
                /**
                 * export to HTML
                 */
                case HTML:
                    JasperExportManager.exportReportToHtmlFile(printFileName, outputFileName + ".html");

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

    }
}
