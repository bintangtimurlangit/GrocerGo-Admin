package com.admin.grocergo;

public class DBConstants {
    public static final String URL = "http://192.168.31.69/";
    public static final String ROOT_URL_TRACKING = URL + "grocergo/crud_api/tracking/";
    public static final String ROOT_URL_COURIER = URL + "grocergo/crud_api/courier/";
    public static final String ROOT_URL_SUPPLIER = URL + "grocergo/crud_api/supplier/";
    public static final String ROOT_URL_STOCK = URL + "grocergo/crud_api/stock/";
    public static final String ROOT_URL_SUPPLYSTOCK = URL + "grocergo/crud_api/supplystock/";

    // TRACKING
    public static final String SERVER_GET_URL_TRACKING_DELIV = ROOT_URL_TRACKING + "readDataDelivered.php";
    public static final String SERVER_POST_URL_TRACKING_DELIV = ROOT_URL_TRACKING + "createDataDelivered.php";
    public static final String SERVER_PUT_URL_TRACKING_DELIV = ROOT_URL_TRACKING + "updateDataDelivered.php";
    public static final String SERVER_DELETE_URL_TRACKING_DELIV = ROOT_URL_TRACKING + "deleteDataDelivered.php";

    public static final String SERVER_GET_URL_TRACKING_ONGOING = ROOT_URL_TRACKING + "readDataOngoing.php";
    public static final String SERVER_POST_URL_TRACKING_ONGOING = ROOT_URL_TRACKING + "createDataOngoing.php";
    public static final String SERVER_PUT_URL_TRACKING_ONGOING = ROOT_URL_TRACKING + "updateDataOngoing.php";
    public static final String SERVER_DELETE_URL_TRACKING_ONGOING = ROOT_URL_TRACKING + "deleteDataOngoing.php";

    public static final String SERVER_GET_URL_TRACKING_PREP = ROOT_URL_TRACKING + "readDataPreparing.php";
    public static final String SERVER_POST_URL_TRACKING_PREP = ROOT_URL_TRACKING + "createDataPreparing.php";
    public static final String SERVER_PUT_URL_TRACKING_PREP = ROOT_URL_TRACKING + "updateDataPreparing.php";
    public static final String SERVER_DELETE_URL_TRACKING_PREP = ROOT_URL_TRACKING + "deleteDataPreparing.php";

    // COURIER SECTION
    public static final String SERVER_GET_URL_COURIER = ROOT_URL_COURIER + "readData.php";
    public static final String SERVER_POST_URL_COURIER = ROOT_URL_COURIER + "createData.php";
    public static final String SERVER_PUT_URL_COURIER = ROOT_URL_COURIER + "updateData.php";
    public static final String SERVER_DELETE_URL_COURIER = ROOT_URL_COURIER + "deleteData.php";

    // SUPPLY SECTION
    public static final String SERVER_GET_URL_SUPPLIER = ROOT_URL_SUPPLIER + "readData.php";
    public static final String SERVER_PUT_URL_SUPPLIER = ROOT_URL_SUPPLIER + "updateData.php";

    // STOCK SECTION
    public static final String SERVER_GET_URL_STOCK = ROOT_URL_STOCK + "readData.php";
    public static final String SERVER_PUT_URL_STOCK = ROOT_URL_STOCK + "updateData.php";

    // SUPPLY & STOCK SECTION
    public static final String SERVER_GET_URL_SUPPLYSTOCK = ROOT_URL_SUPPLYSTOCK + "readData.php";
    public static final String SERVER_POST_URL_SUPPLYSTOCK = ROOT_URL_SUPPLYSTOCK + "createData.php";
    public static final String SERVER_PUT_URL_SUPPLYSTOCK = ROOT_URL_SUPPLYSTOCK + "updateData.php";
    public static final String SERVER_DELETE_URL_SUPPLYSTOCK = ROOT_URL_SUPPLYSTOCK + "deleteData.php";
}
