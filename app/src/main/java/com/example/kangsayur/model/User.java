package com.example.kangsayur.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
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

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String email, String firstname, String phonenumber, String alamat, String provinsi, String kota, String kecamatan, String kodepos) {
        this.email = email;
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.kodepos = kodepos;
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
}