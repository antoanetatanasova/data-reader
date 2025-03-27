package com.antoanetatanasova.dataprovider.customer;

import com.antoanetatanasova.dto.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import com.antoanetatanasova.utls.ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerExcelDataProvider extends BaseDataProvider {
    @DataProvider
    public static Object[] customerData() throws IOException {
        return getData(ConfigReader.fetchProperty("files.customerXLSX"));
    }

    public static Customer[] getData(String filePath) throws IOException {
        List<Customer> records = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheet(EXCEL_SHEET);
            if (sheet != null) {
                processItemSheet(sheet, records);
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw e;
        }
        return records.toArray(new Customer[0]);
    }

    private static void processItemSheet(XSSFSheet sheet, List<Customer> records) {
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header row
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            Customer customer = createItemFromRow(sheet, row);
            records.add(customer);
        }
    }

    private static Customer createItemFromRow(XSSFSheet sheet, Row row) {
        Customer customer = new Customer();
        int cellCount = row.getLastCellNum();

        for (int j = 0; j < cellCount; j++) {
            Cell currCell = row.getCell(j);
            if (!isCellEmpty(currCell)) {
                String currValue = getCellStringValue(currCell);
                String columnTitle = getColumnTitle(sheet, j);
                setCellValue(customer, columnTitle, currValue);
            }
        }

        return customer;
    }
}
