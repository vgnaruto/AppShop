package com.example.windows10.dapurukm;

import java.util.ArrayList;

public class Kabupaten {
    private String id;
    private String id_prov;
    private String nama;

    public Kabupaten(String id, String id_prov, String nama) {
        this.id = id;
        this.id_prov = id_prov;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_prov() {
        return id_prov;
    }

    public void setId_prov(String id_prov) {
        this.id_prov = id_prov;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
