package com.amon.ExchangeRates;


import java.util.Date;
import javax.persistence.Entity;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tech
 */
@Entity
public class USD {

    private String code;
    private String rate;
    private String description;
    private double rate_float;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rate_float
     */
    public double getRate_float() {
        return rate_float;
    }

    /**
     * @param rate_float the rate_float to set
     */
    public void setRate_float(double rate_float) {
        this.rate_float = rate_float;
    }
}
