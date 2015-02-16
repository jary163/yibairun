package com.yibairun.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/23.
 * author:wangzhiyuan mailto:wzyax@qq.com
 */
public class Bank implements Serializable{
    private int id;
    private String banktype;
    private String city;
    private String banknum;
    private String  bankname;
    private String province;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBanknum() {
        return banknum;
    }

    public void setBanknum(String banknum) {
        this.banknum = banknum;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
