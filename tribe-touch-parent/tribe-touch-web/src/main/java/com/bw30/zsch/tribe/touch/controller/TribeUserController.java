package com.bw30.zsch.tribe.touch.controller;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.zsch.client.android.model.common.enumeration.SendMsgType;
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
import com.bw30.zsch.tribe.touch.constants.SystemConstants;
import com.bw30.zsch.tribe.touch.controller.bean.TribeTouchResponseBean;
import com.bw30.zsch.tribe.touch.user.service.TribeLoginService;
import com.bw30.zsch.tribe.touch.user.service.bean.LoginConstants;
import com.bw30.zsch.tribe.touch.user.service.bean.LoginUserInfo;
import com.bw30.zsch.tribe.touch.user.service.bean.MemberLoginInfo;
import com.bw30.zsch.tribe.touch.user.service.bean.NoMemberLoginInfo;
import com.bw30.zsch.tribe.touch.web.utils.ThreadLocalUtils;
import com.zsch.portal.model.pojo.Bw30RegisterUser;

/**
 * 用户控制器
 * 
 * @author ShengHao
 *
 *         2017年7月28日 - 上午9:31:39
 */
@Controller
@RequestMapping(value = "/tribe/user")
public class TribeUserController extends TribeBaseController {

	private final static Logger LOG = Logger.getLogger(TribeUserController.class);

	// 用户页面的根路径
	private final static String USER_ROOT_DIR = "/user";
	// 登录页面路径
	private final static String GO_LOGIN_PAGE_URL = USER_ROOT_DIR + "/loginPage.vm";

	/**
	 * 登录接口服务
	 */
	@Autowired
	private TribeLoginService tribeLoginService;

