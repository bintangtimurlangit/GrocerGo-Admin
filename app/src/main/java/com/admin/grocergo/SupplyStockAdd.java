package com.admin.grocergo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.z2wenfa.spinneredittext.SpinnerEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SupplyStockAdd extends AppCompatActivity {
    String SUPPLYSTOCK_DATA_JSON_STRING, supplystock_data_json_string;
    String supplier_id, supplier_name, supplier_email;
    String item_id, item_name, item_stock, item_price;
    EditText supplierName, supplierEmail, itemId, itemName, itemStock, itemPrice;
    TextView itemImageName;
    Button addNewInvButton, cancelAddInvButton, uploadInvImageButton;
    Boolean valid = true;
    ProgressDialog progressDialog;
    ArrayList<SupplyStockModel> supplyStockArrayList = new ArrayList<>();
    ArrayList<SupplierAltModel> supplierArrayList = new ArrayList<>();
    SpinnerEditText<ArrayList> supplierId;

    int countData;
    String encodedImage;
    ImageView itemImageView;

//    int getFileNameIndex;
//    String getFileName;

    // Image Upload Initialization
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_stock_add);

        supplierName = (EditText) findViewById(R.id.supplierAdd_name);
        supplierEmail = (EditText) findViewById(R.id.supplierAdd_email);
        itemId = (EditText) findViewById(R.id.itemAdd_id);
        itemName = (EditText) findViewById(R.id.itemAdd_name);
        itemStock = (EditText) findViewById(R.id.itemAdd_stock);
        itemPrice = (EditText) findViewById(R.id.itemAdd_price);
        itemImageName = (TextView) findViewById(R.id.itemImage_name);
        itemImageView = (ImageView) findViewById(R.id.itemImageView);

        uploadInvImageButton = (Button) findViewById(R.id.uploadBtn);
        addNewInvButton = (Button) findViewById(R.id.insertBtn);
        cancelAddInvButton = (Button) findViewById(R.id.cancelBtn);

        itemImageName.setText("Image Name: ");

        addNewInvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplier_id = supplierId.getText().toString();
                supplier_name = supplierName.getText().toString();
                supplier_email = supplierEmail.getText().toString();
                item_id = itemId.getText().toString();
                item_name = itemName.getText().toString();
                item_stock = itemStock.getText().toString();
                item_price = itemPrice.getText().toString();

                if(TextUtils.isEmpty(supplier_id)) {
                    supplierId.setError("Supplier ID cannot be empty!");
                    valid = false;
                } else {
                    valid = true;
                    if (TextUtils.isEmpty(supplier_name)) {
                        supplierName.setError("Supplier name cannot be empty!");
                        valid = false;
                    } else {
                        valid = true;
                        if (TextUtils.isEmpty(supplier_email)) {
                            supplierEmail.setError("Supplier email cannot be empty!");
                            valid = false;
                        } else {
                            valid = true;
                            if (TextUtils.isEmpty(item_id)) {
                                itemId.setError("Item ID cannot be empty!");
                                valid = false;
                            } else {
                                valid = true;
                                if (TextUtils.isEmpty(item_name)) {
                                    itemName.setError("Item name cannot be empty!");
                                    valid = false;
                                } else {
                                    valid = true;
                                    if (TextUtils.isEmpty(item_stock)) {
                                        itemName.setError("Item stock cannot be empty!");
                                        valid = false;
                                    } else {
                                        valid = true;
                                        if (TextUtils.isEmpty(item_price)) {
                                            itemPrice.setError("Item price cannot be empty!");
                                            valid = false;
                                        } else {
                                            valid = true;
//                                            if (getFileName == "") {
//                                                Toast.makeText(getApplicationContext(), "Please upload an image!", Toast.LENGTH_SHORT);
//                                                valid = false;
//                                            } else {
//                                                valid = true;
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(valid) {
                    // progressDialog.setMessage("Loading");
                    // progressDialog.show();
                    createSupplyStockData(supplier_id, supplier_name, supplier_email,
                            item_id, item_name, item_stock, item_price);
                }

                StringRequest request = new StringRequest(Request.Method.POST, DBConstants.SERVER_UPLOADIMG_URL_SUPPLYSTOCK
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(SupplyStockAdd.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SupplyStockAdd.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("item_id", item_id);
                        params.put("image", encodedImage);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(SupplyStockAdd.this);
                requestQueue.add(request);
            }
        });

        cancelAddInvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplyStockAdd.this, SupplyStock.class);
                startActivity(intent);
            }
        });

        uploadInvImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(SupplyStockAdd.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {}

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        // Read Data for Courier ID
        getJSON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readSupplierData();
            }
        }, 1000);

        initSupplierSpinnerID();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                itemImageView.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void createSupplyStockData(String supplier_id, String supplier_name, String supplier_email,
                                      String item_id, String item_name, String item_stock, String item_price) {
        if(checkNetworkConnection()) {
            // progressDialog.show();
            getJSON();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBConstants.SERVER_POST_URL_SUPPLYSTOCK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                getJSON();
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if(resp.equals("OK")) {
                                    getJSON();
                                    Toast.makeText(getApplicationContext(), "Data successfully inserted!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getJSON();
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getJSON();
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("supplier_id", supplier_id);
                    params.put("supplier_name", supplier_name);
                    params.put("supplier_email", supplier_email);
                    params.put("item_id", item_id);
                    params.put("item_name", item_name);
                    params.put("item_stock", item_stock);
                    params.put("item_price", item_price);
                    return params;
                }
            };

            VolleySingleton.getInstance(SupplyStockAdd.this).addToRequestQue(stringRequest);

            Intent intent = new Intent(SupplyStockAdd.this, SupplyStock.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "No connection to database!", Toast.LENGTH_SHORT).show();

        }
    }

    private void initSupplierSpinnerID() {
        supplierId = (SpinnerEditText<ArrayList>) findViewById(R.id.supplierSpinner_id);
        supplierId.setRightCompoundDrawable(com.z2wenfa.spinneredittext.R.drawable.vector_drawable_arrowdown);

        supplierId.setNeedShowSpinner(true);
        // supplierId.setList(supplierIDList);
        supplierId.setSelection(0);

    }

    public void getJSON() { new SupplyStockAdd.BackgroundTask().execute(); }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void readSupplierData() {
        if(checkNetworkConnection()) {
           supplierArrayList.clear();
            try {
                JSONObject object = new JSONObject(supplystock_data_json_string);
                JSONArray serverResponse = object.getJSONArray("server_response");

                for(int i = 0; i < serverResponse.length(); i++) {
                    JSONObject jsonObject = serverResponse.getJSONObject(i);
                    // supplierIDList.add(jsonObject.optString("supplier_id"));
                }

                String supplier_ol_id, supplier_ol_name, supplier_ol_email;

                while(countData < serverResponse.length()) {
                    JSONObject jsonObject = serverResponse.getJSONObject(countData);
                    supplier_ol_id = jsonObject.getString("supplier_id");
                    supplier_ol_name = jsonObject.getString("supplier_name");
                    supplier_ol_email = jsonObject.getString("supplier_email");

                    supplierArrayList.add(new SupplierAltModel(supplier_ol_id, supplier_ol_name, supplier_ol_email));
                    countData++;
                }
                countData = 0;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = DBConstants.SERVER_GET_URL_SUPPLYSTOCK;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((SUPPLYSTOCK_DATA_JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(SUPPLYSTOCK_DATA_JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            supplystock_data_json_string = result;
        }
    }
}
