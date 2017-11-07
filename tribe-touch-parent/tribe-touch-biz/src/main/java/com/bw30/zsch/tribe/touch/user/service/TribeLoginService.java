package com.bw30.zsch.tribe.touch.user.service;

import javax.servlet.http.HttpServletRequest;

import com.bw.zsch.client.android.model.message.GetPicCodeURLReq;
import com.bw.zsch.client.android.model.message.GetPicCodeURLResp;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeReq;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeResp;
import com.bw.zsch.client.android.model.trip.user.TPLogCheckResp;
import com.bw.zsch.client.android.model.trip.user.TPLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPLoginResp;
import com.bw.zsch.client.android.model.trip.user.TPLogoutReq;
import com.bw.zsch.client.android.model.trip.user.TPLogoutResp;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginResp;

/**
 * 登录接口服务
 * 
 * @author ShengHao
 *
 *         2017年7月28日 - 上午9:32:20
 */
public interface TribeLoginService {

	/**
	 * 会员用户登录
	 */
	public TPLoginResp memberLogin(TPLoginReq tpLoginReq) throws Exception;

	/**
	 * 非会员用户登录
	 */
	public TPNoMemberLoginResp noMemberLogin(TPNoMemberLoginReq tpNoMemberLoginReq) throws Exception;

	/**
	 * 校验用户是否登录
	 */
	public TPLogCheckResp checkLogin(HttpServletRequest request) throws Exception;

	/**
	 * 用户登出
	 */
	public TPLogoutResp logout(TPLogoutReq tpLogoutReq) throws Exception;

	/**
	 * 获取图片验证码链接地址
	 */
	public GetPicCodeURLResp getPicCodeUrl(GetPicCodeURLReq getPicCodeURLReq) throws Exception;

	/**
	 * 发送短信验证码，包括图片验证码，防刷
	 */
	public SendMsgCodeWithTypeResp sendMsgCode(SendMsgCodeWithTypeReq sendMsgCodeWithTypeReq) throws Exception;

}
