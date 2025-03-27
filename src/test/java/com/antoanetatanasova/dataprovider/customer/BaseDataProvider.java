package com.antoanetatanasova.dataprovider.customer;

import com.antoanetatanasova.dto.Customer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class BaseDataProvider {
    protected static final int EXCEL_TITLE_ROW_NUM = 0;
    protected static final String EXCEL_SHEET = "Sheet1";

    /**
     * Checks whether a given Excel cell is considered empty.
     * <p>
     * A cell is considered empty if:
     * <ul>
     *   <li>It is {@code null}</li>
     *   <li>Its type is {@code CellType.BLANK}</li>
     *   <li>It is of type {@code STRING} and the content is empty or whitespace</li>
     * </ul>
     * For other cell types (e.g., numeric, boolean, formula), the cell is treated as non-empty.
     *
     * @param cell the Excel {@link Cell} to check
     * @return {@code true} if the cell is empty; {@code false} otherwise
     */
    protected static Boolean isCellEmpty(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim().isEmpty();
        }

        return false;
    }

    /**
     * Retrieves the column title from a specified column index in the title row of the Excel sheet.
     * <p>
     * This method accesses the row defined by {@code EXCEL_TITLE_ROW_NUM} and returns the trimmed
     * string value of the cell at the given column index. If the row or cell is {@code null},
     * an empty string is returned.
     *
     * @param sheet    the Excel sheet to extract the column title from
     * @param colIndex the zero-based index of the column
     * @return the trimmed column title, or an empty string if not found
     */
    protected static String getColumnTitle(XSSFSheet sheet, int colIndex) {
        Row titleRow = sheet.getRow(EXCEL_TITLE_ROW_NUM);
        return (titleRow != null && titleRow.getCell(colIndex) != null) ? titleRow.getCell(colIndex).getStringCellValue().trim() : "";
    }


    /**
     * Returns the string representation of a cell's value, trimmed of leading and trailing whitespace.
     * <p>
     * If the cell is {@code null}, an empty string is returned. Otherwise, the method calls
     * {@code toString()} on the cell, which may return the cell's content in a general format,
     * depending on the cell type.
     *
     * @param cell the Excel {@link Cell} to extract the value from
     * @return the trimmed string representation of the cell's value, or an empty string if the cell is {@code null}
     */
    protected static String getCellStringValue(Cell cell) {
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
    protected static String formatValue(String value) {
        if (value.matches("\\d*\\.0")) {
            int dotIndex = value.indexOf(".");
            if (dotIndex != -1) {
                value = value.substring(0, dotIndex);
            }
        }
        return value;
    }

    /**
     * Sets the appropriate field on a {@link Customer} object based on the provided column title and value.
     * <p>
     * This method checks if the input value is not {@code null} or empty, formats it using {@code formatValue()},
     * and then maps it to the correct field in the {@code Customer} object based on the given column title.
     * <p>
     * It supports multiple variants of column titles (including those with invisible characters like zero-width space).
     *
     * @param customer    the {@link Customer} object to populate
     * @param columnTitle the column title from the Excel sheet (in Bulgarian or English)
     * @param value       the cell value to set in the corresponding field of the customer
     */
    public static void setCellValue(Customer customer, String columnTitle, String value) {
        if (value != null && !value.isEmpty()) {
            value = formatValue(value);
            switch (columnTitle) {
                case ("Customer Name\u200B"):
                case ("Customer Name"): {
                    customer.setName(value);
                    break;
                }
                case ("Address\u200B"):
                case ("Address"): {
                    customer.setAddress(value);
                    break;
                }
                case ("City\u200B"):
                case ("City"): {
                    customer.setCity(value);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
}
