package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2014/7/26.
 */
public class ProductList extends StatusMessage {
    @SerializedName("data")
    private List<Product> productList;
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
