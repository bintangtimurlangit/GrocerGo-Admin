package com.admin.grocergo.Adapter;

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

import com.admin.grocergo.R;
import com.admin.grocergo.StockModel;
import com.admin.grocergo.SupplyStockDeleteData;
import com.admin.grocergo.SupplyStockEditData;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {
    private ArrayList<StockModel> stockArrayList = new ArrayList<>();

    public StockAdapter(ArrayList<StockModel> stockArrayList) { this.stockArrayList = stockArrayList; }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, final int position) {
        final StockModel StockArray = stockArrayList.get(position);

        holder.idItemHolder.setText("ID: " + StockArray.getItem_id());
        holder.nameItemHolder.setText(StockArray.getItem_id() + " - " + StockArray.getItem_name());
        holder.stockItemHolder.setText("Sisa Barang: " + StockArray.getItem_stock());
        holder.priceItemHolder.setText("Rp. " + StockArray.getItem_price() + ",00");

        holder.stockCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Loading Delete Data");
                final CharSequence[] dialogItem = {"Edit Stock", "Delete Stock"};
                builder.setTitle("Configure " + StockArray.getItem_name());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                Intent intent = new Intent(v.getContext(), SupplyStockEditData.class);
                                intent.putExtra("item_id", StockArray.getItem_id());
                                v.getContext().startActivity(intent);
                                break;
                            case 1:
                                AlertDialog.Builder builderCourierDelete = new AlertDialog.Builder(v.getContext());
                                builderCourierDelete.setTitle("Delete " + StockArray.getItem_name());
                                builderCourierDelete.setMessage("Are you sure, you want to delete " + StockArray.getItem_id());
                                builderCourierDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent2 = new Intent(v.getContext(), SupplyStockDeleteData.class);
                                        intent2.putExtra("item_id", StockArray.getItem_id());
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
    public int getItemCount() { return stockArrayList.size(); }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        TextView nameItemHolder, stockItemHolder, priceItemHolder, idItemHolder;
        CardView stockCardView;

        public StockViewHolder(View itemView) {
            super(itemView);
            idItemHolder = (TextView) itemView.findViewById(R.id.item_id_text);
            nameItemHolder = (TextView) itemView.findViewById(R.id.item_name_text);
            stockItemHolder = (TextView) itemView.findViewById(R.id.item_stock_text);
            priceItemHolder = (TextView) itemView.findViewById(R.id.item_price_text);

            stockCardView = (CardView) itemView.findViewById(R.id.itemCardView);
        }
    }
}
