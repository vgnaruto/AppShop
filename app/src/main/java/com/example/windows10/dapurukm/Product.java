package com.example.windows10.dapurukm;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Product {
    private ArrayList<Bitmap> foto;
    private String harga;
    private String nama;
    private String productDetails;
    private Seller seller;
    private int rating;
    private int total;
    private boolean inCart;
    private String weight;

    public Product(ArrayList<Bitmap> foto, String harga, String nama, String productDetails, Seller seller, int rating, int weight) {
        this.foto = foto;
        this.harga = harga;
        this.nama = nama;
        this.productDetails = productDetails;
        this.seller = seller;
        this.rating = rating;
        this.total = 1;
        inCart = false;
        this.weight = weight + "";
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Bitmap> getFoto() {
        return foto;
    }

    public void setFoto(ArrayList<Bitmap> foto) {
        this.foto = foto;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
