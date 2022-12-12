package com.admin.grocergo;

public class SupplierAltModel {
    String supplier_id, supplier_name, supplier_email, item_id, item_name;

    SupplierAltModel(String supplier_id, String supplier_name, String supplier_email) {
        this.setSupplier_id(supplier_id);
        this.setSupplier_name(supplier_name);
        this.setSupplier_email(supplier_email);
    }

    public void setSupplier_id(String supplier_id) { this.supplier_id = supplier_id; }
    public void setSupplier_name(String supplier_name) { this.supplier_name = supplier_name; }
    public void setSupplier_email(String supplier_email) { this.supplier_email = supplier_email; }
}
