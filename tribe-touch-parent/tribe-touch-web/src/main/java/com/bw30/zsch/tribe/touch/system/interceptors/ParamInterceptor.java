package com.bw30.zsch.tribe.touch.system.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 公共拦截器，用于修改参数，暂时不用了
 * 
 * @author ShengHao
 *
 *         2017年7月25日 - 下午6:07:27
 */
@Component
public class ParamInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 项目的部署名称
	 */
	@Value("#{applicationConfig['tribe_touch_app_path']}")
	private String appPath;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 添加项目的应用名到请求中，供模板使用
		request.setAttribute("appPath", appPath);

		// if (handler instanceof HandlerMethod) {
		// HandlerMethod handlerMethod = (HandlerMethod) handler;
		// Method method = handlerMethod.getMethod();
		// NeedUpdateParam needUpdateParamAnnotation =
		// method.getAnnotation(NeedUpdateParam.class);
		// if (needUpdateParamAnnotation != null) {
		// MethodParameter[] methodParameterArr =
		// handlerMethod.getMethodParameters();
		// for (MethodParameter methodParameter : methodParameterArr) {
		// RequestBody requestBodyAnnotation =
		// methodParameter.getParameterAnnotation(RequestBody.class);
		// if (requestBodyAnnotation != null) {
		//
		// }
		// }
		// }
		// }
		return super.preHandle(request, response, handler);
	}

}
