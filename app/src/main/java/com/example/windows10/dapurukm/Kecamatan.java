package com.example.windows10.dapurukm;

public class Kecamatan {
    private String id,id_kabupaten,nama;

    public Kecamatan(String id, String id_kabupaten, String nama) {
        this.id = id;
        this.id_kabupaten = id_kabupaten;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_kabupaten() {
        return id_kabupaten;
    }

    public void setId_kabupaten(String id_kabupaten) {
        this.id_kabupaten = id_kabupaten;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
