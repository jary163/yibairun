package com.yibairun.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/26.
 */
public class ProductInvest implements Serializable {
    private String title;	//标题
    private String stoptime;		//产品到期时间
    private String prostatus;		//投资状态
    private float income_money;		//已获得收益
    private String rate;			//年化收益
    private String money;			//投资金额
    private int date;		//投资期限
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStoptime() {
		return stoptime;
	}
	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	public String getProstatus() {
		return prostatus;
	}
	public void setProstatus(String prostatus) {
		this.prostatus = prostatus;
	}
	public float getIncome_money() {
		return income_money;
	}
	public void setIncome_money(float income_money) {
		this.income_money = income_money;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
    
    
}
