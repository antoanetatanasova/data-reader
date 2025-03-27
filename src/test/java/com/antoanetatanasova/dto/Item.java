package com.antoanetatanasova.dto;

import com.antoanetatanasova.dataprovider.item.ExcelColumn;

public class Item {
    @ExcelColumn(names = {"Item Name\u200B", "Item Name"})
    private String name;
    @ExcelColumn(names = {"Unit Price\u200B", "Unit Price"})
    private double unitPrice;
    @ExcelColumn(names = {"Quantity\u200B", "Quantity"})
    private int quantity;
    @ExcelColumn(names = {"Unit of Measure\u200B", "Unit of Measure"})
    private String unit;
    @ExcelColumn(names = {"VAT Rate\u200B", "VAT Rate"})
    private String vatRate;
    @ExcelColumn(names = {"Account\u200B", "Account"})
    private String account;
    @ExcelColumn(names = {"Batch\u200B", "Batch"})
    private String batch;

    public Item() {
    }

    public Item(String name, double unitPrice, int quantity, String unit, String vatRate, String account, String batch) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.unit = unit;
        this.vatRate = vatRate;
        this.account = account;
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", vatRate='" + vatRate + '\'' +
                ", account='" + account + '\'' +
                ", batch='" + batch + '\'' +
                '}';
    }
}

