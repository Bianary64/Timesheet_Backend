package com.example.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company_name;

    private String email;

    private String tax_id;

    private String phone;

    private String address;

    private String website;

    private String city;

    private String state;

    private String zip_code;

    private String fiscal_start_day;

    private String currency;

    private String timezone;

    private Integer status;

    public Tenant() {
    }

    public Tenant(String company_name, String email, String tax_id, String phone, String address, String website, String city,
                  String state, String zip_code, String fiscal_start_day, String currency, String timezone, Integer status) {
        this.company_name = company_name;
        this.email = email;
        this.tax_id = tax_id;
        this.phone = phone;
        this.address = address;
        this.website = website;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.fiscal_start_day = fiscal_start_day;
        this.currency = currency;
        this.timezone = timezone;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getFiscal_start_day() {
        return fiscal_start_day;
    }

    public void setFiscal_start_day(String fiscal_start_day) {
        this.fiscal_start_day = fiscal_start_day;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
