package com.example.windows10.dapurukm;

public class Cost {
    private String value,etd,note;

    public Cost(String value, String etd, String note) {
        this.value = value;
        this.etd = etd;
        this.note = note;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEtd() {
        return etd;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
