package com.bw.mall.client.service.data;

import java.io.Serializable;
import java.util.List;
import com.bw.zsch.client.android.model.common.pojo.BeneficOtherName;

/**
 * 放入session中的扩展缓存信息
 * 
 * @author ShengHao
 *
 */
public class SessionCacheDataEx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258684921739466018L;

	/**
	 * 受益人列表信息
	 */
	private List<BeneficOtherName> beneficList;

	/**
	 * 是否允许免兑 S000表示成功<br>
	 * S001表示登录成功，不允许兑奖
	 */
	private String mileageStatus;

	/**
	 * 是否能网上兑奖
	 */
	private boolean canWebRedeem;

	/**
	 * 是否实名
	 */
	private boolean applyWebRedeem;

	// 用户可用的里程数
	private String mileageNum;

	// 0 男 1 女
	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<BeneficOtherName> getBeneficList() {
		return beneficList;
	}

	public void setBeneficList(List<BeneficOtherName> beneficList) {
		this.beneficList = beneficList;
	}

	public String getMileageStatus() {
		return mileageStatus;
	}

	public void setMileageStatus(String mileageStatus) {
		this.mileageStatus = mileageStatus;
	}

	public boolean getCanWebRedeem() {
		return canWebRedeem;
	}

	public void setCanWebRedeem(boolean canWebRedeem) {
		this.canWebRedeem = canWebRedeem;
	}

	public String getMileageNum() {
		return mileageNum;
	}

	public void setMileageNum(String mileageNum) {
		this.mileageNum = mileageNum;
	}

	public boolean getApplyWebRedeem() {
		return applyWebRedeem;
	}

	public void setApplyWebRedeem(boolean applyWebRedeem) {
		this.applyWebRedeem = applyWebRedeem;
	}

}
