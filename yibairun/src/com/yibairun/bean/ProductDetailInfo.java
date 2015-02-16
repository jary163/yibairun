package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2014/7/26.
 * mailto:wzyax@qq.com
 */
public class ProductDetailInfo extends StatusMessage {
    @SerializedName("data")
    private ProductDetail productDetail;

	public ProductDetail getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

    
}
