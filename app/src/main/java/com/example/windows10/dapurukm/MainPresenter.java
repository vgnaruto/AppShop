package com.example.windows10.dapurukm;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    private UserManager userManager;
    private MainActivity activity;
    private WebServiceManager webServiceManager;
    private SaveDataManager saveDataManager;

    public MainPresenter(MainActivity act) {
        activity = act;
        userManager = new UserManager();
        webServiceManager = new WebServiceManager(act);
        saveDataManager = new SaveDataManager(act);
    }

    public void setUser(User user){
        userManager.setUser(user);
        userManager.setLogin(true);
        notifyUserChanged();
    }
    public User getUser(){
        return userManager.getUser();
    }

    public void saveUser(){
        saveDataManager.saveUser(userManager.getUser());
    }
    public void loadUser(){
        userManager.setUser(saveDataManager.loadUser());
    }

    public String formatRupiah(String angka){
        String result = "";
        boolean first = true;
        String angkas = angka;
//        angkas = angkas.substring(0,angkas.length()-2);
        int index = angkas.length();
        while(index > 0){
            int start;
            if(index-3 > 0){
                start = index - 3;
                if(first){
                    first = false;
                }else{
                    result = "."+result;
                }
            }else{
                start = 0;
                if(first){
                    first = false;
                }else{
                    result = "."+result;
                }
            }
            result = angkas.substring(start,index)+ result;
            index -= 3;
        }

//        while(angka > 0){
//            result = (angka%10) + result;
//            angka/=10;
//            count++;
//            if(angka > 0) {
//                if (count == 4) {
//                    result = "." + result;
//                    count = 1;
//                }
//            }
//        }
        return "Rp "+result;
    }

    public void notifyUserChanged(){
        activity.notifyUserChanged();
    }

    public boolean isLogin(){
        return userManager.isLogin();
    }

    public void setLogin(boolean status){
        userManager.setLogin(status);
    }

    public void getProvinsi(String url, int index){
        webServiceManager.getProvinsi(url,index);
    }
    public void getKabupaten(String url){
        webServiceManager.getKabupaten(url);
    }

    public String formatKeterangan(User user){
        String nama = user.getNama();
        String alamat = user.getAlamat();
        String kota = user.getKabupaten().getCity_name();
        String provinsi = user.getProvinsi().getProvince();
        String kodePost = user.getKodePos();
        String noTelepon = user.getNomorTelepon();
        return nama+"\n\n"+alamat+", "+kota+"\n"+provinsi+", "+kodePost+"\n"+noTelepon;
    }

    /**
     * mengambil product dari keranjang
    * */
    public ArrayList<Product> getProduct(){
        return activity.getProduct();
    }

    public void getCost(String url,String namaAgent,int posisi, String weight,String idSeller,String idBuyer){
        webServiceManager.postCost(url,namaAgent,posisi,weight,idSeller,idBuyer);
    }
    public void notifyCheckout(){
        activity.notifyCheckOutAdapter();
    }

    public void saveItemInCart(List<String> pd){
        saveDataManager.saveFile(pd);
    }

    public ArrayList<String> loadItemForCart(){
        return saveDataManager.loadData();
    }

    public void clearCart(){
        activity.clearCart();
    }

    public int uangToInteger(String uang){
        return Integer.parseInt(uang.substring(3).replaceAll("\\.", ""));
    }
}
