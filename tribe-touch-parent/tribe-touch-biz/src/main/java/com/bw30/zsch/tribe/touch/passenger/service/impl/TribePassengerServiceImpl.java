package com.bw30.zsch.tribe.touch.passenger.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.message.CreateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.CreateCommonPassengerResp;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerReq;
import com.bw.zsch.client.android.model.message.DeleteCommonPassengerResp;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerReq;
import com.bw.zsch.client.android.model.message.QueryCommonPassengerResp;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerReq;
import com.bw.zsch.client.android.model.message.UpdateCommonPassengerResp;
import com.bw30.zsch.tribe.touch.passenger.service.TribePassengerService;

/**
 * 会员下面的乘机人接口服务实现类
 * 
 * @author ShengHao
 *
 *         2017年8月4日 - 上午11:06:36
 */
@Service
public class TribePassengerServiceImpl implements TribePassengerService {

	private final static Logger LOG = Logger.getLogger(TribePassengerServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public CreateCommonPassengerResp createCommonPassenger(CreateCommonPassengerReq createPassengerReq)
			throws Exception {

		LOG.info("invoke port create_common_passenger interface， requestParams："
				+ JSON.toJSONString(createPassengerReq));
		CreateCommonPassengerResp createCommonPassengerResp = theTribeClientService
				.create_common_passenger(createPassengerReq);
		LOG.info("invoke port create_common_passenger interface， responseInfo："
				+ JSON.toJSONString(createCommonPassengerResp));

		return createCommonPassengerResp;
	}

	@Override
	public DeleteCommonPassengerResp deleteCommonPassenger(DeleteCommonPassengerReq deletePassengerReq)
			throws Exception {

		LOG.info("invoke port delete_common_passenger interface， requestParams："
				+ JSON.toJSONString(deletePassengerReq));
		DeleteCommonPassengerResp deleteCommonPassengerResp = theTribeClientService
				.delete_common_passenger(deletePassengerReq);
		LOG.info("invoke port delete_common_passenger interface， responseInfo："
				+ JSON.toJSONString(deleteCommonPassengerResp));

		return deleteCommonPassengerResp;
	}

	@Override
	public UpdateCommonPassengerResp updateCommonPassenger(UpdateCommonPassengerReq updatePassengerReq)
			throws Exception {

		LOG.info("invoke port update_common_passenger interface， requestParams："
				+ JSON.toJSONString(updatePassengerReq));
		UpdateCommonPassengerResp updateCommonPassengerResp = theTribeClientService
				.update_common_passenger(updatePassengerReq);
		LOG.info("invoke port update_common_passenger interface， responseInfo："
				+ JSON.toJSONString(updateCommonPassengerResp));

		return updateCommonPassengerResp;
	}

	@Override
	public QueryCommonPassengerResp queryCommonPassenger(QueryCommonPassengerReq queryPassengerReq) throws Exception {

		LOG.info("invoke port query_common_passenger interface， requestParams：" + JSON.toJSONString(queryPassengerReq));
		QueryCommonPassengerResp queryCommonPassengerResp = theTribeClientService
				.query_common_passenger(queryPassengerReq);
		LOG.info("invoke port query_common_passenger interface， responseInfo："
				+ JSON.toJSONString(queryCommonPassengerResp));

		return queryCommonPassengerResp;
	}

}
