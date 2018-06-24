package com.example.windows10.dapurukm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckoutAdapter extends BaseAdapter {
    private ArrayList<Product> item;
    private ArrayList<Agent[]> agents;
    private MainActivity ui;
    private ViewHolder vh;
    private MainPresenter presenter;
    private ArrayList<Integer> totalOrder;
    private ArrayList<Integer> shippingCost;
    private boolean updateService = false;
    private int currentPosisi = 0;

    public CheckoutAdapter(MainActivity ui, ArrayList<Product> products) {
        this.ui = ui;
        presenter = ui.getPresenter();
        item = products;
        agents = new ArrayList<>();
        totalOrder = new ArrayList<>();
        shippingCost = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            agents.add(null);
            totalOrder.add(0);
            shippingCost.add(0);
        }
    }

//    @Override
//    public void notifyDataSetChanged() {
//        super.notifyDataSetChanged();
//        item = presenter.getProduct();
//        vh.initService = false;
//        vh.initAgent = false;
//    }

    public void setAgents(int posisi, Agent[] agent) {
        agents.set(posisi, agent);
        updateService = true;
        currentPosisi = posisi;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return item.size();
    }

    public ArrayList<Product> getItem(){
        return this.item;
    }



    @Override
    public Product getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ui).inflate(R.layout.checkout_item, parent, false);
            vh = new ViewHolder(convertView, position);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if(updateService == true) {
            if(position == currentPosisi) {
                vh.updateView(getItem(position), agents.get(position), position);
            }
        }else{
            vh.updateView(getItem(position), agents.get(position), position);
        }


        return convertView;
    }

    public ArrayList<Integer> getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(ArrayList<Integer> totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotal() {
        int result = 0;
        for (int total : totalOrder) {
            result += total;
        }
        return result;
    }

    public ArrayList<Integer> getShippingCost() {
        return shippingCost;
    }

    public int getJumlahShipping(){
        int result = 0;
        for(int i : shippingCost){
            result += i;
        }
        return result;
    }

    public void setShippingCost(ArrayList<Integer> shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getTotalShipping() {
        int result = 0;
        for (int total : shippingCost) {
            result += total;
        }
        return result;
    }

    private class ViewHolder {
        protected TextView tvNamaToko, tvJudulProduct, tvTotalBarang, tvTotalHarga, tvETD, tvShippingCost, tvSubtotal;
        protected ImageView ivFotoProduct;
        protected EditText etCatatan;
        protected Spinner spinnerAgent, spinnerService;
        private int flag = 0;
        private boolean initAgent = false;
        private boolean initService = false;

        public ViewHolder(View v, int posisi) {
            Log.d("UPDATEVIEW", "VIEW HOLDER");
            tvNamaToko = v.findViewById(R.id.tv_nama_toko);
            tvJudulProduct = v.findViewById(R.id.tv_nama_product);
            tvTotalBarang = v.findViewById(R.id.tv_jumlah_product);
            tvTotalHarga = v.findViewById(R.id.tv_harga_product);
            etCatatan = v.findViewById(R.id.et_catatan);
            tvETD = v.findViewById(R.id.tv_ETD);
            tvShippingCost = v.findViewById(R.id.tv_shipping_cost);
            tvSubtotal = v.findViewById(R.id.tv_subtotal);
            ivFotoProduct = v.findViewById(R.id.iv_gambar_product);
            spinnerAgent = v.findViewById(R.id.spinner_agent);
            spinnerService = v.findViewById(R.id.spinner_type);

            //SET SPINNER AGENT
            ArrayList<String> namaAgent = new ArrayList<>();
            namaAgent.add("jne");
            namaAgent.add("pos");
            namaAgent.add("tiki");
            ArrayAdapter<String> adapterAgent = new ArrayAdapter<>(ui, android.R.layout.simple_spinner_item, namaAgent);
            adapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAgent.setAdapter(adapterAgent);

            Product cProduct = item.get(posisi);
            String weight = (cProduct.getTotal() * Integer.parseInt(cProduct.getWeight())) + "";
            String posisiSeller = cProduct.getSeller().getKabupaten().getCity_id();
            String posisiBuyer = presenter.getUser().getKabupaten().getCity_id();
            if (!updateService) {
                presenter.getCost("https://api.rajaongkir.com/starter/cost?", (String) spinnerAgent.getSelectedItem(), posisi, weight, posisiSeller, posisiBuyer);
                flag = 1;
            }
        }

        public void updateView(final Product product, final Agent[] cAgents, final int posisi) {
            Log.d("UPDATEVIEW", "DIPANGGIL");
            ivFotoProduct.setImageBitmap(product.getFoto().get(0));
            tvNamaToko.setText(product.getSeller().getName());
            tvJudulProduct.setText(product.getNama());
            tvTotalBarang.setText(product.getTotal() + " pcs");
            final int total = product.getTotal();
            final int harga = Integer.parseInt(product.getHarga().substring(3).replaceAll("\\.", ""));
            tvTotalHarga.setText(presenter.formatRupiah(total * harga));

            totalOrder.set(posisi, total * harga);


            spinnerAgent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (initAgent) {
                        String namaAgent = (String) spinnerAgent.getSelectedItem();
                        String posisiSeller = product.getSeller().getKabupaten().getCity_id();
                        String posisiBuyer = presenter.getUser().getKabupaten().getCity_id();
                        String weight = (product.getTotal() * Integer.parseInt(product.getWeight())) + "";
                        if (flag != 1 && !updateService) {
                            presenter.getCost("https://api.rajaongkir.com/starter/cost?", namaAgent, posisi, weight, posisiSeller, posisiBuyer);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (initService) {
                        if (cAgents != null) {
                            Costs costs = agents.get(posisi)[0].getCosts()[position];
                            String etd = costs.getCosts()[0].getEtd();
                            etd = etd.replaceAll(" HARI", "");
                            int total = product.getTotal();
                            int harga = Integer.parseInt(product.getHarga().substring(3).replaceAll("\\.", ""));

                            shippingCost.set(posisi, Integer.parseInt(costs.getCosts()[0].getValue()));

                            presenter.notifyCheckout();
                            tvETD.setText(etd + " hari");
                            tvShippingCost.setText("" + presenter.formatRupiah(Integer.parseInt(costs.getCosts()[0].getValue())));
                            tvSubtotal.setText(presenter.formatRupiah(total * harga + Integer.parseInt(costs.getCosts()[0].getValue())));
                            flag = 0;

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //SET SPINNER SERVICE
            if (cAgents != null) {
                ArrayList<String> namaService = new ArrayList<>();
                Costs[] costs = cAgents[0].getCosts();
                for (int i = 0; i < costs.length; i++) {
                    namaService.add(costs[i].getService());
                }
                ArrayAdapter<String> adapterService = new ArrayAdapter<>(ui, android.R.layout.simple_spinner_item, namaService);
                adapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerService.setAdapter(adapterService);
            }
            updateService = false;
            initAgent = true;
            initService = true;
        }
    }
}