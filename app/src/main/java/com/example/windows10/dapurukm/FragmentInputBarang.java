package com.example.windows10.dapurukm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class FragmentInputBarang extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private ImageButton imageButtonGallery;
    private ImageView imageButtonGambarProduk;
    private ImageButton backButton;
    private Button simpanButton;
    private EditText etNama, etBerat, etHarga, etKuantitas, etKeterangan;
    private TextView tvNamaPenjual, tvAlamatPenjual;
    private Spinner spKategori;
    private LinearLayout llKategori;

    public static final int PICK_IMAGE = 15;

    private ArrayList<Bitmap> gambar;
    private ArrayList<String> kategori;

    private Product product;
    private MainPresenter presenter;

    public FragmentInputBarang() {
    }

    public static FragmentInputBarang newInstance(MainActivity mainActivity, String title) {
        FragmentInputBarang fragment = new FragmentInputBarang();
        fragment.initialize(mainActivity);
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public void initialize(MainActivity activity) {
        ctx = activity;
        presenter = ctx.getPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_barang_jualan, container, false);

        backButton = view.findViewById(R.id.back_button);
        simpanButton = view.findViewById(R.id.btn_simpan);

        etNama = view.findViewById(R.id.prod_name);
        etHarga = view.findViewById(R.id.prod_price);
        etBerat = view.findViewById(R.id.prod_weight);
        etKuantitas = view.findViewById(R.id.prod_stock);
        etKeterangan = view.findViewById(R.id.et_keterangan_produk);
        tvNamaPenjual = view.findViewById(R.id.seller_name);
        tvAlamatPenjual = view.findViewById(R.id.seller_address);
        imageButtonGambarProduk = view.findViewById(R.id.ib_gambar_produk);
        imageButtonGallery = view.findViewById(R.id.ib_gallery);
        spKategori = view.findViewById(R.id.sp_kategori);
        llKategori = view.findViewById(R.id.ll_kategori);

        gambar = new ArrayList<>();
        kategori = new ArrayList<>();
        tvNamaPenjual.setText(presenter.getUser().getSeller().getName());
        tvAlamatPenjual.setText(presenter.getUser().getSeller().getAddress());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.category_list, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);

        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(spKategori.getSelectedItemPosition() > 0 && !kategori.contains(spKategori.getSelectedItem().toString())) {
                    String selectedCategory = spKategori.getSelectedItem().toString();
                    TextView tvKategori = new TextView(ctx);
                    tvKategori.setTextSize(15);
                    tvKategori.setText(selectedCategory);
                    llKategori.addView(tvKategori);

                    kategori.add(selectedCategory);
                    tvKategori.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kategori.remove(((TextView) v).getText());
                            llKategori.removeView(v);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        imageButtonGambarProduk.setOnClickListener(this);
        imageButtonGallery.setOnClickListener(this);
        backButton.setOnClickListener(this);
        simpanButton.setOnClickListener(this);

        return view;
    }

    public void fillData(Product product){
        if(product != null){
            this.gambar = product.getFoto();
            this.imageButtonGambarProduk.setImageBitmap(this.gambar.get(0));
            this.etNama.setText(product.getNama());
            this.etHarga.setText(product.getHargaAngka() + "");
            this.etBerat.setText(product.getWeight());
            this.etKuantitas.setText(product.getStock());
            this.etKeterangan.setText(product.getProductDetails());
            String[] kategori = product.getKategori();
            for (int i = 0; i < kategori.length; i++) {
                TextView tvKategori = new TextView(ctx);
                tvKategori.setTextSize(15);
                tvKategori.setText(kategori[i]);
                this.llKategori.addView(tvKategori);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            ctx.onBackPressed();
        } else if (v == simpanButton) {
            if (isDataValid()) {
                String nama = etNama.getText().toString();
                String harga = etHarga.getText().toString();
                int berat = Integer.parseInt(etBerat.getText().toString());
                int kuantitas = Integer.parseInt(etKuantitas.getText().toString());
                String keterangan = etKeterangan.getText().toString();
                Seller penjual = presenter.getUser().getSeller();
                int jumlahKategoriTerpilih = kategori.size();
                String[] savedCategory = new String[jumlahKategoriTerpilih];
                for (int i = 0; i < jumlahKategoriTerpilih; i++) {
                    savedCategory[i] = this.kategori.get(i);
                }

                /*DecimalFormat myFormatter = new DecimalFormat("###.###,##");
                harga = myFormatter.format(harga);
                */

                double hargaAngka = Double.parseDouble(harga);
                Product newProduct = new Product(gambar, presenter.formatRupiah(hargaAngka), nama, keterangan, savedCategory, penjual, 0, berat, kuantitas);
                newProduct.setHargaAngka(hargaAngka);
                ctx.addProducttoSeller(newProduct);
                //ctx.createHash(newProduct);

                this.etNama.setHint(etNama.getHint());
                this.etHarga.setHint(etHarga.getHint());
                this.etBerat.setHint(etBerat.getHint());
                this.etKuantitas.setHint(etKuantitas.getHint());
                this.etKeterangan.setHint(etKeterangan.getHint());
                this.llKategori.removeAllViews();

                Toast toast = Toast.makeText(ctx, "Berhasil simpan!", Toast.LENGTH_SHORT);
                toast.show();
                ctx.hideKeyboard();
                ctx.onBackPressed();
            }
        } else if (v == imageButtonGallery || v == imageButtonGambarProduk) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (intent.resolveActivity(ctx.getPackageManager()) != null) {
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        }
    }

    public boolean isDataValid() {
        if(gambar.size() <= 0){
            return false;
        }
        if (etNama.getText().toString().isEmpty()) {
            return false;
        }
        if (etHarga.getText().toString().isEmpty()) {
            return false;
        }
        if (etBerat.getText().toString().isEmpty()) {
            return false;
        }
        if (etKuantitas.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == ctx.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            /*String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = ctx.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.d("fib", picturePath);
            cursor.close();

            Bitmap img = BitmapFactory.decodeFile(picturePath);
            */
            try{
            //String path = saveImage(bitmap);
                Bitmap img = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), selectedImage);
                this.imageButtonGambarProduk.setImageBitmap(img);
                this.gambar.add(img);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
