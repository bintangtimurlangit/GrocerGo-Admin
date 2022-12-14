package com.admin.grocergo;

public class ImageModel {
    private String item_id, image_url;

    public ImageModel() {}
    public ImageModel(String item_id, String image_url) {
        this.item_id = item_id;
        this.image_url = image_url;
    }

    public String getItem_id() { return item_id; }
    public void setItem_id(String item_id) { this.item_id = item_id; }
    public String getImage_url() { return image_url; }
    public void setImage_url(String image_url) { this.image_url = image_url; }

}
