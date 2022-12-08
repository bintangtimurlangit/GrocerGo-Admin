package com.admin.grocergo;

public class CourierModel {
    String courier_id, courier_name, courier_phone;

    public CourierModel(String courier_id, String courier_name, String courier_phone) {
        this.courier_id = courier_id;
        this.courier_name = courier_name;
        this.courier_phone = courier_phone;
    }

    public String getCourier_id() { return courier_id; }
    public String getCourier_name() { return courier_name; }
    public String getCourier_phone() { return courier_phone; }
}
