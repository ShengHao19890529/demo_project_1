package com.bw30.zsch.tribe.touch.cache.service.factory;

import com.bw30.zsch.tribe.touch.cache.service.CachedService;
import com.bw30.zsch.tribe.touch.cache.service.impl.MemCachedServiceImpl;
import com.bw30.zsch.tribe.touch.cache.service.impl.RedisCachedServiceImpl;
import com.bw30.zsch.tribe.touch.service.util.SpringContextHolder;

/**
 * 缓存服务生成静态工厂类
 * 
 * @author ShengHao
 *
 *         2017年8月31日 - 上午11:23:23
 */
public class CacheFactory {

	/**
	 * 根据所传缓存类型，返回缓存服务
	 */
	public static CachedService getCacheServiceInstance(CacheTypeEnum cacheTypeEnum) {
		if (cacheTypeEnum == null) {
			return null;
		}
		if (cacheTypeEnum.equals(CacheTypeEnum.REDIS)) {
			return getRedisCachedService();
		} else if (cacheTypeEnum.equals(CacheTypeEnum.MEMCACHE)) {
			return getMemCachedService();
		}
		return null;
	}

	/**
	 * 获取redis缓存服务
	 */
	public static CachedService getRedisCachedService() {
		return SpringContextHolder.getBean(RedisCachedServiceImpl.class);
	}

	/**
	 * 获取memcache缓存服务
	 */
	public static CachedService getMemCachedService() {
		return SpringContextHolder.getBean(MemCachedServiceImpl.class);
	}

}
