package com.bw30.zsch.tribe.touch.city.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.bw.mall.client.service.client.dubbo.TheTribeClientService;
import com.bw.zsch.client.android.model.common.enumeration.CityType;
import com.bw.zsch.client.android.model.message.QueryAllCityReq;
import com.bw.zsch.client.android.model.message.QueryAllCityResp;
import com.bw.zsch.client.android.model.message.QueryHotCityReq;
import com.bw.zsch.client.android.model.message.QueryHotCityResp;
import com.bw.zsch.client.android.model.message.SearchCityReq;
import com.bw.zsch.client.android.model.message.SearchCityReqBody;
import com.bw.zsch.client.android.model.message.SearchCityResp;
import com.bw30.zsch.tribe.touch.city.service.TribeCityService;

/**
 * 城市服务接口实现类
 * 
 * @author ShengHao
 *
 *         2017年8月2日 - 下午4:34:31
 */
@Service
public class TribeCityServiceImpl implements TribeCityService {

	private final static Logger LOG = Logger.getLogger(TribeCityServiceImpl.class);

	/**
	 * 四川航空port提供的dubbo服务
	 */
	@Autowired
	private TheTribeClientService theTribeClientService;

	@Override
	public QueryAllCityResp queryAllCity(QueryAllCityReq queryAllCityReq) throws Exception {

		LOG.info("invoke port dubbo interface query_all_city ， requestParams：" + JSON.toJSONString(queryAllCityReq));
		QueryAllCityResp queryAllCityResp = theTribeClientService.query_all_city(queryAllCityReq);
		LOG.info("invoke port dubbo interface query_all_city ， responseInfo：" + JSON.toJSONString(queryAllCityResp));

		return queryAllCityResp;
	}

	@Override
	public QueryHotCityResp queryHotCity(QueryHotCityReq queryHotCityReq) throws Exception {

		LOG.info("invoke port dubbo interface query_hot_city ， requestParams：" + JSON.toJSONString(queryHotCityReq));
		QueryHotCityResp queryHotCityResp = theTribeClientService.query_hot_city(queryHotCityReq);
		LOG.info("invoke port dubbo interface query_hot_city ， responseInfo：" + JSON.toJSONString(queryHotCityResp));

		return queryHotCityResp;
	}

	@Override
	public SearchCityResp searchCity(SearchCityReq searchCityReq) throws Exception {

		searchCityReq.getBody().setCityType(CityType.DOMESTIC);
		searchCityReq.getBody().setSearchType(SearchCityReqBody.KEYWORD_CIY);

		LOG.info("invoke port dubbo interface search_city ， requestParams：" + JSON.toJSONString(searchCityReq));
		SearchCityResp searchCityResp = theTribeClientService.search_city(searchCityReq);
		LOG.info("invoke port dubbo interface search_city ， responseInfo：" + JSON.toJSONString(searchCityResp));

		return searchCityResp;
	}

}
