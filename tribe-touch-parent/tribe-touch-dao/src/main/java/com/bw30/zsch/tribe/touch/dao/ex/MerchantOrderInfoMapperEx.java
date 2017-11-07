package com.bw30.zsch.tribe.touch.dao.ex;

import org.apache.ibatis.annotations.Param;

import com.bw30.zsch.tribe.touch.annotation.TribeTouchMyBatisRepository;
import com.bw30.zsch.tribe.touch.dao.bean.MerchantOrderInfo;

@TribeTouchMyBatisRepository
public interface MerchantOrderInfoMapperEx {

	/**
	 * 根据订单号查询订单信息
	 */
	MerchantOrderInfo selectByOrderNo(@Param(value = "merchantTradeSn") String merchantTradeSn);

	/**
	 * 根据订单号做敏感性更新
	 */
	int updateByOrderNoSelective(MerchantOrderInfo updateOutTradeNoBean);

	/**
	 * 根据订单号做更新
	 */
	int updateByOrderNo(MerchantOrderInfo updateOutTradeNoBean);

}
