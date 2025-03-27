package com.antoanetatanasova;

import com.antoanetatanasova.dataprovider.item.ItemDataProvider;
import com.antoanetatanasova.dto.Item;
import org.testng.annotations.Test;

public class ItemDataTest {

    @Test(dataProvider = "itemData", dataProviderClass = ItemDataProvider.class)
    public void itemDataTransferTest(Item item) {
        System.out.println(item.toString());
    }
}
