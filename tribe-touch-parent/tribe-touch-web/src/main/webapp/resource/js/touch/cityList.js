(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory() :
  typeof define === 'function' && define.amd ? define(factory) :
  (factory());
}(this, (function () { 'use strict';

var defineProperty = function (obj, key, value) {
  if (key in obj) {
    Object.defineProperty(obj, key, {
      value: value,
      enumerable: true,
      configurable: true,
      writable: true
    });
  } else {
    obj[key] = value;
  }

  return obj;
};

var _message;

var message = (_message = {
  'OK': '',
  'SEND_VERIFY_CODE_SUCCESS': '',
  'CHECK_IN_SUCCEED': '',
  'SUBMIT_FEED_BACK_SUCCEED': '',
  'DB_OPER_FAILED': '数据库操作失败',
  'NETWORK_BUSY': '网络繁忙，请稍后重试',
  'QUERY_FLIGHT_FAILED': '查询不到航班信息',
  'QUERY_FLIGHT_FAILED_CAN_EXCHANE': '查询不到可改期航班信息',
  'MEMBER_NOT_LOG_IN_ERROR': '请您先登录'
}, defineProperty(_message, 'QUERY_FLIGHT_FAILED_CAN_EXCHANE', '查询不到可改期航班信息'), defineProperty(_message, 'MEMBER_NOT_LOG_IN_ERROR', '请您先登录'), defineProperty(_message, 'QUERY_MEMBER_FAILED', '没有查询到该常旅客信息'), defineProperty(_message, 'MEMBER_OR_PSD_ERROR', '用户名或密码错误，请查证后重新输入'), defineProperty(_message, 'PSD_ERROR', '您输入的旧密码不正确，请重新输入'), defineProperty(_message, 'INPUT_RIGHT_MEMBER_ID', '请输入正确的会员卡号'), defineProperty(_message, 'LOG_IN_ERROR_MANY_TIMES', '请输入验证码'), defineProperty(_message, 'LOGIN_PSD_ERROR', '密码错误，请重新输入'), defineProperty(_message, 'LOGIN_TOKEN_ERROR', '登录失效,请重新登录'), defineProperty(_message, 'VERIFY_CODE_NO_MATCH_ERROR', '验证码错误'), defineProperty(_message, 'VERIFY_CODE_INVALID_ERROR', '验证码失效'), defineProperty(_message, 'VERIFY_CODE_MOBILE_NOT_FIND', '系统未找到您的手机号信息'), defineProperty(_message, 'MOBILE_REGISTERWD_ERROR', '手机号码已被注册'), defineProperty(_message, 'FOID_NO_REGISTERWD_ERROR', '证件号码已被注册'), defineProperty(_message, 'EXCHANGE_CHECK_FAILED', '很抱歉，您的累计里程达5000公里或有效乘机3次，才能进行首次兑换'), defineProperty(_message, 'CREATE_ORDER_FAILED_NOT_ENOUGH_AMOUNT', '生成订单失败，余票不足'), defineProperty(_message, 'CREATE_ORDER_FAILED_NOT_ENOUGH_MILES', '您的里程余额不足'), defineProperty(_message, 'QUERY_ORDER_FAILED', '未查询到订单记录'), defineProperty(_message, 'QUERY_ORDER_DETAIL_FAILED', '未查询到该订单记录'), defineProperty(_message, 'QUERY_FLIGHT_STATUS_FAILED', '查询不到航班信息'), defineProperty(_message, 'SEAT_IS_SAVED', '您选择的座位已经被占用请重新选择'), defineProperty(_message, 'CHECK_IN_FAILED', '值机失败'), defineProperty(_message, 'CREATE_MILES_RECORD_INFO_ERROR', '补登信息有误，无法验证信息有效性，请与川航常旅客服务中心联系'), defineProperty(_message, 'NAME_NOT_MATCH_FOID', '旅客姓名与证件不匹配'), defineProperty(_message, 'ORDERID_NOT_MATCH_MOBILE', '订单号和手机号不匹配'), defineProperty(_message, 'GET_JOURNEY_FAILED', '很抱歉！无法提取到您的行程信息，请检查你的输入是否正确。'), defineProperty(_message, 'GET_BOARDING_PASS_ERROR', '很抱歉，暂时无法获取到登机牌信息！'), defineProperty(_message, 'DEAD_LINE_BEFORE_TODAY_ERROR', '截止日期不能大于当前日期'), defineProperty(_message, 'REFUND_FAILED', '退票失败，请先取消值机后再进行退票'), defineProperty(_message, 'FOID_OR_BIRTHDAY_ERROR', '证件号码或生日错误，请确认后重新输入！'), defineProperty(_message, 'QUERY_WEATHER_FAILED', '暂时无法获取本地区天气信息'), defineProperty(_message, 'HEAD_ERROR', '请求头格式错误'), defineProperty(_message, 'SIGN_ERROR', '签名验证异常'), defineProperty(_message, 'VERIFY_CODE_NULL_ERROR', '验证码不能为空'), defineProperty(_message, 'MOBILE_UN_REGISTER', '手机号未注册'), defineProperty(_message, 'PLACE_ORDER_ERROR', '提交订单失败'), defineProperty(_message, 'PPAY_TYPE_INVALID_ERROR', '您选择的支付方式已不支持该笔订单'), defineProperty(_message, 'ORDER_RE_PAY_ERROR', '订单已支付成功'), defineProperty(_message, 'ADD_CART_ERROR', '商品加入购物车失败'), defineProperty(_message, 'OTHER_ERROR', '其他异常'), _message);

axios.defaults.headers.common['Content-Type'] = 'application/json; charset=utf-8';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 请求拦截器
axios.interceptors.request.use(function (config) {
	var jsessionid = sessionStorage.getItem('sessionId');
	if (jsessionid) {
		config.url = config.url + ';jsessionid=' + jsessionid;
	}
	return config;
}, function (error) {
	// Do something with request error
	return Promise.reject(error);
});

// 响应拦截器
axios.interceptors.response.use(function (response) {
	// 处理改造过的登录接口返回值
	if (response.data.extJsonObj) {
		response.data.message = message[response.data.responseObj.body.status];
	} else {
		// 处理不符合规则的接口返回
		if (!response.data.body.status) {
			response.data.message = response.data.body.message.value;
			return Promise.reject(response.data);
			// 符合规则的接口，使用映射表，常量转中文
		} else {
			response.data.message = message[response.data.body.status];
		}
	}
	return response.data;
}, function (error) {
	// Do something with response error
	return Promise.reject(error);
});

var cityService = {
	/*
  * @name 获取所有城市
  * @param { pbject } data: {
  *                     cityType: 'DOMESTIC' -> 国内热门,  'INTERNATIONAL' -> 国际热门
  *                   }
  */
	getAllcity: function getAllcity(data) {
		return axios.post('queryAllCity', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 获取热门城市
  * @param { object } data: {
  *                     cityType: 'DOMESTIC' -> 国内热门,  'INTERNATIONAL' -> 国际热门
  *                   }
  */
	getHotCity: function getHotCity(data) {
		return axios.post('queryHotCity', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 根据三字码查询城市
  * @param { object } data: {
  *                     cityType: 'DOMESTIC' -> 国内,  'INTERNATIONAL' -> 国际
  *                     keyword: 关键字,
  *                     searchType: 0 -> 城市, 1 -> 机场
  *                   }
  */
	getkeyWordCity: function getkeyWordCity(data) {
		return axios.post('queryCity', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	}
};

var changePage = function changePage(path, routerParams) {

	var baseUrl = '/tribe-touch-web/tribe';

	// console.log(sessionStorage.getItem('sessionId'))

	var newPath = baseUrl + path + ';jsessionid=' + sessionStorage.getItem('sessionId');

	if (routerParams) {
		var keyValueArray = Object.keys(routerParams).map(function (item) {
			return item + '=' + routerParams[item];
		});
		newPath = newPath + '?' + keyValueArray.join('&');
	}

	window.location.href = newPath;
};

new Vue({
	el: '#arrive-city',
	directives: {
		contact: {
			bind: function bind(el, binding, vnode) {
				setTimeout(function () {
					var contactList = new Contact({
						id: binding.value.id,
						nav: binding.value.nav
					});
				}, 1000);
			}
		}
	},
	data: function data() {
		return {
			keyword: '',
			searchFocus: false,
			debouceSign: false,
			tabValue: 1,
			activiCityCode: '',
			inlandHotCityList: [], // 缓存内地热门城市
			inlandAllCityList: [], // 缓存内地全部城市
			internationalHotCityList: [], // 缓存国际热门城市
			internationalAllCityList: [], // 缓存国际全部城市
			inlandOptions: {
				id: '#inlind-city',
				nav: '#contact-nav'
			},
			currentHotCity: [], // 实际展示的热门城市
			currentAllCityList: [], // 实际展示的全部城市
			searchResult: [], // 搜索出的城市列表
			titleType: 1
		};
	},
	created: function created() {
		var initData = sessionStorage.getItem('cityTab');
		if (initData) {
			initData = JSON.parse(initData);
			this.activiCityCode = initData.cityName.code;
			this.titleType = initData.titleType;
		}
	},
	mounted: function mounted() {
		this.getHotCityList(1);
		this.getAllCityList(1);
	},

	methods: {
		inputFocus: function inputFocus() {
			this.searchFocus = true;
			this.keyword = '';
		},
		searchCity: function searchCity() {
			var _this = this;

			// 模糊搜索接口，需要debounce
			if (!this.keyword) {
				return false;
			}
			if (this.debouceSign) {
				// console.log('搜获太频繁')
				return false;
			} else {
				this.debouceSign = true;
				cityService.getkeyWordCity({
					cityType: this.tabValue == 1 ? 'DOMESTIC' : 'INTERNATIONAL',
					keyword: this.keyword,
					searchType: 0
				}).then(function (res) {
					_this.debouceSign = false;
					_this.searchResult = res.body.cityList;
				}).catch(function (error) {
					_this.debouceSign = false;
				});
			}
		},
		getInternationInfo: function getInternationInfo() {
			this.getHotCityList(2);
			this.getAllCityList(2);
		},
		getHotCityList: function getHotCityList(tabValue) {
			var _this2 = this;

			if (this.internationalHotCityList.length > 0) {
				return false;
			}

			cityService.getHotCity({
				cityType: tabValue == 1 ? 'DOMESTIC' : 'INTERNATIONAL'
			}).then(function (res) {
				if (tabValue == 1) {
					_this2.inlandHotCityList = res.body.hotCityList;
					_this2.currentHotCity = _this2.inlandHotCityList;
				} else {
					_this2.internationalHotCityList = res.body.hotCityList;
					_this2.currentHotCity = _this2.internationalHotCityList;
				}
			}).catch(function (error) {});
		},
		getAllCityList: function getAllCityList(tabValue) {
			var _this3 = this;

			if (this.internationalAllCityList.length > 0) {
				return false;
			}

			cityService.getAllcity({
				cityType: tabValue == 1 ? 'DOMESTIC' : 'INTERNATIONAL'
			}).then(function (res) {
				if (tabValue == 1) {
					_this3.inlandAllCityList = res.body.allCityList;
					_this3.currentAllCityList = _this3.inlandAllCityList;
				} else {
					_this3.internationalAllCityList = res.body.allCityList;
					_this3.currentAllCityList = _this3.internationalAllCityList;
				}
			}).catch(function (error) {});
		},
		back: function back() {
			history.go(-1);
		},
		selectCity: function selectCity(city) {
			this.activiCityCode = city.code;
			var obj = {
				code: city.code,
				name: city.name,
				rel_type: this.titleType
			};
			sessionStorage.setItem('selectCity', JSON.stringify(obj));
			changePage('/home/goHomePage');
		}
	}

});

})));
