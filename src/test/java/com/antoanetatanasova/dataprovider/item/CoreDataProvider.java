package com.antoanetatanasova.dataprovider.item;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CoreDataProvider {
    private static final String EXCEL_SHEET = "Sheet1";

    /**
     * Creates records of the specified type from an Excel document.
     * <p>
     * This method reads data from an Excel file located at the provided {@code filePath},
     * maps each row (excluding the header) in the predefined sheet to an instance of the specified class {@code clazz},
     * and returns the resulting objects as an array.
     *
     * @param filePath      the path to the Excel document
     * @param clazz         the target class type to which each row of the Excel sheet will be mapped
     * @param arrayTemplate an array of the target type used as a template for the result conversion
     * @param <T>           the generic type of the object to be created from the Excel rows
     * @return an array of objects of type {@code T} populated with the data from the Excel file
     * @throws IOException if an I/O error occurs when reading the Excel file
     */
    public static <T> T[] getDataFromExcel(String filePath, Class<T> clazz, T[] arrayTemplate) throws IOException {
        List<T> records = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheet(EXCEL_SHEET);
            if (sheet != null) {
                int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

                for (int i = 1; i <= rowCount; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    T obj = mapRowToObject(sheet, row, clazz);
                    records.add(obj);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw e;
        }

        return records.toArray(arrayTemplate);
    }

    /**
     * Maps a row from an Excel sheet to an instance of the specified class using {@link ExcelColumn} annotations.
     * <p>
     * The method reads the header row to determine column names and maps the cell values in the given row
     * to fields in the target class that are annotated with {@code @ExcelColumn}. The mapping supports multiple
     * possible names for each column, as defined in the annotation.
     *
     * @param sheet the Excel sheet containing the data
     * @param row   the specific row to map to an object
     * @param clazz the class type to which the row should be mapped
     * @param <T>   the generic type of the object to be created
     * @return an instance of {@code T} populated with values from the row
     * @throws RuntimeException if any reflection or type conversion error occurs during mapping
     */
    private static <T> T mapRowToObject(XSSFSheet sheet, Row row, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Row headerRow = sheet.getRow(sheet.getFirstRowNum());

            if (headerRow == null) return instance;

            Map<String, Integer> headerIndexMap = new HashMap<>();
            IntStream.range(0, headerRow.getLastCellNum()).forEach(i -> {
                String header = getCellStringValue(headerRow.getCell(i));
                headerIndexMap.put(header, i);
            });

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    String[] columnNames = annotation.names();
                    Integer cellIndex = null;

                    for (String possibleName : columnNames) {
                        cellIndex = headerIndexMap.get(possibleName);
                        if (cellIndex != null) break;
                    }

                    if (cellIndex != null) {
                        Cell cell = row.getCell(cellIndex);
                        String cellValue = getCellStringValue(cell);
                        cellValue = formatValue(cellValue);

                        field.setAccessible(true);
                        Object value = convertToFieldType(field.getType(), cellValue);
                        field.set(instance, value);
                    }
                }
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map row to " + clazz.getSimpleName(), e);
        }
    }

    /**
     * Converts a string value to the specified field type.
     * <p>
     * This method supports common types including {@code String}, {@code int}/{@code Integer},
     * {@code double}/{@code Double}, {@code boolean}/{@code Boolean}, and {@code long}/{@code Long}.
     * If the type is not recognized, the original string value is returned.
     *
     * @param type  the target field type to convert the value to
     * @param value the string representation of the value
     * @return the converted value as an {@code Object} of the specified type
     * @throws NumberFormatException if the value cannot be parsed into a numeric type
     */
    private static Object convertToFieldType(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        return value;
    }

    /**
     * Retrieves the string value of a cell, trimmed of leading and trailing whitespace.
     * <p>
     * If the cell is {@code null}, an empty string is returned.
     *
     * @param cell the Excel cell to extract the value from
     * @return the trimmed string value of the cell, or an empty string if the cell is {@code null}
     */
    private static String getCellStringValue(Cell cell) {
        return (cell != null) ? cell.toString().trim() : "";
    }

    /**
     * Formats a string value by removing a trailing decimal part if it's ".0".
     * <p>
     * For example, a value like "123.0" will be converted to "123".
     * If the value does not match this pattern, it is returned unchanged.
     *
     * @param value the input string value to format
     * @return the formatted string with ".0" removed if present; otherwise, the original value
     */
    private static String formatValue(String value) {
        if (value.matches("\\d*\\.0")) {
            int dotIndex = value.indexOf(".");
            if (dotIndex != -1) {
                value = value.substring(0, dotIndex);
            }
        }
        return value;
    }
}
