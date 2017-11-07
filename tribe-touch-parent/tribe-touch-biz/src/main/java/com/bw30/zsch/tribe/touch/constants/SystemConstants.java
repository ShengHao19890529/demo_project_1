package com.bw30.zsch.tribe.touch.constants;

/**
 * 控制层的变量集
 * 
 * @author ShengHao
 *
 *         2017年7月25日 - 下午5:27:41
 */
public interface SystemConstants {

	// nginx配置的访问客户端IP的key值
	public final static String NGINX_REQUEST_IP_HEADER = "IPHeader";

	public final static String PLAT_FORM_ID_KEY = "platformId";
	public final static String TP_HEAD_KEY = "tpHead";

	// touch应用的登录用户session key
	public final static String TOUCH_SESSION_USER_INFO = "touchSessionUserInfo";

	/***************** 与port同步 start ********************/
	// 登陆用户
	public static final String PORT_LOGIN_USER = "LOGIN_USER";

	// tomcat配置的sessionid名称，默认JSESSIONID
	public final static String TOMCAT_SESSION_NAME_CAPITAL = "JSESSIONID";
	public final static String TOMCAT_SESSION_NAME_LOWERCASE = "jsessionid";

	// 客户端改期跳转到touch所带参数列表
	public final static String CLIENT_CHANGE_DATE_REQUEST_PARAM = "";

	/***************** 与port同步 end ********************/

	// port生成的订单号
	public final static String ORDER_ID = "orderId";
	public final static String TELEPHONE_NO = "telephoneNo";

	// 验证码session key
	public final static String PIC_VERIFY_CODE_SESSION_KEY = "picVerifyCode";

	// 非会员登陆发送短信返回验证码信息，放入缓存中，登录时候回传
	public final static String SESSION_KEY_VALIDATE_CODE = "SESSION_KEY_VALIDATE_CODE";

	/**
	 * touch项目的使用终端code
	 * 
	 * @author ShengHao
	 *
	 *         2017年8月2日 - 下午4:54:16
	 */
	public static enum TribeH5PlatFormEnum {

		PLATFORMID_TOUCH(3, "手机"),

		PLATFORM_TOUCH_PAD(6, "平板"),

		PLATFORM_THIRD_PART(11, "第三方平台"),

		PLATFORM_WECHAT(14, "微信");

		// PLATFORM_SCHK_ANDROID(,"四川航空安卓客户端")
		// PLATFORM_SCHK_IOS(,"四川航空苹果客户端")

		private int platFormCode;

		private String platFormDesc;

		private TribeH5PlatFormEnum(int platFormCode, String platFormDesc) {
			this.platFormCode = platFormCode;
			this.platFormDesc = platFormDesc;
		}

		public int getPlatFormCode() {
			return platFormCode;
		}

		public void setPlatFormCode(int platFormCode) {
			this.platFormCode = platFormCode;
		}

		public String getPlatFormDesc() {
			return platFormDesc;
		}

		public void setPlatFormDesc(String platFormDesc) {
			this.platFormDesc = platFormDesc;
		}

	}

	/**
	 * 系统符号枚举类
	 * 
	 * @author ShengHao
	 *
	 *         2017年8月7日 - 上午11:06:16
	 */
	public static enum SymbolEnum {

		ENGLISH_DOUHAO(",", "英文逗号"),

		ENGLISH_RIGHT_XIEGANG("/", "英文右斜杠"),

		ENGLISH_FENHAO(";", "英文分号");

		private String symbolValue;

		private String symbolDesc;

		private SymbolEnum(String symbolValue, String symbolDesc) {
			this.symbolValue = symbolValue;
			this.symbolDesc = symbolDesc;
		}

		public String getSymbolValue() {
			return symbolValue;
		}

		public void setSymbolValue(String symbolValue) {
			this.symbolValue = symbolValue;
		}

		public String getSymbolDesc() {
			return symbolDesc;
		}

		public void setSymbolDesc(String symbolDesc) {
			this.symbolDesc = symbolDesc;
		}

	}

}
