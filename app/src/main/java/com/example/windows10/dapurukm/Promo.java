package com.example.windows10.dapurukm;

import android.graphics.Bitmap;

public class Promo {
    private Bitmap poster;
    private String namaPromo;
    private String deskPromo;

    public Promo(Bitmap poster, String namaPromo, String deskPromo) {
        this.poster = poster;
        this.namaPromo = namaPromo;
        this.deskPromo = deskPromo;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public String getNamaPromo() {
        return namaPromo;
    }

    public void setNamaPromo(String namaPromo) {
        this.namaPromo = namaPromo;
    }

    public String getDeskPromo() {
        return deskPromo;
    }

    public void setDeskPromo(String deskPromo) {
        this.deskPromo = deskPromo;
    }
}
