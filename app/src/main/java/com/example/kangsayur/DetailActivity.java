package com.example.kangsayur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangsayur.model.Cart;
import com.example.kangsayur.model.home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends BaseActivity implements View.OnClickListener{
    DatabaseReference myRef;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabase;
    ImageView iv_detailimage, btn_minusdetail, btn_plusdetail;
    TextView tv_namedetail, tv_pricedetail, tv_stringdetail, tv_pricetotaldetail, tv_totaldetail;
    LinearLayout btn_addtocart;
    String text1, gambar, name;
    int price, items, total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btn_minusdetail = findViewById(R.id.btn_minusdetail);
        btn_plusdetail = findViewById(R.id.btn_plusdetail);
        btn_addtocart= findViewById(R.id.btn_addtocart);
        iv_detailimage = findViewById(R.id.imageView);
        tv_namedetail = findViewById(R.id.tv_namedetail);
        tv_pricedetail = findViewById(R.id.tv_pricedetail);
        tv_stringdetail = findViewById(R.id.tv_stringdetail);
        tv_pricetotaldetail = findViewById(R.id.tv_pricetotaldetail);
        tv_totaldetail = findViewById(R.id.tv_totaldetail);
        btn_minusdetail.setOnClickListener(this);
        btn_plusdetail.setOnClickListener(this);
        btn_addtocart.setOnClickListener(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        text1 = getIntent().getStringExtra("nama");
        myRef = FirebaseDatabase.getInstance().getReference().child("Data").child(text1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                home home = dataSnapshot.getValue(home.class);
                gambar = home.getGambar();
                name = home.getNama();
                Picasso.get().load(gambar).into(iv_detailimage);
                tv_namedetail.setText(home.getNama());
                tv_pricedetail.setText(home.getPricename());
                price = home.getPrice();
                tv_pricetotaldetail.setText("Rp 0");
                if (home.getText1().equals("alpukat")){
                    tv_stringdetail.setText(R.string.Alpukat);
                }
                else if (home.getText1().equals("bayam")){
                    tv_stringdetail.setText(R.string.bayam);
                }
                else if (home.getText1().equals("brokoli")){
                    tv_stringdetail.setText(R.string.brokoli);
                }
                else if (home.getText1().equals("buahnaga")){
                    tv_stringdetail.setText(R.string.buahnaga);
                }
                else if (home.getText1().equals("genjer")){
                    tv_stringdetail.setText(R.string.genjer);
                }
                else if (home.getText1().equals("kacangpanjang")){
                    tv_stringdetail.setText(R.string.kacangpanjang);
                }
                else if (home.getText1().equals("kangkung")){
                    tv_stringdetail.setText(R.string.kangkung);
                }
                else if (home.getText1().equals("nanas")){
                    tv_stringdetail.setText(R.string.nanas);
                }
                else if (home.getText1().equals("salak")){
                    tv_stringdetail.setText(R.string.salak);
                }
                else if (home.getText1().equals("sawi")){
                    tv_stringdetail.setText(R.string.sawi);
                }
                else if (home.getText1().equals("sirsak")){
                    tv_stringdetail.setText(R.string.sirsak);
                }
                else if (home.getText1().equals("strawberry")){
                    tv_stringdetail.setText(R.string.strawberry);
                }
                else if (home.getText1().equals("terongbelanda")){
                    tv_stringdetail.setText(R.string.terongbelanda);
                }
                else if (home.getText1().equals("wortel")){
                    tv_stringdetail.setText(R.string.wortel);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == btn_plusdetail.getId()) {
           items=items +1;
           total = items*price;

           tv_totaldetail.setText(String.valueOf(items));
           tv_pricetotaldetail.setText("Rp " + String.valueOf(total));
        }
        if (v.getId() == btn_minusdetail.getId()){
            items= items - 1;
            if(items<0){
                items=0;
            }
            total = items*price;
            tv_totaldetail.setText(String.valueOf(items));
            tv_pricetotaldetail.setText("Rp " + String.valueOf(total));
        }
        if (v.getId() == btn_addtocart.getId()){
            final String userId = getUid();
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            if (items>0){
                writeNewPost(userId, name, gambar, String.valueOf(items), String.valueOf(total));
                startActivity(intent);
                finish();
            }
            else if(items==0){
                Toast.makeText(DetailActivity.this, "Harap isi jumlah pesanan Anda",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void Back(View view){
        onBackPressed();
    }
    private void writeNewPost(String userId, String Name,String gambar, String items, String total) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("cart").push().getKey();
        Cart cart = new Cart(userId, Name, gambar, items, total);
        Map<String, Object> postValues = cart.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/cart/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
