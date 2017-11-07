package com.bw30.zsch.tribe.touch.config.bean;

/**
 * application.properties配置文件中的key-value映射成属性-属性值
 * 
 * @author ShengHao
 *
 *         2017年9月14日 - 下午2:20:03
 */
public class TribePropertiesConfig {

	/**
	 * 项目部署到服务器中，访问的根路径名，一般为项目应用名
	 */
	private String appPath;

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

}
