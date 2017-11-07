package com.bw30.zsch.tribe.touch.cache.service;

/**
 * 缓存服务接口
 * 
 * @author ShengHao
 *
 *         2017年7月24日 - 上午9:17:56
 */
public interface CachedService {

	public boolean setValue(String key, int seconnd, Object o) throws Exception;

	public boolean delValue(String key) throws Exception;

	public String getValue(String key) throws Exception;

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
	public <T> T getValue(String key, Class<T> classT) throws Exception;

}
