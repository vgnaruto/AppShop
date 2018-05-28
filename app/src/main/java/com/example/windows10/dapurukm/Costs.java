package com.example.windows10.dapurukm;

public class Costs {
    private String service,description;
    private Cost[] cost;

    public Costs(String service, String description, Cost[] costs) {
        this.service = service;
        this.description = description;
        this.cost = costs;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cost[] getCosts() {
        return cost;
    }

    public void setCosts(Cost[] costs) {
        this.cost = costs;
    }
}
