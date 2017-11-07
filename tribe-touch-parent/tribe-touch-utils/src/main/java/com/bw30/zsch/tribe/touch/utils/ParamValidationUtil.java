package com.bw30.zsch.tribe.touch.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bw30.zsch.tribe.touch.utils.annotion.validate.Credit;

/**
 * 参数非空性校验工具
 * 
 * @author ShengHao
 *
 *         2016年11月25日 - 上午10:48:49
 */
public class ParamValidationUtil {

	private static volatile Validator VALIDATOR = null;

	/**
	 * 每次都new一个验证对象，效率较低，线程安全
	 */
	private static Validator getValidatorSafe() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;

	}

	/**
	 * 只初始化一个验证对象，本地测试一万个线程同时校验没问题，效率高
	 */
	private static Validator getValidatorUnSafe() {
		if (VALIDATOR == null) {
			synchronized (ParamValidationUtil.class) {
				if (VALIDATOR == null) {
					VALIDATOR = getValidatorSafe();
				}
			}
		}
		return VALIDATOR;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> validateObject(Object obj, Class groupClass) {
		Map<String, String> errorMap = new HashMap<String, String>();
		Validator validator = getValidatorUnSafe();
		Set<ConstraintViolation<Object>> constraintViolationSet = null;
		if (groupClass == null) {
			constraintViolationSet = validator.validate(obj);
		} else {
			constraintViolationSet = validator.validate(obj, Credit.class);
		}
		for (ConstraintViolation<Object> validatorResult : constraintViolationSet) {
			errorMap.put(validatorResult.getPropertyPath().toString(), validatorResult.getMessage());
		}
		return errorMap;
	}

}
