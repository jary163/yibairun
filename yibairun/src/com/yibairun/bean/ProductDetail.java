package com.yibairun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2014/7/26.
 */
public class ProductDetail implements Serializable {
   private int id;
    private String title;
    private float money;
    private float bank_rate;
    private float zfb_rate;
    private String date;
    private int date_status;
    private int number;
    private float totalmoney;
    private String industryimg;
    private String score;
    private String fx_type;
    private String otherinfo;
    private String isrunout;
    private Rate rate;
    private List<Rate> rate_range;
    private int stoptime_status;
    private String stoptime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStoptime_status() {
        return stoptime_status;
    }

    public void setStoptime_status(int stoptime_status) {
        this.stoptime_status = stoptime_status;
    }

    public String getStoptime() {
        return stoptime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public float getBank_rate() {
		return bank_rate;
	}

	public void setBank_rate(float bank_rate) {
		this.bank_rate = bank_rate;
	}

	public float getZfb_rate() {
		return zfb_rate;
	}

	public void setZfb_rate(float zfb_rate) {
		this.zfb_rate = zfb_rate;
	}

	public float getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(float totalmoney) {
		this.totalmoney = totalmoney;
	}

	public String getIndustryimg() {
		return industryimg;
	}

	public void setIndustryimg(String industryimg) {
		this.industryimg = industryimg;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getFx_type() {
		return fx_type;
	}

	public void setFx_type(String fx_type) {
		this.fx_type = fx_type;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public String getIsrunout() {
		return isrunout;
	}

	public void setIsrunout(String isrunout) {
		this.isrunout = isrunout;
	}

	public List<Rate> getRate_range() {
		return rate_range;
	}

	public void setRate_range(List<Rate> rate_range) {
		this.rate_range = rate_range;
	}

	public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDate_status() {
        return date_status;
    }

    public void setDate_status(int date_status) {
        this.date_status = date_status;
    }



    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
