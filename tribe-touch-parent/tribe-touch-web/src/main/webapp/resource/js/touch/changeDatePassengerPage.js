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
	var jsessionid = sessionStorage.getItem('jsessionid');

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

			// 如果keycode
			if (response.data.body.message && response.data.body.message.keyCode % 10 != 0) {
				response.data.message = response.data.body.message.value;
			} else {
				response.data.message = '';
			}
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

var changeDateService = {
	/*
  * @name 获取可改期的旅客列表数据
  */
	getChangeDatePassengerList: function getChangeDatePassengerList(data) {
		return axios.post('queryChangeDatePassengerList', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 获取需要改期的乘客和航班，做效验使用
  * @ext 订单详情存在本地，供后面流程使用
  */
	getCanChangePassengersFlight: function getCanChangePassengersFlight(data) {
		return axios.post('submitCanChangePassengersFlight', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
   * @name 获取可改期的航班列表
  */
	getChangeDateFlightList: function getChangeDateFlightList(data) {
		return axios.post('queryChangeDateFlightList', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 更改选择的航班
  */
	changeDateFlightItem: function changeDateFlightItem(data) {
		return axios.post('changeDate', { body: data, head: { platformId: 3 } }).then(function (res) {
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

		document.body.appendChild(content);

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

		document.body.appendChild(content);

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

var vueLoading = {};

vueLoading.install = function (Vue) {
	var content, interval;
	var addHtml = function addHtml() {
		checkLoading();

		content = document.createElement('div');
		content.className = 'loading-bg';

		document.body.appendChild(content);

		var html = '<div class="loading">' + '<div class="loading-content">' + '<p>' + '<img src="../../resource/images/loading.gif">' + '</p>' + '</div>' + '</div>';
		content.innerHTML = html;
	};

	function checkLoading() {
		if (content) {
			hideHtml();
		}
	}

	var hideHtml = function hideHtml() {
		var content = document.querySelector('.loading-bg');
		if (content) {
			document.body.removeChild(content);
		}
	};

	var loading = {
		show: function show(options) {
			addHtml();
		},
		hide: function hide() {
			hideHtml();
		}
	};

	Vue.prototype.$loading = loading;
};

var week = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];

var relativeTime = function relativeTime(value, reg) {
	var str = reg || 'YYYY-MM-DD';
	var now = moment().format(str);
	var tomorrow = moment().add(1, 'days').format(str);
	// 今天
	if (now == value) {
		return '今天';
	} else if (tomorrow == value) {
		// 明天
		return '明天';
	} else {
		// 星期几
		var week$$1 = moment(value, str).day();
		return week[week$$1];
	}
};

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

/*返回证件值*/
var docType = function docType(doc) {
  var type = "";
  switch (doc) {
    case 'ID':
      type = "身份证";
      break;
    case 'MILITARY_OFFICER':
      type = "军官证";
      break;
    case 'POLICE':
      type = "警官证";
      break;
    case 'SOLDIER':
      type = "士兵证";
      break;
    case 'PASSPORT':
      type = "护照";
      break;
    case 'PRC':
      type = "回乡证";
      break;
    case 'OTHER':
      type = "其他";
      break;
  }
  return type;
};

///*返回乘客类型*/

var passengerType = function passengerType(doc) {
  var type = "";
  switch (doc) {
    case 'ADT':
      type = "成人";
      break;
    case 'CHD':
      type = "儿童";
      break;
    case 'INF':
      type = "婴儿";
      break;
    case 'GRP':
      type = "团体";
      break;
  }
  return type;
};

/*
 * @name 是否是电话号码
 */
/*
 * @name 是否大于18岁
 */

var isAdult = function isAdult(birthday) {
  var today = new Date();
  var birthDate = new Date(birthday);
  var age = today.getFullYear() - birthDate.getFullYear();
  var m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || m === 0 && today.getDate() < birthDate.getDate()) {
    age--;
  }
  return age >= 18;
};

var findObj = function findObj(array, key, value) {
	var bool = false;
	for (var i = 0; i < array.length; i++) {
		if (array[i][key] == value) {
			bool = array[i];
		}
	}
	return bool;
};

var getSessionId = function getSessionId() {
	var r = window.location.href.split(";");
	if (r.length > 1) {
		var session = r[1].split("?");
		return session[0].substring(11);
	}
};

var changePage = function changePage(path, routerParams) {

	var baseUrl = '/tribe-touch-web/tribe';

	// console.log(sessionStorage.getItem('sessionId'))

	var newPath = baseUrl + path + ';jsessionid=' + (sessionStorage.getItem('jsessionid') || '');

	if (routerParams) {
		var keyValueArray = Object.keys(routerParams).map(function (item) {
			return item + '=' + routerParams[item];
		});
		newPath = newPath + '?' + keyValueArray.join('&');
	}

	window.location.href = newPath;
};

Vue.use(vueToast);
Vue.use(vueDialog);
Vue.use(vueLoading);

Vue.filter('timeFormat', timeFormat);
Vue.filter('relativeTime', relativeTime);
Vue.filter('docType', docType);
Vue.filter('passengerType', passengerType);

new Vue({
	el: '#change-passenge',
	data: function data() {
		return {
			orderId: '',
			ticketCanChangeList: [],
			userList: [],
			checkModel: {
				checkAdult: 0, // 选中的成人(> 18岁)数量
				notCheckAdult: 0,
				checkChild: 0, // 选中的儿童数量
				notCheckChild: 0,
				checkPerson: 0, // 选中的总人数(主要是因为要包含未满18岁的成人)
				checkUserList: []
			},
			checkFlightModel: '',
			data: ''
		};
	},
	created: function created() {
		this.$loading.show();
		var url = window.location.href;
		this.orderId = url.substring(url.lastIndexOf('=') + 1) || sessionStorage.getItem('orderId');
		var sessionId = getSessionId();
		sessionStorage.setItem('orderId', this.orderId);
		sessionStorage.setItem('jsessionid', sessionId);
	},
	mounted: function mounted() {
		this.getTicketList();
	},

	methods: {
		getTicketList: function getTicketList() {
			var _this = this;

			changeDateService.getChangeDatePassengerList({
				ticketCanChangeDateRequest: {
					orderId: this.orderId
				}
			}).then(function (res) {
				_this.$loading.hide();
				if (res.message) {
					_this.$toast.show({
						text: res.message
					});
				} else {
					_this.data = res.body.ticketCanChangeDateResponse.editProductList;
					_this.initData(res.body);
				}
			});
		},
		initData: function initData(data) {
			var oldList = data.ticketCanChangeDateResponse.editProductList;
			var flight = [];
			var person = [];

			// 去重用
			var idArray = [];

			for (var i = 0; i < oldList.length; i++) {
				var airlineItem = oldList[i];
				var productNumber = airlineItem.productNumber;
				for (var j = 0; j < airlineItem.airline.passengerList.length; j++) {
					var item = airlineItem.airline.passengerList[j];
					var flightList = item.flightList;

					var personObj = item;
					personObj.productNumber = productNumber;
					this.$set(personObj, 'check', false);

					for (var k = 0; k < flightList.length; k++) {

						var flightListItem = flightList[k];
						flightListItem.id = flightListItem.orgCity + '-' + flightListItem.destCity;

						if (idArray.indexOf(flightListItem.id) == -1) {
							idArray.push(flightListItem.id);
							flight.push(flightListItem);
						}
					}

					person.push(personObj);
				}
			}

			this.ticketCanChangeList = flight;
			this.userList = person;
		},
		changeFlight: function changeFlight(item) {
			if (item == this.checkFlightModel) {
				this.checkFlightModel = '';
			} else {
				this.checkFlightModel = item;
			}
		},
		changeUser: function changeUser(item) {
			item.check = !item.check;
		},
		initCheckModel: function initCheckModel() {
			this.checkModel = {
				checkAdult: 0,
				notCheckAdult: 0,
				checkChild: 0,
				notCheckChild: 0,
				checkPerson: 0,
				checkUserList: []
			};
		},
		postChangeFlight: function postChangeFlight() {
			var _this2 = this;

			this.initCheckModel();
			this.userList.forEach(function (user) {
				// 被选中的人
				if (user.check) {
					// 成人
					if (user.passengerType == 'ADT') {
						// 需要满18岁才算成人
						if (isAdult(user.birthday)) {
							_this2.checkModel.checkAdult++;
						}
					}
					// 小孩
					if (user.passengerType == 'CHD') {
						_this2.checkModel.checkChild++;
					}

					_this2.checkModel.checkPerson++;
					// 需要保存下已选择乘机人的信息 - 主要是用flight ID
					_this2.checkModel.checkUserList.push(user);
					// 没被选中的人
				} else {
					// 成人
					if (user.passengerType == 'ADT') {
						if (isAdult(user.birthday)) {
							_this2.checkModel.notCheckAdult++;
						}
					}
					// 小孩
					if (user.passengerType == 'CHD') {
						_this2.checkModel.notCheckChild++;
					}
				}
			});

			console.log('选择人数情况', this.checkModel);
			console.log('选择航线情况', this.checkFlightModel);

			/* 一堆验证 */
			if (!this.checkModel.checkPerson || !this.checkFlightModel) {
				this.$dialog.show({
					title: '温馨提示',
					text: '航班或者乘机人不能为空，请选择后再提交变更申请'
				});
				return false;
			}

			if (this.checkModel.checkChild && this.checkModel.checkPerson == this.checkModel.checkChild) {
				this.$dialog.show({
					title: '温馨提示',
					text: '儿童不可单独改期'
				});
				return false;
			}

			if (!this.checkModel.checkAdult && this.checkModel.checkChild) {
				this.$dialog.show({
					title: '温馨提示',
					text: '携带儿童的成人乘机人年龄必须大于等于18岁'
				});
				return false;
			}

			if (this.checkModel.notCheckAdult * 2 < this.checkModel.notCheckChild) {
				this.$dialog.show({
					title: '温馨提示',
					text: '该航班未改期乘机人中有儿童无满18岁成人陪同'
				});
				return false;
			}

			if (this.checkModel.checkAdult * 2 < this.checkModel.checkChild) {
				this.$dialog.show({
					title: '温馨提示',
					text: '1名成人只能携带2名儿童改期'
				});
				return false;
			}

			// 选择了非自己的票
			var errorList = [];
			// 有票不能改
			var ticketErrorList = [];
			// 当前选择的乘机人
			this.checkModel.checkUserList.forEach(function (user) {
				// 当前选择的航班内，有未匹配的乘机人
				var obj = findObj(user.flightList, 'id', _this2.checkFlightModel.id);
				var time = moment(_this2.checkFlightModel.takeOffTime, 'YYYY-MM-DD hh:mm:ss').format('YYYY年MM月DD日');
				var airlineType = _this2.checkFlightModel.marketingAirline;
				var flightNumber = _this2.checkFlightModel.flightNo;
				var errorText = user.surnName + '的' + time + airlineType + flightNumber + '航班';

				if (!obj) {
					errorList.push(errorText);
				}

				if (obj) {
					if (obj.ticketingStatus != 'OPEN FOR USE') {
						ticketErrorList.push(errorText);
					} else {
						obj.canUse = true;
					}
				}
			});

			var productNumbers = this.checkModel.checkUserList.map(function (user) {
				return user.productNumber;
			});
			var productNumber = [];

			// 去重
			for (var i = 0; i < productNumbers.length; i++) {
				if (productNumber.indexOf(productNumbers[i]) == -1) {
					productNumber.push(productNumbers[i]);
				}
			}

			if (errorList.length > 0) {
				this.$dialog.show({
					title: '温馨提示',
					text: errorList.join(',') + '已经退票/改期，不能进行改期操作。'
				});
				return false;
			}

			if (ticketErrorList.length > 0) {
				this.$dialog.show({
					title: '温馨提示',
					text: ticketErrorList.join(',') + '客票状态无法改期'
				});
				return false;
			}

			if (productNumber.length > 1) {
				this.$dialog.show({
					title: '温馨提示',
					text: '选只能更改同一订单编码下的航班'
				});
				return false;
			}

			this.ajaxFlight(productNumber[0]);
		},
		ajaxFlight: function ajaxFlight(productNumber) {
			var _this3 = this;

			window.sessionStorage.setItem('selectedProductNumber', productNumber);

			var data = JSON.parse(JSON.stringify(this.data));
			var productObj = findObj(data, 'productNumber', productNumber);
			var index;
			if (productObj) {

				var userList = productObj.airline.passengerList.filter(function (user) {
					return user.check;
				});

				userList.forEach(function (item) {
					var flight = findObj(item.flightList, 'id', _this3.checkFlightModel.id);
					index = item.flightList.indexOf(flight);
					item.flightList = [flight];
				});

				// 处理BUG
				productObj.airline = {
					passengerList: userList
				};
			}

			/* 精简req  */
			var obj = {
				airline: {
					passengerList: []
				},
				productNumber: productObj.productNumber
			};

			for (var i = 0; i < productObj.airline.passengerList.length; i++) {
				var flightList = productObj.airline.passengerList[i].flightList;
				var flight = {
					rph: productObj.airline.passengerList[i].rph,
					flightList: []
				};
				for (var j = 0; j < flightList.length; j++) {
					flight.flightList.push({ rph: flightList[j].rph });
				}
				obj.airline.passengerList.push(flight);
			}

			var req = {
				validateMMBRequest: {
					editProductList: [obj],
					validateMMBActionType: 'DATECHANGE',
					validateMMBChangeType: 'CHANGE'
				}
			};

			this.$loading.show();

			changeDateService.getCanChangePassengersFlight(req).then(function (res) {
				_this3.$loading.hide();

				if (res.message) {
					_this3.$toast.show({
						text: res.message
					});
				} else {
					var cjrObj = { list: _this3.checkModel.checkUserList };
					sessionStorage.submitCanChangePassengersFlightObj = JSON.stringify(res);
					sessionStorage.cjrInfo = JSON.stringify(cjrObj); //乘机人信息存储
					sessionStorage.hbInfo = index; //乘机人信息存储
					changePage("/changeDate/goChangeDateChoosePage");
				}
			});
		}
	}
});

})));
