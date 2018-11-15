package com.sharnit.banglalinkqms.Adapter;

public class ServiceType {

    String name;
    int id;

    public ServiceType() {
    }

    public ServiceType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
