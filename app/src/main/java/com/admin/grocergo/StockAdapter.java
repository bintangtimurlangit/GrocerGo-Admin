package com.admin.grocergo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {
    private ArrayList<SupplierModel> stockArrayList = new ArrayList<>();

    public StockAdapter(ArrayList<SupplierModel> stockArrayList) { this.stockArrayList = stockArrayList; }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, final int position) {
//        final StockModel StockArray = stockArrayList.get(position);

//        holder.idItemHolder.setText(StockArray.getItem_id());
//        holder.nameItemHolder.setText(StockArray.getItem_name());
//        holder.stockItemHolder.setText(StockArray.getItem_stock());
//        holder.priceItemHolder.setText(StockArray.getItem_price());
    }

    @Override
    public int getItemCount() { return stockArrayList.size(); }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        TextView stockSupplierHolder, nameSupplierHolder, emailSupplierHolder, idItemHolder;
        CardView stockCardView;

        public StockViewHolder(View itemView) {
            super(itemView);
//            idSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_id_text);
//            nameSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_name_text);
//            emailSupplierHolder = (TextView) itemView.findViewById(R.id.supplier_email_text);
//            idItemHolder = (TextView) itemView.findViewById(R.id.supplier_item_id_text);

//            stockCardView = (CardView) itemView.findViewById(R.id.stockCardView);
        }

    }
}
