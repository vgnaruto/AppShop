package com.example.windows10.dapurukm;

public class MainPresenter {
    public MainPresenter(){}

    public String formatRupiah(int angka){
        String result = "";
        int count = 1;
        while(angka > 0){
            result = (angka%10) + result;
            angka/=10;
            count++;
            if(angka > 0) {
                if (count == 4) {
                    result = "." + result;
                    count = 1;
                }
            }
        }
        return "Rp "+result;
    }
}
