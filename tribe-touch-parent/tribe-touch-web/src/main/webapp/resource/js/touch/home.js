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

var homeService = {
	/*
  * @name 获取首页资源(banner, 公告)
  * @param { object } 
  */
	getHomeData: function getHomeData() {
		return axios.post('queryHomeResourceInfo', { body: {}, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 获取首页广告
  */
	getAd: function getAd(data) {
		return axios.post('queryAlertDialogAd', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 获取价格日历数据
  */
	getDate: function getDate(data) {
		return axios.post('queryPriceCalendar', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	}
};

var vueToast = {};

vueToast.install = function (Vue) {
	var content, interval;

	var addHtml = function addHtml(dataModel) {
		checkToast();
		content = document.createElement('div');
		content.className = 'toast-bg';

		document.body.append(content);

		var html = '<div class="toast">' + '<span class="toast-content">' + dataModel.text + '</span>' + '</div>';
		content.innerHTML = html;

		var time = dataModel.time;
		content.className = 'toast-bg toast-bg-hide';

		if (!interval) {
			interval = setInterval(function () {
				time--;
				if (time < 1) {
					clearInterval(interval);
					closeToast();
					interval = '';
					return;
				}
			}, 1000);
		}
	};

	function closeToast() {
		var content = document.querySelector('.toast-bg');
		if (content) {
			document.body.removeChild(content);
		}
	}

	function checkToast() {
		if (content) {
			closeToast();
		}
	}

	var dataModel = {
		success: function success() {},
		cancel: function cancel() {}
	};

	var toast = {
		show: function show(options) {
			dataModel = {
				text: options.text,
				success: options.options || dataModel.success,
				cancel: options.cancel || dataModel.cancel,
				time: options.time || 3
			};
			addHtml(dataModel);
		}
	};

	Vue.prototype.$toast = toast;
};

var vueDialog = {};

vueDialog.install = function (Vue) {

	var content, interval;

	var addHtml = function addHtml(dataModel) {
		checkDialog();
		content = document.createElement('div');
		content.className = 'dialog-bg';

		document.body.append(content);

		var html = '<div class="dialog">' + '<div class="dialog-content">' + '<p class="dialog-title">' + dataModel.title + '</p>' + '<div>' + dataModel.text + '</div>' + '</div>' + '<div class="dialog-footer">' + '<div class="dialog-left-btn text-info">' + dataModel.leftText + '</div>' + '<div class="dialog-right-btn">' + dataModel.rightText + '</div>' + '</div>' + '</div>';
		content.innerHTML = html;

		var leftBtn = document.querySelector('.dialog-left-btn');
		var rightBtn = document.querySelector('.dialog-right-btn');

		leftBtn.addEventListener('click', closeDialog);
		rightBtn.addEventListener('click', closeDialog);

		if (dataModel.time) {
			var time = dataModel.time;
			content.className = 'dialog-bg dialog-bg-hide';
			var interval = setInterval(function () {
				time--;
				if (time < 1) {
					clearInterval(interval);
					closeDialog();
					interval = '';
					return;
				}
			}, 1000);
		}

		function checkDialog() {
			if (content) {
				closeDialog();
			}
		}

		function closeDialog() {
			var content = document.querySelector('.dialog-bg');
			if (leftBtn) {
				leftBtn.removeEventListener('click', closeDialog);
			}
			if (rightBtn) {
				rightBtn.removeEventListener('click', closeDialog);
			}
			if (content) {
				document.body.removeChild(content);
			}
		}
	};

	var dataModel = {
		title: '',
		leftText: '确认',
		rightText: '',
		success: function success() {},
		cancel: function cancel() {}
	};

	var dialog = {
		show: function show(options) {
			dataModel = {
				title: options.title || '',
				text: options.text,
				leftText: options.leftText || dataModel.leftText,
				rightText: options.rightText || dataModel.rightText,
				success: options.options || dataModel.success,
				cancel: options.cancel || dataModel.cancel,
				time: options.time || 0
			};
			addHtml(dataModel);
		}
	};

	Vue.prototype.$dialog = dialog;
};

var week = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];

var relativeTime = function relativeTime(value) {
	var now = moment().format('YYYY-MM-DD');
	var tomorrow = moment().add(1, 'days').format('YYYY-MM-DD');
	// 今天
	if (now == value) {
		return '今天';
	} else if (tomorrow == value) {
		// 明天
		return '明天';
	} else {
		// 星期几
		var week$$1 = moment(value, 'YYYY-MM-DD').day();
		return week[week$$1];
	}

	return value;
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

var cityTab = 'cityTab'; // 首页 -> 城市列表页   当前被选择的城市数据
var orderParams = 'orderParams'; // 首页 -> 预订航班页   预订查询参数
var selectCity = 'selectCity'; // 城市列表页 -> 首页   更改被选择的城市数据
var dateTab = 'dateTab'; // 首页 -> 日历页       当前被选择的日期数据
var selectDate = 'selectDate'; // 日历页 -> 首页       更改被选择的日历数据
var user = 'user'; // 登录页 -> 其他页     当前登录的用户详情

var store = {
	cityTab: cityTab,
	orderParams: orderParams,
	selectCity: selectCity,
	dateTab: dateTab,
	selectDate: selectDate,
	user: user
};

Vue.use(vueToast);
Vue.use(vueDialog);

Vue.filter('relativeTime', relativeTime);

new Vue({
	el: '#home',
	directives: {
		swiper: {
			update: function update(el, binding) {
				var mySwiper = new Swiper(binding.value.className, binding.value);
			}
		},
		picker: {
			inserted: function inserted(el, binding, vnode) {
				var dataPicker = new LazyPicker(binding.value.className, {
					data: binding.value.data,
					theme: 'red',
					onChange: function onChange(data) {
						var adultNumber = data[0][0];
						var childNumber = data[1][0];
						vnode.context.adultNumber = adultNumber;
						vnode.context.childNumber = childNumber;
					}
				});
			}
		}
	},
	data: function data() {
		return {
			tabValue: 1,
			start: {
				name: '成都',
				code: 'CTU'
			},
			end: {
				name: '北京',
				code: 'PEK'
			},
			startTime: moment().add(1, 'd').format('YYYY-MM-DD'),
			endTime: moment().add(2, 'd').format('YYYY-MM-DD'),
			adultNumber: 1,
			childNumber: 0,
			iconGridList: [],
			discountList: [],
			focusOptions: {
				className: '.ad-list',
				autoplay: 3000,
				grabCursor: true,
				setWrapperSize: true,
				autoHeight: true,
				pagination: '.ad-pagination',
				paginationClickable: true
			},
			adList: [],
			cityList: [],
			annoucementList: [],
			prizeList: [],
			mileageshopList: [],
			acticvityList: [],
			announceOptions: {
				className: '.announce-list',
				direction: 'vertical',
				autoplay: 3000,
				height: 20,
				autoHeight: true,
				loop: true
			},
			user: '',
			pickerOptions: {
				className: '.picker',
				data: {
					"item": [{
						"id": '成人',
						"selected": true,
						"name": "1",
						"child": [{
							"id": '儿童',
							"name": "0"
						}, {
							"id": '儿童',
							"name": "1"
						}, {
							"id": '儿童',
							"name": "2"
						}]
					}, {
						"id": '成人',
						"name": "2",
						"child": [{
							"id": '儿童',
							"name": "0"
						}, {
							"id": '儿童',
							"name": "1"
						}, {
							"id": '儿童',
							"name": "2"
						}, {
							"id": '儿童',
							"name": "3"
						}]
					}, {
						"id": '成人',
						"name": "3",
						"child": [{
							"id": '儿童',
							"name": "0"
						}, {
							"id": '儿童',
							"name": "1"
						}, {
							"id": '儿童',
							"name": "2"
						}]
					}, {
						"id": '成人',
						"name": "4",
						"child": [{
							"id": '儿童',
							"name": "0"
						}, {
							"id": '儿童',
							"name": "1"
						}]
					}, {
						"id": '成人',
						"name": "5",
						"child": [{
							"id": '儿童',
							"name": "0"
						}]
					}]
				}
			},
			promotionCode: '' // 促销码
		};
	},
	created: function created() {
		// 用户信息
		var user = sessionStorage.getItem(store.user);
		if (user) {
			this.user = JSON.parse(user);
		}
		// 被选择的城市 - 从城市列表来
		var selectCity = sessionStorage.getItem(store.selectCity);
		if (selectCity) {
			selectCity = JSON.parse(selectCity);

			if (selectCity.rel_type == 1) {
				this.start = selectCity;
			} else {
				this.end = selectCity;
			}
		}

		// 被选择的日期 - 从日期列表来
		var selectedDate = sessionStorage.getItem(store.selectDate);
		if (selectedDate) {
			selectedDate = JSON.parse(selectedDate);
			if (selectedDate.direction == 1) {
				this.startTime = selectedDate.time;
				this.endTime = selectedDate.otherTime;
			} else {
				this.endTime = selectedDate.time;
				this.startTime = selectedDate.otherTime;
			}
		}
	},
	mounted: function mounted() {
		this.getHomeData();
	},

	methods: {
		getHomeData: function getHomeData() {
			var _this = this;

			homeService.getHomeData().then(function (res) {
				// 广告列表
				_this.adList = res.body.adInfoList;
				var that = _this;
				res.body.resourceList.forEach(function (item) {
					switch (item.type) {
						// 通知列表
						case 'ANNOUNCEMENT':
							that.annoucementList = item.infoList || [];
							break;
						case 'PRIZE':
							that.prizeList = item.infoList || [];
							break;
						case 'MILEAGESHOP':
							that.mileageshopList = item.infoList || [];
							break;
						case 'ACTICVITY':
							that.acticvityList = item.infoList || [];
							break;
					}
				});
			}).catch(function (error) {

				_this.$toast.show({
					text: error.message
				});
			});
		},
		reversePlace: function reversePlace() {
			var _ref = [this.end, this.start];
			this.start = _ref[0];
			this.end = _ref[1];
		},
		openDialog: function openDialog() {
			this.$dialog.show({
				title: '提示',
				text: '<p class="clearfix child-dialog-item">\n\t\t\t\t\t\t\t\t\t<span class="text-small tabel-cell">1.</span>\n\t\t\t\t\t\t\t\t\t<span class="text-small text-left tabel-cell">\u513F\u7AE5\u5E74\u9F84\u6307\u4ECE\u65C5\u5BA2\u4E4B\u65E5\u7B97\u8D77\uFF1A2&le;\u513F\u7AE5&le;12</span>\n\t\t\t\t\t\t\t </p>\n\t\t\t\t\t\t\t <p class="clearfix child-dialog-item">\n\t\t\t\t\t\t\t \t\t<span class="text-small tabel-cell">2.</span>\n\t\t\t\t\t\t\t \t\t<span class="text-small text-left tabel-cell">\u8BF7\u6CE8\u610F\u60A8\u9009\u62E9\u7684\u65C5\u7A0B\u4E3A\u591A\u6BB5\u822A\u73ED\u65F6\uFF0C\u5982\u513F\u7AE5\u65C5\u5BA2\u5728\u65C5\u9014\u4E2D\u8D85\u8FC712\u5C81\uFF0C\u5219\u53EF\u80FD\u5BFC\u81F4\u5728\u586B\u5199\u8BA2\u5355\u65F6\uFF0C\u65E0\u6CD5\u6DFB\u52A0\u8BE5\u513F\u7AE5</span>\n\t\t\t\t\t\t\t </p>\n\t\t\t\t\t\t\t <p class="clearfix child-dialog-item">\n\t\t\t\t\t\t\t \t\t<span class="text-small tabel-cell">3.</span>\n\t\t\t\t\t\t\t \t\t<span class="text-small text-left tabel-cell">\u5BA2\u6237\u7AEF\u6682\u65F6\u4E0D\u652F\u6301\u5A74\u513F\u8D2D\u7968</span>\n\t\t\t\t\t\t\t </p>'
			});
		},
		goLoginPage: function goLoginPage() {
			changePage('/user/goLoginPage');
		},
		goCityList: function goCityList(tab) {
			if (tab == 1) {
				var obj = {
					cityName: this.start,
					titleType: 1
				};
				sessionStorage.setItem(store.cityTab, JSON.stringify(obj));
				changePage('/city/goCityListPage');
				return;
			}

			if (tab == 2) {
				var obj = {
					cityName: this.end,
					titleType: 2
				};
				sessionStorage.setItem(store.cityTab, JSON.stringify(obj));
				changePage('/city/goCityListPage');
				return;
			}
		},
		goToOrderFlight: function goToOrderFlight() {

			var orderParams = {
				adultNumber: this.adultNumber,
				childNumber: this.childNumber,
				startTime: this.startTime,
				end: this.end,
				start: this.start,
				promotionCode: this.promotionCode
			};

			sessionStorage.setItem('orderParams', JSON.stringify(orderParams));
			changePage('/flight/goFlightListPage');
		},
		goToDateList: function goToDateList(tab) {
			if (tab == 1) {
				var obj = {
					direction: 1,
					time: this.startTime,
					otherTime: this.endTime
				};
				sessionStorage.setItem(store.dateTab, JSON.stringify(obj));
				changePage('/home/priceCanlender');
				return;
			}
			if (tab == 2) {
				var obj = {
					direction: 2,
					time: this.endTime,
					otherTime: this.startTime
				};
				sessionStorage.setItem(store.dateTab, JSON.stringify(obj));
				changePage('/home/priceCanlender');
				return;
			}
		}
	}
});

})));
