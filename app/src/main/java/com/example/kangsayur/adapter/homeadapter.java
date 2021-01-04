package com.example.kangsayur.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kangsayur.R;
import com.squareup.picasso.Picasso;

public class homeadapter extends RecyclerView.ViewHolder{
    View mView;
    ImageView imageView;
    TextView tvnama, tvnameprice, textview1;
    public homeadapter(View itemView) {
        super(itemView);
        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }
    public void setDetails(Context ctx, String nama, String gambar, String pricename, int price, String text1){
        imageView = mView.findViewById(R.id.iv_homeholder);
        Picasso.get().load(gambar).into(imageView);
        tvnama = mView.findViewById(R.id.tv_nameholder);
        tvnameprice = mView.findViewById(R.id.tv_pricehomeholder);
        textview1 = mView.findViewById(R.id.tv_textviewholder);
        tvnama.setText(nama);
        tvnameprice.setText(pricename);
        textview1.setText(text1);

    }
    private homeadapter.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(homeadapter.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
