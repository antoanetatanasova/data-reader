package com.antoanetatanasova.dataprovider._example;

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

public class ExcelDataProvider {
    @DataProvider
    public static Object[][] excelData() throws IOException {
        return getExcelData(ConfigReader.fetchProperty("files.customerCSV"));
    }

    public static String[][] getExcelData(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        //Create an object of FileInputStream class to read Excel file
        try (FileInputStream inputStream = new FileInputStream(filePath);
             //creating workbook instance that refers to .xls file
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            //creating a Sheet object
            XSSFSheet sheet = workbook.getSheetAt(0);
            //get all rows in the sheet
            int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            //iterate over all the row to print the data present in each cell.
            for (int i = 1; i <= rowCount; i++) { // Start from 1 to skip header row
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //get cell count in a row
                int cellCount = row.getLastCellNum();

                //iterate over each cell to collect its value
                String[] fields = new String[cellCount];

                for (int j = 0; j < cellCount; j++) {
                    Cell cell = row.getCell(j);
                    fields[j] = cell == null ? "" : cell.getStringCellValue();
                }
                records.add(fields);
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw e;
        }

        return records.toArray(new String[0][]);
    }
}
