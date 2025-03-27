package com.antoanetatanasova.dataprovider._example;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataProvider {
    @DataProvider
    public static Object[][] csvData() throws IOException {
        return getCsvData("src/test/resources/data/customers.csv");
    }

    public static String[][] getCsvData(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader(filePath))) {
            String line = file.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                records.add(fields);
                line = file.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            throw e;
        }

        return records.toArray(new String[records.size()][]);
    }
}

