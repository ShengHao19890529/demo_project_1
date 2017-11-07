package com.bw30.zsch.tribe.touch.passenger.service;

import com.bw.zsch.client.android.model.message.CreateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.CreateCommonPassengerResp;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerReq;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerResp;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerReq;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerResp;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerResp;

/**
 * 会员下面的乘机人接口服务
 * 
 * @author ShengHao
 *
 *         2017年8月4日 - 上午11:06:36
 */
public interface TribePassengerService {

	/**
	 * 创建常用乘机人
	 */
	public CreateCommonPassengerResp createCommonPassenger(CreateCommonPassengerReq createPassengerReq)
			throws Exception;

	/**
	 * 功能删除常用乘机人
	 */
	public DeleteCommonPassengerResp deleteCommonPassenger(DeleteCommonPassengerReq deletePassengerReq)
			throws Exception;

	/**
	 * 更新常用乘机人
	 */
	public UpdateCommonPassengerResp updateCommonPassenger(UpdateCommonPassengerReq updatePassengerReq)
			throws Exception;

	/**
	 * 查询常用乘机人列表
	 */
	public QueryCommonPassengerResp queryCommonPassenger(QueryCommonPassengerReq queryPassengerReq) throws Exception;

}
