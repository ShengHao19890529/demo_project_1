package com.bw30.zsch.tribe.touch.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 扫描指定注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repository
public @interface TribeTouchMyBatisRepository {
	String value() default "";
}