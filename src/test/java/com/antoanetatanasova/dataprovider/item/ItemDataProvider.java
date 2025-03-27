package com.antoanetatanasova.dataprovider.item;

import com.antoanetatanasova.dto.Item;
import org.testng.annotations.DataProvider;
import com.antoanetatanasova.utls.ConfigReader;

import java.io.IOException;

public class ItemDataProvider extends CoreDataProvider {
    /**
     * Provides item data from an Excel file for use in data-driven tests.
     * <p>
     * This method reads item records from the Excel file specified in the configuration
     * property {@code files.itemXLSX}, maps each row to an {@link Item} object, and returns
     * the data as an {@code Object[]} for use with a test framework like TestNG.
     *
     * @return an array of {@link Item} objects loaded from the Excel file
     * @throws IOException if an error occurs while reading the Excel file
     */
    @DataProvider
    public static Object[] itemData() throws IOException {
        return getDataFromExcel(ConfigReader.fetchProperty("files.itemXLSX"), Item.class, new Item[0]);
    }
}
