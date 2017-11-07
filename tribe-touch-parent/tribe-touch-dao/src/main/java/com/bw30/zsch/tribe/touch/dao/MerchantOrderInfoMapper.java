package com.bw30.zsch.tribe.touch.dao;

import com.bw30.zsch.tribe.touch.annotation.TribeTouchMyBatisRepository;
import com.bw30.zsch.tribe.touch.dao.bean.MerchantOrderInfo;

@TribeTouchMyBatisRepository
public interface MerchantOrderInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(MerchantOrderInfo record);

	int insertSelective(MerchantOrderInfo record);

	MerchantOrderInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(MerchantOrderInfo record);

	int updateByPrimaryKey(MerchantOrderInfo record);
}