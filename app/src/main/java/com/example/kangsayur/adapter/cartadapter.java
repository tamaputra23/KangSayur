package com.example.kangsayur.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kangsayur.R;
import com.example.kangsayur.model.Cart;
import com.squareup.picasso.Picasso;

public class cartadapter extends RecyclerView.ViewHolder  {
    View mView;
    TextView tvTotal, tvMenu, tvPrice,btnRemove;
    ImageView iv_cartholder;
    String gambar;
    public cartadapter(View itemView) {
        super(itemView);
        mView = itemView;
        btnRemove = mView.findViewById(R.id.tv_remove);
        iv_cartholder = mView.findViewById(R.id.iv_cartholder);
        tvMenu = mView.findViewById(R.id.tv_namecartholder);
        tvPrice = mView.findViewById(R.id.tv_pricecartholder);
    }
    public void bindToPost(Cart cart, View.OnClickListener removeClickListener){
        tvMenu.setText(cart.Name);
        tvPrice.setText(cart.items + "items | Rp " + cart.total);
        gambar = cart.gambar;
        Picasso.get().load(gambar).into(iv_cartholder);
        btnRemove.setOnClickListener(removeClickListener);
    }
}
