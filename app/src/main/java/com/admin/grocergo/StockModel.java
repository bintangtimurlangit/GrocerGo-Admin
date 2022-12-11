package com.admin.grocergo;

public class StockModel {
    String item_id, item_name, item_stock, item_price, item_image_path, item_image;

    StockModel(String item_id, String item_name, String item_stock, String item_price, String item_image_path, String item_image) {
        this.setItem_id(item_id);
        this.setItem_name(item_name);
        this.setItem_stock(item_stock);
        this.setItem_price(item_price);
        this.setItem_image_path(item_image_path);
        this.setItem_image(item_image);
    }

    public String getItem_id() { return item_id; }
    public String getItem_name() { return item_name; }
    public String getItem_stock() { return item_stock; }
    public String getItem_price() { return item_price; }
    public String getItem_image_path() { return item_image_path; }
    public String getItem_image() { return item_image; }

    public void setItem_id(String item_id) { this.item_id = item_id; }
    public void setItem_name(String item_name) { this.item_name = item_name; }
    public void setItem_stock(String item_stock) { this.item_stock = item_stock; }
    public void setItem_price(String item_price) { this.item_price = item_price; }
    public void setItem_image_path(String item_image_path) { this.item_image_path = item_image_path; }
    public void setItem_image(String item_image) { this.item_image = item_image; }
}
