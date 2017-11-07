package com.bw30.zsch.tribe.touch.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring静态获取bean工具
 * 
 * @author ShengHao
 *
 *         2017年8月31日 - 下午12:20:19
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <D> D getBean(Class<D> clazz) {
		return (D) context.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <D> D getBean(String name, Class<D> clazz) {
		return (D) context.getBean(name);
	}

}
