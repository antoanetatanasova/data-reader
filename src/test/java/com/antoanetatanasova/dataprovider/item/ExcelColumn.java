package com.antoanetatanasova.dataprovider.item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to map a field to one or more possible column headers in an Excel file.
 * <p>
 * This annotation is used to indicate which column(s) in the Excel sheet correspond
 * to a particular field in a class. During Excel row-to-object mapping, the specified
 * column names are matched against the header row in the sheet.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * @ExcelColumn(names = {"Item Name", "Product Name"})
 * private String name;
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    /**
     * One or more possible header names that map to this field.
     */
    String[] names();
}
