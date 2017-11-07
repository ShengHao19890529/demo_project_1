package com.bw30.zsch.tribe.touch.cache.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.bw30.zsch.tribe.touch.cache.service.CachedService;
import redis.clients.jedis.JedisCluster;

/**
 * redis缓存服务实现类
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:18:33
 */
// @Service
public class RedisCachedServiceImpl implements CachedService {

	private final static Logger LOG = Logger.getLogger(RedisCachedServiceImpl.class);

	// @Autowired
	private JedisCluster jedisCluster;

	@Override
	public boolean setValue(String key, int seconnd, Object o) throws Exception {
		boolean result = true;
		String setResult = null;
		try {
			if (seconnd == -1) {
				setResult = jedisCluster.set(key, (String) o);
			} else {
				setResult = jedisCluster.setex(key, seconnd, (String) o);
			}
			LOG.info("插入redis缓存key：" + key + " ， value：" + o + " ， 超时时间：" + seconnd + " ， 插入结果：" + setResult);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean delValue(String key) throws Exception {
		boolean result = true;
		try {
			Long delResult = jedisCluster.del(key);
			LOG.info("删除redis缓存key：" + key + " ， 删除结果：" + delResult);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getValue(String key) throws Exception {
		Object cacheObj = jedisCluster.get(key);
		String valueStr = JSON.toJSONString(cacheObj);
		LOG.info("查询redis缓存key：" + key + " ， 查询结果：" + valueStr);
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
