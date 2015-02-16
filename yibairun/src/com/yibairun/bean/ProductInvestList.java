package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2014/7/26.
 */
public class ProductInvestList extends StatusMessage {
    @SerializedName("data")
    private List<ProductInvest> productInvestList;
    public List<ProductInvest> getProductList() {
        return productInvestList;
    }

    public void setProductList(List<ProductInvest> productList) {
        this.productInvestList = productList;
    }

}
