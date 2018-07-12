package com.example.windows10.dapurukm;

public class Bank {
    private String namaBank;
    private String noRek;

    public Bank(String namaBank, String noRek) {
        this.namaBank = namaBank;
        this.noRek = noRek;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }
}
