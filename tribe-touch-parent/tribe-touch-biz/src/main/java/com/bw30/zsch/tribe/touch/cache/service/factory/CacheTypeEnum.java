package com.bw30.zsch.tribe.touch.cache.service.factory;

/**
 * 缓存服务产品类型枚举
 * 
 * @author ShengHao
 *
 *         2017年8月31日 - 上午11:26:41
 */
public enum CacheTypeEnum {

	REDIS("1", "Redis缓存"),

	MEMCACHE("2", "Memcache缓存");

	private String typeCode;

	private String typeDesc;

	private CacheTypeEnum(String typeCode, String typeDesc) {
		this.typeCode = typeCode;
		this.typeDesc = typeDesc;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
