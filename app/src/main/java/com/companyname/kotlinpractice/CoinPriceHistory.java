package com.companyname.kotlinpractice;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinPriceHistory implements Serializable {
    private static final long serialVersionUID = -2011858451043025720L;

    @SerializedName("chart")
    private List<List<Double>> record;

    public List<List<Double>> getRecord() {
        if (record == null){
            return Collections.emptyList();
        }
        return record;
    }

    public void setRecord(List<List<Double>> record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "CoinPriceHistory{" +
                "record=" + record +
                '}';
    }
}
