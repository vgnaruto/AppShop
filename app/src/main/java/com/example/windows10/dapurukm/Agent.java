package com.example.windows10.dapurukm;

public class Agent {
    private String code;
    private String name;
    private Costs[] costs;

    public Agent(String code, String name, Costs[] costs) {
        this.code = code;
        this.name = name;
        this.costs = costs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Costs[] getCosts() {
        return costs;
    }

    public void setCosts(Costs[] costs) {
        this.costs = costs;
    }
}
