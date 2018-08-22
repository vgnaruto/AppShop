package com.example.windows10.dapurukm;

public class User {
    private String nama, alamat;
    private Provinsi provinsi;
    private Kabupaten kabupaten;
    private String kodePos;
    private String nomorTelepon;
    private String email;
    private boolean penjual;
    private Seller seller;

    public User() {
        nama = "GUEST";
        alamat = "-/-";
        kodePos = "-/-";
        nomorTelepon = "-/-";
        email = "-/-";
        penjual = false;
    }

    public User(String nama, String alamat, Provinsi provinsi, Kabupaten kabupaten, String kodePos, String nomorTelepon, String email, boolean penjual) {
        this.nama = nama;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kodePos = kodePos;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.penjual = penjual;
        if(this.penjual){
            this.seller = new Seller(this.nama, this.alamat);
            this.seller.setProvinsi(this.provinsi);
            this.seller.setKabupaten(this.kabupaten);
        }
        else{
            this.seller = null;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Provinsi getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(Provinsi provinsi) {
        this.provinsi = provinsi;
    }

    public Kabupaten getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(Kabupaten kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public boolean isPenjual() {
        return penjual;
    }

    public void setPenjual(boolean penjual) {
        this.penjual = penjual;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
