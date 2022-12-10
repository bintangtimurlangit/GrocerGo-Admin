package com.admin.grocergo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.idCourierHolder.setText(arrayList.get(position).getCourier_id());
        holder.nameCourierHolder.setText(arrayList.get(position).getCourier_name());
        holder.phoneCourierHolder.setText(arrayList.get(position).getCourier_phone());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idCourierHolder, nameCourierHolder, phoneCourierHolder;

        public MyViewHolder(View itemView) {
            super(itemView);
            idCourierHolder = (TextView) itemView.findViewById(R.id.courier_id_text);
            nameCourierHolder = (TextView) itemView.findViewById(R.id.courier_name_text);
            phoneCourierHolder = (TextView) itemView.findViewById((R.id.courier_phone_text));
        }
    }
}