	/**
	 * 跳转到登录页面
	 */
	@RequestMapping("/goLoginPage")
	public ModelAndView goLoginPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return buidlModelAndView(GO_LOGIN_PAGE_URL);
	}

	/**
	 * 会员登陆操作
	 */
	@RequestMapping("/memberLogin")
	@ResponseBody
	public TribeTouchResponseBean<TPLoginResp> memberLogin(@RequestBody TPLoginReq tpLoginReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
		TPLoginResp tpLoginResp = tribeLoginService.memberLogin(tpLoginReq);

		// 登录成功，则把当前登录的用户信息放入session中
		if (parseInvokePortDubboResult(tpLoginResp.getBody())) {

			LoginUserInfo loginUserInfo = new LoginUserInfo();
			MemberLoginInfo memberLoginInfo = new MemberLoginInfo();
			memberLoginInfo.setPortMember(tpLoginResp.getBody().getMember());
			memberLoginInfo.setBeneficOtherNameList(tpLoginResp.getBody().getBeneficOtherNameList());
			memberLoginInfo.setCouponNumber(tpLoginResp.getBody().getCouponNumber());
			memberLoginInfo.setMileageNum(tpLoginResp.getBody().getMileageNum());
			memberLoginInfo.setToken(tpLoginResp.getBody().getToken());
			memberLoginInfo.setTokenId(tpLoginResp.getBody().getTokenId());
			memberLoginInfo.setVerifyPicUrl(tpLoginResp.getBody().getVerifyPicUrl());

			if (StringUtils.isNotEmpty(RpcContext.getContext().getAttachment(SystemConstants.PORT_LOGIN_USER))) {
				Bw30RegisterUser portLoginUser = JSON.parseObject(
						RpcContext.getContext().getAttachment(SystemConstants.PORT_LOGIN_USER), Bw30RegisterUser.class);
				memberLoginInfo.setPortLoginUser(portLoginUser);
			}
			loginUserInfo.setMemberLoginInfo(memberLoginInfo);
			loginUserInfo.setLoginType(LoginConstants.LOGIN_TYPE);

			request.getSession().setAttribute(SystemConstants.TOUCH_SESSION_USER_INFO, loginUserInfo);
		}

		TribeTouchResponseBean<TPLoginResp> responseBean = new TribeTouchResponseBean<TPLoginResp>();
		responseBean.setResponseObj(tpLoginResp);
		responseBean.setExtJsonObj(buildExtJsonObjString(request));

		return responseBean;
	}

	/**
	 * 为会员和非会员登陆添加扩展数据，满足touch的个性化要求
	 */
	private JSONObject buildExtJsonObjString(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SystemConstants.TOMCAT_SESSION_NAME_LOWERCASE, request.getSession().getId());
		return jsonObject;
	}

	/**
	 * 非会员登陆操作
	 */
	@RequestMapping("/noMemberLogin")
	@ResponseBody
	public TribeTouchResponseBean<TPNoMemberLoginResp> noMemberLogin(@RequestBody TPNoMemberLoginReq tpNoMemberLoginReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		String validCodeObjKey = buildValidCodeObjKey(tpNoMemberLoginReq.getBody().getPhone());
		String validateCodeValue = (String) request.getSession()
				.getAttribute(SystemConstants.SESSION_KEY_VALIDATE_CODE);

		LOG.info("非会员登陆，传给port参数的手机验证码组装key：" + validCodeObjKey + " ， value：" + validateCodeValue);

		RpcContext.getContext().setAttachment(validCodeObjKey, validateCodeValue);

		TPNoMemberLoginResp tpNoMemberLoginResp = tribeLoginService.noMemberLogin(tpNoMemberLoginReq);

		// 登录成功，则把当前登录的用户信息放入session中
		if (parseInvokePortDubboResult(tpNoMemberLoginResp.getBody())) {
			LoginUserInfo loginUserInfo = new LoginUserInfo();
			NoMemberLoginInfo noMemberLoginInfo = new NoMemberLoginInfo();
			noMemberLoginInfo.setPhone(tpNoMemberLoginReq.getBody().getPhone());

			loginUserInfo.setLoginType(LoginConstants.LOGIN_TYPE_VALUE_TRIBE_GUEST);
			loginUserInfo.setNoMemberLoginInfo(noMemberLoginInfo);

			request.getSession().setAttribute(SystemConstants.TOUCH_SESSION_USER_INFO, loginUserInfo);
		}

		TribeTouchResponseBean<TPNoMemberLoginResp> responseBean = new TribeTouchResponseBean<TPNoMemberLoginResp>();
		responseBean.setResponseObj(tpNoMemberLoginResp);
		responseBean.setExtJsonObj(buildExtJsonObjString(request));

		return responseBean;
	}

	/**
	 * 校验用户是否登录操作
	 */
	@RequestMapping("/checkLogin")
	@ResponseBody
	public TPLogCheckResp checkLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return tribeLoginService.checkLogin(request);
	}

	/**
	 * 登出操作
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/logout")
	@ResponseBody
	public TPLogoutResp logout(@RequestBody TPLogoutReq tpLogoutReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		TPLogoutResp tpLogoutResp = null;
		try {
			tpLogoutResp = tribeLoginService.logout(tpLogoutReq);
		} finally {
			Enumeration enumeration = request.getSession().getAttributeNames();
			while (enumeration.hasMoreElements()) {
				request.getSession().removeAttribute(enumeration.nextElement().toString());
			}
			request.getSession().invalidate();
		}
		return tpLogoutResp;
	}

	/**
	 * 获取生成验证码链接
	 */
	@RequestMapping("/getPicCodeUrl")
	@ResponseBody
	public GetPicCodeURLResp getPicCodeUrl(@RequestBody GetPicCodeURLReq getPicCodeURLReq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return tribeLoginService.getPicCodeUrl(getPicCodeURLReq);
	}

	/**
	 * 发送短信，同时会校验验证码<br>
	 * 下面是请求数据<br>
	 * { "body": { "kind": "TEXT_MSG", "mobile": "18502504332", "type":
	 * "NO_MEMBER_LOGIN", "verifyCode": "nvhp" }, "head": { "action":
	 * "SEND_MSG_CODE_WITH_TYPE", "appVersion": "4.0.3", "channelId": "91",
	 * "imie": "860797035685452", "jpushId": "170976fa8a8745a6bce",
	 * "macAddress": "02:00:00:00:00:00", "platformId": 0, "proVersion": "2.0",
	 * "sessionId": "35F8A14C7AE8ECC30965D980443DD442-n2", "sign":
	 * "45921cb5296e527f705961a7cb7ed94989761ac0e79ea8d4b87429d43be0bb347831081b2d491e38acf3ef83a51aab43bb3007bed8ff8c75310bb6f114a5e364f7a30b83c32040f12c166be26735ffd12bb55e62cc748ada18ec3e6e7bb5d662ac11e6a5ecdab4526a7f9e32b49a1d1d2f3c70a78f39f056671c6f012a3da73c",
	 * "timestamp": "2017-10-09 15:48:50", "transActionId":
	 * "c8c6687b-a012-48b8-ab22-f0d06fa0211d" } }
	 */
	@RequestMapping("/sendMsgCode")
	@ResponseBody
	public SendMsgCodeWithTypeResp sendMsgCode(@RequestBody SendMsgCodeWithTypeReq sendMsgCodeWithTypeReq,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		SendMsgCodeWithTypeResp sendMsgCodeWithTypeResp = tribeLoginService.sendMsgCode(sendMsgCodeWithTypeReq);
		String validCodeObjKey = buildValidCodeObjKey(sendMsgCodeWithTypeReq.getBody().getMobile());
		String validateCodeValue = sendMsgCodeWithTypeResp.getBody().getAttachments().get(validCodeObjKey);

		LOG.info("发送短信验证码，手机验证码组装key：" + validCodeObjKey + " ， port返回验证码的value：" + validateCodeValue);

		if (StringUtils.isNotEmpty(validateCodeValue)) {
			request.getSession().setAttribute(SystemConstants.SESSION_KEY_VALIDATE_CODE, validateCodeValue);
		}
		return sendMsgCodeWithTypeResp;

	}

	/**
	 * 组装短信验证码存储key<br>
	 * touch调用port发送短信接口，port将短信验证码数据返回给touch，touch存储到session中，<br>
	 * 用户点击非会员登陆按钮时，将用户填写的验证码和session中的验证码作为参数传给port做校验
	 * 
	 * @param mobile
	 *            非会员用户登陆的手机号
	 * @return port返回的待回传验证码数据的key
	 */
	private String buildValidCodeObjKey(String mobile) {
		return ThreadLocalUtils.getSessionId() + "-" + "VALID_CODE_SMS" + "-" + SendMsgType.NO_MEMBER_LOGIN.getKey()
				+ "-" + mobile;
	}

}
