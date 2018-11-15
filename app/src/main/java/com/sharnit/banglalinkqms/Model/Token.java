package com.sharnit.banglalinkqms.Model;

public class Token {

    public int token_id;
    public int branch_id;
    public String branch_name;
    public int token_no;
    public String token_no_formated;
    public String service_date;
    public String creation_time;
    public int service_status_id;
    public String service_status;
    public String counter_no;
    public String contact_no;

    public Token() {
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public int getToken_no() {
        return token_no;
    }

    public void setToken_no(int token_no) {
        this.token_no = token_no;
    }

    public String getToken_no_formated() {
        return token_no_formated;
    }

    public void setToken_no_formated(String token_no_formated) {
        this.token_no_formated = token_no_formated;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public int getService_status_id() {
        return service_status_id;
    }

    public void setService_status_id(int service_status_id) {
        this.service_status_id = service_status_id;
    }

    public String getService_status() {
        return service_status;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }

    public String getCounter_no() {
        return counter_no;
    }

    public void setCounter_no(String counter_no) {
        this.counter_no = counter_no;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
}
