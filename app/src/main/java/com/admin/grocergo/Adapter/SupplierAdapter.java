package com.admin.grocergo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.admin.grocergo.R;
import com.admin.grocergo.SupplierModel;

import java.util.ArrayList;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {
    private ArrayList<SupplierModel> supplyArrayList = new ArrayList<>();

    public SupplierAdapter(ArrayList<SupplierModel> supplyArrayList) { this.supplyArrayList = supplyArrayList; }

    @Override
    public SupplierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_supplier, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SupplierViewHolder holder, final int position) {
        final SupplierModel SupplierArray = supplyArrayList.get(position);

        holder.idSupplierHolder.setText(SupplierArray.getSupplier_id());
        holder.nameSupplierHolder.setText(SupplierArray.getSupplier_name());
        holder.emailSupplierHolder.setText(SupplierArray.getSupplier_email());
        holder.idItemHolder.setText(SupplierArray.getItem_name() + " - " + SupplierArray.getItem_id());
    }

    @Override
    public int getItemCount() { return supplyArrayList.size(); }

    public static class SupplierViewHolder extends RecyclerView.ViewHolder {
        TextView idSupplierHolder, nameSupplierHolder, emailSupplierHolder, idItemHolder;
        CardView supplierCardView;

        public SupplierViewHolder(View itemView) {
            super(itemView);
            idSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_id_text);
            nameSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_name_text);
            emailSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_email_text);
            idItemHolder = (TextView) itemView.findViewById(R.id.supplier_item_id_text);

            supplierCardView = (CardView) itemView.findViewById(R.id.supplierCardView);
        }

    }
}
