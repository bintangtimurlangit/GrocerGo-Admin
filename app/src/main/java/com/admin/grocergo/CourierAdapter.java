package com.admin.grocergo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.MyViewHolder> {
    private ArrayList<CourierModel> arrayList = new ArrayList<>();

    public CourierAdapter(ArrayList<CourierModel> arrayList) { this.arrayList = arrayList; }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_courier, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CourierModel CourierArray = arrayList.get(position);

        holder.idCourierHolder.setText(CourierArray.getCourier_id());
        holder.nameCourierHolder.setText(CourierArray.getCourier_name());
        holder.phoneCourierHolder.setText(CourierArray.getCourier_phone());

        holder.courierCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogItem = {"Edit Data", "Delete Data"};
                builder.setTitle(CourierArray.getCourier_name());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                Intent intent = new Intent(v.getContext(), CourierEditData.class);
                                intent.putExtra("courier_id", CourierArray.getCourier_id());
                                intent.putExtra("courier_name", CourierArray.getCourier_name());
                                intent.putExtra("courier_phone", CourierArray.getCourier_phone());
                                v.getContext().startActivity(intent);
                                break;

                            case 1:
                                AlertDialog.Builder builderCourierDelete = new AlertDialog.Builder(v.getContext());
                                builderCourierDelete.setTitle(CourierArray.getCourier_name());
                                builderCourierDelete.setMessage("Are you sure, you want to delete data?");
                                builderCourierDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent2 = new Intent(v.getContext(), CourierDeleteData.class);
                                        intent2.putExtra("courier_id", CourierArray.getCourier_id());
                                        v.getContext().startActivity(intent2);
                                    }
                                });

                                builderCourierDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builderCourierDelete.create().show();
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
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idCourierHolder, nameCourierHolder, phoneCourierHolder;
        CardView courierCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            idCourierHolder = (TextView) itemView.findViewById(R.id.courier_id_text);
            nameCourierHolder = (TextView) itemView.findViewById(R.id.courier_name_text);
            phoneCourierHolder = (TextView) itemView.findViewById((R.id.courier_phone_text));

            courierCardView = (CardView) itemView.findViewById(R.id.courierCardView);
        }
    }
}