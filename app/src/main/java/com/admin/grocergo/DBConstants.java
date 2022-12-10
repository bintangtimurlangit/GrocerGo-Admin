package com.admin.grocergo;

public class DBConstants {
    public static final String URL = "http://192.168.1.148/";
    public static final String ROOT_URL = URL + "grocergo/crud_api/courier/";

    // COURIER SECTION
    public static final String SERVER_GET_URL = ROOT_URL + "readData.php";
    public static final String SERVER_POST_URL = ROOT_URL + "createData.php";
    public static final String SERVER_PUT_URL = ROOT_URL + "updateData.php";
    public static final String SERVER_DELETE_URL = ROOT_URL + "deleteData.php";
}
