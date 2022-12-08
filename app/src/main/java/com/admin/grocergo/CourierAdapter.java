package com.admin.grocergo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {
    private List<CourierModel> courierListItems;
    private Context context;
    private ProgressDialog dialog;

    public CourierAdapter(List<CourierModel> courierListItems, Context context)  {
        this.courierListItems = courierListItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courier_id;
        public TextView courier_name;
        public TextView courier_phone;
        public CardView courierCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            courier_id = (TextView) itemView.findViewById(R.id.id);
            courier_name = (TextView) itemView.findViewById(R.id.name);
            courier_phone = (TextView) itemView.findViewById(R.id.phone);
            courierCardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_courier, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CourierModel listItem = courierListItems.get(position);
        holder.courier_id.setText(listItem.getCourier_id());
        holder.courier_name.setText(listItem.getCourier_name());
        holder.courier_phone.setText(listItem.getCourier_phone());

        holder.courierCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogitem = {"View Data","Edit Data","Delete Data"};
                builder.setTitle(listItem.getCourier_name());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(view.getContext(), CourierDetailData.class);
                                intent.putExtra("id", listItem.getCourier_id());
                                intent.putExtra("name",listItem.getCourier_name());
                                intent.putExtra("phone", listItem.getCourier_phone());
                                view.getContext().startActivity(intent);
                                break;

                            case 1 :
                                Intent intent2 = new Intent(view.getContext(), CourierEditData.class);
                                intent2.putExtra("id", listItem.getCourier_id());
                                intent2.putExtra("name",listItem.getCourier_name());
                                intent2.putExtra("phone", listItem.getCourier_phone());
                                view.getContext().startActivity(intent2);
                                break;

                            case 2 :
                                AlertDialog.Builder builderDel = new AlertDialog.Builder(view.getContext());
                                builderDel.setTitle(listItem.getCourier_name());
                                builderDel.setMessage("Are You Sure, You Want to Delete Data?");
                                builderDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.show();

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_COURIER, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                dialog.hide();
                                                dialog.dismiss();
                                                Toast.makeText(view.getContext(),"Successfully Deleted Data "+ listItem.getCourier_name(), Toast.LENGTH_LONG).show();
                                                Courier.ma.courierRefreshList();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                dialog.hide();
                                                dialog.dismiss();
                                            }
                                        }){
                                            protected HashMap<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                params.put("id", listItem.getCourier_id());

                                                return (HashMap<String, String>) params;
                                            }
                                        };
                                        RequestHandler.getInstance(view.getContext()).addToRequestQueue(stringRequest);
                                        dialogInterface.dismiss();
                                    }
                                });

                                builderDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });

                                builderDel.create().show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courierListItems.size();
    }
}
