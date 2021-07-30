package com.companyname.kotlinpractice;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class Coin implements Serializable {
    private static final long serialVersionUID = -1989683519909633849L;

    @SerializedName("id")
    private String id;
    @SerializedName("icon")
    private String icon;
    @SerializedName("name")
    private String name;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("price")
    private double price;
    @SerializedName("priceChange1d")
    private double priceChange1d;

    public Boolean isFav = false;

    private String formattedDouble(Double d) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(d);
    }

    public double getPriceChange1d() {
        return priceChange1d;
    }

    public String getFormattedPriceChange1d() {
        return formattedDouble(priceChange1d) + "%";
    }

    public void setPriceChange1d(double priceChange1d) {
        this.priceChange1d = priceChange1d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return formattedDouble(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }

    public Boolean getFav() {
        return isFav;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }
}
