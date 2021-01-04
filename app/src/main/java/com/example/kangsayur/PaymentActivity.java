package com.example.kangsayur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangsayur.adapter.cartadapter;
import com.example.kangsayur.adapter.paymentadapter;
import com.example.kangsayur.model.Cart;
import com.example.kangsayur.model.Payment;
import com.example.kangsayur.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends BaseActivity {
    private static final String TAG = "PaymentActivity";
    TextView tv_datepayment, tv_namepayment, tv_phonepayment, tv_alamatpayment, tv_sumpayment, tv_nopesananpayment;
    View view5;
    Button btn_pesan;
    String kodetransaksi;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    RecyclerView recyclerView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        kodetransaksi = getIntent().getStringExtra("kodetransaksi");
        tv_datepayment = findViewById(R.id.tv_datepayment);
        tv_alamatpayment = findViewById(R.id.tv_alamatpayment);
        tv_sumpayment = findViewById(R.id.tv_sumpayment);
        tv_namepayment = findViewById(R.id.tv_namepayment);
        tv_phonepayment = findViewById(R.id.tv_phonepayment);
        tv_nopesananpayment = findViewById(R.id.tv_nopenasananpayment);
        view5 = findViewById(R.id.view123);
        btn_pesan = findViewById(R.id.btn_pesan);
        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view5.setBackgroundResource(R.drawable.roundeddot1);
                ViewDialog alert = new ViewDialog();
                alert.showDialog();
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView5 = findViewById(R.id.recyclerView5);
        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(getApplicationContext());
        recyclerView5.setLayoutManager(mLayoutManager);
        recyclerView5.setHasFixedSize(false);
    }
    public void onStart() {
        super.onStart();
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(PaymentActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String nama= user.getFirstname();
                            String notelpon = user.getPhonenumber();
                            String alamat = user.getAlamat();
                            String provinsi = user.getProvinsi();
                            String kota = user.getKota();
                            String kecamatan = user.getKecamatan();
                            String kodepos = user.getKodepos();
                            String address = alamat + " " + kecamatan + ", " + kota + ", " + provinsi + ". " + kodepos +".";
                            tv_namepayment.setText("Nama          : " + nama);
                            tv_phonepayment.setText("No Telepon : " + notelpon);
                            tv_datepayment.setText("Tanggal      : "+ date);
                            tv_alamatpayment.setText(address);
                            tv_nopesananpayment.setText("No. Pemesanan "+ kodetransaksi);
                        }
                        // [END_EXCLUDE]
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        myRef = FirebaseDatabase.getInstance().getReference().child("payment").child(userId).child(kodetransaksi);
        FirebaseRecyclerAdapter<Payment, paymentadapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Payment, paymentadapter>(
                        Payment.class,
                        R.layout.paymentholder,
                        paymentadapter.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(paymentadapter viewHolder, final Payment model, final int i) {
                        viewHolder.bindToPost(model, new View.OnClickListener() {
                            final DatabaseReference postRef = getRef(i);
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }

                };
        recyclerView5.setAdapter(firebaseRecyclerAdapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum = 0;
                int disc =0;
                int all = 0;
                Cart Order = dataSnapshot.getValue(Cart.class);
                if (Order == null){
                    tv_sumpayment.setText("Rp 0");

                }
                else {
                    for (DataSnapshot snapm : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map <String, Object>) snapm.getValue();
                        Object price = map.get("total");
                        Object total = map.get("items");
                        int iPrice = Integer.parseInt(String.valueOf(price));
                        int iTotal = Integer.parseInt(String.valueOf(total));
                        sum = sum+iPrice;
                        all= iTotal+all;
                        String sAll = "Rp " + String.valueOf(sum);
                        tv_sumpayment.setText(sAll);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public  class  ViewDialog {
        public void showDialog(){
            final Dialog dialog = new Dialog(PaymentActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.paymentpopup);
            Button imgClose = dialog.findViewById(R.id.btn_popup);
            imgClose.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
