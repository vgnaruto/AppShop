package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Product {
    private ArrayList<String> foto;
    private String harga;
    private String nama;
    private String productDetails;
    private Seller seller;
    private int rating;
    private int total;
    private int stock;
//    private double hargaAngka;
    private boolean inCart;
    private String weight;
    private String[] kategori;

    public Product(ArrayList<Bitmap> foto, String harga, String nama, String productDetails, String[] kategori, Seller seller, int rating, int weight, int stock) {
        this.foto = new ArrayList<>();
        ByteArrayOutputStream baos;
        for (int i = 0; i < foto.size(); i++) {
            baos = new ByteArrayOutputStream();
            foto.get(i).compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            this.foto.add(Base64.encodeToString(b, Base64.DEFAULT));
        }
        this.kategori = kategori;
        this.harga = harga;
        this.nama = nama;
        this.productDetails = productDetails;
        this.seller = seller;
        this.rating = rating;
        this.total = 1;
        inCart = false;
        this.weight = weight + "";
        this.stock = stock;
    }

//    public double getHargaAngka() {
//        return hargaAngka;
//    }

//    public void setHargaAngka(double hargaAngka) {
//        this.hargaAngka = hargaAngka;
//    }

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

    public String[] getKategori() {
        return kategori;
    }

    public void setKategori(String[] kategori) {
        this.kategori = kategori;
    }

    public ArrayList<Bitmap> getFoto() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0; i < this.foto.size(); i++) {
            byte[] b = Base64.decode(this.foto.get(i), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            bitmaps.add(bitmap);
        }
        return bitmaps;
    }

    public void setFoto(ArrayList<Bitmap> foto) {
        this.foto = new ArrayList<>();
        ByteArrayOutputStream baos;
        for (int i = 0; i < foto.size(); i++) {
            baos = new ByteArrayOutputStream();
            foto.get(i).compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            this.foto.add(Base64.encodeToString(b, Base64.DEFAULT));
        }
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
