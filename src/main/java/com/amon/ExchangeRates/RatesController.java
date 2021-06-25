package com.amon.ExchangeRates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tech
 */
@RestController
public class RootController extends Thread {

    Bpi bpi = new Bpi();
    Root root = new Root();
    Time time = new Time();
    USD usd = new USD();
    List<Root> resultList = new ArrayList<>();

    public static void main(String[] args) {
        RatesController rts = new RatesController();
        rts.start();
    }

    public void run() {
        try {
            while (true) {
                URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice/USD.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                StringBuilder response = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    response.append(output);
                }
                RatesController rate = new RatesController();
                rate.populateRates(response.toString());
                conn.disconnect();
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void populateRates(String json) {

        ///Redis would come into use here instead of array list
        try {
            JSONObject obj = new JSONObject(json);
            time.setUpdated(obj.getJSONObject("time").getString("updated"));
            time.setUpdatedISO(obj.getJSONObject("time").getString("updatedISO"));
            time.setUpdateduk(obj.getJSONObject("time").getString("updateduk"));
            usd.setCode(obj.getJSONObject("bpi").getJSONObject("USD").getString("code"));
            usd.setRate(obj.getJSONObject("bpi").getJSONObject("USD").getString("rate"));
            usd.setCode(obj.getJSONObject("bpi").getJSONObject("USD").getString("description"));
            usd.setRate_float(obj.getJSONObject("bpi").getJSONObject("USD").getDouble("rate_float"));
            bpi.setUsd(usd);
            root.setBpi(bpi);
            root.setDisclaimer(obj.getString("disclaimer"));
            root.setTime(time);
            resultList.add(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GetMapping(value = "/obtainExchangeWithDuration")
    public String obtainExchangeWithDuration(Date fromDate, Date toDate) {
        StringBuilder br = new StringBuilder();
        try {
            for (Root res : resultList) {
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(res.getRoot().getTime().getUpdatedISO());
                    if (date.after(fromDate) && date.before(toDate)) {
                        br.append("Date :" + res.getRoot().getTime().getUpdatedISO() + " 1 BTC = " + res.getRoot().getBpi().getUsd().getRate() + " USD\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return "000:" + br.toString();
        } catch (Exception ex) {
            return "091" + ex.getMessage();
        }
    }

    @GetMapping("/obtainExchangeRate")
    public String obtainExchangeRate() {
        try {
            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice/USD.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
            return "000:" + "1 BTC = " + response.toString() + " USD";
        } catch (IOException ex) {
            return "091:" + ex.getMessage();
        }
    }

}
