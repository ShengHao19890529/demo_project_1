(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory() :
  typeof define === 'function' && define.amd ? define(factory) :
  (factory());
}(this, (function () { 'use strict';

var isPhone = function isPhone(phone) {
	var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$/;
	return reg.test(phone) ? false : true;
};

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

var loginService = {
	/*
  * @name 会员登录
  * @param  { object } data: {
  *                      loginRequest: {
  *												loginUsername: this.user.name,
  *												loginPassword: this.user.password,
  *												reqId: this.user.imgCode
  *											}
  *                    }
  */
	VIPlogin: function VIPlogin(data) {
		return axios.post('memberLogin', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 非会员登录
  * @param { Object } data: {
  *                     
  *                   }
  */
	commonLogin: function commonLogin(data) {
		return axios.post('noMemberLogin', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},
	checkLogin: function checkLogin(data) {
		return axios.post('checkLogin', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	},
	logout: function logout(data) {
		return axios.post('checkLogin', { body: data, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	}
};

var codeService = {
	/*
  * @name 获取手机验证码
  */
	getPhoneCode: function getPhoneCode(data) {
		return axios.post('sendMsgCode', { body: data, head: { platformId: 3 } }).then(function (res) {
			console.log(res);
		}).catch(function (error) {
			return error;
		});
	},

	/*
  * @name 获取验证码图片
  * @params { object } {
  *											body: {
  *												
  *											},
  *											head: {
  *                         platformId: 3
  *											}
  *										}
  */
	getImgCode: function getImgCode() {
		return axios.post('getPicCodeUrl', { body: {}, head: { platformId: 3 } }).then(function (res) {
			return res;
		}).catch(function (error) {
			return error;
		});
	}
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

var vueLoading = {};

vueLoading.install = function (Vue) {
	var content, interval;
	var addHtml = function addHtml() {
		checkLoading();

		content = document.createElement('div');
		content.className = 'loading-bg';

		document.body.append(content);

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

var changePage = function changePage(path, routerParams) {

	var baseUrl = '/tribe-touch-web/tribe';

	console.log(sessionStorage.getItem('sessionId'));

	var newPath = baseUrl + path + ';jsessionid=' + sessionStorage.getItem('sessionId');

	if (routerParams) {
		var keyValueArray = Object.keys(routerParams).map(function (item) {
			return item + '=' + routerParams[item];
		});
		newPath = newPath + '?' + keyValueArray.join('&');
	}

	window.location.href = newPath;
};

Vue.use(vueDialog);
Vue.use(vueToast);
Vue.use(vueLoading);

new Vue({
	el: '#login',
	data: function data() {
		return {
			tabValue: 1,
			user: {
				phone: '',
				password: '',
				imgCode: '',
				name: '',
				phoneCode: '',
				commonImgCode: ''
			},
			phoneCode: {
				time: 60,
				status: true,
				text: '获取验证码',
				isClick: true
			},
			codeImg: '',
			commonImgCode: ''
		};
	},

	methods: {
		login: function login() {
			var _this = this;

			// 会员登录方式
			if (this.tabValue == 1) {

				// 手机号必填
				if (!this.user.name) {
					this.$toast.show({
						text: '账号不能为空'
					});
					return false;
				}
				// 密码必填
				if (!this.user.password) {
					this.$toast.show({
						text: '密码不能为空'
					});
					return false;
				}

				// 是否为手机号
				if (!isPhone(this.user.name)) {
					this.$toast.show({
						text: '手机号不正确'
					});
					return false;
				}

				// 错误大于三次，必须验证图片
				if (this.codeImg && !this.user.imgCode) {
					this.$toast.show({
						text: '验证码不能为空'
					});
					return false;
				}

				this.$loading.show();

				loginService.VIPlogin({
					loginRequest: {
						loginUsername: this.user.name,
						loginPassword: this.user.password,
						reqId: this.user.imgCode
					}
				}).then(function (res) {
					if (res.responseObj.body.verifyPicUrl) {
						_this.codeImg = res.responseObj.body.verifyPicUrl;
					}

					if (res.message) {
						_this.$toast.show({
							text: res.message
						});
					} else {
						// 登录成功
						var user = res.responseObj.body.member;
						sessionStorage.setItem('user', JSON.stringify(user));
						sessionStorage.setItem('sessionId', res.extJsonObj.jsessionid);
						changePage('/home/goHomePage');
					}

					_this.$loading.hide();
				}).catch(function (error) {
					_this.$toast.show({
						text: error.message
					});
					_this.$loading.hide();
				});
				return false;
			}

			// 普通登录
			if (this.tabValue == 2) {
				// 手机号必填
				if (!this.user.phone) {
					this.$toast.show({
						text: '手机号为空'
					});
					return false;
				}

				// 短信验证码必填
				if (!this.user.phoneCode) {
					this.$toast.show({
						text: '短信验证码为空'
					});
					return false;
				}

				// 登录前，必要获取了手机验证码
				if (!this.phoneCode.isClick) {
					this.$toast.show({
						text: '请先获取手机验证码'
					});
					return false;
				}

				if (!isPhone(this.user.phone)) {
					this.$toast.show({
						text: '手机号不正确'
					});
					return false;
				}

				this.$loading.show();

				loginService.commonLogin({
					phone: this.user.phone,
					verifyCode: this.user.phoneCode
				}).then(function (res) {
					_this.$loading.hide();
					if (res.message) {
						_this.$toast.show({
							text: res.message
						});
					} else {
						var user = res.responseObj.body.member;
						sessionStorage.setItem('user', JSON.stringify(user));
						sessionStorage.setItem('sessionId', res.extJsonObj.jsessionid);
						changePage('/home/goHomePage');
					}
				}).catch(function (error) {
					_this.$loading.hide();
				});
			}
		},
		getPhoneCode: function getPhoneCode() {
			var _this2 = this;

			if (!this.user.phone) {
				this.$toast.show({
					text: '手机号为空'
				});
				return false;
			}

			if (!isPhone(this.user.phone)) {
				this.$toast.show({
					text: '手机号不正确'
				});
				return false;
			}
			if (this.phoneCode.status) {
				this.phoneCode.status = false;
				var interval = setInterval(function () {
					_this2.phoneCode.time--;

					if (_this2.phoneCode.time < 1) {
						_this2.phoneCode.status = true;
						_this2.phoneCode.time = 60;
						_this2.phoneCode.text = '重新获取验证码';
						clearInterval(interval);
						return false;
					}

					_this2.phoneCode.text = _this2.phoneCode.time + '秒后重新发送';
				}, 1000);
			} else {
				this.$toast.show({
					text: '验证码已发送，请稍后重试'
				});
				return false;
			}

			codeService.getPhoneCode({
				mobile: this.user.phone,
				verifyCode: this.user.commonImgCode
			}).then(function (res) {

				_this2.phoneCode.isClick = true;

				_this2.$toast.show({
					text: '验证码已成功发送至' + _this2.user.phone
				});
			}).catch(function (error) {
				_this2.$toast.show({
					text: '由于运营商/网络等原因，短信可能有延迟，请耐心等待，注意查收。'
				});
			});
		},
		getCodeImg: function getCodeImg(type) {
			var _this3 = this;

			this.$loading.show();
			codeService.getImgCode().then(function (res) {
				_this3.$loading.hide();

				if (type == 1) {
					_this3.codeImg = res.body.picCodeUrl + '&r=' + Math.ceil(Math.random() * 10);
				}

				if (type == 2) {
					_this3.commonImgCode = res.body.picCodeUrl + '&r=' + Math.ceil(Math.random() * 10);
				}
			}).catch(function (error) {
				_this3.$loading.hide();
				_this3.$toast.show({
					text: error.message
				});
			});
		},
		back: function back() {
			window.history.go(-1);
		}
	}
});

})));
