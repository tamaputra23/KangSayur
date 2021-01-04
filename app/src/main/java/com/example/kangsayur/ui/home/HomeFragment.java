package com.example.kangsayur.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kangsayur.DetailActivity;
import com.example.kangsayur.LoginActivity;
import com.example.kangsayur.R;
import com.example.kangsayur.adapter.homeadapter;
import com.example.kangsayur.model.home;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference mDatabase;
    SharedPreferences mSharedPref; //for saving sort settings
    LinearLayoutManager mLayoutManager; //for sorting
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        myRef = FirebaseDatabase.getInstance().getReference().child("Data");
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView1);
        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<home, homeadapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<home, homeadapter>(
                        home.class,
                        R.layout.homeholder,
                        homeadapter.class,
                        myRef
                ) {
                    @Override
                    protected void populateViewHolder(homeadapter viewHolder, home model, int i) {
                        viewHolder.setDetails(getContext(), model.getNama(), model.getGambar(), model.getPricename(), model.getPrice(), model.getText1());
                    }

                    @Override
                    public homeadapter onCreateViewHolder(ViewGroup parent, int viewType) {

                        final homeadapter viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new homeadapter.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView mText1 = view.findViewById(R.id.tv_textviewholder);
                                String sText1 = mText1.getText().toString();
                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("nama", sText1);
                                startActivity(intent);
                            }

                        });
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
