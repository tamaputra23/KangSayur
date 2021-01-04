package com.example.kangsayur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangsayur.model.Payment;
import com.example.kangsayur.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SendDetailActivity extends BaseActivity {
    private static final String TAG = "SendDetailActivity";
    EditText et_namalengkap, et_notelpon, et_alamat ;
    TextView et_provinsi, et_kecamatan, et_kota, et_kodepos;
    Button btn_next;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    String date;
    String alamat, provinsi, kecamatan, kota, kodepos, nama, notelpon, email, skodetransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_detail);
        et_namalengkap = findViewById(R.id.et_namalengkap);
        et_notelpon = findViewById(R.id.et_notelepon);
        et_alamat = findViewById(R.id.et_alamat);
        et_provinsi = findViewById(R.id.et_provinsi);
        et_kecamatan = findViewById(R.id.et_kecamatan);
        et_kodepos= findViewById(R.id.et_kodepos);
        et_kota = findViewById(R.id.et_kota);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    @Override
    protected void onStart() {
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
                            Toast.makeText(SendDetailActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            nama= user.getFirstname();
                            notelpon = user.getPhonenumber();
                            email = user.getEmail();
                            et_namalengkap.setText(user.getFirstname());
                            et_notelpon.setText(user.getPhonenumber());
                            if(!user.getAlamat().equals("Alamat Lengkap")){
                                et_alamat.setText(user.getAlamat());
                            }
                        }
                        // [END_EXCLUDE]
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
    public void btn_next(View view){
        final String userId = getUid();
        if (!validateForm()) {
            return;
        }
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        skodetransaksi = email.substring(0,2).toUpperCase() + date.substring(0,2) + time.substring(0,2) + time.substring(3,5) + time.substring(6,8);
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        intent.putExtra("kodetransaksi", skodetransaksi);
        startActivity(intent);
        finish();
        alamat = et_alamat.getText().toString();
        kecamatan = et_kecamatan.getText().toString();
        kota = et_kota.getText().toString();
        provinsi = et_provinsi.getText().toString();
        kodepos = et_kodepos.getText().toString();
        writeNewPost2(userId,email, nama, notelpon, alamat, provinsi, kota, kecamatan, kodepos);
        mDatabase.child("cart").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot snapm : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map <String, Object>) snapm.getValue();
                    Object items = map.get("items");
                    Object name = map.get("Name");
                    Object total = map.get("total");
                    writeNewPost(userId,email, nama, notelpon, alamat, provinsi, kota, kecamatan, kodepos, String.valueOf(name), String.valueOf(items), String.valueOf(total), skodetransaksi,date);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference UserOrderRef = mDatabase.child("cart").child(userId);
        UserOrderRef.setValue(null);

    }
    private void writeNewPost(String userId, String email, String nama, String notelpon, String alamat, String provinsi, String kota, String kecamatan, String kodepos, String Name, String items, String total, String kodetransaksi, String tanggal) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        String key = mDatabase.child("payment").push().getKey();
        Payment payment = new Payment(userId, email, nama, notelpon, alamat, provinsi, kota, kecamatan, kodepos,Name , items, total, kodetransaksi, tanggal);
        Map<String, Object> postValues = payment.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/payment/" + userId + "/" + skodetransaksi + "/" + key, postValues);
        childUpdates.put("/history/" + userId + "/" +  key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
    private void writeNewPost2(String userId, String email, String firstname, String phonenumber, String alamat, String provinsi, String kota, String kecamatan, String kodepos) {
        User user = new User(email, firstname, phonenumber, alamat, provinsi, kota, kecamatan, kodepos);
        mDatabase.child("users").child(userId).setValue(user);
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(et_alamat.getText().toString())) {
            et_alamat.setError("Required");
            result = false;
        } else {
            et_alamat.setError(null);
        }
        if (TextUtils.isEmpty(et_provinsi.getText().toString())) {
            et_provinsi.setError("Required");
            result = false;
        } else {
            et_provinsi.setError(null);
        }
        if (TextUtils.isEmpty(et_kota.getText().toString())) {
            et_kota.setError("Required");
            result = false;
        } else {
            et_kota.setError(null);
        }
        if (TextUtils.isEmpty(et_kecamatan.getText().toString())) {
            et_kecamatan.setError("Required");
            result = false;
        } else {
            et_kecamatan.setError(null);
        }
        if (TextUtils.isEmpty(et_kodepos.getText().toString())) {
            et_kodepos.setError("Required");
            result = false;
        } else {
            et_kodepos.setError(null);
        }
        return result;
    }// [START basic_write]
}
