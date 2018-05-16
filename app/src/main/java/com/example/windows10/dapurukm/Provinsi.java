package com.example.windows10.dapurukm;

import java.util.ArrayList;

public class Provinsi {
    private String id;
    private String nama;

    public Provinsi(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
