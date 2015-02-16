package com.yibairun.api.templates;

import android.content.Context;

import com.yibairun.api.operations.AbstractOperations;
import com.yibairun.api.operations.ProductOperations;
import com.yibairun.api.operations.UserOperations;
import com.yibairun.api.operations.YibaiRunApi;
import com.yibairun.utils.DeviceUtil;


public class YiBaiRunTemplates extends AbstractOperations implements YibaiRunApi {


	private ProductOperations productOperations;
	private UserOperations userOperations;
;


	public YiBaiRunTemplates(Context ctx) {
		super(ctx);
		initSubApis();
	}

	
	public YiBaiRunTemplates(String accessToken) {
		super(accessToken);
		initSubApis();
	}
	



	private void initSubApis() {
        deviceId = DeviceUtil.getDeviceId(ctx);
		this.productOperations = new ProductTemplates(ctx);
        this.userOperations = new UserTemplates(ctx);
        this.productOperations.setDeviceId(deviceId);
        this.userOperations.setDeviceId(deviceId);


	}


    @Override
    public void setToken(String token) {
    this.token=token;
    }

    @Override
    public void setDeviceId(String deviceId) {

    }

    @Override
    public UserOperations userOperations() {
        return userOperations;
    }

    @Override
    public ProductOperations productOperations() {
        return productOperations;
    }
}