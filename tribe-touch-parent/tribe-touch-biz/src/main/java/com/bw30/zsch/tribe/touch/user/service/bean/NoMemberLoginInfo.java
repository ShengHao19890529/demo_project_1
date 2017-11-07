package com.bw30.zsch.tribe.touch.user.service.bean;

import java.io.Serializable;

/**
 * 非会员登陆信息
 * 
 * @author ShengHao
 *
 *         2017年10月9日 - 下午3:02:00
 */
public class NoMemberLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4627311152463992298L;

	/**
	 * 手机号
	 */
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
