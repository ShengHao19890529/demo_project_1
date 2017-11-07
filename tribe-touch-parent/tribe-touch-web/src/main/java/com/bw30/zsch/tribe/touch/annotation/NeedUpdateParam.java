package com.bw30.zsch.tribe.touch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要修改参数注解
 * 
 * @author ShengHao
 *
 *         2017年7月25日 - 下午6:11:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedUpdateParam {

}
