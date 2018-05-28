package com.example.windows10.dapurukm;

public class Seller {
    private String name;
    private String address;
    private Provinsi provinsi;
    private Kabupaten kabupaten;

    public Seller(String name, String address) {
        this.name = name;
        this.address = address;
        //TODO harus dipikirin gimana caranya biar seller masukin alamat bedasarkan api... <<
        this.provinsi = new Provinsi("1","Bali");
        this.kabupaten = new Kabupaten("17","1","Bali","Kabupaten","Badung","80351");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

