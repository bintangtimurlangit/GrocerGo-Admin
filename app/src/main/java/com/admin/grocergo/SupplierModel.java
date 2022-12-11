package com.admin.grocergo;

public class SupplierModel {
    String supplier_id, supplier_name, supplier_email, item_id, item_name;

    SupplierModel(String supplier_id, String supplier_name, String supplier_email, String item_id, String item_name) {
        this.setSupplier_id(supplier_id);
        this.setSupplier_name(supplier_name);
        this.setSupplier_email(supplier_email);
        this.setItem_id(item_id);
        this.setItem_name(item_name);
    }

    public String getSupplier_id() { return supplier_id; }
    public String getSupplier_name() { return supplier_name; }
    public String getSupplier_email() { return supplier_email; }
    public String getItem_id() { return item_id; }
    public String getItem_name() {return item_name; }

    public void setSupplier_id(String supplier_id) { this.supplier_id = supplier_id; }
    public void setSupplier_name(String supplier_name) { this.supplier_name = supplier_name; }
    public void setSupplier_email(String supplier_email) { this.supplier_email = supplier_email; }
    public void setItem_id(String item_id) { this.item_id = item_id; }
    public void setItem_name(String item_name) { this.item_name = item_name; }
}
