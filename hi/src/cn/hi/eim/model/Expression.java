package cn.hi.eim.model;

/**
 * 表情对象
 * 
 * @author daobo.yuan
 * 
 */
public class Expression {
	int drableCode;// 表情图像在asset中的数字名称
	String code;// 表情代码

	public Expression() {
		super();
	}

	public Expression(int drableCode, String code) {
		super();
		this.drableCode = drableCode;
		this.code = code;
	}

	public int getDrableCode() {
		return drableCode;
	}

	public void setDrableCode(int drableCode) {
		this.drableCode = drableCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
