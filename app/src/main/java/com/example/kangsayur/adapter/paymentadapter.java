package com.example.kangsayur.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kangsayur.R;
import com.example.kangsayur.model.Cart;
import com.example.kangsayur.model.Payment;
import com.squareup.picasso.Picasso;

public class paymentadapter extends RecyclerView.ViewHolder{
    View mView;
    TextView tvTotal, tvMenu, tvPrice;
    ImageView iv_cartholder;
    String gambar;
    public paymentadapter(View itemView) {
        super(itemView);
        mView = itemView;
        tvMenu = mView.findViewById(R.id.tv_namepaymentholder);
        tvPrice = mView.findViewById(R.id.tv_pricepaymentholder);
        tvTotal = mView.findViewById(R.id.tv_totalpaymentholder);
    }
    public void bindToPost(Payment payment, View.OnClickListener removeClickListener){
        tvMenu.setText(payment.Name);
        tvPrice.setText(payment.items + "x");
        tvTotal.setText("Rp " + payment.total);
    }
}
