package com.amon.ExchangeRates;


public class Root{

    private Time time;
    private String disclaimer;
    private Bpi bpi;

   
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the disclaimer
     */
    public String getDisclaimer() {
        return disclaimer;
    }

    /**
     * @param disclaimer the disclaimer to set
     */
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * @return the bpi
     */
    public Bpi getBpi() {
        return bpi;
    }

    /**
     * @param bpi the bpi to set
     */
    public void setBpi(Bpi bpi) {
        this.bpi = bpi;
    }
}
