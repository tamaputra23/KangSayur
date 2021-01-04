package com.example.kangsayur.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Payment {
    public String email;
    public String firstname;
    public String lastname;
    public String phonenumber;
    public String id;
    private String alamat;
    private String kota;
    private String provinsi;
    private String kodepos;
    private String kecamatan;
    public String Name;
    public String gambar;
    public String items;
    public String total;
    public String kodetransaksi;
    public String tanggal;
    public Payment() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Payment( String id, String email, String firstname, String phonenumber, String alamat, String provinsi, String kota, String kecamatan, String kodepos, String Name, String items, String total, String kodetransaksi, String tanggal) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.kodepos = kodepos;
        this.Name = Name;
        this.gambar = gambar;
        this.items = items;
        this.total = total;
        this.kodetransaksi = kodetransaksi;
        this.tanggal = tanggal;


    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String akses) {
        this.lastname = lastname;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public  String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public  String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
    public  String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }
    public  String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }
    public  String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }
    public  String getKodetransaksi() {
        return kodetransaksi;
    }

    public void setKodetransaksi(String kodetransaksi) {
        this.kodetransaksi = kodetransaksi;
    }
    public  String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("Name", Name);
        result.put("gambar", gambar);
        result.put("items", items);
        result.put("firstname", firstname);
        result.put("phonenumber", phonenumber);
        result.put("email", email);
        result.put("alamat", alamat);
        result.put("provinsi", provinsi);
        result.put("kota", kota);
        result.put("kecamatan", kecamatan);
        result.put("kodepos", kodepos);
        result.put("total", total);
        result.put("kodetransaksi", kodetransaksi);
        result.put("tanggal", tanggal);
        return result;
    }
}
