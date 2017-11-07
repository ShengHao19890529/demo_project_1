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

var flightService = {
	/*
  * @name 获取航班列表
  */
	getFlightList: function getFlightList(data) {
		return axios.post('queryFlightList', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 过滤和排序航班列表
  */
	sortFlight: function sortFlight(data) {
		return axios.post('filterAndSortFlight', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 提供确认页面的展示数据
  */
	getCheckoutData: function getCheckoutData(data) {
		return axios.post('checkoutShoppingcart', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 添加到购物车里
  */
	prepayFlight: function prepayFlight(data) {
		return axios.post('addShoppingcart', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 创建订单，提交乘机人信息
  */
	submitOrder: function submitOrder(data) {
		return axios.post('submitOrder', { body: data, head: { platformId: 3 } }).then(function (res) {
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

var week = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];

/*
 * @name 格式化时间
 * @param { String } stiring: 'YYYY-MM-DD hh:mm:ss' 详细可参考moment.js文档
 * @param { String } oldStr:  'YYYY-MM-DD hh:mm:ss'
 * @authorBy wilson wang
 */
var timeFormat = function timeFormat(value, string, oldStr) {
	// 格式化标准时间戳
	var str = oldStr || 'YYYY-MM-DD hh:mm:ss';
	var time = moment(value, str).format(string);
	return time;
};

/*
 * @name 持续时间
 * @authorBy wilson wang
 */
var duration = function duration(value) {
	var new_str = value.replace(':', 'h') + 'm';
	return new_str;
};

/*
 * @name 航班列表出发 - 到达时间是否多一天
 * @param 
 */
var moreDay = function moreDay(value, otherTime) {
	//  获取YYYY-MM-DD
	var startTime = value.split(' ')[0];
	var endTime = otherTime.split(' ')[0];
	if (startTime != endTime) {
		return '+1天';
	}
};

var chinsesWeek = function chinsesWeek(value) {
	return week[value].substring(1);
};

var price = function price(value) {
	var number = parseInt(value);
	return '¥' + number;
};

Vue.use(vueToast);

Vue.filter('timeFormat', timeFormat);
Vue.filter('duration', duration);
Vue.filter('price', price);
Vue.filter('moreDay', moreDay);
Vue.filter('chinsesWeek', chinsesWeek);

new Vue({
	el: '#order-flight',
	data: function data() {
		return {
			orderParams: {
				end: {},
				start: {}
			},
			dateList: [],
			flightList: [],
			placeInfo: {
				arrivedAirportList: [],
				takeoffAirportList: []
			},
			showFilterDialog: false,
			// 左右两边列表对应
			filterListId: 1,
			// 起飞时间列表
			takeOffTimeList: [{ time: '06:00 - 12:00', check: true, id: 'FIRST' }, { time: '12:00 - 18:00', check: true, id: 'SECOND' }, { time: '18:00 - 24:00', check: true, id: 'THIRD' }, { time: '00:00 - 06:00', check: true, id: 'FOURTH' }],
			// 选择起飞的模型 - 最终实际穿参
			takeOffModel: {
				id: null,
				check: true
			},
			// 是否是国际航班
			isInternationLine: false,
			// 是否包含共享航班 (单选)
			shareFlight: true,
			// 机型列表
			flightTypeList: [],
			// 选择的机型
			flightTypeModel: {
				check: true
			},
			// 是否是共享航班
			isShareFlight: true,
			// 是否直飞
			isDirectFlight: null,
			// 是否展示税费
			showInterTax: false,
			// 按时间排序
			orderByTime: 0
		};
	},
	created: function created() {
		var params = sessionStorage.getItem('orderParams');
		if (params) {
			this.orderParams = JSON.parse(params);
		} else {
			this.orderParams = {
				adultNumber: 1,
				childNumber: 0,
				end: {
					code: 'PEK',
					name: '北京'
				},
				start: {
					code: 'CTU',
					name: '成都'
				},
				startTime: '2017-10-25'
			};
		}
		// 测试
		var data = {
			"body": {
				"airlines": [{
					"airline": [{
						"airlineDetailList": [{
							"airlineDetail": {
								"duration": "2:50", // 持续时间      
								"flightType": "ONEWAY",
								"flights": {
									"1": {
										"arriveAirport": "北京首都国际",
										"arriveTerminal": "T2",
										"arriveTime": "2017-10-21 00:20:00",
										"bookingClassAvails": {
											"Y": "A",
											"T": "A",
											"H": "A",
											"G": "A"
										},
										"cabinCode": "G",
										"destCity": "PEK",
										"duration": "2:50",
										"flightNo": "3050",
										"flightRPH": "1",
										"infoSource": "Domestic", // 是否国际航班
										"loyalty": "",
										"marketingAirline": "3U",
										"mealCode": "C",
										"onlineCheckin": true,
										"operatingAirline": "CZ",
										"orgCity": "CTU",
										"planeModel": "33G",
										"takeOffAirport": "成都双流",
										"takeOffTerminal": "T2",
										"takeOffTime": "2017-10-20 21:30:00"
									}
								},
								"journeyId": 1, // 注意访问flights时数据的键用此字段
								"summary": {
									"arrivalTime": "2017-10-21 00:20:00", // 到达时间 -- z注意跟起飞时间是否同一天
									"arriveAirport": "北京首都国际", // 到达机场
									"arriveTerminal": "T2", // 到达航站楼
									"cabinClass": "FF",
									"departAirport": "成都双流", // 起飞机场
									"departTerminal": "T2", // 起飞航站楼
									"departTime": "2017-10-20 21:30:00", // 起飞时间
									"destCity": "PEK",
									"orgCity": "CTU"
									// "numStopOvers": "1",                   // 中转次数(不一定存在，需判断)
									// "stopQuantity": "1"                   // 经停次数(不一定存在，需判断)
								}
							},
							"flightNoSegment": [{
								"flegFlightNo": "3050", // 飞机号
								"marketingAirline": "3U",
								"operatingAirline": '3U', // 飞机图标 + 飞机图标之后的展示  					
								"planeModel": "33G", // 不知道什么玩意，共享之后的展示 // 多个架次的话不展示该字段
								"shareFlight": true // 是 -> 共享, 否 -> |
							}],
							"interTax": "0.0",
							"price": "1230.0",
							"ticketNum": "", // 剩余票数
							"totalPrice": "1230.0" // 价格
						}],
						"price": {
							"fareFamilies": [],
							"fareFamilyTotal": [// 子列表
							{
								"cabinCode": ["G"],
								"fareFamilyTotal": {
									"code": "ECO_FLEXIBLE",
									"name": "优选经济舱", // 舱型
									"passengerFares": {
										"ADT": {
											"baseFare": {
												"amount": 1230,
												"currency": "CNY"
											},
											"passengerType": "ADT",
											"quantity": 1,
											"taxs": {
												"CN": {
													"amount": 50,
													"currency": "CNY",
													"taxCode": "CN"
												},
												"YQ": {
													"amount": 0,
													"currency": "CNY",
													"taxCode": "YQ"
												}
											},
											"totalFare": {
												"adjusted": 1280,
												"amount": 1280,
												"currency": "CNY"
											}
										}
									},
									"seq": "0",
									"total": [{
										"amount": 1280,
										"currency": "CNY"
									}]
								},
								"price": "1230.0",
								"sequenceNumber": 1,
								"totalPrice": "1230.0" // 价格
							}, {
								"cabinCode": ["Y"],
								"fareFamilyTotal": {
									"code": "ECO_NON_DISCOUNTABLE",
									"name": "标准经济舱",
									"passengerFares": {
										"ADT": {
											"baseFare": {
												"amount": 1690,
												"currency": "CNY"
											},
											"passengerType": "ADT",
											"quantity": 1,
											"taxs": {
												"CN": {
													"amount": 50,
													"currency": "CNY",
													"taxCode": "CN"
												},
												"YQ": {
													"amount": 0,
													"currency": "CNY",
													"taxCode": "YQ"
												}
											},
											"totalFare": {
												"adjusted": 1740,
												"amount": 1740,
												"currency": "CNY"
											}
										}
									},
									"seq": "0",
									"total": [{
										"amount": 1740,
										"currency": "CNY"
									}]
								},
								"price": "1690.0",
								"sequenceNumber": 1,
								"totalPrice": "1690.0"
							}]
						},
						"sequenceNumber": 1
					}],
					"groupID": {
						"2": ["2"]
					}
				}],
				"flightFilterCondition": {
					"arrivedAirportList": [{
						"airport": "北京机场",
						"code": "PEK",
						"interCity": false,
						"name": "北京"
					}],
					"flightFilter": ["DIRECT_FLIGHT"],
					"planeModelList": ["77A", "330", "320", "321", "33G"],
					"planeTypeList": ["BIG"],
					"shareFlight": ["SHARE_FLIGHT", "NO_SHARE_FLIGHT"],
					"takeOffTimeSpanList": ["SECOND", "FIRST", "THIRD"],
					"takeoffAirportList": [{
						"airport": "双流机场",
						"code": "CTU",
						"interCity": false,
						"name": "成都"
					}]
				}
			}
		};

		this.initData(data);

		this.initDateList();
	},
	mounted: function mounted() {
		this.getOrderInfo();
	},

	methods: {
		initDateList: function initDateList() {
			var dateList = Array.from({ length: 7 });
			this.dateList = dateList.map(function (item, index) {
				var day = moment().add(index, 'days');
				return {
					day: day.format('D'),
					week: day.day(),
					price: 100,
					time: day.format('YYYY-MM-DD')
				};
			});
		},
		getOrderInfo: function getOrderInfo() {
			var _this = this;

			flightService.getFlightList({
				flightSearchRequest: {
					adt: this.orderParams.adultNumber,
					chd: this.orderParams.childNumber,
					calendarSearch: false,
					departDate: this.orderParams.startTime,
					destCity: this.orderParams.end.code,
					orgCity: this.orderParams.start.code,
					promotionCode: this.orderParams.promotionCode,
					reqId: '',
					flightType: "ONEWAY",
					searchType: 'F',
					userPrice: false
				},
				page: {
					count: 2147483647,
					index: 1
				},
				sortType: 'DATE_ASC'
			}).then(function (res) {
				_this.$toast.show({
					text: res.message
				});
				console.log(res);
			});
		},
		setItemValue: function setItemValue(item) {
			var click = item.click ? false : true;
			this.$set(item, 'click', click);
		},
		initData: function initData(data) {
			var _this2 = this;

			this.flightList = data.body.airlines[0].airline.map(function (item) {
				if (item) {
					var key = item.airlineDetailList[0].airlineDetail.journeyId;
					var isInternationLine = item.airlineDetailList[0].airlineDetail.flights[key].infoSource;
					var bool = isInternationLine == 'International' ? true : false;
					item.airlineDetailList[0].airlineDetail.summary.isInternationLine = bool;
					_this2.isInternationLine = true; //bool
					return item;
				}
			});
			this.placeInfo = data.body.flightFilterCondition;
			this.flightTypeList = data.body.flightFilterCondition.planeModelList.map(function (item) {
				return {
					flightType: item,
					check: true
				};
			});
		},
		back: function back() {
			window.history.go(-1);
		},
		changeTakeOffAll: function changeTakeOffAll() {
			this.takeOffModel.check = !this.takeOffModel.check;
			if (this.takeOffModel.check) {
				this.takeOffTimeList.forEach(function (item) {
					return item.check = true;
				});
			} else {
				this.takeOffTimeList.forEach(function (item) {
					return item.check = false;
				});
			}
		},
		changeTakeOffItem: function changeTakeOffItem(item) {
			item.check = !item.check;
			var bool = this.takeOffTimeList.every(function (item) {
				return item.check;
			});
			this.takeOffModel.check = bool;
		},
		changeFlightTypeAll: function changeFlightTypeAll() {
			this.flightTypeModel.check = !this.flightTypeModel.check;
			if (this.flightTypeModel.check) {
				this.flightTypeList.forEach(function (item) {
					return item.check = true;
				});
			} else {
				this.flightTypeList.forEach(function (item) {
					return item.check = false;
				});
			}
		},
		changeFlightTypeItem: function changeFlightTypeItem(item) {
			item.check = !item.check;
			var bool = this.flightTypeList.every(function (item) {
				return item.check;
			});
			this.flightTypeModel.check = bool;
		},
		changeShareFlight: function changeShareFlight() {
			this.isShareFlight = !this.isShareFlight;
		},
		searchFlight: function searchFlight() {
			var _this3 = this;

			var planeModel, takeOffTime;

			if (this.flightTypeModel.check) {
				planeModel = this.flightTypeList.map(function (item) {
					return item.flightType;
				});
			} else {
				planeModel = this.flightTypeList.filter(function (item) {
					return item.check;
				}).map(function (item) {
					return item.flightType;
				});
			}

			if (this.takeOffModel.check) {
				takeOffTime = this.takeOffTimeList.map(function (item) {
					return item.id;
				});
			} else {
				takeOffTime = this.takeOffTimeList.filter(function (item) {
					return item.check;
				}).map(function (item) {
					return item.id;
				});
			}

			flightService.sortFlight({
				flightFilterRequest: {
					planeModel: planeModel,
					takeOffTime: takeOffTime,
					direct: this.isDirectFlight,
					share: this.isShareFlight
				}
			}).then(function (res) {
				_this3.initData(res);
			});
		},
		orderList: function orderList() {
			this.orderByTime = !this.orderByTime;
			if (this.orderByTime) {
				this.flightList.sort(function (a, b) {
					return a.airlineDetailList[0].airlineDetail.summary.departTime - b.airlineDetailList[0].airlineDetail.summary.departTime;
				});
			} else {
				this.flightList.sort(function (a, b) {
					return b.airlineDetailList[0].airlineDetail.summary.departTime - a.airlineDetailList[0].airlineDetail.summary.departTime;
				});
			}
		}
	}
});

})));
