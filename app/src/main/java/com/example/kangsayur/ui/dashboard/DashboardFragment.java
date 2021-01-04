package com.example.kangsayur.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangsayur.BaseActivity;
import com.example.kangsayur.LoginActivity;
import com.example.kangsayur.R;
import com.example.kangsayur.SendDetailActivity;
import com.example.kangsayur.adapter.cartadapter;
import com.example.kangsayur.model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DashboardFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    LinearLayout btn_volumecart;
    TextView tv_totalvolume;
    int sum, all;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        recyclerView = root.findViewById(R.id.recyclerView);
        tv_totalvolume = root.findViewById(R.id.tv_totalVolume);
        btn_volumecart = root.findViewById(R.id.btn_volumecart);
        btn_volumecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum<20000){
                    Toast.makeText(getContext(), "Minimum pemesanan Rp 20.000",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getContext(), SendDetailActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        final String userId = BaseActivity.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("cart").child(userId);
        FirebaseRecyclerAdapter<Cart, cartadapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Cart, cartadapter>(
                        Cart.class,
                        R.layout.cartholder,
                        cartadapter.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(cartadapter viewHolder, final Cart model, final int i) {
                        viewHolder.bindToPost(model, new View.OnClickListener() {
                            final DatabaseReference postRef = getRef(i);
                            @Override
                            public void onClick(View v) {
                                DatabaseReference UserOrderRef = myRef.child("cart").child(userId).child(postRef.getKey());
                                UserOrderRef.setValue(null);
                            }
                        });
                    }

                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sum = 0;
                int disc =0;
                Cart Order = dataSnapshot.getValue(Cart.class);
                if (Order == null){
                    tv_totalvolume.setText("0 items | Rp 0");

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
                        String sAll = String.valueOf(all) + "items | Rp " + String.valueOf(sum);
                        tv_totalvolume.setText(sAll);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
