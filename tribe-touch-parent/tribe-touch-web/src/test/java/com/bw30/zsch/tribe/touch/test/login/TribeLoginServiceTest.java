package com.bw30.zsch.tribe.touch.test.login;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bw.zsch.client.android.model.common.enumeration.SendMsgKind;
import com.bw.zsch.client.android.model.common.enumeration.SendMsgType;
import com.bw.zsch.client.android.model.message.GetPicCodeURLReq;
import com.bw.zsch.client.android.model.message.GetPicCodeURLReqHead;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeReq;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeReqBody;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeReqHead;
import com.bw.zsch.client.android.model.trip.user.TPLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPLoginReqBody;
import com.bw.zsch.client.android.model.trip.user.TPLoginReqHead;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginReqBody;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginReqHead;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;
import com.bw30.zsch.tribe.touch.user.service.TribeLoginService;
import com.bwhk.ticket.gateway.msg.LoginRequest;

/**
 * 登录接口服务测试类
 * 
 * @author ShengHao
 *
 *         2017年7月31日 - 上午10:30:48
 */
public class TribeLoginServiceTest extends BaseJunit4Test {

	// 刘羽账号
	// private final static String LOGIN_LIUYU_USERNAME = "959312244";
	// private final static String LOGIN_LIUYU_PASSWORD = "ly850313";

	// 裴国顺账号
	// private final static String LOGIN_PGS_USERNAME = "716070246";
	// private final static String LOGIN_PGS_PASSWORD = "pgs123456";

	// 杨岑账号
	private final static String LOGIN_YC_USERNAME = "702568311";
	private final static String LOGIN_YC_PASSWORD = "yc123456";

	@Autowired
	private TribeLoginService tribeLoginService;

	@Test
	public void test_tp_login() throws Exception {

		TPLoginReq req = new TPLoginReq();
		TPLoginReqHead head = new TPLoginReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		req.setHead(head);

		TPLoginReqBody body = new TPLoginReqBody();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setLoginUsername(LOGIN_YC_USERNAME);
		loginRequest.setLoginPassword(LOGIN_YC_PASSWORD);
		body.setLoginRequest(loginRequest);
		req.setBody(body);

		tribeLoginService.memberLogin(req);
	}

	@Test
	public void test_getPicCodeUrl() throws Exception {
		GetPicCodeURLReq req = new GetPicCodeURLReq();
		GetPicCodeURLReqHead head = new GetPicCodeURLReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		req.setHead(head);
		tribeLoginService.getPicCodeUrl(req);

	}

	@Test
	public void test_sendMsgCode() throws Exception {
		SendMsgCodeWithTypeReq req = new SendMsgCodeWithTypeReq();
		SendMsgCodeWithTypeReqHead head = new SendMsgCodeWithTypeReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		req.setHead(head);

		SendMsgCodeWithTypeReqBody body = new SendMsgCodeWithTypeReqBody();
		body.setKind(SendMsgKind.TEXT_MSG);
		body.setMobile("18502504332");
		body.setType(SendMsgType.NO_MEMBER_LOGIN);
		req.setBody(body);

		tribeLoginService.sendMsgCode(req);

	}

	@Test
	public void test_noMemberLogin() throws Exception {
		TPNoMemberLoginReq tpNoMemberLoginReq = new TPNoMemberLoginReq();
		TPNoMemberLoginReqHead head = new TPNoMemberLoginReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		tpNoMemberLoginReq.setHead(head);
		TPNoMemberLoginReqBody body = new TPNoMemberLoginReqBody();
		body.setPhone("18502504332");
		body.setVerifyCode("448470");
		tpNoMemberLoginReq.setBody(body);
		tribeLoginService.noMemberLogin(tpNoMemberLoginReq);
	}

}
