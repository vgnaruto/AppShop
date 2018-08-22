package com.example.windows10.dapurukm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Arrays;

public class DataDummy {

    public static ArrayList<Product> getProduct(){
        MainActivity ui = MainActivity.getInstance();

        ArrayList<Bitmap> dummy1 = new ArrayList<>();
        dummy1.addAll(Arrays.asList(new Bitmap[]{
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy1_1)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy1_2)).getBitmap())
        }));
        ArrayList<Bitmap> dummy2 = new ArrayList<>();
        dummy2.addAll(Arrays.asList(new Bitmap[]{
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy2_1)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy2_2)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy2_3)).getBitmap())
        }));
        ArrayList<Bitmap> dummy3 = new ArrayList<>();
        dummy3.addAll(Arrays.asList(new Bitmap[]{
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy3_1)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy3_2)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy3_3)).getBitmap()),
                Bitmap.createBitmap(((BitmapDrawable)ui.getResources().getDrawable(R.drawable.dummy3_4)).getBitmap())
        }));
        Product[] products = new Product[]{
                new Product(dummy1,
                        "Rp 200.000",
                        "BIMOLI MINYAK GORENG",
                        "Bimoli minyak goreng refil 1 Liter",
                        new String[]{"Kategori 1","Kategori 2"},
                        new Seller("Nutrimart","Pagedangan, Tangerang"),
                        3,
                        300,
                        100),
                new Product(dummy2,
                        "Rp 5.000",
                        "AQUA AIR PUTIH MINERAL",
                        "Aqua air putih 550 mili",
                        new String[]{"Kategori 2","Kategori 4"},
                        new Seller("BIZ MART","Pinang(penang), Tangerang"),
                        4,
                        150,
                        100),
                new Product(dummy3,
                        "Rp 15.000",
                        "BENG BENG COKELAT",
                        "Beng-beng adalah sejenis makanan ringan yang sudah terkenal di semua kalangan karena rasanya yang nikmat dan memiliki citra rasa yang berbeda dengan cemilan berbahan dasar wafer lainnya. Dengan 4 kelezatan sekaligus pada setiap lapisannya sehingga pada setiap gigitan memberikan 'sensasi' yang membuat kita ingin memakannya lagi dan lagi .",
                        new String[]{"Kategori 3","Kategori 5"},
                        new Seller("Khaira Jaya","Penjaringang, Jakarta Utara"),
                        5,
                        50,
                        100),
                new Product(dummy2,
                        "Rp 3.000",
                        "AIR MINERAL",
                        "Aqua air putih 550 mili",
                        new String[]{"Kategori 1","Kategori 4"},
                        new Seller("BIZ MART","Pinang(penang), Tangerang"),
                        4,
                        150,
                        5),
                new Product(dummy1,
                        "Rp 20.000",
                        "BARANG 4",
                        "Aqua air putih 550 mili",
                        new String[]{"Kategori 1","Kategori 5"},
                        new Seller("BIZ MART","Pinang(penang), Tangerang"),
                        4,
                        150,
                        1)
        };
        ArrayList<Product> result = new ArrayList<>();
        result.addAll(Arrays.asList(products));

        return result;
    }
}
