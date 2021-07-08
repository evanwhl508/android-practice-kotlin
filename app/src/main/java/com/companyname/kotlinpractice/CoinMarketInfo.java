package com.companyname.kotlinpractice;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CoinMarketInfo implements Serializable {
    private static final long serialVersionUID = -4380367123145423036L;

    @SerializedName("price")
    private double price;
    @SerializedName("exchange")
    private String exchange;
    @SerializedName("pair")
    private String pair;
    @SerializedName("pairPrice")
    private double pairPrice;
    @SerializedName("volume")
    private double volume;

    private String formattedDouble(Double d) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(d);
    }

    public String getPrice() {
        return formattedDouble(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getPairPrice() {
        return formattedDouble(pairPrice);
    }

    public void setPairPrice(double pairPrice) {
        this.pairPrice = pairPrice;
    }

    public String getVolume() {
        return formattedDouble(volume);
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "CoinMarketInfo{" +
                "price=" + price +
                ", exchange='" + exchange + '\'' +
                ", pair='" + pair + '\'' +
                ", pairPrice=" + pairPrice +
                ", volume=" + volume +
                '}';
    }
}
