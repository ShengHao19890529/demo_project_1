package com.bw30.zsch.tribe.touch.user.service.bean;

import java.io.Serializable;

/**
 * 登录的用户信息
 * 
 * @author ShengHao
 *
 *         2017年8月7日 - 下午3:08:49
 */
public class LoginUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5392067337296402283L;

	/**
	 * 用户登录类型 会员或者非会员#LoginConstants
	 */
	private String loginType;

	/**
	 * 会员登录信息
	 */
	private MemberLoginInfo memberLoginInfo;

	/**
	 * 非会员登录信息
	 */
	private NoMemberLoginInfo noMemberLoginInfo;

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public MemberLoginInfo getMemberLoginInfo() {
		return memberLoginInfo;
	}

	public void setMemberLoginInfo(MemberLoginInfo memberLoginInfo) {
		this.memberLoginInfo = memberLoginInfo;
	}

	public NoMemberLoginInfo getNoMemberLoginInfo() {
		return noMemberLoginInfo;
	}

	public void setNoMemberLoginInfo(NoMemberLoginInfo noMemberLoginInfo) {
		this.noMemberLoginInfo = noMemberLoginInfo;
	}

}
