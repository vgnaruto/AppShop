package com.example.windows10.e_commerce;

import android.graphics.Bitmap;

public class Item {
    private Bitmap icon;
    private String nama;
    private String deskripsi;

    public Item(Bitmap icon, String nama, String deskripsi) {
        this.icon = icon;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
