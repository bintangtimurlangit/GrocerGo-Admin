package com.admin.grocergo;

public class SupplyStockModel {
    String supplier_id, supplier_name, supplier_email;
    String item_id, item_name, item_stock, item_price;

    SupplyStockModel(String supplier_id, String supplier_name, String supplier_email,
                     String item_id, String item_name, String item_stock, String item_price) {
        this.setSupplier_id(supplier_id);
        this.setSupplier_name(supplier_name);
        this.setSupplier_email(supplier_email);
        this.setItem_id(item_id);
        this.setItem_name(item_name);
        this.setItem_stock(item_stock);
        this.setItem_price(item_price);
    }

    public String getSupplier_id() { return supplier_id; }
    public String getSupplier_name() { return supplier_name; }
    public String getSupplier_email() { return supplier_email; }
    public String getItem_id() { return item_id; }
    public String getItem_name() { return item_name; }
    public String getItem_stock() { return item_stock; }
    public String getItem_price() { return item_price; }

    public void setSupplier_id(String supplier_id) { this.supplier_id = supplier_id; }
    public void setSupplier_name(String supplier_name) { this.supplier_name = supplier_name; }
    public void setSupplier_email(String supplier_email) { this.supplier_email = supplier_email; }
    public void setItem_id(String item_id) { this.item_id = item_id; }
    public void setItem_name(String item_name) { this.item_name = item_name; }
    public void setItem_stock(String item_stock) { this.item_stock = item_stock; }
    public void setItem_price(String item_price) { this.item_price = item_price; }
}
