package com.bw30.zsch.tribe.touch.cache.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.bw30.zsch.tribe.touch.cache.service.CachedService;
import com.bw30.zsch.tribe.touch.utils.DateUtils;
import com.whalin.MemCached.MemCachedClient;

/**
 * memcached缓存服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:20:22
 */
// @Service
public class MemCachedServiceImpl implements CachedService {

	private final static Logger LOG = Logger.getLogger(MemCachedServiceImpl.class);

	private MemCachedClient memCachedClient;

	@Override
	public boolean setValue(String key, int seconnd, Object o) throws Exception {
		Date expireTime = new Date(new Date().getTime() + seconnd * 1000);
		boolean isSuccess = memCachedClient.set(key, o, expireTime);
		LOG.info("添加到缓存key：" + key + " ， value：" + JSON.toJSONString(o) + " ， 过期时间："
				+ DateUtils.formatDateTime(expireTime) + " ， 添加结果：" + isSuccess);
		return isSuccess;
	}

	@Override
	public boolean delValue(String key) throws Exception {
		boolean isSuccess = memCachedClient.delete(key);
		LOG.info("删除缓存key：" + key + " ， 删除结果：" + isSuccess);
		return isSuccess;
	}

	@Override
	public String getValue(String key) throws Exception {
		Object cacheObj = memCachedClient.get(key);
		String valueStr = JSON.toJSONString(cacheObj);
		LOG.info("查询缓存key：" + key + " ， 查询结果：" + valueStr);
		return valueStr;
	}

	/**
	 * 获取缓存信息
	 * 
	 * @param key
	 *            缓存key
	 * @param classT
	 *            缓存对象Class
	 * @return 缓存对象
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(String key, Class<T> classT) throws Exception {
		String className = classT == null ? "null" : classT.getName();
		LOG.info("获取缓存信息，入参key:" + key + ", class:" + className);
		String jsonStr = getValue(key);
		LOG.info("获得的缓存信息:" + jsonStr);
		if (StringUtils.isNotBlank(jsonStr)) {
			T result = null;
			if (classT == List.class || classT == ArrayList.class) {
				result = (T) JSON.parseArray(jsonStr);
			} else {
				result = (T) JSON.parseObject(jsonStr, classT);
			}
			return result;
		}
		return null;
	}

}
