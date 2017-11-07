package com.bw30.zsch.tribe.touch.city.service;

import com.bw.zsch.client.android.model.message.QueryAllCityReq;
import com.bw.zsch.client.android.model.message.QueryAllCityResp;
import com.bw.zsch.client.android.model.message.QueryHotCityReq;
import com.bw.zsch.client.android.model.message.QueryHotCityResp;
import com.bw.zsch.client.android.model.message.SearchCityReq;
import com.bw.zsch.client.android.model.message.SearchCityResp;

/**
 * 城市服务接口
 * 
 * @author ShengHao
 *
 *         2017年8月2日 - 下午4:34:31
 */
public interface TribeCityService {

	/**
	 * 查询国内或国际所有城市列表
	 */
	public QueryAllCityResp queryAllCity(QueryAllCityReq queryAllCityReq) throws Exception;

	/**
	 * 查询国内或者国际热门城市
	 */
	public QueryHotCityResp queryHotCity(QueryHotCityReq queryHotCityReq) throws Exception;

	/**
	 * 根据城市三字码查询城市对象
	 */
	SearchCityResp searchCity(SearchCityReq searchCityReq) throws Exception;

}
