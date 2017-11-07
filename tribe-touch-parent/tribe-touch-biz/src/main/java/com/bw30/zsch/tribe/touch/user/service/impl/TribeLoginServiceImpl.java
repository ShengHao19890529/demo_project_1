package com.bw30.zsch.tribe.touch.user.service.impl;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.common.enumeration.PicCodeURLType;
import com.bw.zsch.client.android.model.common.enumeration.SendMsgKind;
import com.bw.zsch.client.android.model.common.enumeration.SendMsgType;
import com.bw.zsch.client.android.model.common.pojo.ResultMessage;
import com.bw.zsch.client.android.model.message.GetPicCodeURLReq;
import com.bw.zsch.client.android.model.message.GetPicCodeURLReqBody;
import com.bw.zsch.client.android.model.message.GetPicCodeURLResp;
import com.bw.zsch.client.android.model.message.RespBody.RespBodyStatus;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeReq;
import com.bw.zsch.client.android.model.message.SendMsgCodeWithTypeResp;
import com.bw.zsch.client.android.model.trip.user.TPLogCheckResp;
import com.bw.zsch.client.android.model.trip.user.TPLogCheckRespBody;
import com.bw.zsch.client.android.model.trip.user.TPLogCheckRespHead;
import com.bw.zsch.client.android.model.trip.user.TPLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPLoginResp;
import com.bw.zsch.client.android.model.trip.user.TPLogoutReq;
import com.bw.zsch.client.android.model.trip.user.TPLogoutResp;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginReq;
import com.bw.zsch.client.android.model.trip.user.TPNoMemberLoginResp;
import com.bw30.zsch.tribe.touch.constants.SystemConstants;
import com.bw30.zsch.tribe.touch.user.service.TribeLoginService;
import com.bw30.zsch.tribe.touch.user.service.bean.LoginConstants;
import com.bw30.zsch.tribe.touch.user.service.bean.LoginUserInfo;

/**
 * 登录接口服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月28日 - 上午9:32:47
 */
@Service
public class TribeLoginServiceImpl implements TribeLoginService {

	private final static Logger LOG = Logger.getLogger(TribeLoginServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public TPLoginResp memberLogin(TPLoginReq tpLoginReq) throws Exception {

		LOG.info("invoke port 会员登录接口 tp_login , 请求参数：" + JSON.toJSONString(tpLoginReq));
		TPLoginResp tpLoginResp = theTribeClientService.tp_login(tpLoginReq);
		LOG.info("invoke port 会员登录接口 tp_login , 响应数据：" + JSON.toJSONString(tpLoginResp));

		return tpLoginResp;
	}

	@Override
	public TPNoMemberLoginResp noMemberLogin(TPNoMemberLoginReq tpNoMemberLoginReq) throws Exception {

		LOG.info("invoke port 非会员登录接口 tp_nomember_login , 请求参数：" + JSON.toJSONString(tpNoMemberLoginReq));
		TPNoMemberLoginResp tpNoMemberLoginResp = theTribeClientService.tp_nomember_login(tpNoMemberLoginReq);
		LOG.info("invoke port 非会员登录接口 tp_nomember_login , 响应数据：" + JSON.toJSONString(tpNoMemberLoginResp));

		return tpNoMemberLoginResp;
	}

	@Override
	public TPLogCheckResp checkLogin(HttpServletRequest request) throws Exception {

		// LOG.info("调用port校验是否登录接口tp_log_check , 请求参数：" +
		// JSON.toJSONString(tpLogCheckReq));
		// TPLogCheckResp tpLogCheckResp =
		// theTribeClientService.tp_log_check(tpLogCheckReq);
		// LOG.info("调用port校验是否登录接口tp_log_check , 响应数据：" +
		// JSON.toJSONString(tpLogCheckResp));

		LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession()
				.getAttribute(SystemConstants.TOUCH_SESSION_USER_INFO);

		TPLogCheckResp tpLogCheckResp = new TPLogCheckResp();
		TPLogCheckRespHead head = new TPLogCheckRespHead();
		tpLogCheckResp.setHead(head);
		TPLogCheckRespBody body = new TPLogCheckRespBody();
		tpLogCheckResp.setBody(body);

		ResultMessage message;
		if (loginUserInfo != null) {

			LOG.info("当前用户的登录方式为：" + loginUserInfo.getLoginType());

			if (LoginConstants.LOGIN_TYPE.equals(loginUserInfo.getLoginType())) {
				body.setBeneficOtherNameList(loginUserInfo.getMemberLoginInfo().getBeneficOtherNameList());
				body.setCouponNumber(loginUserInfo.getMemberLoginInfo().getCouponNumber());
				body.setMember(loginUserInfo.getMemberLoginInfo().getPortMember());
				body.setMileageNum(loginUserInfo.getMemberLoginInfo().getMileageNum());
			}
			message = buildResultMessage(RespBodyStatus.OK.getCode(), RespBodyStatus.OK.getMsg());
		} else {
			message = buildResultMessage(RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getCode(),
					RespBodyStatus.MEMBER_NOT_LOG_IN_ERROR.getMsg());
		}
		body.setMessage(message);
		return tpLogCheckResp;
	}

	private ResultMessage buildResultMessage(int code, String msg) {
		ResultMessage message = new ResultMessage();
		message.setKeyCode(code);
		message.setValue(msg);
		return message;
	}

	@Override
	public TPLogoutResp logout(TPLogoutReq tpLogoutReq) throws Exception {

		LOG.info("invoke port tp_logout , 请求参数：" + JSON.toJSONString(tpLogoutReq));
		TPLogoutResp tpLogoutResp = theTribeClientService.tp_logout(tpLogoutReq);
		LOG.info("invoke port tp_logout , 响应数据：" + JSON.toJSONString(tpLogoutResp));

		return tpLogoutResp;
	}

	@Override
	public GetPicCodeURLResp getPicCodeUrl(GetPicCodeURLReq getPicCodeURLReq) throws Exception {

		GetPicCodeURLReq getPicCodeURLReq2 = new GetPicCodeURLReq();
		GetPicCodeURLReqBody body = new GetPicCodeURLReqBody();
		body.setType(PicCodeURLType.NO_MEMBER_LOGIN);
		getPicCodeURLReq2.setBody(body);
		getPicCodeURLReq2.setHead(getPicCodeURLReq.getHead());

		LOG.info("调用port获取图片验证码接口 , 请求参数：" + JSON.toJSONString(getPicCodeURLReq2));
		GetPicCodeURLResp getPicCodeURLResp = theTribeClientService.get_piccode_url(getPicCodeURLReq2);
		LOG.info("调用port获取图片验证码接口 , 响应数据：" + JSON.toJSONString(getPicCodeURLResp));

		return getPicCodeURLResp;
	}

	@Override
	public SendMsgCodeWithTypeResp sendMsgCode(SendMsgCodeWithTypeReq sendMsgCodeWithTypeReq) throws Exception {

		sendMsgCodeWithTypeReq.getBody().setKind(SendMsgKind.TEXT_MSG);
		sendMsgCodeWithTypeReq.getBody().setType(SendMsgType.NO_MEMBER_LOGIN);

		LOG.info("调用port短信验证码接口 , 请求参数：" + JSON.toJSONString(sendMsgCodeWithTypeReq));
		SendMsgCodeWithTypeResp sendMsgCodeWithTypeResp = theTribeClientService
				.send_msg_code_with_type(sendMsgCodeWithTypeReq);
		LOG.info("调用port短信验证码接口 , 响应数据：" + JSON.toJSONString(sendMsgCodeWithTypeResp));

		return sendMsgCodeWithTypeResp;
	}

}
