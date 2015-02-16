package com.yibairun.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/26.
 */
public class Rate implements Serializable {
    private float min;
    private float max;
    private float rate;
    

    public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    @Override
    public String toString() {
    	return min==max?String.valueOf(max):min+"-"+max;
    }
}
