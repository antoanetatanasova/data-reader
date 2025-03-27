package com.antoanetatanasova;

import com.antoanetatanasova.dataprovider.customer.CustomerExcelDataProvider;
import com.antoanetatanasova.dto.Customer;
import org.testng.annotations.Test;

public class CustomerDataTest {

    @Test(dataProvider = "customerData", dataProviderClass = CustomerExcelDataProvider.class)
    public void customerDataTransferTest(Customer customer) {
        System.out.println(customer.toString());
    }
}
