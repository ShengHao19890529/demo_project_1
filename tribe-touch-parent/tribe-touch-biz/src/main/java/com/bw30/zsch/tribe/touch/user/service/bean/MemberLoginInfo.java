package com.bw30.zsch.tribe.touch.user.service.bean;

import java.io.Serializable;
import java.util.List;
import com.bw.zsch.client.android.model.common.pojo.BeneficOtherName;
import com.bw.zsch.client.android.model.common.pojo.Member;
import com.zsch.portal.model.pojo.Bw30RegisterUser;

/**
 * 会员登陆信息
 * 
 * @author ShengHao
 *
 *         2017年10月9日 - 下午3:01:19
 */
public class MemberLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062326479531671697L;

	/**
	 * 会员信息
	 */
	private Member portMember;

	/**
	 * port缓存到session中的对象
	 */
	private Bw30RegisterUser portLoginUser;

	/**
	 * 受益人
	 */
	private List<BeneficOtherName> beneficOtherNameList;

	/**
	 * 图片验证码地址
	 */
	private String verifyPicUrl;

	/**
	 * token
	 */
	private String token;

	/**
	 * 优惠券数量
	 */
	private String couponNumber;

	/**
	 * 持久化登录专用
	 */
	private String tokenId;

	/**
	 * 里程数
	 */
	private String mileageNum;

	public String getVerifyPicUrl() {
		return verifyPicUrl;
	}

	public void setVerifyPicUrl(String verifyPicUrl) {
		this.verifyPicUrl = verifyPicUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getMileageNum() {
		return mileageNum;
	}

	public void setMileageNum(String mileageNum) {
		this.mileageNum = mileageNum;
	}

	public Member getPortMember() {
		return portMember;
	}

	public void setPortMember(Member portMember) {
		this.portMember = portMember;
	}

	public Bw30RegisterUser getPortLoginUser() {
		return portLoginUser;
	}

	public void setPortLoginUser(Bw30RegisterUser portLoginUser) {
		this.portLoginUser = portLoginUser;
	}

	public List<BeneficOtherName> getBeneficOtherNameList() {
		return beneficOtherNameList;
	}

	public void setBeneficOtherNameList(List<BeneficOtherName> beneficOtherNameList) {
		this.beneficOtherNameList = beneficOtherNameList;
	}

}
