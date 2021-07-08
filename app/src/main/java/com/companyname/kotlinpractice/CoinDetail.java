package com.companyname.kotlinpractice;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CoinDetail implements Serializable {
    private static final long serialVersionUID = -6872045662641317212L;

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
    @SerializedName("priceBtc")
    private double priceBtc;
    @SerializedName("volume")
    private double volume;
    @SerializedName("marketCap")
    private double marketCap;
    @SerializedName("availableSupply")
    private double availableSupply;
    @SerializedName("totalSupply")
    private double totalSupply;
    @SerializedName("priceChange1h")
    private double priceChange1h;
    @SerializedName("priceChange1d")
    private double priceChange1d;
    @SerializedName("priceChange1w")
    private double priceChange1w;
    @SerializedName("websiteUrl")
    private String websiteUrl;
    @SerializedName("redditUrl")
    private String redditUrl;
    @SerializedName("twitterUrl")
    private String twitterUrl;
    @SerializedName("contractAddress")
    private String contractAddress;
    @SerializedName("decimals")
    private Integer decimals;
    @SerializedName("exp")
    private List<String> exp;

    public String getPriceBtc() {
        return String.valueOf(priceBtc);
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String getVolume() {
        return String.valueOf(volume);
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getMarketCap() {
        return String.valueOf(marketCap);
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public String getAvailableSupply() {
        return String.valueOf(availableSupply);
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public String getTotalSupply() {
        return String.valueOf(totalSupply);
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getPriceChange1h() {
        return String.valueOf(priceChange1h);
    }

    public void setPriceChange1h(double priceChange1h) {
        this.priceChange1h = priceChange1h;
    }

    public String getPriceChange1w() {
        return String.valueOf(priceChange1w);
    }

    public void setPriceChange1w(double priceChange1w) {
        this.priceChange1w = priceChange1w;
    }

    public String getWebsiteUrl() {
        if (websiteUrl == null) {
            return "-";
        }
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getRedditUrl() {
        if (redditUrl == null) {
            return "-";
        }
        return redditUrl;
    }

    public void setRedditUrl(String redditUrl) {
        this.redditUrl = redditUrl;
    }

    public String getTwitterUrl() {
        if (twitterUrl == null) {
            return "-";
        }
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getContractAddress() {
        if (contractAddress == null) {
            return "-";
        }
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getDecimals() {
        if (decimals == null) {
            return "0";
        }
        return String.valueOf(decimals);
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public List<String> getExp() {

        if (exp == null) {
            return Collections.emptyList();
        }
        return exp;
    }

    public void setExp(List<String> exp) {
        this.exp = exp;
    }

    public String getPriceChange1d() {
        return String.valueOf(priceChange1d);
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

    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CoinDetail{" +
                "id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", priceBtc=" + priceBtc +
                ", volume=" + volume +
                ", marketCap=" + marketCap +
                ", availableSupply=" + availableSupply +
                ", totalSupply=" + totalSupply +
                ", priceChange1h=" + priceChange1h +
                ", priceChange1d=" + priceChange1d +
                ", priceChange1w=" + priceChange1w +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", redditUrl='" + redditUrl + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", decimals=" + decimals +
                ", exp=" + exp +
                '}';
    }
}
