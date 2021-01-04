package com.example.kangsayur.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    public String uid;
    public String Name;
    public String gambar;
    public String items;
    public String total;
    public Cart(){}
    public Cart(String uid, String Name,String gambar, String items, String total) {
        this.uid = uid;
        this.Name = Name;
        this.gambar = gambar;
        this.items = items;
        this.total = total;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("Name", Name);
        result.put("gambar", gambar);
        result.put("items", items);
        result.put("total", total);
        return result;
    }
}
